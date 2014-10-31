package dominio;
import javax.persistence.*;

@Entity
@Table(name = "cambio", schema = "public")
public class Cambio implements java.io.Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer idCambio;
	private Integer idJugadorEntrante;
	private Integer idJugadorSaliente;
	private Integer minutoCambio;
	
	@ManyToOne()
	private Partido partido;

	
	public Cambio(){}
	
	public Cambio(Integer idJugadorEntrante, Integer idJugadorSaliente, Integer minutoCambio, Partido partido)
	{
		this.idJugadorEntrante = idJugadorEntrante;
		this.idJugadorSaliente = idJugadorSaliente;
		this.minutoCambio	   = minutoCambio;
		this.partido 		   = partido;
	}
	
	public Integer getIdCambio() 
	{
		return idCambio;
	}

	public void setIdCambio(Integer idCambio)
	{
		this.idCambio = idCambio;
	}
	
	public Integer getIdJugadorEntrante() 
	{
		return idJugadorEntrante;
	}
	
	public void setIdJugadorEntrante(Integer idJugadorEntrante) 
	{
		this.idJugadorEntrante = idJugadorEntrante;
	}
	
	public Integer getIdJugadorSaliente()
	{
		return idJugadorSaliente;
	}
	
	public void setIdJugadorSaliente(Integer idJugadorSaliente)
	{
		this.idJugadorSaliente = idJugadorSaliente;
	}
	
	public Integer getMinutoCambio() 
	{
		return minutoCambio;
	}
	
	public void setMinutoCambio(Integer minutoCambio) 
	{
		this.minutoCambio = minutoCambio;
	}
	

	public Partido getPartido() 
	{
		return partido;
	}

	public void setPartido(Partido partido) 
	{
		this.partido = partido;
	}
	
}
