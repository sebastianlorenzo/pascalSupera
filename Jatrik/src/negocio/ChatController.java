package negocio;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.UsuarioDAO;
import tipos.DataListaMensaje;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ChatController implements IChatController
{
	@EJB
	private UsuarioDAO usuarioDAO;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void enviarChat(String emisor, String receptor, String mensaje) 
	{
		this.usuarioDAO.enviarChat(emisor, receptor, mensaje);
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DataListaMensaje recibirChat(String receptor) 
	{
		return this.usuarioDAO.recibirChat(receptor);
	}

}
