package dataTypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaMensaje
{
	private  List<DataMensaje> lstMensajes;
	
	public DataListaMensaje()
	{
		this.lstMensajes = new ArrayList<DataMensaje>();
	}

	public List<DataMensaje> getLstMensajes() 
	{
		return lstMensajes;
	}

	public void setLstMensajes(List<DataMensaje> lstMensajes)
	{
		this.lstMensajes = lstMensajes;
	}
	
	public void addDataMensaje(DataMensaje dm)
	{
		lstMensajes.add(dm);
	}
	
}
