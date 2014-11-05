package dominio;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "campeonato", schema = "public")
public class ResultadoCampeonato {
	
	@Id
	@GeneratedValue
	private Integer idResultadoCampeonato;
	private Equipo equipo;
	private Integer puntaje;
	
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
