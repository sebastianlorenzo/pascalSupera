package dominio;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "oferta", schema = "public")
public class Oferta implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long idOferta;	
	private String estado_oferta; // aceptada, rechazada, pendiente	
	private Integer precio;	
	private Date fecha_oferta;
	private String comentario;// comentario opcional para quien realiza la oferta
	private String comentario_acepta; // comentario opcional para quién acepta o rechaza
	
	@ManyToOne
	Jugador jugadorEnVenta;
	
	@ManyToOne
	Equipo equipoOrigen;// equipo al que pertenece
	
	@ManyToOne
	Equipo equipoDestino;// equipo que oferta y posible comprador
	
	public Oferta(Integer precio, Date fecha, Jugador jugador, Equipo eqActual, Equipo eqFuturo)
	{
		this.precio = precio;
		this.fecha_oferta = fecha;
		this.jugadorEnVenta = jugador;
		this.equipoOrigen = eqActual;
		this.equipoDestino = eqFuturo;
	}
	
	public Oferta (){}

	public long getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(long idOferta) {
		this.idOferta = idOferta;
	}

	public Jugador getJugadorEnVenta() {
		return jugadorEnVenta;
	}

	public void setJugadorEnVenta(Jugador jugadorEnVenta) {
		this.jugadorEnVenta = jugadorEnVenta;
	}

	public String getEstado_oferta() {
		return estado_oferta;
	}

	public void setEstado_oferta(String estado_oferta) {
		this.estado_oferta = estado_oferta;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Date getFecha_oferta() {
		return fecha_oferta;
	}

	public void setFecha_oferta(Date fecha_oferta) {
		this.fecha_oferta = fecha_oferta;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	

	public Equipo getEquipoOrigen() {
		return equipoOrigen;
	}

	public void setEquipoOrigen(Equipo equipoOrigen) {
		this.equipoOrigen = equipoOrigen;
	}

	public Equipo getEquipoDestino() {
		return equipoDestino;
	}

	public void setEquipoDestino(Equipo equipoDestino) {
		this.equipoDestino = equipoDestino;
	}

	public String getComentario_acepta() {
		return comentario_acepta;
	}

	public void setComentario_acepta(String comentario_acepta) {
		this.comentario_acepta = comentario_acepta;
	}

}
