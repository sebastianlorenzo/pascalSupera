package negocio;

import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.*;
import dominio.Equipo;
import dominio.Jugador;
import dominio.Partido;
import persistencia.EquipoDAO;
import persistencia.PartidoDAO;
import tipos.DataCambio;
import tipos.DataResumenPartido;

public class PartidoController implements IPartidoController
{

	/* Constantes */
	public static Integer MIN_JUGADAS_PARTIDO = 10;
	public static Integer MAX_JUGADAS_PARTIDO = 20;
	
	@EJB
	private PartidoDAO partidoDAO;

	@EJB
	private EquipoDAO equipoDAO;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataResumenPartido simularPartido(String idPartido)
	{
		// Obtengo el partido que quiero simular
		Partido partido = partidoDAO.getPartido(idPartido);
		
		// Seleccionar aleatoriamente la cantidad de jugadas del partido
		int cantidad_jugadas = (int) (Math.random() * (MAX_JUGADAS_PARTIDO - MIN_JUGADAS_PARTIDO + 1) + MIN_JUGADAS_PARTIDO);
		
		// De 0 a cantidad_jugadas
		for (int i = 0; i < cantidad_jugadas; i++)
		{
			// Elijo el equipo que va a realizar la jugada
			// Se hace 50 y 50.
			float equipoJugada = (float) Math.random(); // Este random genera un número aleatorio entre 0 y 1.
			
			// Si la probabilidad es menor o igual a 0.5 => Asumimos que la jugada es realizada por el equipo local
			Equipo equipo = null;
			if (equipoJugada <= 0.5)
			{
				equipo = partido.getEquipoLocal();
				// Obtener los cambios programados
				ArrayList<DataCambio> cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosLocal();
			}
			else
			{
				equipo = partido.getEquipoVisitante();
				// Obtener los cambios programados
				ArrayList<DataCambio> cambiosProgramados = (ArrayList<DataCambio>) partido.getCambiosVisitante();
			}
			
			// Calcular la probabilidad de jugada de gol
			String nombreEquipo = equipo.getEquipo();
			float probJugadaGol = calcularProbabilidadJugadaGol(nombreEquipo);
			
			// Calcular la probabilidad de gol para la jugada
			float probGolParaJugada = calcularProbabilidadGol(probJugadaGol);
			
			// Calcular la probabilidad de tarjeta roja/amarilla
			
			// Si hubo tarjeta => Calcular la posibilidad de lesión con mayor probabilidad
			
			// Sino => Calcular la posibilidad de lesión con menor probabilidad
			
			// En cualquiera de los dos casos, si hubo lesión, realizar cambio.
			
		}
		
		// Retornar resultado
		DataResumenPartido resumenPartido = null;
		return resumenPartido;
	}
	
	private float calcularProbabilidadJugadaGol(String nombreEquipo)
	{
		// RegateATs = Sumatoria de la habilidad de regate de todos los delanteros
		float RegateATs = 0;
		ArrayList<Jugador> delanteros = (ArrayList<Jugador>) equipoDAO.getJugadoresDelanterosEquipo(nombreEquipo);
		Iterator<Jugador> itD = delanteros.iterator();
        while(itD.hasNext()) 
        {
            Jugador j = itD.next();
            RegateATs += j.getTecnica();
        }

		// RegateMEDs   = Sumatoria de la habilidad de regate de todos los mediocampistas
		// PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
		float RegateMEDs   = 0;
		float PotenciaMEDs = 0;
		ArrayList<Jugador> mediocampistas = (ArrayList<Jugador>) equipoDAO.getJugadoresMediocampistasEquipo(nombreEquipo);
		Iterator<Jugador> itM = mediocampistas.iterator();
        while(itM.hasNext()) 
        {
            Jugador j = itM.next();
            RegateMEDs   += j.getTecnica();
            PotenciaMEDs += j.getAtaque();
        }

		// PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
		float PotenciaDEFs = 0;
		ArrayList<Jugador> defensas = (ArrayList<Jugador>) equipoDAO.getJugadoresDefensasEquipo(nombreEquipo);
		Iterator<Jugador> itDef = defensas.iterator();
        while(itDef.hasNext()) 
        {
            Jugador j = itDef.next();
            PotenciaDEFs += j.getAtaque();
        }

		// probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) – Promedio(PotenciaMEDs+PotenciaDEFs))/100
		float probJugadaGol = (((RegateATs + RegateMEDs) / 2) - ((PotenciaMEDs + PotenciaDEFs) / 2)) / 100; 
		
		return probJugadaGol;
	}
	
	private float calcularProbabilidadGol(float probJugadaGol)
	{
		// probabilidad de gol = ((probabilidad de jugada gol) x (tiro de un jugador*factor_aleatorio_ataque habilidad del portero*factor_aleatorio_portero))/100
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
		float probGol = 0;		
		
		return probGol;
	}
}
