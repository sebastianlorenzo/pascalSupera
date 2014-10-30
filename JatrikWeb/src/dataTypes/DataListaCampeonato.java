package dataTypes;

import java.util.ArrayList;
import java.util.List;

public class DataListaCampeonato
{
	public List<DataCampeonato> listCampeonatos;
	
	public DataListaCampeonato()
	{
		this.listCampeonatos = new ArrayList<DataCampeonato>();
	}
	
	public List<DataCampeonato> getListCampeonatos() 
	{
		return listCampeonatos;
	}
	
	public void setListCampeonatos(List<DataCampeonato> listCampeonatos) 
	{
		this.listCampeonatos = listCampeonatos;
	}
	
	public void addDataCampeonato(DataCampeonato dca)
	{
		listCampeonatos.add(dca);
	}


}
