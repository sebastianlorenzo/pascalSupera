package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;

import prueba.DataNoticia;
import prueba.DataNoticias;

@ManagedBean
public class carouselVistaNoticias {

	private List<DataNoticia> list_noticias;
	
	@PostConstruct
	public void init(){
		
		DataNoticia dn = new DataNoticia("1", "2", 4,0,1, 2,"Centenario");
		DataNoticia dn2 = new DataNoticia("4", "2", 6,0,2, 2,"Monumental");
		DataNoticia dn3 = new DataNoticia("0", "0", 5,2,0, 0,"Parque Saroldi");
		DataNoticia dn4 = new DataNoticia("1", "1", 6,0,2, 2,"Monumental");
		DataNoticia dn5 = new DataNoticia("0", "2", 6,0,2, 2,"Centenario");
		DataNoticia dn6 = new DataNoticia("2", "2", 0,0,0, 2,"Franzini");
		DataNoticia dn7 = new DataNoticia("5", "2", 0,2,0, 1,"Centenario");
		DataNoticias l = new DataNoticias();
		l.agregar(dn);
		l.agregar(dn2);
		l.agregar(dn3);
		l.agregar(dn4);
		l.agregar(dn5);
		l.agregar(dn6);
		l.agregar(dn7);
		Gson g = new Gson();
		String string_lista = g.toJson(l);
		DataNoticias dns = (DataNoticias) g.fromJson(string_lista, DataNoticias.class);
		list_noticias = dns.getL();
		
	}

	public List<DataNoticia> getList_noticias() {
		return list_noticias;
	}

	public void setList_noticias(List<DataNoticia> list_noticias) {
		this.list_noticias = list_noticias;
	}
	
	
}
