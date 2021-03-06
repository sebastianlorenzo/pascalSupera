package dominio;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "jugador", schema = "public")
public class Jugador implements java.io.Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer idJugador;
	
	private String jugador;
	private String posicion;
	private String posicion_ideal;
	private Float velocidad;
	private Float tecnica; // Equivale al regate
	private Float ataque;  // Equivale a potencia
	private Float defensa;
	private Float porteria;
	private String  estado_jugador; //{titular, suplente, lesionado, expulsado}
	private Integer cant_tarjetas_amarillas; // Se usa para contar las tarjetas amarillas POR PARTIDO

	@ManyToOne
	private Equipo equipo;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="oferta_jugadores")
	private Collection<Oferta> oferta_jugadores;
	
	// Para las estadísticas del jugador
	private Integer historicoTarjetasAmarillas;
	private Integer historicoTarjetasRojas;
	private Integer historicoGoles;
	private Integer historicoLesiones;

	public Jugador(){}

	public Jugador(Integer idJugador, String jugador, Equipo equipo, String posicion, Float velocidad,	
				   Float tecnica, Float ataque, Float defensa, Float porteria, String estado_jugador,
				   Integer historicoTarjetasAmarillas, Integer historicoTarjetasRojas, Integer historicoGoles, Integer historicoLesiones) 
	{
		this.idJugador      = idJugador;
		this.jugador        = jugador;
		this.equipo         = equipo;
		this.posicion       = posicion;
		this.posicion_ideal = posicion;
		this.velocidad      = velocidad;
		this.tecnica        = tecnica;
		this.ataque         = ataque;
		this.defensa        = defensa;
		this.porteria       = porteria;
		this.estado_jugador = estado_jugador;
		this.cant_tarjetas_amarillas    = 0;
		this.historicoTarjetasAmarillas = historicoTarjetasAmarillas;
		this.historicoTarjetasRojas     = historicoTarjetasRojas;
		this.historicoGoles             = historicoGoles;
		this.historicoLesiones          = historicoLesiones;
	}
	

	public Integer getIdJugador() 
	{
		return idJugador;
	}

	public void setIdJugador(Integer idJugador)
	{
		this.idJugador = idJugador;
	}
	
	public String getJugador() 
	{
		return this.jugador;
	}

	public void setJugador(String jugador) 
	{
		this.jugador = jugador;
	}
	
	public Equipo getEquipo() 
	{
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) 
	{
		this.equipo = equipo;
	}

	public String getPosicion() 
	{
		return posicion;
	}

	public void setPosicion(String posicion) 
	{
		this.posicion = posicion;
	}

	public String getPosicion_ideal() 
	{
		return posicion_ideal;
	}

	public void setPosicion_ideal(String posicion_ideal) 
	{
		this.posicion_ideal = posicion_ideal;
	}

	public String getPosicionIdeal() 
	{
		return posicion_ideal;
	}

	public void setPosicionIdeal(String posicionIdeal) 
	{
		this.posicion_ideal = posicionIdeal;
	}
	
	public Float getVelocidad() 
	{
		return velocidad;
	}

	public void setVelocidad(Float velocidad) 
	{
		this.velocidad = velocidad;
	}

	public Float getTecnica() 
	{
		return tecnica;
	}

	public void setTecnica(Float tecnica)
	{
		this.tecnica = tecnica;
	}

	public Float getAtaque() 
	{
		return ataque;
	}

	public void setAtaque(Float ataque) 
	{
		this.ataque = ataque;
	}

	public Float getDefensa()
	{
		return defensa;
	}

	public void setDefensa(Float defensa) 
	{
		this.defensa = defensa;
	}

	public Float getPorteria()
	{
		return porteria;
	}

	public void setPorteria(Float porteria) 
	{
		this.porteria = porteria;
	}
	
	public String getEstado_jugador() 
	{
		return estado_jugador;
	}

	public void setEstado_jugador(String estado_jugador) 
	{
		this.estado_jugador = estado_jugador;
	}

	public Collection<Oferta> getOferta_jugadores()
	{
		return oferta_jugadores;
	}

	public void setOferta_jugadores(Collection<Oferta> oferta_jugadores) 
	{
		this.oferta_jugadores = oferta_jugadores;
	}

	public Integer getCant_tarjetas_amarillas() 
	{
		return cant_tarjetas_amarillas;
	}

	public void setCant_tarjetas_amarillas(Integer cant_tarjetas_amarillas) 
	{
		this.cant_tarjetas_amarillas = cant_tarjetas_amarillas;
	}

	public Integer getHistoricoTarjetasAmarillas() 
	{
		return historicoTarjetasAmarillas;
	}

	public void setHistoricoTarjetasAmarillas(Integer historicoTarjetasAmarillas)
	{
		this.historicoTarjetasAmarillas = historicoTarjetasAmarillas;
	}

	public Integer getHistoricoTarjetasRojas() 
	{
		return historicoTarjetasRojas;
	}

	public void setHistoricoTarjetasRojas(Integer historicoTarjetasRojas) 
	{
		this.historicoTarjetasRojas = historicoTarjetasRojas;
	}

	public Integer getHistoricoGoles()
	{
		return historicoGoles;
	}

	public void setHistoricoGoles(Integer historicoGoles)
	{
		this.historicoGoles = historicoGoles;
	}

	public Integer getHistoricoLesiones() 
	{
		return historicoLesiones;
	}

	public void setHistoricoLesiones(Integer historicoLesiones) 
	{
		this.historicoLesiones = historicoLesiones;
	}
}
