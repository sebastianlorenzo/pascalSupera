package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataListaJugador
{
	public List<DataJugador> listJugadores;
	
	public DataListaJugador()
	{
		this.listJugadores = new ArrayList<DataJugador>();
	}
	
	public List<DataJugador> getListJugadores() 
	{
		return listJugadores;
	}
	
	public void setListJugadores(List<DataJugador> listJugadores) 
	{
		this.listJugadores = listJugadores;
	}
	
	public void addDataJugador(DataJugador dj)
	{
		listJugadores.add(dj);
	}


}
