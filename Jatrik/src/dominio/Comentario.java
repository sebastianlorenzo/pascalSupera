package dominio;

import java.sql.Time;
import javax.persistence.*;

@Entity
@Table(name = "comentario", schema = "public")
public class Comentario  implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer idComentario;
	private Time hora;
	private String comentario;
	
	@ManyToOne
	private PartidoResultado partidoResultado;
	
	
	public Comentario() {}
	
	public Comentario(Integer idComentario, Time hora, String comentario, PartidoResultado partidoResultado) 
	{
		this.idComentario     = idComentario;
		this.hora             = hora;
		this.comentario       = comentario;
		this.partidoResultado = partidoResultado;
	}

	public Integer getIdComentario() 
	{
		return idComentario;
	}
	
	public void setIdComentario(Integer idComentario)
	{
		this.idComentario = idComentario;
	}
	
	public Time getHora()
	{
		return hora;
	}
	
	public void setHora(Time hora)
	{
		this.hora = hora;
	}
	
	public String getComentario()
	{
		return comentario;
	}
	
	public void setComentario(String comentario)
	{
		this.comentario = comentario;
	}
	
	public PartidoResultado getPartidoResultado() 
	{
		return partidoResultado;
	}

	public void setPartidoResultado(PartidoResultado partidoResultado)
	{
		this.partidoResultado = partidoResultado;
	}
	
}
