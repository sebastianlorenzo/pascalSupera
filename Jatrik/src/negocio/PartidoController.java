package negocio;

import javax.ejb.*;

import org.hibernate.mapping.Collection;

import dominio.Equipo;
import dominio.Partido;
import persistencia.PartidoDAO;
import tipos.DataResumenPartido;

public class PartidoController implements IPartidoController
{

	/* Constantes */
	public static Integer MIN_JUGADAS_PARTIDO = 10;
	public static Integer MAX_JUGADAS_PARTIDO = 20;
	
	@EJB
	private PartidoDAO partidoDAO;

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
			if (equipoJugada <= 0.5)
			{
				Equipo equipo = partido.getEquipoLocal();
				// Obtener los cambios programados
				Collection cambiosProgramados = (Collection) partido.getCambiosLocal();
			}
			else
			{
				Equipo equipo = partido.getEquipoVisitante();
				// Obtener los cambios programados
				Collection cambiosProgramados = (Collection) partido.getCambiosVisitante();
			}
			
			// Calcular la probabilidad de jugada de gol
			// 		probabildad de jugada de gol = (Promedio (RegateATs+RegateMEDs) – Promedio(PotenciaMEDs+PotenciaDEFs))/100
			// 		RegateATs = Sumatoria de la habilidad de regate de todos los delanteros
			// 		RegateMEDs = Sumatoria de la habilidad de regate de todos los mediocampistas
			// 		PotenciaDEFs = Sumatoria de la habilidad de potencia de todos los defensas
			// 		PotenciaMEDs = Sumatoria de la habilidad de potencia de todos los mediocampistas
			float probabilidad_de_jugada_de_gol; 
			
			// Calcular la probabilidad de gol para la jugada
			
			// Calcular la probabilidad de tarjeta roja/amarilla
			
			// Si hubo tarjeta => Calcular la posibilidad de lesión con mayor probabilidad
			
			// Sino => Calcular la posibilidad de lesión con menor probabilidad
			
			// En cualquiera de los dos casos, si hubo lesión, realizar cambio.
			
		}
		
		// Retornar resultado
		DataResumenPartido resumenPartido = null;
		return resumenPartido;
	}
}
