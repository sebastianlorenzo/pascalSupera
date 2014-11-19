package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import tipos.DataCampeonato;
import tipos.DataListaPartido;
import tipos.DataResumenPartido;

import com.google.gson.Gson;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class MapaPartidosBean {
	
	private List<String> campeonatos;
	private String campeonato_seleccionado;
	private List <DataResumenPartido> ldrp;
	private DataResumenPartido drp;
	
	private MapModel model;
	private MapModel model2;
	private MapModel model3;
	private String googleKey="AIzaSyAqLuGawbfE7GsJbJH2ZJvb6Z02UoAhfIo";
	private List<Marker> lista_lugares; // lista de LatLng
	
	
	@PostConstruct
	public void init() throws JSONException{
		this.campeonatos = new ArrayList<String>();
		VistaWebController vwc = new VistaWebController();		
		String respuesta = vwc.listarCampeonatosFinalizadosEjecucion();
		JSONObject json = new JSONObject(respuesta);
		JSONArray array = json.getJSONArray("listCampeonatos");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				JSONObject ob = array.getJSONObject(i);
				String campeonato = ob.getString("nomCampeonato");
				this.campeonatos.add(campeonato);
				DataCampeonato camp = new DataCampeonato();
				camp.setNomCampeonato(campeonato);
			}
		}
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
	
	public void mostrarPartidos() throws JSONException{
		
		this.lista_lugares = new ArrayList<Marker>();
		model = new DefaultMapModel();
		
		VistaWebController vwc = new VistaWebController();
		String campeonato = this.campeonato_seleccionado;
		if (!campeonato.equals("")){
			String respuesta2 = vwc.listarPartidosMapa(campeonato);
			Gson g = new Gson();
			DataListaPartido dataPartidos =g.fromJson(respuesta2, DataListaPartido.class);
			this.ldrp = dataPartidos.getLstPartidos();
			
			System.out.println("estos son los partidos a listar en mapa");
			System.out.println(respuesta2);
			
			JSONArray aux = new JSONArray();
			for (int i=0; i<ldrp.size();i++){
				String nomPartido = (String) ldrp.get(i).getNomPartido();
				String lugar = (String) ldrp.get(i).getUbicacion();
				Integer k = lugar(lugar,aux);
				if(k==-1){
					JSONObject nuevo_json = new JSONObject();
					nuevo_json.put("partido", nomPartido);
					nuevo_json.put("pais", lugar);
					aux.put(nuevo_json);
				}
				else {
					JSONObject modifico_json = (JSONObject) aux.get(k);
					String partido = modifico_json.getString("partido");
					modifico_json.put("partido", partido+" - "+nomPartido);
				}
				
			}
			System.out.println("todos los partidos repetidos para el mismo lat long");
			System.out.println(aux.toString());
			
			
			// Armo todos los marcados con los equipos repetidos en un mismo lugarfor
			JSONArray jsonArray_latlng = vwc.obtenerLatLong(aux);
			for (int i=0; i<jsonArray_latlng.length();i++){
				JSONObject j = (JSONObject) jsonArray_latlng.get(i);
				JSONObject z = (JSONObject) aux.get(i);
				String lat = j.getString("lat");
				String lng=j.getString("lng");
				String partidos_juntos= z.getString("partido");
				Double lat2 = Double.parseDouble(lat);
				Double lng2 = Double.parseDouble(lng);
				Marker m = new Marker(new LatLng(lat2,lng2),partidos_juntos);
				lista_lugares.add(m);
			}
			
			//agrego los lugares al mapa
			for(Marker m:this.lista_lugares){
				model.addOverlay(m);
			}
		}
	}
	

	public MapaPartidosBean() {	
		model = new DefaultMapModel();
	}
	
	public MapModel getModel() {
		return this.model;
	}

	public MapModel getModel2() {
		return model2;
	}

	public void setModel2(MapModel model2) {
		this.model2 = model2;
	}

	public MapModel getModel3() {
		return model3;
	}

	public void setModel3(MapModel model3) {
		this.model3 = model3;
	}

	public void setModel(MapModel model) {
		this.model = model;
	}

	public String getGoogleKey() {
		return googleKey;
	}

	public void setGoogleKey(String googleKey) {
		this.googleKey = googleKey;
	}
	
	public List<Marker> getLista_lugares() {
		return lista_lugares;
	}

	public void setLista_lugares(List<Marker> lista_lugares) {
		this.lista_lugares = lista_lugares;
	}

	
	// me devuelve la posicion en el cual este objeto esta repetido, asi lo agrego con el equipo repetido
	public Integer lugar(String lugar, JSONArray array) throws JSONException{
		boolean encontre=false;
		Integer i = 0;
		while ((i<array.length())&&(!encontre)){
			JSONObject jo = (JSONObject) array.get(i);
			String l = jo.getString("pais");
			if (l.equals(lugar))
				encontre=true;
			i++;
		}
		if (encontre)
			return i-1;
		else 
			return -1;
	}

}

