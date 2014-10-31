package dominio;

import javax.persistence.*;

@Entity
@Table (name = "notificacion", schema = "public")
public class Notificacion implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long idNotificacion;
	private String texto;
	private Boolean vista;
	
	@ManyToOne
	Usuario receptorNotificacion;
	
	public Notificacion(){}
	
	public Notificacion(Usuario usuarioNot, String texto)
	{
		this.texto = texto;
		this.vista = false;
		this.receptorNotificacion = usuarioNot;		
	}
	
	public long getIdNotificacion()
	{
		return idNotificacion;
	}

	public void setIdNotificacion(long idNotificacion)
	{
		this.idNotificacion = idNotificacion;
	}

	public String getTexto()
	{
		return texto;
	}

	public void setTexto(String texto)
	{
		this.texto = texto;
	}

	public Boolean getVista()
	{
		return vista;
	}

	public void setVista(Boolean vista)
	{
		this.vista = vista;
	}

	public Usuario getReceptorNotificacion()
	{
		return receptorNotificacion;
	}

	public void setReceptorNotificacion(Usuario receptorNotificacion)
	{
		this.receptorNotificacion = receptorNotificacion;
	}
	
}
