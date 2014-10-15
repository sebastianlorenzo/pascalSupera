package negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.*;

import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import persistencia.EquipoDAO;
import persistencia.EstadioDAO;
import persistencia.JugadorDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController{
	
	static final int MAX_CAPACIDAD = 10000;
	
	@EJB
	private EquipoDAO equipoDAO;
	
	@EJB
	private EstadioDAO estadioDAO;
	
	@EJB
	private JugadorDAO jugadorDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean existeEquipoRegistrado(String equipo) {
		return this.equipoDAO.existeEquipo(equipo);
	}
	
	public Equipo crearEquipo(String equipo, String pais, String localidad, String nomestadio){
		
		Equipo e = new Equipo(equipo, pais, localidad);
		int capacidad = MAX_CAPACIDAD;
		Estadio estadio = new Estadio(nomestadio, capacidad);
		this.estadioDAO.insertarEstadio(estadio);
		e.setEstadio(estadio);
		
//poner un tope de jugadores
		ArrayList<Jugador> jugadores = this.jugadorDAO.obtenerJugadoresSinEquipo();
		Collection<Jugador> jug = new ArrayList<Jugador>();
		Iterator<Jugador> iter = jugadores.iterator();
		while(iter.hasNext()){
			Jugador j = iter.next();
			jug.add(j);		
		}
		e.setJugadores(jug);
		
		this.equipoDAO.insertarEquipo(e);
		estadio.setEquipo(e);
		
		Iterator<Jugador> iterdos = jug.iterator();
		while(iterdos.hasNext()){
			Jugador j = iterdos.next();
			j.setEquipo(e);	
		}

		return e;
	}
}
