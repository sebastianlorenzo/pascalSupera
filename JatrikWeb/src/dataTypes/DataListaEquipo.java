package dataTypes;

import java.util.ArrayList;
import java.util.List;

import dataTypes.DataEquipo;

public class DataListaEquipo {
	public List<DataEquipo> listEquipos;
	
	public DataListaEquipo()
	{
		this.listEquipos = new ArrayList<DataEquipo>();
	}
	
	public List<DataEquipo> getListEquipos() 
	{
		return listEquipos;
	}
	
	public void setListEquipos(List<DataEquipo> listEquipos) 
	{
		this.listEquipos = listEquipos;
	}
	
	public void addDataEquipo(DataEquipo deq)
	{
		listEquipos.add(deq);
	}
}
