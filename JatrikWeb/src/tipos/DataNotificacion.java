package tipos;

public class DataNotificacion 
{
	private String texto;
	private String usuarioNotificado;
	
	public DataNotificacion(){}
	
	public DataNotificacion(String usuarioNotificado, String texto)
	{
		this.texto = texto;
		this.usuarioNotificado = usuarioNotificado;
	}
	
	public String getTexto() 
	{
		return texto;
	}
	
	public void setTexto(String texto) 
	{
		this.texto = texto;
	}
	
	public String getUsuarioNotificado() 
	{
		return usuarioNotificado;
	}
	
	public void setUsuarioNotificado(String usuarioNotificado) 
	{
		this.usuarioNotificado = usuarioNotificado;
	}
	
}
