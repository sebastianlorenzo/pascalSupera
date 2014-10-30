package negocio;

import java.util.*;

import javax.ejb.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import persistencia.EquipoDAO;
import persistencia.EstadioDAO;
import persistencia.JugadorDAO;
import tipos.DataJugador;
import tipos.DataListaCampeonato;
import tipos.DataListaEquipo;
import tipos.DataListaJugador;
import tipos.DataListaPosicion;
import tipos.DataPosicion;
import tipos.DataListaOferta;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController
{
	
	static final int MAX_CAPACIDAD = 10000;
	static final int MAX_ALTURA_ESTADIO = 4500;
	static final int MIN_ALTURA_ESTADIO = 0;
	
	static final int MAX_CANT_PORTEROS = 2;
	static final int MAX_CANT_DEFENSAS = 6;
	static final int MAX_CANT_MEDIOCAMPISTAS = 6;
	static final int MAX_CANT_DELANTEROS = 4;
	static final String CONST_TITULAR		= "titular";
	static final String CONST_SUPLENTE		= "suplente";
	
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
		int altura = (int) (Math.random() * (MAX_ALTURA_ESTADIO - MIN_ALTURA_ESTADIO + 1) + MIN_ALTURA_ESTADIO);
		Estadio estadio = new Estadio(nomestadio, capacidad, altura);
		this.estadioDAO.insertarEstadio(estadio);
		e.setEstadio(estadio);
		Collection<Jugador> jug = new ArrayList<Jugador>();
		
		ArrayList<Jugador> jugadoresPorteros = this.jugadorDAO.obtenerPorterosSinEquipo();
		ArrayList<Jugador> jugadoresDefensas = this.jugadorDAO.obtenerDefensasSinEquipo();
		ArrayList<Jugador> jugadoresMediocampistas = this.jugadorDAO.obtenerMediocampistasSinEquipo();
		ArrayList<Jugador> jugadoresDelanteros = this.jugadorDAO.obtenerDelanteroSinEquipo();
		
		Iterator<Jugador> iterPorteros = jugadoresPorteros.iterator();
		Iterator<Jugador> iterDefensas = jugadoresDefensas.iterator();
		Iterator<Jugador> iterMediocampistas = jugadoresMediocampistas.iterator();
		Iterator<Jugador> iterDelanteros = jugadoresDelanteros.iterator();
		
		int i = 0;
		while(iterPorteros.hasNext() && (i< MAX_CANT_PORTEROS)){
			Jugador j = iterPorteros.next();
			if (i == 0)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);
			i++;
		}
		
		i = 0;
		while(iterDefensas.hasNext() && (i< MAX_CANT_DEFENSAS)){
			Jugador j = iterDefensas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
			i++;
		}
		
		i = 0;
		while(iterMediocampistas.hasNext() && (i< MAX_CANT_MEDIOCAMPISTAS)){
			Jugador j = iterMediocampistas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);
			i++;
		}
		
		i = 0;
		while(iterDelanteros.hasNext() && (i< MAX_CANT_DELANTEROS)){
			Jugador j = iterDelanteros.next();
			if (i<2)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
			i++;
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
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject obtenerTactica(String equipo) 
	{
		Object[] res = this.equipoDAO.getTaticaEquipo(equipo);
		JSONObject jsonTactica = new JSONObject();
		try
		{
			jsonTactica.put("Defensa", res[0].toString());
			jsonTactica.put("Mediocampo", res[1].toString());
			jsonTactica.put("Ataque", res[2].toString());
        }
        catch(Exception ex)
		{
            ex.printStackTrace();
        }
		return jsonTactica;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa) 
	{
		this.equipoDAO.modificarTactica(equipo,ataque,mediocampo,defensa);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaEquipo obtenerEquiposData(String nomEquipo) 
	{
		return this.equipoDAO.equiposData(nomEquipo);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean realizarOferta(String nomUsuario, Integer idJugador, Integer precio, String comentario) 
	{	
		return this.equipoDAO.realizarOfertaJugador(nomUsuario, idJugador, precio, comentario);
		
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaOferta obtenerOfertasData(String nomUsuario) 
	{
		return this.equipoDAO.obtenerOfertas(nomUsuario);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String obtenerJugadoresTitulares(String nomEquipo) throws JSONException {
		Equipo e = this.equipoDAO.encontrarEquipo(nomEquipo);
		if (e == null){
			JSONObject j = new JSONObject();
			j.put("Error", true);
			return j.toString();
		}
		else{
			Collection<Jugador> jugadores = e.getJugadores();
			Iterator<Jugador> i = jugadores.iterator();
			DataListaJugador dlj = new DataListaJugador();
			while (i.hasNext()){
				Jugador j = i.next();
				if (j.getEstado_jugador().compareTo(CONST_TITULAR) == 0){
					DataJugador dj = new DataJugador(j.getIdJugador(), j.getJugador(), j.getPosicionIdeal(), j.getVelocidad(), j.getTecnica(), 
														j.getAtaque(), j.getDefensa(), j.getPorteria(), j.getEstado_jugador());
					dlj.addDataJugador(dj);
					
				}
			}
			Gson g = new Gson();
			return g.toJson(dlj);
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public String obtenerJugadoresSuplentes(String nomEquipo) throws JSONException {
		Equipo e = this.equipoDAO.encontrarEquipo(nomEquipo);
		if (e == null){
			JSONObject j = new JSONObject();
			j.put("Result", false);
			return j.toString();
		}
		else{
			Collection<Jugador> jugadores = e.getJugadores();
			Iterator<Jugador> i = jugadores.iterator();
			DataListaJugador dlj = new DataListaJugador();
			while (i.hasNext()){
				Jugador j = i.next();
				if (j.getEstado_jugador().compareTo(CONST_SUPLENTE) == 0){
					DataJugador dj = new DataJugador(j.getIdJugador(), j.getJugador(), j.getPosicionIdeal(), j.getVelocidad(), j.getTecnica(), 
														j.getAtaque(), j.getDefensa(), j.getPorteria(), j.getEstado_jugador());
					dlj.addDataJugador(dj);
					
				}
			}
			Gson g = new Gson();
			return g.toJson(dlj);
		}	
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Boolean modificarJugadoresTitulares(String nomEquipo, DataListaPosicion titulares) throws JSONException {
		Equipo e = this.equipoDAO.encontrarEquipo(nomEquipo);
		if (e == null){
			JSONObject j = new JSONObject();
			j.put("Result", false);
			return false;
		}
		else{
			Collection<Jugador> jugadores = e.getJugadores();
			Iterator<Jugador> i = jugadores.iterator();
			while (i.hasNext()){
				Jugador j = i.next();
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_SUPLENTE);
			}
			Iterator<DataPosicion> idp = titulares.listPosiciones.iterator();
			while (idp.hasNext()){
				DataPosicion dp = idp.next();
				jugadorDAO.setearEstadoJugador(dp.getIdJugador(), CONST_TITULAR);
				jugadorDAO.setearPosicion(dp.getIdJugador(), dp.getPosicion());
			}
			return true;		
		}
		
	}
}
