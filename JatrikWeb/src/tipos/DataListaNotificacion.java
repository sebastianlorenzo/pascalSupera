package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataListaNotificacion 
{
	public List<DataNotificacion> listNotificaciones;
	
	public DataListaNotificacion()
	{
		this.listNotificaciones = new ArrayList<DataNotificacion>();
	}
	
	public List<DataNotificacion> getListNotificaciones() 
	{
		return listNotificaciones;
	}
	
	public void setListNotificaciones(List<DataNotificacion> listNotificaciones) 
	{
		this.listNotificaciones = listNotificaciones;
	}
	
	public void addDataNotificacion(DataNotificacion dnot)
	{
		listNotificaciones.add(dnot);
	}
}
