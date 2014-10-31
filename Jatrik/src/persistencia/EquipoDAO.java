package persistencia;

import javax.ejb.Local;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaEquipo;
import tipos.DataListaOferta;
import dominio.Equipo;

@Local
public interface EquipoDAO
{
	
	public Equipo insertarEquipo(Equipo e);
	
	public void borrarEquipo(Equipo e);
	
	public Equipo encontrarEquipo(String equipo);
	
	public JSONArray obtenerTodosEquipos();
	
	public Boolean existeEquipo(String equipo);
	
	public JSONArray obtenerPaises();

	public JSONObject obtenerLugarEquipo(String nomEquipo);
	
	public Object[] getTaticaEquipo(String nombreEquipo); 
	
	public Boolean modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa);

	public DataListaEquipo equiposData(String nomEquipo);

	public void restablecerEquipoLuegoPartido(String nomEquipo); // Pone tarjetas amarillas de los jugadores en 0
	
	public JSONObject realizarOfertaJugador(String nomUsuario, Integer idJugador, Integer precio, String comentario);

	public DataListaOferta obtenerOfertas(String nomUsuario);
	
	public Boolean puedeRealizarCambios(String nomEquipo);
	
	public void sumarCambio(String nomEquipo);
	
	public JSONObject aceptarOferta(String nomUsuario, String comentario, Integer idOferta);

	public JSONObject rechazarOferta(String nomUsuario, String comentario, Integer idOferta);

	public DataListaOferta obtenerMisOfertas(String nomUsuario);
	
}
