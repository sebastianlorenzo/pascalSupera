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
import persistencia.PartidoDAO;
import persistencia.PartidoDAOImpl;
import tipos.DataCambio;
import tipos.DataResumenPartido;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PartidoController implements IPartidoController
{

	/* Constantes */
	static final Integer MIN_JUGADAS_PARTIDO = 10;
	static final Integer MAX_JUGADAS_PARTIDO = 20;
	static final Float CONST_GOL 			 = (float) 0.9;
	
	static final String CONST_DELANTERO     = "delantero";
	static final String CONST_MEDIOCAMPISTA = "mediocampista";
	static final String CONST_DEFENSA 		= "defensa";
	static final String CONST_PORTERO 		= "portero";
	static final String CONST_TITULAR		= "titular";
	
	
	@EJB
	private PartidoDAO partidoDAO;

	@EJB
	private EquipoDAO equipoDAO;
		
	public PartidoController()
	{
		this.partidoDAO = new PartidoDAOImpl();
		this.equipoDAO = new EquipoDAOImpl();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataResumenPartido simularPartido(String idPartido)
	{
		// Obtener el partido que vamos a simular
		Partido partido = partidoDAO.getPartido(idPartido);
		
		// Seleccionar aleatoriamente la cantidad de jugadas del partido
		int cantidad_jugadas = (int) (Math.random() * (MAX_JUGADAS_PARTIDO - MIN_JUGADAS_PARTIDO + 1) + MIN_JUGADAS_PARTIDO);
		
		boolean es_local = false;

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
			float equipoJugada = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
			
			// Si la probabilidad es menor o igual a 0.5 => Asumimos que la jugada es realizada por el equipo local
			Equipo equipo = null;
			ArrayList<DataCambio> cambiosProgramados = null;
			if (equipoJugada <= 0.5)
			{
				es_local = true;
				equipo = partido.getEquipoLocal();
				// Obtener los cambios programados
				cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosLocal();
			}
			else
			{
				es_local = false;
				equipo = partido.getEquipoVisitante();
				// Obtener los cambios programados
				cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosVisitante();
			}
			String nombreEquipo = equipo.getEquipo(); 
			
			// Realizar cambios antes de jugar
			realizarCambiosEnEquipo(nombreEquipo, cambiosProgramados);
			
			List<Jugador> jugadores = (List<Jugador>) equipo.getJugadores();
			
			// Calcular la probabilidad de jugada de gol
			float probJugadaGol = calcularProbabilidadJugadaGol(jugadores);
			System.out.println("*** PROB. JUG. GOL ***");
			String local_visitante = "visitante";
			if (es_local)
			{
				local_visitante = "local";
			}
			System.out.println("Equipo " + local_visitante + " - Probabilidad de jugada de gol: " + probJugadaGol + "\n");
			System.out.println("**********************");
			
			// Calcular la probabilidad de gol para la jugada
			float probGolParaJugada = calcularProbabilidadGol(jugadores, probJugadaGol);			
			System.out.println("*** PROB. GOL ***");
			local_visitante = "visitante";
			if (es_local)
			{
				local_visitante = "local";
			}
			System.out.println("Equipo " + local_visitante + " - Probabilidad de gol: " + probGolParaJugada + "\n");
			System.out.println("****************");
			
			// Calcular la probabilidad de tarjeta roja/amarilla
			float probTarjeta = calcularProbabilidadTarjeta(jugadores, probGolParaJugada);
			
			// Si hubo tarjeta => Calcular la posibilidad de lesión con mayor probabilidad
			// Sino 		   => Calcular la posibilidad de lesión con menor probabilidad
			float probLesion = calcularProbabilidadLesion(probTarjeta);
			
			// En cualquiera de los dos casos, si hubo lesión, realizar cambio.
			if (probLesion == 1)
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
			}
			else
			{
				if (probGolParaJugada >= CONST_GOL)
				{
					golesVisitante++;
				}
			}
		}
		
		// Retornar resultado
		DataResumenPartido resumenPartido = new DataResumenPartido(tarjetasAmarillasLocal, tarjetasAmarillasVisitante, 
																   tarjetasRojasLocal, tarjetasRojasVisitante,
																   golesLocal,golesVisitante,
																   lesionesLocal, lesionesVisitante);
		return resumenPartido;
	}
	
	private float calcularProbabilidadJugadaGol(List<Jugador> jugadores)
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
        
		// probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) – Promedio(PotenciaMEDs+PotenciaDEFs))/100
		float probJugadaGol = (((RegateATs + RegateMEDs) / cant_delanteros_y_mediocampistas) -
							   ((PotenciaMEDs + PotenciaDEFs) / cant_mediocampistas_y_defensas)) / 100; 
		
		return probJugadaGol;
	}
	
	private float calcularProbabilidadGol(List<Jugador> jugadores, float probJugadaGol)
	{
		/* 
		    El tiro a gol debe ser realizado por un único jugador, por lo cual es necesario definirlo. Dado que
			un delantero tiene mayor probabilidad de gol que un defensa o mediocampista, se definen las
			siguientes probabilidades para la selección del jugador:
			- probabilidad que salga un delantero: 60%
			- probabilidad que salga un mediocampista: 30%
			- probabilidad que salga un defensa: 10%
			Una vez determinado el tipo de jugador (atacante, mediocampista o defensa) que realiza el
			disparo, se debe determinar de alguna forma (puede ser aleatoria), cual de todos es el que
			efectivamente realiza el tiro a gol. La forma de determinar este jugador es libre a cada grupo.
		 */
		
		// Tiro de un jugador
		float tiro_jugador = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
tiro_jugador = 60; /********* Sacar este valor y poner el que va según la posición **********/
		if (tiro_jugador <= 0.60)
		{
			// Es un Delantero
		}
		else if (tiro_jugador <= 0.90)
		{
			// Es un Mediocampista
		}
		else
		{
			// Es un Defensa
		}
		
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
		float factor_ataque  = (float) Math.random()*10;	/******** Ajustar estos parámetros ***********/
		float factor_portero = (float) Math.random()*10;
		
		// Probabilidad de gol = ((probabilidad de jugada gol) x (tiro de un jugador*factor_aleatorio_ataque - habilidad del portero*factor_aleatorio_portero))/100
		float probGol = (probJugadaGol * (tiro_jugador * factor_ataque - habilidad_portero * factor_portero)) / 100;
		
		return probGol;
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
		float probTarjeta = probGolParaJugada * ((PotenciaMEDs + PotenciaDEFs) / cant_mediocampistas_y_defensas);
		
		return probTarjeta;
	}
	
	private float calcularProbabilidadLesion(float probTarjeta)
	{
		if (probTarjeta == 1)
		{
			return 1;
		}
		return 0;
	}
	
	private void realizarCambiosEnEquipo(String nombreEquipo, ArrayList<DataCambio> cambiosProgramados)
	{
		
	}
	
}
