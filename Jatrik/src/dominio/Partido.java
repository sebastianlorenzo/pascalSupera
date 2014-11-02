package dominio;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import dominio.Estadio;

@Entity
@Table(name = "partido", schema = "public")
public class Partido implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String partido;	
	
	@ManyToOne
	private Equipo equipoLocal;	
	
	@ManyToOne
	private Equipo equipoVisitante;	
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaPartido;
		
	@ManyToOne
	private Estadio estadio;
	
	@ManyToOne
	private Campeonato campeonato;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(nullable=true)
	private Collection<Cambio> cambiosLocal;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(nullable=true)
	private Collection<Cambio> cambiosVisitante;
	
	// Para el resultado del partido
	private Integer tarjetasAmarillasLocal;
	private Integer tarjetasAmarillasVisitante;
	private Integer tarjetasRojasLocal;
	private Integer tarjetasRojasVisitante;
	private Integer golesLocal;
	private Integer golesVisitante;
	private Integer lesionesLocal;
	private Integer lesionesVisitante;	
	@OneToMany
	private Collection<Comentario> comentarios;
	
	
	public Partido(){}
	
	public Partido (String partido, Equipo equipoLocal, Equipo equipoVisitante, Date fechaPartido, Estadio estadio, 
					Campeonato campeonato, Collection<Cambio> cambiosLocal, Collection<Cambio> cambiosVisitante)
	{	
		this.partido = partido;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.fechaPartido = fechaPartido;
		this.estadio = estadio;
		this.campeonato = campeonato;
		this.cambiosLocal     = cambiosLocal;
		this.cambiosVisitante = cambiosVisitante;
		this.tarjetasAmarillasLocal     = 0;
		this.tarjetasAmarillasVisitante = 0;
		this.tarjetasRojasLocal         = 0;
		this.tarjetasRojasVisitante     = 0;
		this.golesLocal                 = 0;
		this.golesVisitante             = 0;
		this.lesionesLocal              = 0;
		this.lesionesVisitante          = 0;
		this.comentarios                = null;
	}
	
	public String getPartido() 
	{
		return partido;
	}
	
	public void setPartido(String partido) 
	{
		this.partido = partido;
	}
	
	public Equipo getEquipoLocal() 
	{
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) 
	{
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() 
	{
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) 
	{
		this.equipoVisitante = equipoVisitante;
	}

	public Date getFechaPartido() 
	{
		return fechaPartido;
	}

	public void setFechaPartido(Date fechaPartido) 
	{
		this.fechaPartido = fechaPartido;
	}

	public Estadio getEstadio() 
	{
		return estadio;
	}

	public void setEstadio(Estadio estadio) 
	{
		this.estadio = estadio;
	}
	
	public Campeonato getCampeonato() 
	{
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) 
	{
		this.campeonato = campeonato;
	}
	
	public Collection<Cambio> getCambiosLocal() 
	{
		return cambiosLocal;
	}

	public void setCambiosLocal(Collection<Cambio> cambiosLocal) 
	{
		this.cambiosLocal = cambiosLocal;
	}

	public Collection<Cambio> getCambiosVisitante() 
	{
		return cambiosVisitante;
	}

	public void setCambiosVisitante(Collection<Cambio> cambiosVisitante) 
	{
		this.cambiosVisitante = cambiosVisitante;
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
		
}
