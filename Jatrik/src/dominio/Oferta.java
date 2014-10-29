package dominio;

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
	
	@ManyToOne
	Jugador jugadorEnVenta;
	
	
	public Oferta(){}

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
	

}
