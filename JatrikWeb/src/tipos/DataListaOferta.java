package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataListaOferta 
{
	public List<DataOferta> listOfertas;
	
	public DataListaOferta()
	{
		this.listOfertas = new ArrayList<DataOferta>();
	}
	
	public List<DataOferta> getListOfertas() 
	{
		return listOfertas;
	}
	
	public void setListOfertas(List<DataOferta> listOfertas) 
	{
		this.listOfertas = listOfertas;
	}
	
	public void addDataOferta(DataOferta dof)
	{
		listOfertas.add(dof);
	}

}
