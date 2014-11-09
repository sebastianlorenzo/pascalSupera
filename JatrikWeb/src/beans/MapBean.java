package beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import controladores.VistaWebController;

@ManagedBean
@RequestScoped
public class MapBean {
	
	private MapModel model;
	private MapModel model2;
	private MapModel model3;
	private String googleKey="AIzaSyAqLuGawbfE7GsJbJH2ZJvb6Z02UoAhfIo";
	private List<Marker> lista_lugares; // lista de LatLng
	
	public MapBean() throws JSONException {				
		this.lista_lugares = new ArrayList<Marker>();	
		model = new DefaultMapModel();
		
		VistaWebController vwc = new VistaWebController();
		String respuesta =vwc.listarEquiposMapa();
		System.out.println("estos son los equipos a listar en mapa");
		System.out.println(respuesta);
		
		JSONObject json = new JSONObject(respuesta);
		JSONArray equipos = json.getJSONArray("equipos");
		JSONArray aux = new JSONArray();
		for (int i=0; i<equipos.length();i++){
			JSONObject jo = (JSONObject) equipos.get(i);
			String nomEquipo = (String) jo.get("equipo");
			String lugar = (String) jo.get("pais");
			Integer k = lugar(lugar,aux);
			if(k==-1){
				JSONObject nuevo_json = new JSONObject();
				nuevo_json.put("equipo", nomEquipo);
				nuevo_json.put("pais", lugar);
				aux.put(nuevo_json);
			}
			else {
				JSONObject modifico_json = (JSONObject) aux.get(k);
				String equipo = modifico_json.getString("equipo");
				modifico_json.put("equipo", equipo+" - "+nomEquipo);
			}
			
		}
		System.out.println("todos los equipos repetidos para el mismo lat long");
		System.out.println(aux.toString());
		
		
		// Armo todos los marcados con los equipos repetidos en un mismo lugarfor
		JSONArray jsonArray_latlng = vwc.obtenerLatLong(aux);
		for (int i=0; i<jsonArray_latlng.length();i++){
			JSONObject j = (JSONObject) jsonArray_latlng.get(i);
			JSONObject z = (JSONObject) aux.get(i);
			String lat = j.getString("lat");
			String lng=j.getString("lng");
			String equipos_juntos= z.getString("equipo");
			Double lat2 = Double.parseDouble(lat);
			Double lng2 = Double.parseDouble(lng);
			Marker m = new Marker(new LatLng(lat2,lng2),equipos_juntos);
			lista_lugares.add(m);
		}
		
		//agrego los lugares al mapa
		for(Marker m:this.lista_lugares){
			model.addOverlay(m);
		}
		
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
