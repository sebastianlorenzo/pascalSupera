package dataTypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaPosicion
{
	public List<DataPosicion> listPosiciones;
	
	public DataListaPosicion()
	{
		this.listPosiciones = new ArrayList<DataPosicion>();
	}
	
	public List<DataPosicion> getListPosiciones() 
	{
		return listPosiciones;
	}
	
	public void setListPosiciones(List<DataPosicion> listPosiciones) 
	{
		this.listPosiciones = listPosiciones;
	}
	
	public void addDataPosicion(DataPosicion dp)
	{
		listPosiciones.add(dp);
	}
	
}