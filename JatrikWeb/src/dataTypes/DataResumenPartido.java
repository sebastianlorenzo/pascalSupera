package dataTypes;

import java.util.List;

public class DataResumenPartido 
{
	public String nomPartido; //para mostrar es eqLocal vs. eqVisitante
	public String eqLocal;
	public String eqVisitante;
	public String campeonato;
	public String fecha;
	
	public Integer[] tarjetasAmarillasEquipoLocalEquipoVisitante = new Integer[2];
	public Integer[] tarjetasRojasEquipoLocalEquipoVisitante 	 = new Integer[2];
	public Integer[] golesEquipoLocalEquipoVisitante 			 = new Integer[2];
	public Integer[] lesionesEquipoLocalEquipoVisitante 		 = new Integer[2];
	
	public List<String> detalle;
	
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
		this.tarjetasAmarillasEquipoLocalEquipoVisitante[0] = tarjetasAmarillasLocal;
		this.tarjetasAmarillasEquipoLocalEquipoVisitante[1] = tarjetasAmarillasVisitante;
		this.tarjetasRojasEquipoLocalEquipoVisitante[0]     = tarjetasRojasLocal;
		this.tarjetasRojasEquipoLocalEquipoVisitante[1]     = tarjetasRojasVisitante;
		this.golesEquipoLocalEquipoVisitante[0]             = golesLocal;
		this.golesEquipoLocalEquipoVisitante[1]             = golesVisitante;
		this.lesionesEquipoLocalEquipoVisitante[0]          = lesionesLocal;
		this.lesionesEquipoLocalEquipoVisitante[1]          = lesionesVisitante;
	}
	
	public DataResumenPartido(Integer tarjetasAmarillasLocal, Integer tarjetasAmarillasVisitante, 
							  Integer tarjetasRojasLocal, Integer tarjetasRojasVisitante,
							  Integer gloesLocal,Integer gloesVisitante,
							  Integer lesionesLocal, Integer lesionesVisitante)
	{
		this.tarjetasAmarillasEquipoLocalEquipoVisitante[0] = tarjetasAmarillasLocal;
		this.tarjetasAmarillasEquipoLocalEquipoVisitante[1] = tarjetasAmarillasVisitante;
		this.tarjetasRojasEquipoLocalEquipoVisitante[0]     = tarjetasRojasLocal;
		this.tarjetasRojasEquipoLocalEquipoVisitante[1]     = tarjetasRojasVisitante;
		this.golesEquipoLocalEquipoVisitante[0]             = gloesLocal;
		this.golesEquipoLocalEquipoVisitante[1]             = gloesVisitante;
		this.lesionesEquipoLocalEquipoVisitante[0]          = lesionesLocal;
		this.lesionesEquipoLocalEquipoVisitante[1]          = lesionesVisitante;
	}
	
	public Integer[] getTarjetasAmarillasEquipoLocalEquipoVisitante() 
	{
		return tarjetasAmarillasEquipoLocalEquipoVisitante;
	}
	
	public void setTarjetasAmarillasEquipoLocalEquipoVisitante (Integer[] tarjetasAmarillasEquipoLocalEquipoVisitante) 
	{
		this.tarjetasAmarillasEquipoLocalEquipoVisitante = tarjetasAmarillasEquipoLocalEquipoVisitante;
	}
	
	public Integer[] getTarjetasRojasEquipoLocalEquipoVisitante()
	{
		return tarjetasRojasEquipoLocalEquipoVisitante;
	}
	
	public void setTarjetasRojasEquipoLocalEquipoVisitante (Integer[] tarjetasRojasEquipoLocalEquipoVisitante) 
	{
		this.tarjetasRojasEquipoLocalEquipoVisitante = tarjetasRojasEquipoLocalEquipoVisitante;
	}
	
	public Integer[] getGolesEquipoLocalEquipoVisitante() 
	{
		return golesEquipoLocalEquipoVisitante;
	}
	
	public void setGolesEquipoLocalEquipoVisitante (Integer[] golesEquipoLocalEquipoVisitante) 
	{
		this.golesEquipoLocalEquipoVisitante = golesEquipoLocalEquipoVisitante;
	}
	
	public Integer[] getLesionesEquipoLocalEquipoVisitante() 
	{
		return lesionesEquipoLocalEquipoVisitante;
	}
	
	public void setLesionesEquipoLocalEquipoVisitante (Integer[] lesionesEquipoLocalEquipoVisitante) 
	{
		this.lesionesEquipoLocalEquipoVisitante = lesionesEquipoLocalEquipoVisitante;
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

	public List<String> getDetalle()
	{
		return detalle;
	}

	public void setDetalle(List<String> detalle) 
	{
		this.detalle = detalle;
	}
	
}
