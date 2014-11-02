package dataTypes;

public class DataPosicion
{
	private Integer idJugador;
	private String posicion;
	
	public DataPosicion(){}
	
	public DataPosicion(Integer idJugador, String posicion)
	{
		this.idJugador = idJugador;
		this.posicion = posicion;
	}
	
	public Integer getIdJugador() 
	{
		return idJugador;
	}
	public void setIdJugador(Integer idJugador) 
	{
		this.idJugador = idJugador;
	}
	public String getPosicion() 
	{
		return posicion;
	}
	public void setPosicion(String posicion) 
	{
		this.posicion = posicion;
	}	
}