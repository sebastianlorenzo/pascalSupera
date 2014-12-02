package tipos;

public class DataEquipoRanking  implements Comparable<DataEquipoRanking>
{
	
	private String nomEquipo;
	private Integer ranking;
	
	
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
	
	 @Override
     public int compareTo(DataEquipoRanking d) {
         if (this.ranking > d.getRanking()) {
             return -1;
         }
         if (this.ranking < d.getRanking()) {
             return 1;
         }
         return 0;
     }
}
