package tipos;

import java.util.List;

public class DataResumenPartido 
{
	public String nomPartido; //para mostrar es eqLocal vs. eqVisitante
	public String eqLocal;
	public String eqVisitante;
	public String campeonato;
	public String fecha;
	public String nomPartidoEnBase;
	public String ubicacion;

	public Integer tarjetasAmarillasEquipoLocal;
	public Integer tarjetasAmarillasEquipoVisitante;
	public Integer tarjetasRojasEquipoLocal;
	public Integer tarjetasRojasEquipoVisitante;
	public Integer golesEquipoLocal;
	public Integer golesEquipoVisitante;
	public Integer lesionesEquipoLocal;
	public Integer lesionesEquipoVisitante;	
	
	public List<String> detalle;
	
	public DataResumenPartido(){};
	
	public DataResumenPartido(String nomPartido, String eqLocal, String eqVisitante, String campeonato, String fecha,
			Integer tarjetasAmarillasLocal, Integer tarjetasAmarillasVisitante, 
			  Integer tarjetasRojasLocal, Integer tarjetasRojasVisitante,
			  Integer golesLocal,Integer golesVisitante,
			  Integer lesionesLocal, Integer lesionesVisitante) 
	{
		this.nomPartido = nomPartido;
		this.eqLocal = eqLocal;
		this.eqVisitante = eqVisitante;
		this.campeonato = campeonato;
		this.fecha = fecha;
		this.tarjetasAmarillasEquipoLocal = tarjetasAmarillasLocal;
		this.tarjetasAmarillasEquipoVisitante = tarjetasAmarillasVisitante;
		this.tarjetasRojasEquipoLocal = tarjetasRojasLocal;
		this.tarjetasRojasEquipoVisitante = tarjetasRojasVisitante;
		this.golesEquipoLocal = golesLocal;
		this.golesEquipoVisitante = golesVisitante;
		this.lesionesEquipoLocal  = lesionesLocal;
		this.lesionesEquipoVisitante= lesionesVisitante;
	}
	
	public String getNomPartido()
	{
		return nomPartido;
	}

	public void setNomPartido(String nomPartido)
	{
		this.nomPartido = nomPartido;
	}

	public String getEqLocal()
	{
		return eqLocal;
	}

	public void setEqLocal(String eqLocal)
	{
		this.eqLocal = eqLocal;
	}

	public String getEqVisitante()
	{
		return eqVisitante;
	}

	public void setEqVisitante(String eqVisitante)
	{
		this.eqVisitante = eqVisitante;
	}

	public String getCampeonato()
	{
		return campeonato;
	}

	public void setCampeonato(String campeonato)
	{
		this.campeonato = campeonato;
	}

	public String getFecha()
	{
		return fecha;
	}

	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}
	
	public String getNomPartidoEnBase() 
	{
		return nomPartidoEnBase;
	}

	public void setNomPartidoEnBase(String nomPartidoEnBase) 
	{
		this.nomPartidoEnBase = nomPartidoEnBase;
	}

	public Integer getTarjetasAmarillasEquipoLocal() 
	{
		return tarjetasAmarillasEquipoLocal;
	}

	public void setTarjetasAmarillasEquipoLocal(Integer tarjetasAmarillasEquipoLocal) 
	{
		this.tarjetasAmarillasEquipoLocal = tarjetasAmarillasEquipoLocal;
	}

	public Integer getTarjetasAmarillasEquipoVisitante() 
	{
		return tarjetasAmarillasEquipoVisitante;
	}

	public void setTarjetasAmarillasEquipoVisitante(Integer tarjetasAmarillasEquipoVisitante) 
	{
		this.tarjetasAmarillasEquipoVisitante = tarjetasAmarillasEquipoVisitante;
	}

	public Integer getTarjetasRojasEquipoLocal() 
	{
		return tarjetasRojasEquipoLocal;
	}

	public void setTarjetasRojasEquipoLocal(Integer tarjetasRojasEquipoLocal) 
	{
		this.tarjetasRojasEquipoLocal = tarjetasRojasEquipoLocal;
	}

	public Integer getTarjetasRojasEquipoVisitante() 
	{
		return tarjetasRojasEquipoVisitante;
	}

	public void setTarjetasRojasEquipoVisitante(Integer tarjetasRojasEquipoVisitante) 
	{
		this.tarjetasRojasEquipoVisitante = tarjetasRojasEquipoVisitante;
	}

	public Integer getGolesEquipoLocal() 
	{
		return golesEquipoLocal;
	}

	public void setGolesEquipoLocal(Integer golesEquipoLocal)
	{
		this.golesEquipoLocal = golesEquipoLocal;
	}

	public Integer getGolesEquipoVisitante() 
	{
		return golesEquipoVisitante;
	}

	public void setGolesEquipoVisitante(Integer golesEquipoVisitante) 
	{
		this.golesEquipoVisitante = golesEquipoVisitante;
	}

	public Integer getLesionesEquipoLocal() 
	{
		return lesionesEquipoLocal;
	}

	public void setLesionesEquipoLocal(Integer lesionesEquipoLocal)
	{
		this.lesionesEquipoLocal = lesionesEquipoLocal;
	}

	public Integer getLesionesEquipoVisitante() 
	{
		return lesionesEquipoVisitante;
	}

	public void setLesionesEquipoVisitante(Integer lesionesEquipoVisitante) 
	{
		this.lesionesEquipoVisitante = lesionesEquipoVisitante;
	}
	

	public List<String> getDetalle()
	{
		return detalle;
	}

	public void setDetalle(List<String> detalle) 
	{
		this.detalle = detalle;
	}
	
	
	public String getUbicacion() 
	{
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion = ubicacion;
	}

}
