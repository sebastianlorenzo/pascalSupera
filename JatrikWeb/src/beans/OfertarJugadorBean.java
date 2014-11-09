package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataEquipo;
import tipos.DataJugador;
import tipos.DataListaEquipo;

@ManagedBean
@ViewScoped
public class OfertarJugadorBean {
	
	private List<DataEquipo> lista_equipos;
	private DataJugador jugador;
	private String precio;
	private String comentario;
	private String nomUsr;
	
	@PostConstruct
	public void init(){
		this.comentario="";
		this.precio="";
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nomUsr = bean.getNombre();
		String nomEquipo = bean.getNomEquipo();
		String respuesta = vwc.obtenerEquipos(nomEquipo);
		Gson g = new Gson();
		DataListaEquipo dle = g.fromJson(respuesta, DataListaEquipo.class);
		this.lista_equipos = dle.getListEquipos();
	}
	
	public OfertarJugadorBean(){	
		
	}
	
	public List<DataEquipo> getLista_equipos() {
		return lista_equipos;
	}

	public void setLista_equipos(List<DataEquipo> lista_equipos) {
		this.lista_equipos = lista_equipos;
	}

	public DataJugador getJugador() {
		return jugador;
	}

	public void setJugador(DataJugador jugador) {
		this.jugador = jugador;
	}
	
	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void ofertar() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		String idJugador = Integer.toString(this.jugador.getIdJugador());
		String respuesta = vwc.realizarOferta(this.nomUsr, idJugador, this.comentario, this.precio);
		JSONObject json = new JSONObject(respuesta);
		Severity icono;
		String mensaje;
		this.comentario="";
		this.precio="";
		if (json.getBoolean("oferta")){
			icono=FacesMessage.SEVERITY_INFO;			
		}			
		else{
			icono=FacesMessage.SEVERITY_ERROR;
		}
		mensaje = json.getString("mensaje");
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(icono ,"" ,mensaje);
		context.addMessage(null, message);		
	}

	
}
