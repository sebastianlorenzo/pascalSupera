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

import dataTypes.DataListaPartido;

import com.google.gson.Gson;

import controladores.VistaWebController;
import dataTypes.DataResumenPartido;

@ManagedBean
@ViewScoped
public class HistorialPartidosBean {

	private List<String> campeonatos;
	private String campeonato_seleccionado;
	private List <DataResumenPartido> ldrp;
	private Integer amarillas_local;
	private Integer amarillas_visitante;
	private Integer goles_local;
	private Integer goles_visitante;
	
	
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

	public Integer getAmarillas_local() {
		return amarillas_local;
	}

	public void setAmarillas_local(Integer amarillas_local) {
		this.amarillas_local = amarillas_local;
	}

	public Integer getAmarillas_visitante() {
		return amarillas_visitante;
	}

	public void setAmarillas_visitante(Integer amarillas_visitante) {
		this.amarillas_visitante = amarillas_visitante;
	}

	public Integer getGoles_local() {
		return goles_local;
	}

	public void setGoles_local(Integer goles_local) {
		this.goles_local = goles_local;
	}

	public Integer getGoles_visitante() {
		return goles_visitante;
	}

	public void setGoles_visitante(Integer goles_visitante) {
		this.goles_visitante = goles_visitante;
	}

	public String getCampeonato_seleccionado() {
		return campeonato_seleccionado;
	}

	public void setCampeonato_seleccionado(String campeonato_seleccionado) {
		this.campeonato_seleccionado = campeonato_seleccionado;
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

