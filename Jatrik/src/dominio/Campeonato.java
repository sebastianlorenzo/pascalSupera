package dominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import dominio.Partido;

@Entity
@Table(name = "campeonato", schema = "public")
public class Campeonato 
{
	
	@Id
	private String campeonato;
	
	private Date inicioCampeonato;
	
	private Integer cantEquipos;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private Collection<Equipo> equipos;
	
	public Campeonato(){}
	
	public Campeonato(String campeonato, Date inicioCampeonato, Integer cantEquipos)
	{
		this.campeonato = campeonato;
		this.inicioCampeonato = inicioCampeonato;
		this.cantEquipos = cantEquipos;
		Collection<Partido> partidos = new ArrayList<Partido>();
		this.partidos = partidos;
		Collection<Equipo> equipos = new ArrayList<Equipo>();
		this.equipos = equipos;
	}

	public String getCampeonato() 
	{
		return campeonato;
	}

	public void setCampeonato(String campeonato)
	{
		this.campeonato = campeonato;
	}
	
	public Date getInicioCampeonato() 
	{
		return inicioCampeonato;
	}

	public void setInicioCampeonato(Date inicioCampeonato)
	{
		this.inicioCampeonato = inicioCampeonato;
	}
	
	public void setPartidos(Collection<Partido> partidos)
	{
		this.partidos = partidos;
	}

	public Collection<Partido> getPartidos() 
	{
		return partidos;
	}
	
	public Collection<Equipo> getEquipos() 
	{
		return equipos;
	}
	
	public void setEquipos(Collection<Equipo> equipos)
	{
		this.equipos = equipos;
	}

	public Integer getCantEquipos() {
		return cantEquipos;
	}

	public void setCantEquipos(Integer cantEquipos) {
		this.cantEquipos = cantEquipos;
	}
}
