package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controladores.VistaWebController;
import tipos.DataRanking;

@ManagedBean
@RequestScoped
public class VerRankingBean {

	List<DataRanking> ldr;
	
	@PostConstruct
	public void init() throws JSONException{
		this.ldr = new ArrayList<DataRanking>();
		VistaWebController vwc = new VistaWebController();
		String respuesta = vwc.verRanking();
		JSONObject json = new JSONObject(respuesta);
		JSONArray array = json.getJSONArray("ranking");
		for (int i = 0; i < array.length(); i++) {
			JSONObject ob = array.getJSONObject(i);
			String posicion = ob.getString("posicion");
			String nomUsr = ob.getString("usuario");
			String nomEquipo = ob.getString("equipo");
			DataRanking dr = new DataRanking(posicion, nomUsr,nomEquipo);
			this.ldr.add(dr);
		}
	}
	
	public VerRankingBean(){	
		
	}

	public List<DataRanking> getLdr() {
		return ldr;
	}

	public void setLdr(List<DataRanking> ldr) {
		this.ldr = ldr;
	}
}
