package dataTypes;

import java.util.List;

public class DataCampeonato
{
	public String nomCampeonato;
	public String fechaInicio;
	public Integer disponibilidad;
	public List<String> equiposCampeonato;
	
	public DataCampeonato()
	{
		
	}
	
	public DataCampeonato(String nomCampeonato, String fechaInicio, Integer disponibilidad, List<String> equiposCampeonato)
	{
		this.nomCampeonato = nomCampeonato;
		this.fechaInicio = fechaInicio;
		this.disponibilidad = disponibilidad;
		this.equiposCampeonato = equiposCampeonato;	
		
	}

	public String getNomCampeonato()
	{
		return nomCampeonato;
	}

	public void setNomCampeonato(String nomCampeonato)
	{
		this.nomCampeonato = nomCampeonato;
	}

	public String getFechaInicio()
	{
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio)
	{
		this.fechaInicio = fechaInicio;
	}

	public List<String> getEquiposCampeonato()
	{
		return equiposCampeonato;
	}

	public void setEquiposCampeonato(List<String> equiposCampeonato)
	{
		this.equiposCampeonato = equiposCampeonato;
	}
	

	public Integer getDisponibilidad()
	{
		return disponibilidad;
	}

	public void setDisponibilidad(Integer disponibilidad)
	{
		this.disponibilidad = disponibilidad;
	}

}
