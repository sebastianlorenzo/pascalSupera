package tipos;

import java.util.ArrayList;
import java.util.List;

public class DataListaGanadoresCamp 
{
	
	private List<DataGanadoresCamp> listaGanadoresCamp;
	
	
	public DataListaGanadoresCamp()
	{
		this.listaGanadoresCamp = new ArrayList<DataGanadoresCamp>();
	}

	public List<DataGanadoresCamp> getListaGanadoresCamp() 
	{
		return listaGanadoresCamp;
	}

	public void setListaGanadoresCamp(List<DataGanadoresCamp> listaGanadoresCamp) 
	{
		this.listaGanadoresCamp = listaGanadoresCamp;
	}
	
}
