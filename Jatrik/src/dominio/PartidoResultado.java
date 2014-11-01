package dominio;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "partidoResultado", schema = "public")
public class PartidoResultado implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer idPartidoResultado;
	private Integer tarjetasAmarillasLocal;
	private Integer tarjetasAmarillasVisitante;
	private Integer tarjetasRojasLocal;
	private Integer tarjetasRojasVisitante;
	private Integer golesLocal;
	private Integer golesVistiante;
	private Integer lesionesLocal;
	private Integer lesionesVisitante;
	
	@OneToMany
	private Collection<Comentario> comentarios;
	
	
	public PartidoResultado() {}
	
	public PartidoResultado(Integer idPartidoResultado,	Integer tarjetasAmarillasLocal, Integer tarjetasAmarillasVisitante,
							Integer tarjetasRojasLocal, Integer tarjetasRojasVisitante,	Integer golesLocal, Integer golesVistiante,
							Integer lesionesLocal, Integer lesionesVisitante, Collection<Comentario> comentarios) 
	{
		this.idPartidoResultado         = idPartidoResultado;
		this.tarjetasAmarillasLocal     = tarjetasAmarillasLocal;
		this.tarjetasAmarillasVisitante = tarjetasAmarillasVisitante;
		this.tarjetasRojasLocal         = tarjetasRojasLocal;
		this.tarjetasRojasVisitante     = tarjetasRojasVisitante;
		this.golesLocal                 = golesLocal;
		this.golesVistiante             = golesVistiante;
		this.lesionesLocal              = lesionesLocal;
		this.lesionesVisitante          = lesionesVisitante;
		this.comentarios                = comentarios;
	}
	
	public Integer getIdPartidoResultado() 
	{
		return idPartidoResultado;
	}
	
	public void setIdPartidoResultado(Integer idPartidoResultado)
	{
		this.idPartidoResultado = idPartidoResultado;
	}
	
	public Integer getTarjetasAmarillasLocal() 
	{
		return tarjetasAmarillasLocal;
	}
	
	public void setTarjetasAmarillasLocal(Integer tarjetasAmarillasLocal)
	{
		this.tarjetasAmarillasLocal = tarjetasAmarillasLocal;
	}
	
	public Integer getTarjetasAmarillasVisitante() 
	{
		return tarjetasAmarillasVisitante;
	}
	
	public void setTarjetasAmarillasVisitante(Integer tarjetasAmarillasVisitante)
	{
		this.tarjetasAmarillasVisitante = tarjetasAmarillasVisitante;
	}
	
	public Integer getTarjetasRojasLocal() 
	{
		return tarjetasRojasLocal;
	}
	
	public void setTarjetasRojasLocal(Integer tarjetasRojasLocal)
	{
		this.tarjetasRojasLocal = tarjetasRojasLocal;
	}
	
	public Integer getTarjetasRojasVisitante()
	{
		return tarjetasRojasVisitante;
	}
	
	public void setTarjetasRojasVisitante(Integer tarjetasRojasVisitante) 
	{
		this.tarjetasRojasVisitante = tarjetasRojasVisitante;
	}
	
	public Integer getGolesLocal()
	{
		return golesLocal;
	}
	
	public void setGolesLocal(Integer golesLocal)
	{
		this.golesLocal = golesLocal;
	}
	
	public Integer getGolesVistiante()
	{
		return golesVistiante;
	}
	
	public void setGolesVistiante(Integer golesVistiante) 
	{
		this.golesVistiante = golesVistiante;
	}
	
	public Integer getLesionesLocal() 
	{
		return lesionesLocal;
	}
	
	public void setLesionesLocal(Integer lesionesLocal)
	{
		this.lesionesLocal = lesionesLocal;
	}
	
	public Integer getLesionesVisitante() 
	{
		return lesionesVisitante;
	}
	
	public void setLesionesVisitante(Integer lesionesVisitante) 
	{
		this.lesionesVisitante = lesionesVisitante;
	}
	
	public Collection<Comentario> getComentarios() 
	{
		return comentarios;
	}
	
	public void setComentarios(Collection<Comentario> comentarios) 
	{
		this.comentarios = comentarios;
	}
	
}
