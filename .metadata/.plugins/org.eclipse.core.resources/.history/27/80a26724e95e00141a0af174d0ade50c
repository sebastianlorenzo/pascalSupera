package negocio;

import java.util.*;

import javax.ejb.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import persistencia.EquipoDAO;
import persistencia.EstadioDAO;
import persistencia.JugadorDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController
{
	
	static final int MAX_CAPACIDAD = 10000;
	static final int MAX_CANT_JUG = 2;
	
	@EJB
	private EquipoDAO equipoDAO;
	
	@EJB
	private EstadioDAO estadioDAO;
	
	@EJB
	private JugadorDAO jugadorDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEquipoRegistrado(String equipo) 
	{
		return this.equipoDAO.existeEquipo(equipo);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Equipo crearEquipo(String equipo, String pais, String localidad, String nomestadio)
	{
		Equipo e = new Equipo(equipo, pais, localidad);
		int capacidad = MAX_CAPACIDAD;
		Estadio estadio = new Estadio(nomestadio, capacidad);
		this.estadioDAO.insertarEstadio(estadio);
		e.setEstadio(estadio);
		
		ArrayList<Jugador> jugadores = this.jugadorDAO.obtenerJugadoresSinEquipo();
		Collection<Jugador> jug = new ArrayList<Jugador>();
		Iterator<Jugador> iter = jugadores.iterator();
		int cant = 1;
		while(iter.hasNext() && (cant<= MAX_CANT_JUG))
		{
			Jugador j = iter.next();
			jug.add(j);	
			cant++;
		}

		e.setJugadores(jug);
		
		this.equipoDAO.insertarEquipo(e);
		this.estadioDAO.setearEquipo(nomestadio,e);
		
		Iterator<Jugador> iterdos = jug.iterator();
		while(iterdos.hasNext())
		{
			Jugador j = iterdos.next();
			Integer idJugador = j.getIdJugador();
			this.jugadorDAO.setearEquipo(idJugador, e);
		}

		return e;
	}
		
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray obtenerPaisesInicial()
	{
		return this.equipoDAO.obtenerPaises();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEstadioRegistrado(String estadio) 
	{
		return this.estadioDAO.existeEstadio(estadio);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONArray obtenerEquipos() 
	{
		return this.equipoDAO.obtenerTodosEquipos();
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject obtenerZonaEquipo(String nomEquipo) 
	{
		return this.equipoDAO.obtenerLugarEquipo(nomEquipo);
	}
	
}
