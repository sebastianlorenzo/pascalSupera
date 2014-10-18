package prueba;

import java.util.ArrayList;
import java.util.List;

public class DataNoticias {

	private List<DataNoticia> l = new ArrayList<DataNoticia>();

	public List<DataNoticia> getL() {
		return l;
	}
	
	public void agregar(DataNoticia dn){
		l.add(dn);
	}

	public void setL(List<DataNoticia> l) {
		this.l = l;
	}
	
}
