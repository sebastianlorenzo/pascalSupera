package negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.*;
import org.codehaus.jettison.json.JSONArray;
import dominio.Cambio;
import dominio.Comentario;
import dominio.Equipo;
import dominio.Jugador;
import dominio.Partido;
import persistencia.EquipoDAO;
import persistencia.EquipoDAOImpl;
import persistencia.JugadorDAO;
import persistencia.JugadorDAOImpl;
import persistencia.PartidoDAO;
import persistencia.PartidoDAOImpl;
import persistencia.UsuarioDAO;
import persistencia.UsuarioDAOImpl;
import tipos.DataCambio;
import tipos.DataListaPartido;
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
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	
	public PartidoController()
	{
		this.partidoDAO = new PartidoDAOImpl();
		this.equipoDAO  = new EquipoDAOImpl();
		this.jugadorDAO = new JugadorDAOImpl();
		this.usuarioDAO = new UsuarioDAOImpl();
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
	public void simularPartido(String idPartido)
	{
		
		float[] penalizacion    = {0,0};
		int[] tarjetasAmarillas = {0,0};
		int[] tarjetasRojas	    = {0,0};
		int[] goles			    = {0,0};
		int[] lesiones		    = {0,0};
		
		/*** Obtener el partido que vamos a simular ***/
		Partido partido = partidoDAO.getPartido(idPartido);
		
		System.out.print("*************************\n");
		System.out.print("***** PARTIDO " + partido.getPartido() + " *****\n");
		System.out.print("*************************\n");
		
		/*** Hacer un "respaldo" de los jugadores para dejarlos en el mismo estado al finalizar el partido ***/
		List<Jugador> jugadoresLocal     = (List<Jugador>) partido.getEquipoLocal().getJugadores();
		List<Jugador> jugadoresVisitante = (List<Jugador>) partido.getEquipoVisitante().getJugadores();
		
		/*** Obtener los cambios programados ***/
		List<Cambio> cambiosProgramadosEquipoLocal     = (List<Cambio>) partidoDAO.getCambiosPartido(idPartido, true);
		List<Cambio> cambiosProgramadosEquipoVisitante = (List<Cambio>) partidoDAO.getCambiosPartido(idPartido, false);
		
		/*** Seleccionar aleatoriamente la cantidad de jugadas del partido ***/
		int cantidad_jugadas = (int) (Math.random() * (Constantes.MAX_JUGADAS_PARTIDO - Constantes.MIN_JUGADAS_PARTIDO + 1) + Constantes.MIN_JUGADAS_PARTIDO);
		
		/*** Inicializar la lista de comentarios ***/
		List <Comentario> comentarios = new ArrayList<Comentario>();
		
		/*** De 0 a cantidad_jugadas ***/
		for (int i = 0; i < cantidad_jugadas; i++)
		{
			/*** Elegir el equipo que va a realizar la jugada ***/
			// Si la probabilidad es menor o igual a 0.5 => Asumimos que la jugada es realizada por el equipo local
			float equipoJugada     = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
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
			
			/*** Realizar los cambios programados en ambos equipos antes de jugar ***/
			realizarCambiosEnEquipo(equipo.getEquipo(), cambiosProgramadosEquipoLocal, cantidad_jugadas, i, 0);
			realizarCambiosEnEquipo(equipoContrario.getEquipo(), cambiosProgramadosEquipoVisitante, cantidad_jugadas, i, 0);
			
			/*** Obtener los jugadores de ambos equipos ***/
			List<Jugador> jugadores           = (List<Jugador>) equipo.getJugadores();
			List<Jugador> jugadoresContrarios = (List<Jugador>) equipoContrario.getJugadores();
			
			/*** Calcular la probabilidad de jugada de gol ***/
			float probJugadaGol = calcularProbabilidadJugadaGol(jugadores, jugadoresContrarios, es_local ? penalizacion[0] : penalizacion[1]);
			
			/*** Calcular la probabilidad de gol para la jugada ***/
			String[] prob_gol = calcularProbabilidadGol(jugadores, jugadoresContrarios, probJugadaGol);
			float probGolParaJugada = Float.parseFloat(prob_gol[0]);
			String tipoJugador      = prob_gol[1];
			Integer idJugadorGol    = Integer.parseInt(prob_gol[2]);
			
			/*** Calcular la probabilidad de tarjeta roja/amarilla ***/
			float probTarjeta = calcularProbabilidadTarjeta(jugadoresContrarios, probGolParaJugada);
			
			/*** Verificar si hubo lesión ***/
			boolean lesion = huboLesion(probTarjeta);
			if (lesion)
			{
				realizarCambiosEnEquipo(equipo.getEquipo(), es_local ? cambiosProgramadosEquipoLocal : cambiosProgramadosEquipoVisitante, 0, i, idJugadorGol);
			}
			
			System.out.print("*************************\n");
			System.out.print("***** JUGADA NRO. " + i + " *****\n");
			System.out.print("*************************\n");
			System.out.print("***** Penalizacion: " + penalizacion[0] + " - " + penalizacion[1] + "\n");
			System.out.print("Equipo " + (es_local ? "local: " : "visitante: ") + equipo.getEquipo() + "\n");			
			System.out.print(" - Probabilidad de jugada de gol: " + probJugadaGol + "\n");
			System.out.print(" - Probabilidad de gol          : " + probGolParaJugada + "\n");
			System.out.print(" - Probabilidad de tarjeta      : " + probTarjeta + "\n");
						
			/*** Actualizar los valores de la jugada ***/
			int local_visitante = es_local ? 0 : 1;
			
			// Hubo gol
			String mensaje = "";
			if (probGolParaJugada >= Constantes.CONST_GOL)
			{
				goles[local_visitante]++;
				mensaje = "Gol de " + jugadorDAO.getNombreJugador(idJugadorGol) + " del equipo " + jugadorDAO.obtenerEquipo(idJugadorGol).getEquipo() + ".\n";
			}
			// Si no hubo gol, hacer algún comentario acerca de la jugada
			else
			{
				mensaje = getMensajeComentarioJugadaErrada(jugadorDAO.getRegateJugador(idJugadorGol));
			}
			Integer minuto = (i != 0) ? ((i * Constantes.CONST_DURACION_PARTIDO) / cantidad_jugadas) : 1;
			Comentario comentario = new Comentario(minuto, mensaje, partido);
			comentarios.add(comentario);
			System.out.print(" - " + mensaje);
			
			// Hubo tarjeta
			if (probTarjeta >= Constantes.CONST_TARJETA)
			{
				// Calcular el jugador que recibió la tarjeta y hacer el cálculo de tarjetas que lleva - Es un jugador del equipo contrario
				Jugador j = getJugadorTarjeta(jugadoresContrarios, tipoJugador);
				System.out.println("ANTES Jugador " + j.getJugador());
				System.out.println("ANTES Estado " + j.getEstado_jugador());
				if(probTarjeta <= Constantes.CONST_TARJETA_AMARILLA)
				{
					// Las tarjetas van sobre el equipo contrario
					tarjetasAmarillas[1 - local_visitante]++;
					jugadorDAO.sumarTarjetaAmarilla(j.getIdJugador());					
					mensaje = "Tarjeta amarilla para el jugador " + j.getJugador() + " del equipo " + jugadorDAO.obtenerEquipo(j.getIdJugador()).getEquipo() + ".";
					if (jugadorDAO.getCantidadTarjetasAmarillas(j.getIdJugador()) == 2)
					{
						penalizacion[1 - local_visitante] += (float) 0.1;
						jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), Constantes.CONST_EXPULSADO);
						mensaje += " El mismo ha sido expulsado del juego.";
					}
					mensaje += "\n";
				}
				else
				{
					tarjetasRojas[1 - local_visitante]++;
					penalizacion[1 - local_visitante] += (float) 0.1;
					jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), Constantes.CONST_EXPULSADO);
					mensaje = "Tarjeta roja para el jugador " + j.getJugador() + " del equipo " + jugadorDAO.obtenerEquipo(j.getIdJugador()).getEquipo() + ". El mismo ha sido expulsado del juego.\n";
				}
				minuto = (i != 0) ? ((i * Constantes.CONST_DURACION_PARTIDO) / cantidad_jugadas) : 1;
				comentario = new Comentario(minuto, mensaje, null);
				comentarios.add(comentario);
				System.out.print(" - " + mensaje);
				System.out.println("DESPUES Jugador " + j.getJugador());
				System.out.println("DESPUES Estado " + j.getEstado_jugador());
			}
			
			// Hubo lesión
			if (lesion)
			{
				lesiones[local_visitante]++;
				mensaje = "Se lesionó el jugador " + jugadorDAO.getNombreJugador(idJugadorGol) + " del equipo " + jugadorDAO.obtenerEquipo(idJugadorGol).getEquipo() + ".\n";
				minuto = (i != 0) ? ((i * Constantes.CONST_DURACION_PARTIDO) / cantidad_jugadas) : 1;
				comentario = new Comentario(minuto, mensaje, null);
				comentarios.add(comentario);
				System.out.print(" - " + mensaje);
			}
			System.out.print("\n");
			
		}
		
		/*** Restaurar los atributos de los jugadores (tarjetas amarillas y estado del jugador), ***/
		/*** así como también la cantidad de cambios realizados por el equipo. 					 ***/
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoLocal().getEquipo(), jugadoresLocal);
		equipoDAO.restablecerEquipoLuegoPartido(partido.getEquipoVisitante().getEquipo(), jugadoresVisitante);
		
		/*** Eliminar de la BD los cambios prestablecidos para el partido ***/
		partidoDAO.eliminarCambiosHechosDurantePartido(partido);
		
		/*** Enviar notificaciones a los equipos local y visitante ***/
		SimpleDateFormat formato_fecha = new SimpleDateFormat("dd--MM-yyyy hh:mm");
		String fecha_partido           = formato_fecha.format(partido.getFechaPartido());
		String nom_equipo_local        = partido.getEquipoLocal().getEquipo();
		String nom_equipo_visitante    = partido.getEquipoVisitante().getEquipo();
		
		String notificacionEquipoLocal     = ((goles[0] >  goles[1]) ? getMensajeGanadorPartido(fecha_partido, nom_equipo_visitante) : 
											 ((goles[0] == goles[1]) ? getMensajeEmpatePartido(fecha_partido, nom_equipo_visitante)  : 
											                           getMensajePerdedorPartido(fecha_partido, nom_equipo_local)))  + 
											 getMensajeResumenPartido(nom_equipo_local, nom_equipo_visitante, goles, tarjetasAmarillas, tarjetasRojas, lesiones);
		
		String notificacionEquipoVisitante = ((goles[1] >  goles[0]) ? getMensajeGanadorPartido(fecha_partido, nom_equipo_visitante) : 
										     ((goles[1] == goles[0]) ? getMensajeEmpatePartido(fecha_partido, nom_equipo_local)      : 
																	   getMensajePerdedorPartido(fecha_partido, nom_equipo_local)))  + 
											 getMensajeResumenPartido(nom_equipo_local, nom_equipo_visitante, goles, tarjetasAmarillas, tarjetasRojas, lesiones);
		
		usuarioDAO.enviarNotificacion(partido.getEquipoLocal().getUsuario().getLogin(), notificacionEquipoLocal);
		usuarioDAO.enviarNotificacion(partido.getEquipoVisitante().getUsuario().getLogin(), notificacionEquipoVisitante);
		
		/*** Guardar los resultados y comentarios del partido ***/
		partidoDAO.guardarResultadoPartido(tarjetasAmarillas, tarjetasRojas, goles, lesiones, partido, comentarios);
		System.out.println("Goles Local: " + goles[0]);
		System.out.println("Goles Visitante: " + goles[1]);
	}
	
	private float calcularProbabilidadJugadaGol(List<Jugador> jugadores, List<Jugador> jugadoresContrarios, float penalizacion)
	{
		// RegateATs    = Sumatoria de la habilidad de regate de todos los delanteros
		// RegateMEDs   = Sumatoria de la habilidad de regate de todos los mediocampistas
		// PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
		// PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
		float regate   = 0; // Regate   = RegateATs + RegateMEDs
		float potencia = 0; // Potencia = PotenciaMEDs + PotenciaDEFs
		
		int cant_delanteros_y_mediocampistas = 0;
		int cant_mediocampistas_y_defensas   = 0;
				
		/*** Calcular el regate como la suma de técnica y velocidad ***/
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext())
        {
            Jugador j = it.next();
            if ((j.getEstado_jugador().equals(Constantes.CONST_TITULAR)) && 
                ((j.getPosicion().equals(Constantes.CONST_DELANTERO)) || (j.getPosicion().equals(Constantes.CONST_MEDIOCAMPISTA))))
            {
	            regate += j.getTecnica() + j.getVelocidad();
            	cant_delanteros_y_mediocampistas++;
            }
        }
        
        /*** Calcular la potencia del equipo contrario como la suma de defensa y velocidad ***/ 
        it = jugadoresContrarios.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if ((j.getEstado_jugador().equals(Constantes.CONST_TITULAR)) &&
            	((j.getPosicion().equals(Constantes.CONST_MEDIOCAMPISTA)) || (j.getPosicion().equals(Constantes.CONST_DEFENSA))))
            {
	            potencia += j.getDefensa() + j.getVelocidad();
            	cant_mediocampistas_y_defensas++;
            }
        }
        
		/*** probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) – Promedio(PotenciaMEDs+PotenciaDEFs))/100 ***/
		float probJugadaGol = ((regate / cant_delanteros_y_mediocampistas) - (potencia / cant_mediocampistas_y_defensas)) / 100; 
		
		return (probJugadaGol * (1 - penalizacion));
	}
	
	private String[] calcularProbabilidadGol(List<Jugador> jugadores, List<Jugador> jugadoresContrarios, float probJugadaGol)
	{
		/*** Tiro de un jugador ***/
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
		
		/*** Habilidad del portero del equipo contrario ***/
		float habilidad_portero = 0;
		Iterator<Jugador> it = jugadoresContrarios.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if ((j.getEstado_jugador().equals(Constantes.CONST_TITULAR)) && (j.getPosicion().equals(Constantes.CONST_PORTERO)))
            {
            	habilidad_portero = j.getPorteria();
            	break;
            }
        }
		
		/*** Factores aleatorios ***/
		float factor_ataque  = (float) Math.random();
		float factor_portero = (float) Math.random();
		
		/*** Probabilidad de gol = ((probabilidad de jugada gol) x (tiro de un jugador*factor_aleatorio_ataque - habilidad del portero*factor_aleatorio_portero))/100 ***/
		float probGol = (probJugadaGol * ((tiro_jugador * factor_ataque) - (habilidad_portero * factor_portero))) / 100;
		
		String[] resultado = new String[3];
		resultado[0] = Float.toString(probGol);
		resultado[1] = jugador_realiza_tiro.getPosicion();
		resultado[2] = jugador_realiza_tiro.getIdJugador().toString();
		return resultado;
	}
	
	private float calcularProbabilidadTarjeta(List<Jugador> jugadoresContrarios, float probGolParaJugada)
	{
		// PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
		// PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
		float potencia = 0; // Potencia = PotenciaMEDs + PotenciaDEFs
		
		Integer cant_mediocampistas_y_defensas = 0;
		
		/*** Calcular la potencia del equipo contrario como la suma de defensa y velocidad ***/
		Iterator<Jugador> it = jugadoresContrarios.iterator();
        while(it.hasNext()) 
        {
            Jugador j = it.next();
            if ((j.getEstado_jugador().equals(Constantes.CONST_TITULAR)) &&
                ((j.getPosicion().equals(Constantes.CONST_MEDIOCAMPISTA)) || (j.getPosicion().equals(Constantes.CONST_DEFENSA))))
            {
	            potencia += j.getDefensa() + j.getVelocidad();
            	cant_mediocampistas_y_defensas++;
            }
        }
		
		/*** Probabilidad de tarjeta = Oportunidad de gol x (promedio potencia de jugadores defensores y mediocampistas) ***/
		float probTarjeta = (probGolParaJugada * (potencia / cant_mediocampistas_y_defensas))/100;
		
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
	
	private void realizarCambiosEnEquipo(String nombreEquipo, List<Cambio> cambiosProgramados, int cantidad_jugadas, int nro_jugada, int idJugadorLesionado)
	{
		/*** Si todavía no se realizó la máxima cantidad de cambios permitidos ***/ 
		if (equipoDAO.puedeRealizarCambios(nombreEquipo))
		{
			/*** Cambio por lesion ***/ 
			if (cantidad_jugadas == 0)
			{
				// Cambiar el estado del jugador a lesionado
				jugadorDAO.cambiarEstadoJugador(idJugadorLesionado, Constantes.CONST_LESIONADO);
				
				// Ver qué posición tiene el jugador que se lesionó
				String posicionLesionado = jugadorDAO.getPosicionJugador(idJugadorLesionado);
				
				// Si alguno de los cambios prestablecidos es de un jugador con esta posición => Hacer ese cambio
				Integer idJugadorEntrante = 0;
				Iterator<Cambio> it = cambiosProgramados.iterator();
				boolean encontre = false;
				while ((!encontre) && (it.hasNext()))
				{
					Cambio cambio = it.next();
					if (jugadorDAO.getPosicionJugador(cambio.getIdJugadorEntrante()) == posicionLesionado)
					{
						jugadorDAO.cambiarEstadoJugador(cambio.getIdJugadorEntrante(), Constantes.CONST_TITULAR);
						equipoDAO.sumarCambio(nombreEquipo);
						it.remove();
						encontre = true;
					}
				}
				// Sino, buscar otro jugador con esa posición
				if (!encontre)
				{
					Jugador j = getJugadorParaCambiarPorLesion((List<Jugador>) jugadorDAO.obtenerEquipo(idJugadorLesionado).getJugadores(), posicionLesionado);
					if (j != null)
					{
						idJugadorEntrante = j.getIdJugador();
						jugadorDAO.cambiarEstadoJugador(j.getIdJugador(), Constantes.CONST_TITULAR);
						equipoDAO.sumarCambio(nombreEquipo);
					}
				}
				
				System.out.print(" - Cambio por lesión en el equipo " + jugadorDAO.obtenerEquipo(idJugadorLesionado).getEquipo() + "\n");
				System.out.print("     Jugador que entra: " + idJugadorEntrante + "\n");
				System.out.print("     Jugador que sale: " + idJugadorLesionado + "\n\n");
			}
			/*** Cambio programado ***/
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
						it.remove();
						System.out.print(" - Cambio programado en el equipo " + jugadorDAO.obtenerEquipo(cambio.getIdJugadorEntrante()).getEquipo() + "\n");
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
			if ((j.getEstado_jugador().equals(Constantes.CONST_TITULAR)) && (j.getPosicion().equals(tipo_jugador)))
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
	
	private Jugador getJugadorParaCambiarPorLesion(List<Jugador> jugadores, String tipo_jugador)
	{
		Iterator<Jugador> it = jugadores.iterator();
		while (it.hasNext())
		{
			Jugador j = it.next();
			if ((j.getEstado_jugador().equals(Constantes.CONST_SUPLENTE)) && (j.getPosicion().equals(tipo_jugador)))
			{
				return j;
			}
		}
		return null;
	}
	
	private String getMensajeGanadorPartido(String fecha_partido, String nom_equipo)
	{
		return "FELICITACIONES! Ha ganado el partido jugado en la fecha " + fecha_partido + 
			   " contra el equipo " + nom_equipo + ".\n";
	}
	
	private String getMensajeEmpatePartido(String fecha_partido, String nom_equipo)
	{
		return "El partido jugado en la fecha " + fecha_partido + 
			   " contra el equipo " + nom_equipo + " ha finalizado con un empate.\n";
	}
	
	private String getMensajePerdedorPartido(String fecha_partido, String nom_equipo)
	{
		return "El partido jugado en la fecha " + fecha_partido + 
			   " contra el equipo " + nom_equipo + " ha finalizado. Lamentablemente, ha sido derrotado.\n";
	}
	
	private String getMensajeResumenPartido(String nom_equipo_local, String nom_equipo_visitante, int[] goles, int[] tarjetasAmarillas, int[] tarjetasRojas, int[] lesiones)
	{
		return "\nHa continuación se muestra el resumen del partido:\n\t"
			     + nom_equipo_local     + "\n\t\tGoles: "          + goles[0]         + "\n\t\tTarjetas Amarillas: " + tarjetasAmarillas[0] 
			 				            + "\n\t\tTarjetas Rojas: " + tarjetasRojas[0] + "\n\t\tLesiones: "           + lesiones[0]          + "\n\n\t"
			 	 + nom_equipo_visitante + "\n\t\tGoles: "          + goles[1]         + "\n\t\tTarjetas Amarillas: " + tarjetasAmarillas[1]
	 				                    + "\n\t\tTarjetas Rojas: " + tarjetasRojas[1] + "\n\t\tLesiones: "           + lesiones[1]          + "\n";
	}
	
	private String getMensajeComentarioJugadaErrada(Integer regateJugador)
	{
		int nro_mensaje = (int) (Math.random() * (Constantes.MAX_CANT_COMENTARIOS - Constantes.MIN_CANT_COMENTARIOS + 1) + Constantes.MIN_CANT_COMENTARIOS);
		if (regateJugador <= Constantes.CONST_REGATE_BAJO)
		{
			return Constantes.CONST_MENSAJES_REGATE_BAJO[nro_mensaje];
		}
		else if (regateJugador <= Constantes.CONST_REGATE_MEDIO)
		{
			return Constantes.CONST_MENSAJES_REGATE_MEDIO[nro_mensaje];
		}
		else
		{
			return Constantes.CONST_MENSAJES_REGATE_ALTO[nro_mensaje];
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray obtenerPartidosPorZona(String nomCampeonato) 
	{
		return this.partidoDAO.obtenerPartidosLugar(nomCampeonato);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaPartido listarPartidosJugados(String nomCampeonato) 
	{
		return this.partidoDAO.listarJugados(nomCampeonato);
	}
	
	public List<Partido> listaPartidosSimular(Date fecha)
	{
		return partidoDAO.getPartidosSimular(fecha);
	}
	
}
