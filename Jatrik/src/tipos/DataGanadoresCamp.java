package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataGanadoresCamp 
{
	
	public String nomCampeonato;
	public List<DataEquipoRanking> listaEquipos;
	
	
	public DataGanadoresCamp()
	{
		this.nomCampeonato = null;
		this.listaEquipos  = new ArrayList<DataEquipoRanking>();
	}
	
	public String getNomCampeonato() 
	{
		return nomCampeonato;
	}
	
	public void setNomCampeonato(String nomCampeonato) 
	{
		this.nomCampeonato = nomCampeonato;
	}
	
	public List<DataEquipoRanking> getListaEquipos() 
	{
		return listaEquipos;
	}
	
	public void setListaEquipos(List<DataEquipoRanking> listaEquipos)
	{
		this.listaEquipos = listaEquipos;
	}
	
}
