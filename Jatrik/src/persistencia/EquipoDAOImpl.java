package persistencia;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.*;
import javax.persistence.*;

import org.codehaus.jettison.json.*;

import tipos.DataEquipo;
import tipos.DataJugador;
import tipos.DataListaEquipo;
import tipos.DataListaOferta;
import tipos.DataOferta;
import dominio.Campeonato;
import dominio.Equipo;
import dominio.Estadio;
import dominio.Jugador;
import dominio.Oferta;
import dominio.Pais;
import dominio.Partido;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoDAOImpl implements EquipoDAO
{
	
	static final String CONST_TITULAR = "titular";
	static final Integer CONST_CANT_MAX_CAMBIOS = 3;
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo insertarEquipo(Equipo e) 
	{
		try
		{
			em.persist(e);
			return e;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean actualizarEquipo(String equipo, String pais, String localidad,
									Estadio estadio, Usuario usuario, Collection<Jugador> jugadores,
									Collection<Partido> partidos, Collection<Campeonato> campeonatos) 
	{
		Equipo e = em.find(Equipo.class, equipo);
		if (e != null)
		{
			e.setEquipo(equipo);
			e.setPais(pais);
			e.setLocalidad(localidad);
			e.setEstadio(estadio);
			e.setUsuario(usuario);
			e.setJugadores(jugadores);
			e.setPartidos(partidos);
			e.setCampeonatos(campeonatos);
			em.merge(e);
			return true;
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarEquipo(Equipo e)
	{
		em.remove(e);	
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo encontrarEquipo(String equipo) 
	{
		return em.find(Equipo.class, equipo);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerTodosEquipos() 
	{
		Query query = em.createQuery("SELECT eq FROM Equipo eq");
		List<Equipo> equiposList = query.getResultList();
		JSONArray jequipos = new JSONArray();
		
		for (Equipo e : equiposList) 
		{
			JSONObject ob = new JSONObject();
			try 
			{
				ob.put("equipo", e.getEquipo());
				ob.put("pais", e.getPais());
				ob.put("localidad", e.getLocalidad());
			} 
			catch (JSONException ex) 
			{
				ex.printStackTrace();
			}
			jequipos.put(ob);
		}
		return jequipos;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeEquipo(String equipo)
	{
		return (em.find(Equipo.class, equipo) != null);
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerPaises()
	{
		Query query = em.createQuery("SELECT p FROM Pais p");
		List<Pais> paisesList = query.getResultList();		
		JSONArray jpaises = new JSONArray();
		
		for (Pais p : paisesList) 
		{
			JSONObject ob = new JSONObject();
			try 
			{
				ob.put("pais", p.getPais());
				ob.put("localidad", p.getLocalidad());
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			jpaises.put(ob);
		}
		return jpaises;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerLugarEquipo(String nomEquipo) 
	{
		JSONObject paisYLocalidad = new JSONObject();
		Equipo eq = em.find(Equipo.class, nomEquipo);
		
		try
		{
			paisYLocalidad.put("pais", eq.getPais() );
			paisYLocalidad.put("localidad", eq.getLocalidad());
        }
        catch(Exception ex)
		{
            ex.printStackTrace();
        }
		return paisYLocalidad;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object[]  getTaticaEquipo(String nombreEquipo) 
	{
		Query query = em.createQuery("SELECT e.tacticaDefensa , e.tacticaMediocampo , e.tacticaAtaque "
									+ "FROM  Equipo e "
									+ "WHERE e.equipo = '"+ nombreEquipo + "'");
		List <Object[]> tactica = query.getResultList();
		return tactica.get(0);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean modificarTactica(String equipo, Integer ataque, Integer mediocampo, Integer defensa) 
	{
		Equipo e = em.find(Equipo.class, equipo);
		if (e != null)
		{
			e.setTacticaAtaque(ataque);
			e.setTacticaMediocampo(mediocampo);
			e.setTacticaDefensa(defensa);
			em.merge(e);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaEquipo equiposData(String nomEquipo)
	{
		Query query = em.createQuery("SELECT eq FROM Equipo eq WHERE eq.equipo != :equipo ORDER BY eq.puntaje desc");
		query.setParameter("equipo", nomEquipo);
		List<Equipo> lequipos = query.getResultList();		
		DataListaEquipo dlequipos = new DataListaEquipo();
		
		for (Equipo e: lequipos)
		{
			String nomEq = e.getEquipo();
			DataEquipo de = new DataEquipo(nomEq);
			Collection<Jugador> ljugadores = e.getJugadores();
			
			for(Jugador jug: ljugadores)
			{
				DataJugador dj = new DataJugador(jug.getIdJugador(), jug.getJugador(), jug.getPosicionIdeal(), 
				jug.getVelocidad(), jug.getTecnica(), jug.getAtaque(),
				jug.getDefensa(), jug.getPorteria(), jug.getEstado_jugador());
				de.addDataJugador(dj);
			}
			dlequipos.addDataEquipo(de);
		}
		return dlequipos;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	// Pone tarjetas amarillas de los jugadores y cantidad de cambios realizados por el equipo en 0.
	// Además, restablece el valor del estado del jugador como estaba antes de jugarse el partido
	public void restablecerEquipoLuegoPartido(String nomEquipo)
	{
		Equipo e = em.find(Equipo.class, nomEquipo);
		List<Jugador> jugadores = (List<Jugador>) e.getJugadores();
		Iterator<Jugador> it = jugadores.iterator();
        while(it.hasNext()) 
        {
        	Jugador j = it.next();
        	j.setCant_tarjetas_amarillas(0);
        	j.setEstado_jugador(CONST_TITULAR); /** Cambiar esto!!!!!!!!!! **/
        }
        e.setCant_cambios_realizados(0);
		em.merge(e);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject realizarOfertaJugador(String nomUsuario, Integer idJugador, Integer precio, String comentario) 
	{
		Usuario us = em.find(Usuario.class, nomUsuario);
		Jugador jug = em.find(Jugador.class, idJugador);
		
		JSONObject respuesta = new JSONObject();
				
		if (( us == null) || (jug == null)) {
			try
			{
				respuesta.put("oferta", false);
				respuesta.put("mensaje", "ERROR. Usuario o Jugador no existen en el sistema.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}else if(us.getCapital() < precio){
			
			try
			{
				respuesta.put("oferta", false);
				respuesta.put("mensaje", "ERROR. El precio debe ser menor a su capital actual: "+ us.getCapital());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else{
			Equipo equipoDestino = us.getEquipo(); // equipo del usuario que realiza la oferta
			Equipo equipoActual = jug.getEquipo(); // equipo al que pertenece el jugador	
			
			Date fechaOferta = new Date();
			
			Oferta of = new Oferta(precio, fechaOferta, jug, equipoActual, equipoDestino);
			of.setEstado_oferta("pendiente");
			if(comentario != "")
				of.setComentario(comentario);
			em.persist(of);
			
			Collection<Oferta> oferta_jugadores = jug.getOferta_jugadores();
			oferta_jugadores.add(of);
			jug.setOferta_jugadores(oferta_jugadores);
			
			Collection<Oferta> ofertasRealizadas = equipoDestino.getOfertasRealizadas();
			ofertasRealizadas.add(of);
			equipoDestino.setOfertasRealizadas(ofertasRealizadas);
					
			Collection<Oferta> ofertasRecibidas = equipoActual.getOfertasRecibidas();
			ofertasRecibidas.add(of);
			equipoActual.setOfertasRecibidas(ofertasRecibidas);
			
			try
			{
				respuesta.put("oferta", true);
				respuesta.put("mensaje", "Oferta realizada correctamente.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaOferta obtenerOfertas(String nomUsuario) 
	{
		Usuario us = em.find(Usuario.class, nomUsuario);
		Equipo miEquipo = us.getEquipo();
		Collection<Oferta> ofertasRecibidas = miEquipo.getOfertasRecibidas();
		Iterator<Oferta> iter = ofertasRecibidas.iterator();
		
		DataListaOferta dlo = new DataListaOferta();
		DataOferta dof = null;
		
		while(iter.hasNext()){
			
			Oferta of = iter.next();
			String estadoOf = of.getEstado_oferta();
			if(estadoOf.equals("pendiente"))
			{	
				Date fechaOferta = of.getFecha_oferta();
				SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			    String fechaOf = formateador.format(fechaOferta);
				
			    Equipo equipoOferente = of.getEquipoDestino();
			    String eqDestino = equipoOferente.getEquipo();
			    
			    String usuarioOferente = equipoOferente.getUsuario().getLogin();
			    
			    Jugador jugadorEnVenta = of.getJugadorEnVenta();
			    String 	nomJugador = jugadorEnVenta.getJugador();
			    Integer idJugador = jugadorEnVenta.getIdJugador();
			    
			    Integer precio = of.getPrecio();
			    
			    dof = new DataOferta(eqDestino, nomJugador, idJugador, precio, fechaOf);
			    dof.setUsuarioOferente(usuarioOferente);
			    
			    String comentario = of.getComentario();
			    if (!comentario.equals(""))
			    	dof.setComentario(comentario);
			    
			if(dof != null)
				dlo.addDataOferta(dof);
			}

		}
		return dlo;
	}
	
	public Boolean puedeRealizarCambios(String nomEquipo)
	{
		Equipo e = em.find(Equipo.class, nomEquipo);
		return (e.getCant_cambios_realizados() < CONST_CANT_MAX_CAMBIOS);
	}
	
	public void sumarCambio(String nomEquipo)
	{
		Equipo e = em.find(Equipo.class, nomEquipo);
		e.setCant_cambios_realizados(e.getCant_cambios_realizados() + 1);
		em.merge(e);
	}
	
	public JSONObject aceptarOferta(String nomUsuario, String comentario, Integer idOferta)
	{
		Usuario us = em.find(Usuario.class, nomUsuario);
		Oferta oferta = em.find(Oferta.class, idOferta);
		
		JSONObject respuesta = new JSONObject();		
		if ((us == null) || (oferta == null)) {
			try
			{
				respuesta.put("Oferta aceptada", false);
				respuesta.put("mensaje", "ERROR. Usuario u Oferta no existen en el sistema.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else{
			
			Equipo miEquipo = us.getEquipo();
			Equipo eqDestino = oferta.getEquipoDestino();
			Usuario usComprador = eqDestino.getUsuario();
			Jugador jug = oferta.getJugadorEnVenta();		
			
			Integer precio = oferta.getPrecio();
			Integer micapital = us.getCapital();
			us.setCapital(micapital + precio);
			
			Integer capitalComprador = usComprador.getCapital();
			usComprador.setCapital(capitalComprador - precio);
			
			Collection<Oferta> ofertasRecibidas = miEquipo.getOfertasRecibidas();
			Iterator<Oferta> iter = ofertasRecibidas.iterator();
			
			Integer idJug = jug.getIdJugador();
			
			while(iter.hasNext()){
				Oferta of = iter.next();
				Integer id = of.getJugadorEnVenta().getIdJugador();
				if(id == idJug){
					of.setEstado_oferta("rechazar");
					of.setComentario_acepta("Oferta rechazada, el jugador ya fue vendido a otro equipo.");
				}
			}
			
			Collection<Jugador> misJugadores = miEquipo.getJugadores();
			misJugadores.remove(jug);
			
			Collection<Jugador> jugadoresDeEqDestino = eqDestino.getJugadores();
			jugadoresDeEqDestino.add(jug);
					
			oferta.setEstado_oferta("aceptar");
			oferta.setComentario_acepta(comentario);
			
			try
			{
				respuesta.put("Oferta aceptada", true);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}
	
}
