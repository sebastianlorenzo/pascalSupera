package negocio;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import persistencia.PartidoDAO;
import persistencia.UsuarioDAO;

public class PartidoController implements IPartidoController
{
	
	@EJB
	private PartidoDAO partidoDAO;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataResumenPartido simularPartido(String partido)
	{
		
	}
}
