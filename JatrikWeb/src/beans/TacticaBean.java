package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.model.DualListModel;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataJugador;
import tipos.DataListaJugador;
import tipos.DataListaPosicion;
import tipos.DataPosicion;

@ManagedBean
@RequestScoped
public class TacticaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<DataJugador> titulares;
	private List<DataJugador> suplentes;
	private String jugador;
	private int cant_defensas;
	private int cant_mediocampistas;
	private int cant_delanteros;
	private String nueva_formacion;
	private DualListModel<String> jugadores;
	private String nomEquipo;
	
	@PostConstruct
	public void init() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nomEquipo = bean.getNomEquipo();
		String respuesta_titulares = vwc.obtenerTitulares(bean.getNomEquipo());
		String respuesta_suplentes = vwc.obtenerSuplentes(bean.getNomEquipo());
		String respuesta_tactica = vwc.obtenerTactica(this.nomEquipo);
		JSONObject json_tactica = new JSONObject(respuesta_tactica);
		System.out.println(json_tactica);
		String defensa = json_tactica.getString("Defensa");
		String mediocampo = json_tactica.getString("Mediocampo");
		String ataque = json_tactica.getString("Ataque");
		this.cant_defensas = Integer.parseInt(defensa);
		this.cant_mediocampistas = Integer.parseInt(mediocampo);
		this.cant_delanteros = Integer.parseInt(ataque);
		Gson g = new Gson();
		DataListaJugador dlj_t = g.fromJson(respuesta_titulares, DataListaJugador.class);
		DataListaJugador dlj_s = g.fromJson(respuesta_suplentes, DataListaJugador.class);
		this.titulares = dlj_t.getListJugadores();
		this.suplentes = dlj_s.getListJugadores();	
		List<String> jugadoresSource = new ArrayList<String>();
        List<String> jugadoresTarget = new ArrayList<String>();
		Iterator<DataJugador> it = this.titulares.iterator();
		while (it.hasNext()){
			DataJugador dj =(DataJugador) it.next();
			jugadoresTarget.add(dj.getNomJugador());
		}
		it= this.suplentes.iterator();
		while (it.hasNext()){
			DataJugador dj =(DataJugador) it.next();
			jugadoresSource.add(dj.getNomJugador());
		}
		jugadores= new DualListModel<String>(jugadoresSource, jugadoresTarget);
	}
	
	public List<DataJugador> getTitulares() {
		return titulares;
	}
	public void setTitulares(List<DataJugador> titulares) {
		this.titulares = titulares;
	}
	public List<DataJugador> getSupelentes() {
		return suplentes;
	}
	public void setSuplentes(List<DataJugador> suplentes) {
		this.suplentes = suplentes;
	}
	public int getCant_defensas() {
		return cant_defensas;
	}
	public void setCant_defensas(int cant_defensas) {
		this.cant_defensas = cant_defensas;
	}
	public int getCant_mediocampistas() {
		return cant_mediocampistas;
	}
	public void setCant_mediocampistas(int cant_mediocampistas) {
		this.cant_mediocampistas = cant_mediocampistas;
	}
	public int getCant_delanteros() {
		return cant_delanteros;
	}
	public void setCant_delanteros(int cant_delanteros) {
		this.cant_delanteros = cant_delanteros;
	}

	public String getNueva_formacion() {
		return nueva_formacion;
	}

	public void setNueva_formacion(String nueva_formacion) {
		this.nueva_formacion = nueva_formacion;
	}

	public List<DataJugador> getSuplentes() {
		return suplentes;
	}

	public DualListModel<String> getJugadores() {
		return jugadores;
	}

	public void setJugadores(DualListModel<String> jugadores) {
		this.jugadores = jugadores;
	}
	
	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;
	}
	public String getNomEquipo() {
		return nomEquipo;
	}

	public void setNomEquipo(String nomEquipo) {
		this.nomEquipo = nomEquipo;
	}

	public void confirmarEquipo() throws JSONException{
		FacesContext context = FacesContext.getCurrentInstance();       
		if (this.jugadores.getTarget().size()!=11){
			context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"El equipo titular debe tener 11 jugadores","") );
		}
		else {
			Iterator<String> it = (this.jugadores.getTarget()).iterator();
			List<DataJugador> nuevos_titulares = new ArrayList<DataJugador>();
			while (it.hasNext()){
				String j = it.next();
				nuevos_titulares.add(dameJugador(j));
			}			
			List<DataPosicion> ldp = new ArrayList<DataPosicion>();
			Iterator<DataJugador> it2 = nuevos_titulares.iterator();
			while (it2.hasNext()){
				DataJugador dj = (DataJugador) it2.next();
				DataPosicion dp = new DataPosicion();
				dp.setIdJugador(dj.getIdJugador());
				dp.setPosicion(dj.getPosicion());
				ldp.add(dp);
			}
			DataListaPosicion dlp = new DataListaPosicion();
			dlp.setListPosiciones(ldp);
			VistaWebController vwc = new VistaWebController();
			String respuesta = vwc.modificarTitulares(this.nomEquipo, dlp);
			JSONObject json = new JSONObject(respuesta);
			if (json.getBoolean("Resultado"))
				context.addMessage(null, new FacesMessage( "","Equipo titular actualizado con éxito") );
			
			else 
				context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error, intente nuevamente","") );
			
		}
		
	}
	
	public void confirmarFormacion() throws JSONException{
		FacesContext context = FacesContext.getCurrentInstance();
		if(this.nueva_formacion==null){
			context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Seleccione una formación","") );
		}
		else {
			VistaWebController vwc = new VistaWebController();
			String[] nueva_f = nueva_formacion.split("-");			
			String respuesta = vwc.modificarTactica(this.nomEquipo, nueva_f[0], nueva_f[1],nueva_f[2]);
			JSONObject json = new JSONObject(respuesta);
			if (json.getBoolean("Result"))
			context.addMessage(null, new FacesMessage( "","Formación actualizada con éxito") );
			else
				context.addMessage(null, new FacesMessage( FacesMessage.SEVERITY_ERROR,"Ha ocurrido un error, intente nuevamente","") );
		}
        
	}
	//Toma de la lista target(nuevos titulares) el objeto DataJugador , los busca en los titulares y suplentes
	public DataJugador dameJugador(String nomJ){
		Iterator<DataJugador> it = this.titulares.iterator();
		DataJugador dj=null;
		boolean encontre= false;
		while((it.hasNext())&&!(encontre)){
			 dj = it.next();
			if (dj.getNomJugador().equals(nomJ))
				encontre=true;
		}
		if (!encontre){
			it= this.suplentes.iterator();
			while((it.hasNext())&&!(encontre)){
			 dj = it.next();
			if (dj.getNomJugador().equals(nomJ))
				encontre=true;
			}
		}
		return dj;
	}
	
	
}
