package negocio;

import javax.ejb.Local;

import tipos.DataListaMensaje;

@Local
public interface IChatController 
{
	public void enviarChat(String emisor, String receptor, String mensaje);
	
	public DataListaMensaje recibirChat(String receptor);
}
