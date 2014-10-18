package tipos;

import java.sql.Time;

public class DataCambio 
{

	public Integer idJugadorEntrante;
	public Integer idJugadorSaliente;
	public Time horaCambio;
	
	
	public DataCambio(Integer idJugadorEntrante, Integer idJugadorSaliente, Time horaCambio)
	{
		this.idJugadorEntrante = idJugadorEntrante;
		this.idJugadorSaliente = idJugadorSaliente;
		this.horaCambio		   = horaCambio;
	}
	
	public Integer getIdJugadorEntrante() 
	{
		return idJugadorEntrante;
	}
	
	public void setIdJugadorEntrante(Integer idJugadorEntrante) 
	{
		this.idJugadorEntrante = idJugadorEntrante;
	}
	
	public Integer getIdJugadorSaliente()
	{
		return idJugadorSaliente;
	}
	
	public void setIdJugadorSaliente(Integer idJugadorSaliente)
	{
		this.idJugadorSaliente = idJugadorSaliente;
	}
	
	public Time getHoraCambio() 
	{
		return horaCambio;
	}
	
	public void setHoraCambio(Time horaCambio) 
	{
		this.horaCambio = horaCambio;
	}
	
}
