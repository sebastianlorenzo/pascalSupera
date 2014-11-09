package dominio;

import javax.persistence.*;

@Entity
@Table(name = "resultadoCampeonato", schema = "public")
public class ResultadoCampeonato implements java.io.Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer idResultadoCampeonato;
	private Integer puntaje;

	@OneToOne(fetch = FetchType.LAZY)
	private Equipo equipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Campeonato campeonato;

	
	public ResultadoCampeonato() {}
	
	public ResultadoCampeonato(Equipo equipo, Integer puntaje, Campeonato campeonato) {
		this.equipo     = equipo;
		this.puntaje    = puntaje;
		this.campeonato = campeonato;
	}

	public Integer getIdResultadoCampeonato() 
	{
		return idResultadoCampeonato;
	}

	public void setIdResultadoCampeonato(Integer idResultadoCampeonato)
	{
		this.idResultadoCampeonato = idResultadoCampeonato;
	}

	public Equipo getEquipo()
	{
		return equipo;
	}

	public void setEquipo(Equipo equipo) 
	{
		this.equipo = equipo;
	}

	public Integer getPuntaje()
	{
		return puntaje;
	}

	public void setPuntaje(Integer puntaje) 
	{
		this.puntaje = puntaje;
	}

	public Campeonato getCampeonato() 
	{
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) 
	{
		this.campeonato = campeonato;
	}
	
}
