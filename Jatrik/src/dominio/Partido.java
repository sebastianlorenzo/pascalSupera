package dominio;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dominio.Estadio;

@Entity
@Table(name = "partido", schema = "public")
public class Partido implements java.io.Serializable{

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
	
	
	public Partido() {
	}
	
	public Partido (Equipo equipoLocal, Equipo equipoVisitante, Date fechaPartido, Estadio estadio){
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.fechaPartido = fechaPartido;
		this.estadio = estadio;
	}
	
	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public Date getFechaPartido() {
		return fechaPartido;
	}

	public void setFechaPartido(Date fechaPartido) {
		this.fechaPartido = fechaPartido;
	}

	public Estadio getEstadio() {
		return estadio;
	}

	public void setEstadio(Estadio estadio) {
		this.estadio = estadio;
	}
	

}
