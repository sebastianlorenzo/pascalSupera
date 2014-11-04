package dataTypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaPartido 
{
	private  List<DataResumenPartido> lstPartidos;
		
	public DataListaPartido()
	{
		this.lstPartidos = new ArrayList<DataResumenPartido>();
	}
	
	public List<DataResumenPartido> getLstPartidos() 
	{
		return lstPartidos;
	}
	
	public void setLstPartidos(List<DataResumenPartido> lstPartidos)
	{
		this.lstPartidos = lstPartidos;
	}
	
	public void addDataPartido(DataResumenPartido dp)
	{
		lstPartidos.add(dp);
	}
	
}
