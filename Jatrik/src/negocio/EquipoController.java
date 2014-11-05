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
import tipos.Constantes;
import tipos.DataJugador;
import tipos.DataListaEquipo;
import tipos.DataListaJugador;
import tipos.DataListaPartido;
import tipos.DataListaPosicion;
import tipos.DataPosicion;
import tipos.DataListaOferta;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController
{
	
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
	public Equipo crearEquipo(String equipo, String pais, String nomestadio)
	{
		Equipo e = new Equipo(equipo, pais);
		int capacidad = Constantes.MAX_CAPACIDAD;
		int altura = (int) (Math.random() * (Constantes.MAX_ALTURA_ESTADIO - Constantes.MIN_ALTURA_ESTADIO + 1) + Constantes.MIN_ALTURA_ESTADIO);
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
		while(iterPorteros.hasNext() && (i< Constantes.MAX_CANT_PORTEROS)){
			Jugador j = iterPorteros.next();
			if (i == 0)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), Constantes.CONST_TITULAR);
			jug.add(j);
			i++;
		}
		
		i = 0;
		while(iterDefensas.hasNext() && (i< Constantes.MAX_CANT_DEFENSAS)){
			Jugador j = iterDefensas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), Constantes.CONST_TITULAR);
			jug.add(j);	
			i++;
		}
		
		i = 0;
		while(iterMediocampistas.hasNext() && (i< Constantes.MAX_CANT_MEDIOCAMPISTAS)){
			Jugador j = iterMediocampistas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), Constantes.CONST_TITULAR);
			jug.add(j);
			i++;
		}
		
		i = 0;
		while(iterDelanteros.hasNext() && (i< Constantes.MAX_CANT_DELANTEROS)){
			Jugador j = iterDelanteros.next();
			if (i<2)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), Constantes.CONST_TITULAR);
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
	public JSONObject realizarOferta(String nomUsuario, Integer idJugador, Integer precio, String comentario) 
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
				if (j.getEstado_jugador().compareTo(Constantes.CONST_TITULAR) == 0){
					DataJugador dj = new DataJugador(j.getIdJugador(), j.getJugador(), j.getPosicion(), j.getPosicionIdeal(), (int)(float) j.getVelocidad(),  
														(int)(float) j.getTecnica(), (int)(float) j.getAtaque(), (int)(float) j.getDefensa(), 
														(int)(float) j.getPorteria(), j.getEstado_jugador());
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
				if (j.getEstado_jugador().compareTo(Constantes.CONST_TITULAR) != 0){
					DataJugador dj = new DataJugador(j.getIdJugador(), j.getJugador(), j.getPosicion(), j.getPosicionIdeal(), (int)(float) j.getVelocidad(), 
													(int)(float) j.getTecnica(), (int)(float) j.getAtaque(), (int)(float) j.getDefensa(), 
													(int)(float) j.getPorteria(), j.getEstado_jugador());
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
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), Constantes.CONST_SUPLENTE);
			}
			Iterator<DataPosicion> idp = titulares.listPosiciones.iterator();
			while (idp.hasNext()){
				DataPosicion dp = idp.next();
				jugadorDAO.setearEstadoJugador(dp.getIdJugador(), Constantes.CONST_TITULAR);
				jugadorDAO.setearPosicion(dp.getIdJugador(), dp.getPosicion());
			}
			return true;		
		}
		
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject aceptarOferta(String nomUsuario, String comentario, Integer idOferta) 
	{
		return this.equipoDAO.aceptarOferta(nomUsuario, comentario, idOferta);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject rechazarOferta(String nomUsuario, String comentario, Integer idOferta) 
	{
		return this.equipoDAO.rechazarOferta(nomUsuario, comentario, idOferta);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaOferta obtenerOfertasRealizadas(String nomUsuario) 
	{
		return this.equipoDAO.obtenerMisOfertas(nomUsuario);
	}
	
		@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public JSONObject obtenerEntrenamiento(String equipo) 
	{
		Object[] res = this.equipoDAO.getEntrenamientoEquipo(equipo);
		JSONObject jsonTactica = new JSONObject();
		try
		{
			jsonTactica.put("Ofensivo", res[0].toString());
			jsonTactica.put("Defensivo", res[1].toString());
			jsonTactica.put("Fisico", res[2].toString());
			jsonTactica.put("Porteria", res[3].toString());
        }
        catch(Exception ex)
		{
            ex.printStackTrace();
        }
		return jsonTactica;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void modificarEntrenamiento(String equipo, Integer ofensivo, Integer defensivo, Integer fisico, Integer porteria) 
	{
		this.equipoDAO.modificarEntrenamiento(equipo, ofensivo, defensivo, fisico, porteria);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void ejecutarEntrenamiento() {
		Collection<Equipo> equipos = equipoDAO.obtenerEquipos();
		Iterator<Equipo> iteradorEquipo = equipos.iterator();
		while (iteradorEquipo.hasNext()){
			Equipo e = iteradorEquipo.next();
			Collection<Jugador> jugadores = e.getJugadores();
			Iterator<Jugador> i = jugadores.iterator();
			Integer entrenamientoOfensivo = e.getEntrenamientoOfensivo();
			Integer entrenamientoDefensivo = e.getEntrenamientoDefensivo();
			Integer entrenamientoFisico = e.getEntrenamientoFisico();
			Integer entrenamientoPorteria = e.getEntrenamientoPorteria();
			Float coeficiente = (float) 0.25;
			while (i.hasNext()){
				Jugador j = i.next();
				if (j.getPosicionIdeal().compareTo(Constantes.CONST_DEFENSA) == 0){
					jugadorDAO.aumentarHabilidades(j.getIdJugador(), 
													(entrenamientoDefensivo * coeficiente), 
													(entrenamientoOfensivo * coeficiente * 1/3), 
													(entrenamientoFisico * coeficiente), 
													(entrenamientoOfensivo * coeficiente * 1/3), 
													(float) 0);
				}	
				if (j.getPosicionIdeal().compareTo(Constantes.CONST_DELANTERO) == 0){
					jugadorDAO.aumentarHabilidades(j.getIdJugador(), 
													(entrenamientoDefensivo * coeficiente * 1/3), 
													(entrenamientoOfensivo * coeficiente), 
													(entrenamientoFisico * coeficiente), 
													(entrenamientoOfensivo * coeficiente), 
													(float) 0);
				}	
				if (j.getPosicionIdeal().compareTo(Constantes.CONST_MEDIOCAMPISTA) == 0){
					jugadorDAO.aumentarHabilidades(j.getIdJugador(), 
													(entrenamientoDefensivo * coeficiente * 1/2), 
													(entrenamientoOfensivo * coeficiente * 1/2), 
													(entrenamientoFisico * coeficiente), 
													(entrenamientoOfensivo * coeficiente * 1/2), 
													(float) 0);
				}	
				if (j.getPosicionIdeal().compareTo(Constantes.CONST_PORTERO) == 0){
					jugadorDAO.aumentarHabilidades(j.getIdJugador(), 
													(float) 0, 
													(float) 0, 
													(entrenamientoFisico * coeficiente), 
													(float) 0, 
													entrenamientoPorteria * coeficiente);
				}	
			}
		}
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaPartido obtenerUltimosResultados(String nomUsuario) 
	{
		return this.equipoDAO.obtenerUltimosResultadosEquipo(nomUsuario);
	}
}
