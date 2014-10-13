package dominio;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import dominio.Partido;
import dominio.Equipo;

@Entity
@Table(name = "estadio", schema = "public")
public class Estadio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "estadio", unique = true, nullable = false)
	private String estadio;
	
	@Column(name = "capacidad", unique = true, nullable = false)
	private int capacidad;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipo", nullable = false)
	private Equipo equipo;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	public Estadio() {
	}

	public Estadio(String estadio, int capacidad) {
		this.estadio = estadio;
		this.capacidad = capacidad;
		this.equipo = null;
		this.partidos = null;
	}

	
	public String getEstadio() {
		return this.estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}
	
	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	public void setPartidos(Collection<Partido> partidos) {
		this.partidos = partidos;
	}

	public Collection<Partido> getPartidos() {
		return partidos;
	}

}
