package persistencia;

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
			System.out.println("EXCEPCI�N: " + ex.getClass());
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
		
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Usuario> buscarUsuario(String login){
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
		query.setParameter("login", login);
		List<Usuario> resultList = query.getResultList();
		return resultList;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Usuario> obtenerTodos(){
		Query query = em.createQuery("SELECT u FROM Usuario u");
		List<Usuario> resultList = query.getResultList();
		return resultList;
	}
	
}