package tipos;

public class DataResumenPartido 
{
	
	public Integer[] tarjetasAmarillasEquipoLocalEquipoVisitante = new Integer[2];
	public Integer[] tarjetasRojasEquipoLocalEquipoVisitante 	 = new Integer[2];
	public Integer[] golesEquipoLocalEquipoVisitante 			 = new Integer[2];
	public Integer[] lesionesEquipoLocalEquipoVisitante 		 = new Integer[2];
	
	
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
	
}
