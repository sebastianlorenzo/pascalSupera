package persistencia;

import java.util.List;

import javax.ejb.*;
import javax.persistence.Query;

import dominio.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAOImpl implements UsuarioDAO {

	@javax.persistence.PersistenceContext(unitName="USUARIOS_UNIT")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario insert(Usuario entity)
	{
		//try
		//{
			em.persist(entity);
			return entity;
		/*}
		catch (Throwable ex)
		{
			throw ExceptionManager.process(ex);
		}*/
	}
	
	@Override
	public void update(Usuario entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Usuario entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> buscarUsuario(String login, String password) {
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login and u.password = :password");
		query.setParameter("login", login);
		/*query.setParameter("password", password);*/
		List<Usuario> resultList = query.getResultList();
		return resultList;
	}
	
}
