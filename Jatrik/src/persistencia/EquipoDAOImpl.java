package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dominio.Equipo;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoDAOImpl implements EquipoDAO{
	
	@PersistenceContext(unitName="Jatrik")
	private javax.persistence.EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Equipo insertarEquipo(Equipo e) {
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

	@Override
	public void actualizarEquipo(Equipo e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarEquipo(Equipo e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public Equipo encontrarEquipo(String equipo) {
		return null;
	}

	@Override
	public List<Equipo> obtenerTodosEquipos() {
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> obtenerPaises() {
		Query query = em.createQuery("Select distinct(pais) from Equipo e order by pais");
		List<String> paises = query.getResultList();
		return paises;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeEquipo(String equipo) {
		return (em.find(Equipo.class, equipo) != null);
	}
}
