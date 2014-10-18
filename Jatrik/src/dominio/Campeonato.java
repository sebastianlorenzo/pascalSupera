package dominio;

import java.util.Collection;
import javax.persistence.*;
import dominio.Partido;

@Entity
@Table(name = "campeonato", schema = "public")
public class Campeonato 
{
	
	@Id
	private String campeonato;
	
	private Integer anio;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Equipo> equipos;
	
	public Campeonato(){}
	
	public Campeonato(String campeonato, Integer anio, Collection<Partido> partidos)
	{
		this.campeonato = campeonato;
		this.anio = anio;
		this.partidos = partidos;		
	}

	public String getCampeonato() 
	{
		return campeonato;
	}

	public void setCampeonato(String campeonato)
	{
		this.campeonato = campeonato;
	}
	
	public Integer getAnio() 
	{
		return anio;
	}

	public void setAnio(Integer anio)
	{
		this.anio = anio;
	}
	
	public void setPartidos(Collection<Partido> partidos)
	{
		this.partidos = partidos;
	}

	public Collection<Partido> getPartidos() 
	{
		return partidos;
	}

}
