package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataListaPartido 
{
	private  List<DataPartido> lstPartidos;
		
	public DataListaPartido()
	{
		this.lstPartidos = new ArrayList<DataPartido>();
	}
	
	public List<DataPartido> getLstPartidos() 
	{
		return lstPartidos;
	}
	
	public void setLstPartidos(List<DataPartido> lstPartidos)
	{
		this.lstPartidos = lstPartidos;
	}
	
	public void addDataPartido(DataPartido dp)
	{
		lstPartidos.add(dp);
	}
	
}
