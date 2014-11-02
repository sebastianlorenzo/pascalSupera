package dominio;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "partidoResultado", schema = "public")
public class PartidoResultado implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.AUTO)
	private Integer idPartidoResultado;
	private Integer tarjetasAmarillasLocal;
	private Integer tarjetasAmarillasVisitante;
	private Integer tarjetasRojasLocal;
	private Integer tarjetasRojasVisitante;
	private Integer golesLocal;
	private Integer golesVisitante;
	private Integer lesionesLocal;
	private Integer lesionesVisitante;
	
	/*
	@OneToOne(fetch = FetchType.LAZY)
	private Partido partido;*/
	
	@OneToMany
	private Collection<Comentario> comentarios;
	
	
	public PartidoResultado() {}
	
	public PartidoResultado(Integer tarjetasAmarillasLocal, Integer tarjetasAmarillasVisitante, Integer tarjetasRojasLocal,
							Integer tarjetasRojasVisitante,	Integer golesLocal, Integer golesVisitante,
							Integer lesionesLocal, Integer lesionesVisitante, Collection<Comentario> comentarios) 
	{
		this.tarjetasAmarillasLocal     = tarjetasAmarillasLocal;
		this.tarjetasAmarillasVisitante = tarjetasAmarillasVisitante;
		this.tarjetasRojasLocal         = tarjetasRojasLocal;
		this.tarjetasRojasVisitante     = tarjetasRojasVisitante;
		this.golesLocal                 = golesLocal;
		this.golesVisitante             = golesVisitante;
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
	
	public Integer getGolesVisitante()
	{
		return golesVisitante;
	}
	
	public void setGolesVisitante(Integer golesVisitante) 
	{
		this.golesVisitante = golesVisitante;
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

/*
	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
*/	
}