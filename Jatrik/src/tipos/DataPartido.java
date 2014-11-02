package tipos;

public class DataPartido 
{
	public String nomPartido; //para mostrar es eqLocal vs. EqVisitante
	public String EqLocal;
	public String EqVisitante;
	public String campeonato;
	public String fecha;
	public Integer golesLocal;
	public Integer golesVisitante;
	
	
	
	public DataPartido() {}
	
	public DataPartido(String nomPartido, String eqLocal, String eqVisitante,
			String campeonato, String fecha, Integer golesLocal, Integer golesVisitante) 
	{
		this.nomPartido = nomPartido;
		this.EqLocal = eqLocal;
		this.EqVisitante = eqVisitante;
		this.campeonato = campeonato;
		this.fecha = fecha;
		this.golesLocal = golesLocal;
		this.golesVisitante = golesVisitante;
	}


	public String getNomPartido() 
	{
		return nomPartido;
	}
	public void setNomPartido(String nomPartido) 
	{
		this.nomPartido = nomPartido;
	}
	
	public String getEqLocal() 
	{
		return EqLocal;
	}
	public void setEqLocal(String eqLocal) 
	{
		EqLocal = eqLocal;
	}
	
	public String getEqVisitante() 
	{
		return EqVisitante;
	}
	public void setEqVisitante(String eqVisitante) 
	{
		EqVisitante = eqVisitante;
	}
	
	public String getCampeonato() 
	{
		return campeonato;
	}
	public void setCampeonato(String campeonato) 
	{
		this.campeonato = campeonato;
	}
	
	public String getFecha() 
	{
		return fecha;
	}
	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}
	
	public Integer getGolesLocal() 
	{
		return golesLocal;
	}
	public void setGolesLocal(Integer golesLocal) 
	{
		this.golesLocal = golesLocal;
	}
	
	public Integer getGolesVisitante() 
	{
		return golesVisitante;
	}
	public void setGolesVisitante(Integer golesVisitante) 
	{
		this.golesVisitante = golesVisitante;
	}
	
}
