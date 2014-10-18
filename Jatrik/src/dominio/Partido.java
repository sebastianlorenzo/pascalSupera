package dominio;

import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import dominio.Estadio;
import tipos.DataCambio;

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
	
	private Collection<DataCambio> cambiosLocal;	
	
	private Collection<DataCambio> cambiosVisitante;
	
	public Partido(){}
	
	public Partido (String partido, Equipo equipoLocal, Equipo equipoVisitante, Date fechaPartido, Estadio estadio, 
					Campeonato campeonato, Collection<DataCambio> cambiosLocal, Collection<DataCambio> cambiosVisitante)
	{	
		this.partido = partido;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.fechaPartido = fechaPartido;
		this.estadio = estadio;
		this.campeonato = campeonato;
		this.cambiosLocal     = cambiosLocal;
		this.cambiosVisitante = cambiosVisitante;
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
	
	public Collection<DataCambio> getCambiosLocal() 
	{
		return cambiosLocal;
	}

	public void setCambiosLocal(Collection<DataCambio> cambiosLocal) 
	{
		this.cambiosLocal = cambiosLocal;
	}

	public Collection<DataCambio> getCambiosVisitante() 
	{
		return cambiosVisitante;
	}

	public void setCambiosVisitante(Collection<DataCambio> cambiosVisitante) 
	{
		this.cambiosVisitante = cambiosVisitante;
	}
	
}
