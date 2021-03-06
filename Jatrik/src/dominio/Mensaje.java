package dominio;

import javax.persistence.*;

@Entity
@Table (name = "mensaje", schema = "public")
public class Mensaje implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long idMensaje;	
	private String texto;	
	private Boolean leido;
	
	@ManyToOne
	Usuario receptorMensaje;
	
	@ManyToOne
	Usuario emisorMensaje;
	
	public Mensaje (){}
	
	public Mensaje(Usuario emisor, Usuario receptor, String texto)
	{
		this.emisorMensaje = emisor;
		this.receptorMensaje = receptor;
		this.texto = texto;
		this.leido = false;
	}

	public long getIdMensaje() 
	{
		return idMensaje;
	}

	public void setIdMensaje(long idMensaje) 
	{
		this.idMensaje = idMensaje;
	}
	
	public String getTexto() 
	{
		return texto;
	}

	public void setTexto(String texto) 
	{
		this.texto = texto;
	}

	public Usuario getReceptorMensaje() 
	{
		return receptorMensaje;
	}

	public void setReceptorMensaje(Usuario receptorMensaje) 
	{
		this.receptorMensaje = receptorMensaje;
	}

	public Usuario getEmisorMensaje() 
	{
		return emisorMensaje;
	}

	public void setEmisorMensaje(Usuario emisorMensaje) 
	{
		this.emisorMensaje = emisorMensaje;
	}

	public Boolean getLeido() {
		return leido;
	}

	public void setLeido(Boolean leido) {
		this.leido = leido;
	}	

}
