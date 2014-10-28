package dominio;

import java.util.Collection;
import javax.persistence.*;
import dominio.Partido;
import dominio.Equipo;

@Entity
@Table(name = "estadio", schema = "public")
public class Estadio implements java.io.Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	private String estadio;
	
	private Integer capacidad;
	
	private Integer altura;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipo", nullable = true)
	private Equipo equipo;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	
	public Estadio(){}

	public Estadio(String estadio, Integer capacidad, Integer altura) 
	{
		this.estadio = estadio;
		this.capacidad = capacidad;
		this.altura = altura;
		this.equipo = null;
		this.partidos = null;
	}
	
	public String getEstadio() 
	{
		return this.estadio;
	}

	public void setEstadio(String estadio) 
	{
		this.estadio = estadio;
	}
	
	public Integer getCapacidad()
	{
		return this.capacidad;
	}

	public void setCapacidad(Integer capacidad) 
	{
		this.capacidad = capacidad;
	}
	
	public Equipo getEquipo()
	{
		return equipo;
	}
	public void setEquipo(Equipo equipo)
	{
		this.equipo = equipo;
	}
	
	public void setPartidos(Collection<Partido> partidos) 
	{
		this.partidos = partidos;
	}

	public Collection<Partido> getPartidos() 
	{
		return partidos;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

}
