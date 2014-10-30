package negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.*;
import dominio.Cambio;
import dominio.Equipo;
import dominio.Jugador;
import dominio.Partido;
import persistencia.EquipoDAO;
import persistencia.EquipoDAOImpl;
import persistencia.JugadorDAO;
import persistencia.JugadorDAOImpl;
import persistencia.PartidoDAO;
import persistencia.PartidoDAOImpl;
import tipos.DataCambio;
import tipos.DataResumenPartido;
import tipos.Constantes;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PartidoController implements IPartidoController
{
	
	@EJB
	private PartidoDAO partidoDAO;

	@EJB
	private EquipoDAO equipoDAO;
	
	@EJB
	private JugadorDAO jugadorDAO;
		
	public PartidoController()
	{
		this.partidoDAO = new PartidoDAOImpl();
		this.equipoDAO  = new EquipoDAOImpl();
		this.jugadorDAO = new JugadorDAOImpl();
	}

	// Se asume que se mandan cambios para un equipo solo, no para los dos a la vez
	public void configurarCambiosPartido(String partido, DataCambio[] cambios)
	{
		List<Cambio> cambiosAgregar = new ArrayList<Cambio>();
		Partido p = partidoDAO.getPartido(partido);
		for (int i = 0; i < cambios.length; i++)
		{
			if (cambios[i] != null)
			{
				Cambio c = new Cambio(cambios[i].getIdJugadorEntrante(), cambios[i].getIdJugadorSaliente(), cambios[i].getMinutoCambio(), p);
				cambiosAgregar.add(c);
			}
		}
		
		boolean local = true;
		// Si es el equipo visitante
		if (jugadorDAO.obtenerEquipo(cambios[0].getIdJugadorEntrante()).getEquipo().equals(p.getEquipoVisitante().getEquipo()))
		{
			local = false;
		}
		partidoDAO.setearCambios(p, cambiosAgregar, local);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataResumenPartido simularPartido(String idPartido)
	{
		
		float[] penalizacion    = {0,0};
		int[] tarjetasAmarillas = {0,0};
		int[] tarjetasRojas	    = {0,0};
		int[] goles			    = {0,0};
		int[] lesiones		    = {0,0};
		
		// Obtener el partido que vamos a simular
		Partido partido = partidoDAO.getPartido(idPartido);
		
		// Obtener los cambios programados
		List<Cambio> cambiosProgramadosEquipoLocal = (List<Cambio>) partido.getCambiosLocal();
		List<Cambio> cambiosProgramadosEquipoVisitante = (List<Cambio>) partido.getCambiosVisitante();
		
		// Seleccionar aleatoriamente la cantidad de jugadas del partido
		int cantidad_jugadas = (int) (Math.random() * (Constantes.MAX_JUGADAS_PARTIDO - Constantes.MIN_JUGADAS_PARTIDO + 1) + Constantes.MIN_JUGADAS_PARTIDO);
		
		// De 0 a cantidad_jugadas
		for (int i = 0; i < cantidad_jugadas; i++)
		{
			// Elijo el equipo que va a realizar la jugada
			// Se hace 50 y 50.
			float equipoJugada = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
			
			// Si la probabilidad es menor o igual a 0.5 => Asumimos que la jugada es realizada por el equipo local
			boolean es_local       = true;
			Equipo equipo          = null;
			Equipo equipoContrario = null;
			
			if (equipoJugada <= 0.5)
			{
				es_local = true;
				equipo   = partido.getEquipoLocal();
				equipoContrario = partido.getEquipoVisitante();
			}
			else
			{
				es_local = false;
				equipo   = partido.getEquipoVisitante();
				equipoContrario = partido.getEquipoLocal();
			}
			
			// Realizar los cambios programados en ambos equipos antes de jugar
			realizarCambiosEnEquipo(equipo.getEquipo(), cambiosProgramadosEquipoLocal, cantidad_jugadas, i);
			realizarCambiosEnEquipo(equipoContrario.getEquipo(), cambiosProgramadosEquipoVisitante, cantidad_jugadas, i);
			
			// Obtengo los jugadores de ambos equipos
			List<Jugador> jugadores           = (List<Jugador>) equipo.getJugadores();
			List<Jugador> jugadoresContrarios = (List<Jugador>) equipoContrario.getJugadores();
			
			// Calcular la probabilidad de jugada de gol
			float probJugadaGol = 0;
			probJugadaGol = calcularProbabilidadJugadaGol(jugadores, es_local ? penalizacion[0] : penalizacion[1]);
			
			// Calcular la probabilidad de gol para la jugada
			String[] prob_gol = calcularProbabilidadGol(jugadores, probJugadaGol);
			float probGolParaJugada = Float.parseFloat(prob_gol[0]);
			String tipoJugador = prob_gol[1];
			
			// Calcular la probabilidad de tarjeta roja/amarilla
			float probTarjeta = calcularProbabilidadTarjeta(jugadoresContrarios, probGolParaJugada);
			
			// Verificar si hubo lesión
			boolean lesion = huboLesion(probTarjeta);
			if (lesion)
			{
				realizarCambiosEnEquipo(equipo.getEquipo(), es_local ? cambiosProgramadosEquipoLocal : cambiosProgramadosEquipoVisitante, 0, i);
			}
			

			System.out.print("*************************\n");
			System.out.print("***** JUGADA NRO. " + i + " *****\n");
			System.out.print("*************************\n");
			System.out.print("Equipo " + (es_local ? "local: " : "visitante: ") + equipo.getEquipo() + "\n");			
			System.out.print(" - Probabilidad de jugada de gol: " + probJugadaGol + "\n");
			System.out.print(" - Probabilidad de gol: " + probGolParaJugada + "\n");
			System.out.print(" - Probabilidad de tarjeta: " + probTarjeta + "\n");
			
			
			// Actualizo los valores de la jugada
			int local_visitante = es_local ? 0 : 1;
			
			// Hubo gol
			if (probGolParaJugada >= Constantes.CONST_GOL)
			{
				goles[local_visitante]++;
			}
			
			// Hubo tarjeta
			if (probTarjeta >= Constantes.CONST_TARJETA)
			{
				// Calcular el jugador que recibió la tarjeta y hacer el cálculo de tarjetas que lleva - Es un jugador del equipo contrario
				Jugador j = getJugadorTarjeta(jugadoresContrarios, tipoJugador);
				if(probTarjeta <= Constantes.CONST_TARJETA_AMARILLA)
				{
					// Las tarjetas van sobre el equipo contrario
					tarjetasAmarillas[1 - local_visitante]++;
					jugadorDAO.sumarTarjetaAmarilla(j.getIdJugador());
					System.out.print(" - Tarjeta amarilla al jugador " + j.getJugador() + " (id = " + j.getIdJugador() + ") del equipo contrario");
					if (jugadorDAO.getCantidadTarjetasAmarillas(j.getIdJugador()) == 2)
					{
						penalizacion[1 - local_visitante] += (float) 0.1;
						jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), Constantes.CONST_EXPULSADO);
						System.out.print(" => EXPULSADO");
					}
					System.out.print("\n");
				}
				else
				{
					tarjetasRojas[1 - local_visitante]++;
					penalizacion[1 - local_visitante] += (float) 0.1;
					jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), Constantes.CONST_EXPULSADO);
					System.out.print(" - Tarjeta roja al jugador " + j.getJugador() + " (id = " + j.getIdJugador() + ") del equipo contrario => EXPULSADO\n");
				}
			}
			System.out.print("\n");
			
			if (lesion)
			{
				lesiones[local_visitante]++;
			}
			
		}
		
		// Restauro los atributos de los jugadores (cant_tarjetas_amarillas y ¿estado_jugador?)
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoLocal().getEquipo());
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoVisitante().getEquipo());
		
		// Retornar resultado
		DataResumenPartido resumenPartido = new DataResumenPartido(tarjetasAmarillas[0], tarjetasAmarillas[1], 
																   tarjetasRojas[0], tarjetasRojas[1],
																   goles[0],goles[1],
																   lesiones[0], lesiones[1]);
		return resumenPartido;
	}
	
	private float calcularProbabilidadJugadaGol(List<Jugador> jugadores, float penalizacion)
	{
		// RegateATs    = Sumatoria de la habilidad de regate de todos los delanteros
		// RegateMEDs   = Sumatoria de la habilidad de regate de todos los mediocampistas
		// PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
		// PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
		float RegateATs    = 0;
		float RegateMEDs   = 0;
		float PotenciaMEDs = 0;
		float PotenciaDEFs = 0;
		
		Integer cant_delanteros_y_mediocampistas = 0;
		Integer cant_mediocampistas_y_defensas   = 0;
		
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if (j.getEstado_jugador().equals(Constantes.CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(Constantes.CONST_DELANTERO))
	            {
	            	RegateATs += j.getTecnica();
	            	cant_delanteros_y_mediocampistas++;
	            }
	            else if (posicion.equals(Constantes.CONST_MEDIOCAMPISTA))
	            {
	            	RegateMEDs   += j.getTecnica();
	                PotenciaMEDs += j.getAtaque();
	                cant_delanteros_y_mediocampistas++;
	                cant_mediocampistas_y_defensas++;
	            }
	            else if (posicion.equals(Constantes.CONST_DEFENSA))
	            {
	            	PotenciaDEFs += j.getAtaque();
	            	cant_mediocampistas_y_defensas++;
	            }
            }
        }
        
		// probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) – Promedio(PotenciaMEDs+PotenciaDEFs))/100
		float probJugadaGol = (((RegateATs + RegateMEDs) / cant_delanteros_y_mediocampistas) -
							   ((PotenciaMEDs + PotenciaDEFs) / cant_mediocampistas_y_defensas)) / 100; 
		
		return (probJugadaGol * (1 - penalizacion));
	}
	
	private String[] calcularProbabilidadGol(List<Jugador> jugadores, float probJugadaGol)
	{
		// Tiro de un jugador
		float tipo_jugador = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
		Jugador jugador_realiza_tiro = null;
		// Probabilidad que salga un delantero: 60%
		if (tipo_jugador <= 0.60)
		{
			// Es un Delantero
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, Constantes.CONST_DELANTERO);
		}
		// Probabilidad que salga un mediocampista: 30%
		else if (tipo_jugador <= 0.90)
		{
			// Es un Mediocampista
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, Constantes.CONST_MEDIOCAMPISTA);
		}
		// Probabilidad que salga un defensa: 10%
		else
		{
			// Es un Defensa
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, Constantes.CONST_DEFENSA);
		}
		float tiro_jugador = jugador_realiza_tiro.getAtaque();
		
		// Habilidad del portero
		float habilidad_portero = 0;
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if (j.getEstado_jugador().equals(Constantes.CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(Constantes.CONST_PORTERO))
	            {
	            	habilidad_portero = j.getPorteria();
	            	break;
	            }
            }
        }
		
		// Factores aleatorios
		float factor_ataque  = (float) Math.random();
		float factor_portero = (float) Math.random();
		
		// Probabilidad de gol = ((probabilidad de jugada gol) x (tiro de un jugador*factor_aleatorio_ataque - habilidad del portero*factor_aleatorio_portero))/100
		float probGol = (probJugadaGol * (tiro_jugador * factor_ataque - habilidad_portero * factor_portero)) / 100;
		
		String[] resultado = new String[2];
		resultado[0] = Float.toString(probGol);
		resultado[1] = jugador_realiza_tiro.getPosicion();
		return resultado;
	}
	
	private float calcularProbabilidadTarjeta(List<Jugador> jugadores, float probGolParaJugada)
	{
		// PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
		// PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
		float PotenciaMEDs = 0;
		float PotenciaDEFs = 0;
		
		Integer cant_mediocampistas_y_defensas = 0;
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if (j.getEstado_jugador().equals(Constantes.CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(Constantes.CONST_MEDIOCAMPISTA))
	            {
	                PotenciaMEDs += j.getAtaque();
	                cant_mediocampistas_y_defensas++;
	            }
	            else if (posicion.equals(Constantes.CONST_DEFENSA))
	            {
	            	PotenciaDEFs += j.getAtaque();
	            	cant_mediocampistas_y_defensas++;
	            }
            }
        }
		
		// Probabilidad de tarjeta = Oportunidad de gol x (promedio potencia de jugadores defensores y mediocampistas)
		float probTarjeta = probGolParaJugada * ((PotenciaMEDs + PotenciaDEFs) / cant_mediocampistas_y_defensas)/100;
		
		return probTarjeta;
	}
	
	private boolean huboLesion(float probTarjeta)
	{
		if ((probTarjeta >= Constantes.CONST_MIN_LESION) && (probTarjeta <= Constantes.CONST_MAX_LESION))
		{
			return true;
		}
		return false;
	}
	
	private void realizarCambiosEnEquipo(String nombreEquipo, List<Cambio> cambiosProgramados, int cantidad_jugadas, int nro_jugada)
	{	
		// Si todavía no se realizó la máxima cantidad de cambios permitidos 
		if (equipoDAO.puedeRealizarCambios(nombreEquipo))
		{
			// Cambio por lesion 
			if (cantidad_jugadas == 0)
			{
				
			}
			// Cambio programado
			else
			{
				Iterator<Cambio> it = cambiosProgramados.iterator();
				while (it.hasNext())
				{
					// Si este cambio se debe realizar ahora => lo hago y lo elimino de la lista de cambios
					Cambio cambio = it.next();
					int minuto_cambio = ((cantidad_jugadas * cambio.getMinutoCambio()) / Constantes.CONST_DURACION_PARTIDO);
					if (minuto_cambio == nro_jugada)
					{
						jugadorDAO.cambiarEstadoJugador(cambio.getIdJugadorEntrante(), Constantes.CONST_TITULAR);
						jugadorDAO.cambiarEstadoJugador(cambio.getIdJugadorSaliente(), Constantes.CONST_SUPLENTE);
						equipoDAO.sumarCambio(nombreEquipo);
						//cambiosProgramados.remove(cambio);
						System.out.print(" - Cambio\n");
						System.out.print("     Minuto: " + cambio.getMinutoCambio() + "\n");
						System.out.print("     Jugador que entra: " + cambio.getIdJugadorEntrante() + "\n");
						System.out.print("     Jugador que sale: " + cambio.getIdJugadorSaliente() + "\n\n");
					}
				}
			}
		}
	}
	
	private Jugador getJugadorPatearGol(List<Jugador> jugadores, String tipo_jugador)
	{
		Iterator<Jugador> it = jugadores.iterator();
		while (it.hasNext())
		{
			Jugador j = it.next();
			if (j.getPosicion().equals(tipo_jugador))
			{
				return j;
			}
		}
		return null;
	}
	
	// Retorna el jugador al que le voy a sacar tarjeta amarilla/roja
	// tipoJugador se refiere al tipo del jugador que está tratando de hacer el gol
	private Jugador getJugadorTarjeta(List<Jugador> jugadores, String tipoJugador)
	{
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
			if (j.getEstado_jugador().equals(Constantes.CONST_TITULAR))
	        {
	            String posicion = j.getPosicion();
	            if ((tipoJugador.equals(Constantes.CONST_DELANTERO) && posicion.equals(Constantes.CONST_DEFENSA)) ||
	            	(tipoJugador.equals(Constantes.CONST_MEDIOCAMPISTA) && posicion.equals(Constantes.CONST_MEDIOCAMPISTA)) ||
	                (tipoJugador.equals(Constantes.CONST_DEFENSA) && posicion.equals(Constantes.CONST_DELANTERO)))
	            {
	            	return j;
	            }
	        }
		}
		return null;
	}
	
}
