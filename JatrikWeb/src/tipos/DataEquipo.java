package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataEquipo 
{
	public String nomEquipo;
	public List<DataJugador> listJugadores;
	
	public DataEquipo(){}
	
	public DataEquipo(String nomEquipo)
	{
		this.nomEquipo = nomEquipo;
		this.listJugadores = new ArrayList<DataJugador>();
	}
	
	public String getNomEquipo() 
	{
		return nomEquipo;
	}
	public void setNomEquipo(String nomEquipo) 
	{
		this.nomEquipo = nomEquipo;
	}
	public List<DataJugador> getListJugadores() 
	{
		return listJugadores;
	}
	public void setListJugadores(List<DataJugador> listJugadores) 
	{
		this.listJugadores = listJugadores;
	}
	
	public void addDataJugador(DataJugador djug)
	{
		listJugadores.add(djug);
	}
	
}
