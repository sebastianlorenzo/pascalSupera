package dominio;

import javax.persistence.*;

@Entity
@Table(name = "comentario", schema = "public")
public class Comentario  implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer idComentario;
	private Integer minuto;
	private String comentario;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private PartidoResultado partidoResultado;
	
	
	public Comentario() {}
	
	public Comentario(Integer minuto, String comentario, PartidoResultado partidoResultado) 
	{
		this.minuto           = minuto;
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
	
	public Integer getMinuto()
	{
		return minuto;
	}
	
	public void setMinuto(Integer minuto)
	{
		this.minuto = minuto;
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