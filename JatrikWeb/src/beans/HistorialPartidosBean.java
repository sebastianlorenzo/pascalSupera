package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.event.TabChangeEvent;

import tipos.DataListaPartido;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataResumenPartido;

@ManagedBean
@ViewScoped
public class HistorialPartidosBean {

	private List<String> campeonatos;
	private String campeonato_seleccionado;
	private List <DataResumenPartido> ldrp;
	private DataResumenPartido drp;
	
	
	@PostConstruct
	public void init() throws JSONException{
		this.campeonatos = new ArrayList<String>();
		VistaWebController vwc = new VistaWebController();		
		String respuesta = vwc.listarCampeonatosFinalizadosEjecucion();
		JSONObject json = new JSONObject(respuesta);
		JSONArray array = json.getJSONArray("campeonatos");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				String campeonato = ob.getString("nomCampeonato");
				this.campeonatos.add(campeonato);
			}
		}
		
	}
	
	public HistorialPartidosBean(){	
		
	}

	public List<String> getCampeonatos() {
		return campeonatos;
	}

	public void setCampeonatos(List<String> campeonatos) {
		this.campeonatos = campeonatos;
	}

	public List<DataResumenPartido> getLdrp() {
		return ldrp;
	}

	public void setLdrp(List<DataResumenPartido> ldrp) {
		this.ldrp = ldrp;
	}

	public String getCampeonato_seleccionado() {
		return campeonato_seleccionado;
	}

	public void setCampeonato_seleccionado(String campeonato_seleccionado) {
		this.campeonato_seleccionado = campeonato_seleccionado;
	}
	
	public DataResumenPartido getDrp() {
		return drp;
	}

	public void setDrp(DataResumenPartido drp) {
		this.drp = drp;
	}

	public void onChange(TabChangeEvent event) {
		if (this.campeonato_seleccionado!=null){
			VistaWebController vwc = new VistaWebController();		
			String respuesta = vwc.listarPartidosJugados(this.campeonato_seleccionado);
			Gson g = new Gson();
			DataListaPartido dataPartidos =g.fromJson(respuesta, DataListaPartido.class);
			this.ldrp = dataPartidos.getLstPartidos();
			
		}
		
	}
	
}

