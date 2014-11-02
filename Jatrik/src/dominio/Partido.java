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
	
	/*
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partido", nullable = true)
	private PartidoResultado partidoResultado;*/
	
	
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
//		this.partidoResultado = null;
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
	/*
	public PartidoResultado getPartidoResultado() {
		return partidoResultado;
	}

	public void setPartidoResultado(PartidoResultado partidoResultado) {
		this.partidoResultado = partidoResultado;
	}*/
		
}
