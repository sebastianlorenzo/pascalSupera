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
	static final int MAX_CANT_JUG = 18;
	static final int MAX_ALTURA_ESTADIO = 4500;
	static final int MIN_ALTURA_ESTADIO = 0;
	
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
		
		
		//DE ACA PARA ABAOJ HAY QUE COMENTAR SI QUIEREN USAR LA IMPLEMENTACION VIEJA
		ArrayList<Jugador> jugadoresPorteros = this.jugadorDAO.obtenerPorterosSinEquipo();
		ArrayList<Jugador> jugadoresDefensas = this.jugadorDAO.obtenerDefensasSinEquipo();
		ArrayList<Jugador> jugadoresMediocampistas = this.jugadorDAO.obtenerMediocampistasSinEquipo();
		ArrayList<Jugador> jugadoresDelanteros = this.jugadorDAO.obtenerDelanteroSinEquipo();
		
		Iterator<Jugador> iterPorteros = jugadoresPorteros.iterator();
		Iterator<Jugador> iterDefensas = jugadoresDefensas.iterator();
		Iterator<Jugador> iterMediocampistas = jugadoresMediocampistas.iterator();
		Iterator<Jugador> iterDelanteros = jugadoresDelanteros.iterator();
		
		for (int i = 0 ; i<2; i++){
			Jugador j = iterPorteros.next();
			if (i == 0)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
		}
		
		for (int i = 0 ; i<6; i++){
			Jugador j = iterDefensas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
		}
		
		for (int i = 0 ; i<6; i++){
			Jugador j = iterMediocampistas.next();
			if (i<4)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
		}
		
		for (int i = 0 ; i<4; i++){
			Jugador j = iterDelanteros.next();
			if (i<2)
				jugadorDAO.setearEstadoJugador(j.getIdJugador(), CONST_TITULAR);
			jug.add(j);	
		}
		
		
		/* HASTA ACA PARA LA IMPLEMENTACION VIEJA DESCOMENTAR LO DE ABAJO LA DEJO PORQUE SI NO TIENE CARGADA LA BASE DE DATOS PROBABLEMENTE FALLE
		ArrayList<Jugador> jugadores = this.jugadorDAO.obtenerJugadoresSinEquipo();
		Iterator<Jugador> iter = jugadores.iterator();
		int cant = 0;
		while(iter.hasNext() && (cant<= MAX_CANT_JUG))
		{
			Jugador j = iter.next();
			jug.add(j);	
			cant++;
		}
		*/
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
	public void modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa) {
		this.equipoDAO.modificarTactica(equipo,ataque,mediocampo,defensa);
	}
	
}
