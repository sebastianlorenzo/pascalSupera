package persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.*;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Equipo;
import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAOImpl implements UsuarioDAO {

	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario insertarUsuario(Usuario u){
		try
		{
			em.persist(u);
			return u;
		}
		catch (Throwable ex)
		{
			System.out.println("EXCEPCIÓN: " + ex.getClass());
            return null;
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean actualizarUsuario(String login, String password, String mail, Equipo equipo, boolean conectado){
		Usuario u = em.find(Usuario.class, login);
		if (u != null){
			u.setPassword(password);
			u.setMail(mail);
			u.setEquipo(equipo);
			u.setConectado(conectado);
			em.merge(u);
			return true;
		}else
			return false;	
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void borrarUsuario(Usuario u){
		em.remove(u);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario obtenerUsuario(String login){
		return em.find(Usuario.class, login);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearConectado(String login){
		Usuario u = em.find(Usuario.class, login);
		if (u != null){
			u.setConectado(true);
			em.merge(u);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setearDesconectado(String login){
		Usuario u = em.find(Usuario.class, login);
		if (u != null){
			u.setConectado(false);
			em.merge(u);
		}
	}
		
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario buscarUsuario(String login, String password){
		Query query = em.createQuery("SELECT u FROM Usuario u "
				+ "WHERE u.login = :login and u.password = :password");
		query.setParameter("login", login);
		query.setParameter("password", password);
//		return ((Usuario) query.getSingleResult());
		ArrayList<Usuario> lst = (ArrayList<Usuario>) query.getResultList();
		if (lst.isEmpty()){
			return null;
		}
		else{
			return lst.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Usuario> obtenerTodos(){
		Query query = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> resultList = query.getResultList();
		return resultList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeUsuarioRegistrado(String login){
		return (em.find(Usuario.class, login) != null);
	}
	
}
