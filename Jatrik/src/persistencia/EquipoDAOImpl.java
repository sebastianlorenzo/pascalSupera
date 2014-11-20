package persistencia;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.*;
import javax.persistence.*;

import org.codehaus.jettison.json.*;

import tipos.Constantes;
import tipos.DataEquipo;
import tipos.DataJugador;
import tipos.DataListaEquipo;
import tipos.DataListaOferta;
import tipos.DataListaPartido;
import tipos.DataOferta;
import tipos.DataResumenPartido;
import dominio.Campeonato;
import dominio.Comentario;
import dominio.Equipo;
import dominio.Jugador;
import dominio.Notificacion;
import dominio.Oferta;
import dominio.Partido;
import dominio.ResultadoCampeonato;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoDAOImpl implements EquipoDAO
{

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
			System.out.println("EXCEPCI�N: " + ex.getClass());
            return null;
		}
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
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject obtenerLugarEquipo(String nomEquipo) 
	{
		JSONObject pais = new JSONObject();
		Equipo eq = em.find(Equipo.class, nomEquipo);
		
		try
		{
			pais.put("pais", eq.getPais());
        }
        catch(Exception ex)
		{
            ex.printStackTrace();
        }
		return pais;
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
	public DataListaEquipo equiposData(String nomEquipo, boolean incluir_equipo)
	{
		Query query = null;
		if (incluir_equipo)
		{
			query = em.createQuery("SELECT eq FROM Equipo eq WHERE eq.equipo = :equipo ORDER BY eq.puntaje desc");
		}
		else
		{
			query = em.createQuery("SELECT eq FROM Equipo eq WHERE eq.equipo != :equipo ORDER BY eq.puntaje desc");
		}
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
				DataJugador dj = new DataJugador(jug.getIdJugador(), jug.getJugador(), jug.getPosicion(),  jug.getPosicionIdeal(), 
												(int)(float) jug.getVelocidad(), (int)(float) jug.getTecnica(), (int)(float) jug.getAtaque(),
												(int)(float) jug.getDefensa(), (int)(float) jug.getPorteria(), jug.getEstado_jugador(), 
												jug.getHistoricoTarjetasAmarillas(), jug.getHistoricoTarjetasRojas(), jug.getHistoricoGoles(),
												jug.getHistoricoLesiones());
				de.addDataJugador(dj);
			}
			dlequipos.addDataEquipo(de);
		}
		return dlequipos;
	}

	// Pone tarjetas amarillas de los jugadores y cantidad de cambios realizados por el equipo en 0.
	// Adem�s, restablece el valor del estado del jugador como estaba antes de jugarse el partido
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void restablecerEquipoLuegoPartido(String nomEquipo, List<Jugador> jugadoresAntes)
	{
		Equipo e = em.find(Equipo.class, nomEquipo);
		if (e != null)
		{
			List<Jugador> jugadores = (List<Jugador>) e.getJugadores();
			Iterator<Jugador> it    = jugadores.iterator();
	        while(it.hasNext()) 
	        {
	        	Jugador j = it.next();
	        	j.setCant_tarjetas_amarillas(0);
	
	        	Iterator<Jugador> itj = jugadoresAntes.iterator();
	        	String estado = null;
	        	while (itj.hasNext())
	        	{
	        		Jugador ja = itj.next();
		        	if (ja.getIdJugador().equals(j.getIdJugador()))
		        	{
		        		estado = ja.getEstado_jugador();
		        		break;
		        	}
	        	}
		        j.setEstado_jugador(estado);
		        em.merge(j);
	        }
	        e.setCant_cambios_realizados(0);
	        em.merge(e);
		}
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
			    
			    Integer idOferta = of.getIdOferta();
			    dof.setIdOferta(idOferta);
			    
			    String comentario = of.getComentario();
			    if(comentario != null)
			    //if (!comentario.equals("null"))
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
		System.out.print(" - Cantidad de cambios " + nomEquipo + ": " + e.getCant_cambios_realizados() + "\n");
		return (e.getCant_cambios_realizados() < Constantes.CONST_CANT_MAX_CAMBIOS);
	}
	
	public void sumarCambio(String nomEquipo)
	{
		Equipo e = em.find(Equipo.class, nomEquipo);
		e.setCant_cambios_realizados(e.getCant_cambios_realizados() + 1);
		em.persist(e);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
			
			Collection<Jugador> misJugadores = miEquipo.getJugadores();
			misJugadores.remove(jug);
			
			Collection<Jugador> jugadoresDeEqDestino = eqDestino.getJugadores();
			jugadoresDeEqDestino.add(jug);
			
			jug.setEquipo(eqDestino);
			
			Boolean es_titular = false;
			if(jug.getEstado_jugador().equals("titular")){
				jug.setEstado_jugador("suplente");
				es_titular = true;
			}								
			oferta.setEstado_oferta("aceptada");
						
			Collection<Notificacion> notificacionesRecibidas = usComprador.getNotificacionesRecibidas();
			String mensaje= "La oferta realizada por el jugador "+jug.getJugador()+" perteneciente al equipo "
							+miEquipo.getEquipo()+" fue aceptada.";
			Notificacion not = new Notificacion();
			not.setReceptorNotificacion(usComprador);
			not.setVista(false);
			
			if (comentario != null){
				oferta.setComentarioAcepta(comentario);
				not.setTexto(mensaje+" "+comentario);
			}else{
				not.setTexto(mensaje);
			}
			em.persist(not);
			
			notificacionesRecibidas.add(not);
			usComprador.setNotificacionesRecibidas(notificacionesRecibidas);			
			
			while(iter.hasNext())
			{
				Oferta of = iter.next();
				Integer id = of.getJugadorEnVenta().getIdJugador();
				String estadoOf = of.getEstado_oferta();
				
				if(id == idJug && estadoOf.equals("pendiente"))
				{
					of.setEstado_oferta("rechazada");
					of.setComentarioAcepta("Oferta rechazada, el jugador ya fue vendido a otro equipo.");
					
					Usuario uRech = of.getEquipoDestino().getUsuario();
					Collection<Notificacion> notificacionesRecibidasUR = uRech.getNotificacionesRecibidas();
						String msj= "La oferta ha sido rechazada, pues el jugador "+of.getJugadorEnVenta().getJugador()+
								" fue vendido.";
						Notificacion noti = new Notificacion(uRech, msj);
						em.persist(noti);
						
					notificacionesRecibidasUR.add(noti);
					uRech.setNotificacionesRecibidas(notificacionesRecibidasUR);	
				}
			}
						
			try
			{
				respuesta.put("Oferta aceptada", true);
				if(es_titular == false)
					respuesta.put("mensaje", "La venta del jugador fue realizada correctamente.");
				else
					respuesta.put("mensaje", "La venta del jugador titular fue realizada correctamente. Debes modificar tu lista de jugadores titulares.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONObject rechazarOferta(String nomUsuario, String comentario, Integer idOferta) 
	{
		Usuario us = em.find(Usuario.class, nomUsuario);
		Oferta oferta = em.find(Oferta.class, idOferta);
		
		JSONObject respuesta = new JSONObject();		
		if ((us == null) || (oferta == null)) {
			try
			{
				respuesta.put("Oferta rechazada", false);
				respuesta.put("mensaje", "ERROR. Usuario u Oferta no existen en el sistema.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else{
			
			oferta.setEstado_oferta("rechazada");
			
			Usuario usOferente = oferta.getEquipoDestino().getUsuario();
			String NomJug = oferta.getJugadorEnVenta().getJugador();
			String NomEqJug = oferta.getEquipoOrigen().getEquipo();
			
			Collection<Notificacion> notificacionesRecibidas = usOferente.getNotificacionesRecibidas();
			String mensajeUno= "La oferta realizada por el jugador "+NomJug+" perteneciente al equipo "+NomEqJug+" fue rechazada.";
			
			Notificacion not = new Notificacion();
			not.setReceptorNotificacion(usOferente);
			not.setVista(false);
			
			if (comentario != null){
				oferta.setComentarioAcepta(comentario);
				not.setTexto(mensajeUno+" "+comentario);
			}else{
				not.setTexto(mensajeUno);
			}
			em.persist(not);
			
			notificacionesRecibidas.add(not);
			usOferente.setNotificacionesRecibidas(notificacionesRecibidas);
			
			try
			{
				respuesta.put("Oferta rechazada", true);
				respuesta.put("mensaje", "La oferta fue rechazada correctamente.");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		return respuesta;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaOferta obtenerMisOfertas(String nomUsuario) 
	{
		Usuario us = em.find(Usuario.class, nomUsuario);
		
		Equipo miEquipo = us.getEquipo();
		Collection<Oferta> ofertasRealizadas = miEquipo.getOfertasRealizadas();
		Iterator<Oferta> iter = ofertasRealizadas.iterator();
		
		DataListaOferta dlo = new DataListaOferta();
		DataOferta dof = null;
		
		while(iter.hasNext()){
			
			Oferta of = iter.next();
			String estadoOf = of.getEstado_oferta();
			
			Date fechaOferta = of.getFecha_oferta();
			SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		    String fechaOf = formateador.format(fechaOferta);
		    
		    Equipo eqOrigen = of.getEquipoOrigen();
		    String nomEqActual = eqOrigen.getEquipo();
		    String usActual = eqOrigen.getUsuario().getLogin();

		    Jugador jugadorEnVenta = of.getJugadorEnVenta();
		    String 	nomJugador = jugadorEnVenta.getJugador();
		    
		    Integer precio = of.getPrecio();
		    
		    dof = new DataOferta(nomJugador, precio, fechaOf, estadoOf);
		    
		    dof.setEquipoActual(nomEqActual);
		    dof.setUsuarioActual(usActual);			    
		    
		    Integer idOferta = of.getIdOferta();
		    dof.setIdOferta(idOferta);
		    
		    String comentario = of.getComentarioAcepta();
		    if (comentario != null && !comentario.equals(""))
		    	dof.setComentario(comentario);
			    
			if(dof != null)
				dlo.addDataOferta(dof);
		}
		return dlo;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Object[]  getEntrenamientoEquipo(String nombreEquipo) 
	{
		Query query = em.createQuery("SELECT e.entrenamientoOfensivo , e.entrenamientoDefensivo , e.entrenamientoFisico , e.entrenamientoPorteria "
									+ "FROM  Equipo e "
									+ "WHERE e.equipo = '"+ nombreEquipo + "'");
		List <Object[]> entrenamiento = query.getResultList();
		return entrenamiento.get(0);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean modificarEntrenamiento(String equipo, Integer ofensivo, Integer defensivo, Integer fisico, Integer porteria) 
	{
		Equipo e = em.find(Equipo.class, equipo);
		if (e != null)
		{
			e.setEntrenamientoOfensivo(ofensivo);
			e.setEntrenamientoDefensivo(defensivo);
			e.setEntrenamientoFisico(fisico);
			e.setEntrenamientoPorteria(porteria);
			em.merge(e);
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<Equipo> obtenerEquipos()
	{
		Query query = em.createQuery("SELECT e FROM Equipo e");
		ArrayList<Equipo> resultList = (ArrayList<Equipo>) query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaPartido obtenerUltimosResultadosEquipo(String nomUsuario) 
	{
		Query query;
		if(nomUsuario != null){
			
			Usuario us = em.find(Usuario.class, nomUsuario);
			String miEquipo = us.getEquipo().getEquipo();
			
			query = em.createQuery("SELECT p FROM Partido p " + 
					"WHERE p.equipoLocal = '" + miEquipo 
					+ "' or p.equipoVisitante = '" + miEquipo + "' ORDER BY p.fechaPartido DESC");
		}
		else
		{
			query = em.createQuery("SELECT p FROM Partido p ORDER BY p.fechaPartido DESC");
			
		}
		List<Partido> partidos = query.getResultList();
		
		if(partidos.isEmpty())
			return null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);		
		Date ahora = calendar.getTime();

		DataListaPartido dlpartidos = new DataListaPartido();
		
		Iterator<Partido> iter = partidos.iterator();
		int cant = 0;
		while (cant < Constantes.MAX_RESULTADOS_PARTIDOS && iter.hasNext())
		{
			Partido p = iter.next();
			Date fecha_p = p.getFechaPartido();
			if(fecha_p.before(ahora))
			{	
				String nomCampeonato = p.getCampeonato().getCampeonato();
				String nomEqLocal = p.getEquipoLocal().getEquipo();
				String nomEqVisitante = p.getEquipoVisitante().getEquipo();
				String nomPartido = nomEqLocal+" vs. "+nomEqVisitante;
				
				Date fechaPartido = p.getFechaPartido();
				SimpleDateFormat formateador2 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			    String fecha = formateador2.format(fechaPartido);
						
				Integer golesLocal = p.getGolesLocal();
				Integer golesVisitante = p.getGolesVisitante();
				
				DataResumenPartido dp = new DataResumenPartido (nomPartido, nomEqLocal, nomEqVisitante, 
						nomCampeonato, fecha, p.getTarjetasAmarillasLocal(), p.getTarjetasAmarillasVisitante(), 
						  p.getTarjetasRojasLocal(), p.getTarjetasRojasVisitante(),
						  golesLocal, golesVisitante,
						  p.getLesionesLocal(), p.getLesionesVisitante());
				
				Collection<Comentario> listComentarios = p.getComentarios();
				
				List<String> comentariosEnvio = new ArrayList<String>();
				for (Comentario com: listComentarios ){
						String comentario = com.getMinuto()+" "+com.getComentario();
						comentariosEnvio.add(comentario);		
				}
				
				if(comentariosEnvio != null)
					dp.setDetalle(comentariosEnvio);
				
				dlpartidos.addDataPartido(dp);
				cant++;
			}
		}
		
		return dlpartidos;
	}

	@SuppressWarnings("unchecked")
	public void actualizarPuntajesEquipo(String campeonato, String equipo, Integer puntos)
	{
		/*** Obtengo el equipo, el usuario y el campeonato ***/
		Equipo e = em.find(Equipo.class, equipo);		
		if (e == null)
		{
			System.out.println("actualizarPuntajesEquipo: Equipo null.");
			return;
		}
		Query query = em.createQuery("SELECT e.usuario FROM Equipo e WHERE e.equipo = '" + equipo + "'");
		List<Usuario> usuarios = query.getResultList();
		Usuario u = (usuarios != null) ? usuarios.get(0) : null;
		if (u == null)
		{
			System.out.println("actualizarPuntajesEquipo: Usuario null.");
			return;
		}
		query = em.createQuery("SELECT c FROM Campeonato c WHERE c.campeonato = '" + campeonato + "'");
		List<Campeonato> campeonatos = query.getResultList();
		Campeonato c = (campeonatos != null) ? campeonatos.get(0) : null;
		if (c == null)
		{
			System.out.println("actualizarPuntajesEquipo: Campeonato null.");
			return;
		}
		
		/*** Seteo los puntos correspondientes ***/
		e.setPuntaje(e.getPuntaje() + puntos);
		e.setRanking(e.getRanking() + puntos);
		
		// El capital es un porcentaje del capital del usuario, multiplicado por la cantidad de puntos asignados en el partido
		Integer capital =  u.getCapital();
		capital += puntos * capital * Constantes.CONT_PORCENTAJE_CAPITAL / 100; 
		u.setCapital(capital);
		
		List<ResultadoCampeonato> resultados = (List<ResultadoCampeonato>) c.getResultadoCampeonato();
		Iterator<ResultadoCampeonato> it = resultados.iterator();
		while (it.hasNext())
		{
			ResultadoCampeonato r = it.next();
			if (r.getEquipo().getEquipo().equals(equipo) && r.getCampeonato().getCampeonato().equals(campeonato))
			{
				r.setPuntaje(r.getPuntaje() + puntos);
			}
		}
		
		em.merge(c);
		em.merge(u);
		em.merge(e);
	}
	
	public void reiniciarPuntajesEquipo(String equipo)
	{
		Equipo e = em.find(Equipo.class, equipo);		
		if (e == null)
		{
			System.out.println("reiniciarPuntajesEquipo: Equipo null.");
			return;
		}
		e.setPuntaje(-1);		
		em.merge(e);
	}
	
}
