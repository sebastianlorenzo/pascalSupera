package persistencia;

import java.util.*;

import javax.ejb.*;
import javax.persistence.*;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import tipos.DataListaMensaje;
import tipos.DataMensaje;
import dominio.Equipo;
import dominio.Mensaje;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAOImpl implements UsuarioDAO 
{

	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario insertarUsuario(Usuario u)
	{
		try
		{
			em.persist(u);
			return u;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCI�N: " + ex.getClass());
            return null;
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean actualizarUsuario(String login, String password, String mail, Equipo equipo, Boolean conectado)
	{
		Usuario u = em.find(Usuario.class, login);
		if (u != null)
		{
			u.setPassword(password);
			u.setMail(mail);
			u.setEquipo(equipo);
			u.setConectado(conectado);
			em.merge(u);
			return true;
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarUsuario(Usuario u)
	{
		em.remove(u);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario obtenerUsuario(String login)
	{
		return em.find(Usuario.class, login);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearConectado(String login)
	{
		Usuario u = em.find(Usuario.class, login);
		if (u != null)
		{
			u.setConectado(true);
			em.merge(u);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearDesconectado(String login)
	{
		Usuario u = em.find(Usuario.class, login);
		if (u != null)
		{
			u.setConectado(false);
			em.merge(u);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean getEstaConectadoUsuario(String login)
	{
		Usuario u = em.find(Usuario.class, login);
		if (u != null)
		{
			return u.getConectado();
		}
		return false;
	}
		
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuario(String login, String password)
	{
		Query query = em.createQuery("SELECT u FROM Usuario u "
								   + "WHERE u.login = :login and u.password = :password");
		query.setParameter("login", login);
		query.setParameter("password", password);
		ArrayList<Usuario> lst = (ArrayList<Usuario>) query.getResultList();
		if (lst.isEmpty())
		{
			return null;
		}
		else
		{
			return lst.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Usuario> obtenerTodos()
	{
		Query query = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> resultList = query.getResultList();
		return resultList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean existeUsuarioRegistrado(String login)
	{
		return (em.find(Usuario.class, login) != null);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean esAdministrador(String login) 
	{
		Usuario u = em.find(Usuario.class, login);
		if (u != null)
		{
			return u.getEsAdmin();
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean enviarChat(String emisor, String receptor, String mensaje) 
	{
		Usuario uEmisor =  em.find(Usuario.class, emisor);
		Usuario uReceptor =  em.find(Usuario.class, receptor);
		
		if((uEmisor == null) || (uReceptor == null)){
			return false;
		}
		Mensaje m = new Mensaje(uEmisor, uReceptor, mensaje);
		em.persist(m);
		
		Collection<Mensaje> mensajesEnviados = uEmisor.getMensajesEnviados();
		mensajesEnviados.add(m);
		uEmisor.setMensajesEnviados(mensajesEnviados);
		
		Collection<Mensaje> mensajesRecibidos = uReceptor.getMensajesRecibidos();
		mensajesRecibidos.add(m);
		uReceptor.setMensajesRecibidos(mensajesRecibidos);
		
		return true;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DataListaMensaje recibirChat(String receptor) 
	{
		Usuario uReceptor =  em.find(Usuario.class, receptor);
		Collection<Mensaje> mensajesRecibidos = uReceptor.getMensajesRecibidos();
		Iterator<Mensaje> iter = mensajesRecibidos.iterator();
		
		DataListaMensaje dlm = new DataListaMensaje();
		DataMensaje dm = null;
		
		while(iter.hasNext()){
			Mensaje m = iter.next();
			if(m.getLeido() == false)
			{	
				dm = new DataMensaje();
				//agrego la info a DataMensaje
				dm.setEmisor(m.getEmisorMensaje().getLogin());
				dm.setTexto(m.getTexto());
				
				//seteo mensaje como leido = true.
				//El usuario tendr� disponibles los mensajes recibidos para leer
				m.setLeido(true);
			}
			if(dm != null)
			dlm.addDataMensaje(dm);
		}
		return dlm;
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public JSONArray obtenerDesconectados() 
	{	Query query = em.createQuery("SELECT us FROM Usuario us");
		List<Usuario> usuarios = query.getResultList();		
		JSONArray jdesconectados = new JSONArray();
		JSONObject ob;
	
		for (Usuario u : usuarios) 
		{
			try 
			{	
				ob = new JSONObject();
				if(u.getConectado() == false) 
				{
					ob.put("desconectado", u.getLogin());
					jdesconectados.put(ob);
				}
			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
			
		}
		return jdesconectados;
	}
	
}
