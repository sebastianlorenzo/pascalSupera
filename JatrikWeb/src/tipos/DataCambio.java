package tipos;

public class DataCambio
{
	
	private Integer idJugadorEntrante;
	private Integer idJugadorSaliente;
	private Integer minutoCambio;
	private String  partido;

	
	public DataCambio(){}
	
	public DataCambio(Integer idJugadorEntrante, Integer idJugadorSaliente, Integer minutoCambio, String partido)
	{
		this.idJugadorEntrante = idJugadorEntrante;
		this.idJugadorSaliente = idJugadorSaliente;
		this.minutoCambio	   = minutoCambio;
		this.partido 		   = partido;
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
	
	public Integer getMinutoCambio() 
	{
		return minutoCambio;
	}
	
	public void setMinutoCambio(Integer minutoCambio) 
	{
		this.minutoCambio = minutoCambio;
	}
	
	public String getPartido() 
	{
		return partido;
	}

	public void setPartido(String partido) 
	{
		this.partido = partido;
	}
	
}
