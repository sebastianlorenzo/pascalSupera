package dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jugador", schema = "public")
public class Jugador implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String jugador;
	private String posicion;
	private Integer velocidad;
	private Integer tecnica;
	private Integer ataque;
	private Integer defensa;
	private Integer porteria;
	
	@ManyToOne
	private Equipo equipo;
	
	public Jugador() {
	}

	public Jugador(String jugador, Equipo equipo, String posicion, Integer velocidad,	
	Integer tecnica, Integer ataque, Integer defensa, Integer porteria) {
		this.jugador = jugador;
		this.equipo = equipo;
		this.posicion = posicion;
		this.velocidad = velocidad;
		this.tecnica = tecnica;
		this.ataque = ataque;
		this.defensa = defensa;
		this.porteria = porteria;
	}

	
	public String getJugador() {
		return this.jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}

	
	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public Integer getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(Integer velocidad) {
		this.velocidad = velocidad;
	}

	public Integer getTecnica() {
		return tecnica;
	}

	public void setTecnica(Integer tecnica) {
		this.tecnica = tecnica;
	}

	public Integer getAtaque() {
		return ataque;
	}

	public void setAtaque(Integer ataque) {
		this.ataque = ataque;
	}

	public Integer getDefensa() {
		return defensa;
	}

	public void setDefensa(Integer defensa) {
		this.defensa = defensa;
	}

	public Integer getPorter�a() {
		return porteria;
	}

	public void setPorter�a(Integer porteria) {
		this.porteria = porteria;
	}

}