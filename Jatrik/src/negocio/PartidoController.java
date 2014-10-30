package negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.*;

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

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PartidoController implements IPartidoController
{

	/* Constantes */
	static final Integer MIN_JUGADAS_PARTIDO  = 10;
	static final Integer MAX_JUGADAS_PARTIDO  = 20;
	static final Float CONST_GOL 			  = (float) 0.9;  // Probabilidad m�nima para que haya gol
	static final Float CONST_TARJETA		  = (float) 0.4;  // Probabilidad m�nima para que haya tarjeta
	static final Float CONST_TARJETA_AMARILLA = (float) 0.75; // Probabilidad m�xima para que sea tarjeta amarilla
	static final Float CONST_MIN_LESION       = (float) 0.65; // Probabilidad m�nima para que sea lesi�n
	static final Float CONST_MAX_LESION       = (float) 0.85; // Probabilidad m�xima para que sea lesi�n
	
	static final String CONST_DELANTERO     = "delantero";
	static final String CONST_MEDIOCAMPISTA = "mediocampista";
	static final String CONST_DEFENSA 		= "defensa";
	static final String CONST_PORTERO 		= "portero";
	static final String CONST_TITULAR		= "titular";
	static final String CONST_EXPULSADO		= "expulsado";
	
	
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

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataResumenPartido simularPartido(String idPartido)
	{
		// Obtener el partido que vamos a simular
		Partido partido = partidoDAO.getPartido(idPartido);
		
		// Seleccionar aleatoriamente la cantidad de jugadas del partido
		int cantidad_jugadas = (int) (Math.random() * (MAX_JUGADAS_PARTIDO - MIN_JUGADAS_PARTIDO + 1) + MIN_JUGADAS_PARTIDO);
		
		boolean es_local = false;
		float penalizacionLocal = 0;
		float penalizacionVisitante = 0;
		
		int tarjetasAmarillasLocal		= 0;
		int tarjetasAmarillasVisitante  = 0; 
		int tarjetasRojasLocal			= 0;
		int tarjetasRojasVisitante		= 0;
		int golesLocal					= 0;
		int golesVisitante				= 0;
		int lesionesLocal				= 0;
		int lesionesVisitante			= 0;
		
		// De 0 a cantidad_jugadas
		for (int i = 0; i < cantidad_jugadas; i++)
		{
			
			// Elijo el equipo que va a realizar la jugada
			// Se hace 50 y 50.
			float equipoJugada = (float) Math.random(); // Este random genera un n�mero aleatorio entre 0 y 1.
			
			// Si la probabilidad es menor o igual a 0.5 => Asumimos que la jugada es realizada por el equipo local
			Equipo equipo = null;
			Equipo equipoContrario = null;
			ArrayList<DataCambio> cambiosProgramados = null;
			if (equipoJugada <= 0.5)
			{
				es_local = true;
				equipo = partido.getEquipoLocal();
				equipoContrario = partido.getEquipoVisitante();
				// Obtener los cambios programados
				cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosLocal();
			}
			else
			{
				es_local = false;
				equipo = partido.getEquipoVisitante();
				equipoContrario = partido.getEquipoLocal();
				// Obtener los cambios programados
				cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosVisitante();
			}
			String nombreEquipo = equipo.getEquipo();
			
			// Realizar cambios antes de jugar
			realizarCambiosEnEquipo(nombreEquipo, cambiosProgramados);
			
			List<Jugador> jugadores = (List<Jugador>) equipo.getJugadores();
			List<Jugador> jugadoresContrarios = (List<Jugador>) equipoContrario.getJugadores();
			
			// Calcular la probabilidad de jugada de gol
			float probJugadaGol = 0;
			if (es_local)
			{
				probJugadaGol = calcularProbabilidadJugadaGol(jugadores, penalizacionLocal);				
			}
			else
			{
				probJugadaGol = calcularProbabilidadJugadaGol(jugadores, penalizacionVisitante);				
			}
			System.out.println("*** PROB. JUG. GOL ***");
			String local_visitante = "visitante";
			if (es_local)
			{
				local_visitante = "local";
			}
			System.out.println("Equipo " + local_visitante + " - Probabilidad de jugada de gol: " + probJugadaGol + "\n");
			System.out.println("**********************");
			
			// Calcular la probabilidad de gol para la jugada
			String[] prob_gol = calcularProbabilidadGol(jugadores, probJugadaGol);
			float probGolParaJugada = Float.parseFloat(prob_gol[0]);
			String tipoJugador = prob_gol[1];
			
			System.out.println("*** PROB. GOL ***");
			local_visitante = "visitante";
			if (es_local)
			{
				local_visitante = "local";
			}
			System.out.println("Equipo " + local_visitante + " - Probabilidad de gol: " + probGolParaJugada + "\n");
			System.out.println("****************");
			
			// Calcular la probabilidad de tarjeta roja/amarilla
			float probTarjeta = calcularProbabilidadTarjeta(jugadoresContrarios, probGolParaJugada);
			System.out.println("*** PROB. TARJETA ***");
			local_visitante = "visitante";
			if (es_local)
			{
				local_visitante = "local";
			}
			System.out.println("Equipo " + local_visitante + " - Probabilidad de tarjeta: " + probTarjeta + "\n");
			System.out.println("********************");
			
			// Verificar si hubo lesi�n
			boolean lesion = huboLesion(probTarjeta);
			if (lesion)
			{
				realizarCambiosEnEquipo(nombreEquipo, cambiosProgramados);
			}
			
			// Actualizo los valores de la jugada
			if (es_local)
			{
				if (probGolParaJugada >= CONST_GOL)
				{
					golesLocal++;
				}
				
				// Hubo tarjeta
				if (probTarjeta >= CONST_TARJETA)
				{
					// Calcular el jugador que recibi� la tarjeta y hacer el c�lculo de tarjetas que lleva - Es un jugador del equipo contrario
					Jugador j = getJugadorTarjeta(jugadoresContrarios, tipoJugador);
					if(probTarjeta <= CONST_TARJETA_AMARILLA)
					{
						// Las tarjetas van sobre el equipo contrario
						tarjetasAmarillasVisitante++;
						jugadorDAO.sumarTarjetaAmarilla(j.getIdJugador());
						if (jugadorDAO.getCantidadTarjetasAmarillas(j.getIdJugador()) == 2)
						{
							penalizacionVisitante += (float) 0.1;
							jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), CONST_EXPULSADO);
						}
					}
					else
					{
						tarjetasRojasVisitante++;
						penalizacionVisitante += (float) 0.1;
						jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), CONST_EXPULSADO);
					}
				}
				
				if (lesion)
				{
					lesionesLocal++;
				}
				
			}
			else
			{
				if (probGolParaJugada >= CONST_GOL)
				{
					golesVisitante++;
				}
				
				if (probTarjeta >= CONST_TARJETA)
				{
					// Calcular el jugador que recibi� la tarjeta y hacer el c�lculo de tarjetas que lleva - Es un jugador del equipo contrario
					Jugador j = getJugadorTarjeta(jugadoresContrarios, tipoJugador);
					if(probTarjeta <= CONST_TARJETA_AMARILLA)
					{
						// Las tarjetas van sobre el equipo contrario
						tarjetasAmarillasLocal++;
						jugadorDAO.sumarTarjetaAmarilla(j.getIdJugador());
						if (jugadorDAO.getCantidadTarjetasAmarillas(j.getIdJugador()) == 2)
						{
							penalizacionLocal += (float) 0.1;
							jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), CONST_EXPULSADO);
						}
					}
					else
					{
						tarjetasRojasLocal++;
						penalizacionLocal += (float) 0.1;
						jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), CONST_EXPULSADO);
					}
				}

				if (lesion)
				{
					lesionesVisitante++;
				}
				
			}
		}
		
		// Restauro los atributos de los jugadores (cant_tarjetas_amarillas y �estado_jugador?)
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoLocal().getEquipo());
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoVisitante().getEquipo());
		
		// Retornar resultado
		DataResumenPartido resumenPartido = new DataResumenPartido(tarjetasAmarillasLocal, tarjetasAmarillasVisitante, 
																   tarjetasRojasLocal, tarjetasRojasVisitante,
																   golesLocal,golesVisitante,
																   lesionesLocal, lesionesVisitante);
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
            if (j.getEstado_jugador().equals(CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(CONST_DELANTERO))
	            {
	            	RegateATs += j.getTecnica();
	            	cant_delanteros_y_mediocampistas++;
	            }
	            else if (posicion.equals(CONST_MEDIOCAMPISTA))
	            {
	            	RegateMEDs   += j.getTecnica();
	                PotenciaMEDs += j.getAtaque();
	                cant_delanteros_y_mediocampistas++;
	                cant_mediocampistas_y_defensas++;
	            }
	            else if (posicion.equals(CONST_DEFENSA))
	            {
	            	PotenciaDEFs += j.getAtaque();
	            	cant_mediocampistas_y_defensas++;
	            }
            }
        }
        
		// probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) � Promedio(PotenciaMEDs+PotenciaDEFs))/100
		float probJugadaGol = (((RegateATs + RegateMEDs) / cant_delanteros_y_mediocampistas) -
							   ((PotenciaMEDs + PotenciaDEFs) / cant_mediocampistas_y_defensas)) / 100; 
		
		return (probJugadaGol * (1 - penalizacion));
	}
	
	private String[] calcularProbabilidadGol(List<Jugador> jugadores, float probJugadaGol)
	{
		// Tiro de un jugador
		float tipo_jugador = (float) Math.random(); // Este random genera un n�mero aleatorio entre 0 y 1.
		Jugador jugador_realiza_tiro = null;
		// Probabilidad que salga un delantero: 60%
		if (tipo_jugador <= 0.60)
		{
			// Es un Delantero
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, CONST_DELANTERO);
		}
		// Probabilidad que salga un mediocampista: 30%
		else if (tipo_jugador <= 0.90)
		{
			// Es un Mediocampista
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, CONST_MEDIOCAMPISTA);
		}
		// Probabilidad que salga un defensa: 10%
		else
		{
			// Es un Defensa
			jugador_realiza_tiro = getJugadorPatearGol(jugadores, CONST_DEFENSA);
		}
		float tiro_jugador = jugador_realiza_tiro.getAtaque();
		
		// Habilidad del portero
		float habilidad_portero = 0;
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if (j.getEstado_jugador().equals(CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(CONST_PORTERO))
	            {
	            	habilidad_portero = j.getPorteria();
	            	break;
	            }
            }
        }
		
		// Factores aleatorios
		float factor_ataque  = (float) Math.random()*10;	/******** Ajustar estos par�metros ***********/
		float factor_portero = (float) Math.random()*10;
		
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
            if (j.getEstado_jugador().equals(CONST_TITULAR))
            {
	            String posicion = j.getPosicion();
	            if (posicion.equals(CONST_MEDIOCAMPISTA))
	            {
	                PotenciaMEDs += j.getAtaque();
	                cant_mediocampistas_y_defensas++;
	            }
	            else if (posicion.equals(CONST_DEFENSA))
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
		if ((probTarjeta >= CONST_MIN_LESION) && (probTarjeta <= CONST_MAX_LESION))
		{
			return true;
		}
		return false;
	}
	
	private void realizarCambiosEnEquipo(String nombreEquipo, ArrayList<DataCambio> cambiosProgramados)
	{
		
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
	// tipoJugador se refiere al tipo del jugador que est� tratando de hacer el gol
	private Jugador getJugadorTarjeta(List<Jugador> jugadores, String tipoJugador)
	{
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
			if (j.getEstado_jugador().equals(CONST_TITULAR))
	        {
	            String posicion = j.getPosicion();
	            if ((tipoJugador.equals(CONST_DELANTERO) && posicion.equals(CONST_DEFENSA)) ||
	            	(tipoJugador.equals(CONST_MEDIOCAMPISTA) && posicion.equals(CONST_MEDIOCAMPISTA)) ||
	                (tipoJugador.equals(CONST_DEFENSA) && posicion.equals(CONST_DELANTERO)))
	            {
	            	return j;
	            }
	        }
		}
		return null;
	}
	
}
