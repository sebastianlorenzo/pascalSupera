package tipos;

public class DataEquipoRanking 
{
	
	String nomEquipo;
	Integer ranking;
	
	
	public DataEquipoRanking()
	{
		this.nomEquipo = null;
		this.ranking   = -1;
	}
	
	public String getNomEquipo() 
	{
		return nomEquipo;
	}
	
	public void setNomEquipo(String nomEquipo) 
	{
		this.nomEquipo = nomEquipo;
	}
	
	public Integer getRanking()
	{
		return ranking;
	}
	
	public void setRanking(Integer ranking)
	{
		this.ranking = ranking;
	}

}
