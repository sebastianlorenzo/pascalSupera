package beans;

import java.util.Iterator;
import java.util.List;

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
import tipos.DataListaOferta;
import tipos.DataOferta;

@ManagedBean
@ViewScoped
public class VerOfertasBean {

	private List<DataOferta> lista_ofertas;
	private DataOferta oferta;
	private String comentario;
	private String nombreUsr;
	
	public VerOfertasBean() {		
		this.comentario="";
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nombreUsr = bean.getNombre();
		String respuesta = vwc.obtenerOfertas(this.nombreUsr);
		System.out.println(respuesta);
		Gson g = new Gson();
		DataListaOferta dlo = g.fromJson(respuesta, DataListaOferta.class);
		this.lista_ofertas = dlo.getListOfertas();
	}
	
	public List<DataOferta> getLista_ofertas() {
		return lista_ofertas;
	}
	public void setLista_ofertas(List<DataOferta> lista_ofertas) {
		this.lista_ofertas = lista_ofertas;
	}
	public DataOferta getOferta() {
		return oferta;
	}
	public void setOferta(DataOferta oferta) {
		this.oferta = oferta;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNombreUsr() {
		return nombreUsr;
	}

	public void setNombreUsr(String nombreUsr) {
		this.nombreUsr = nombreUsr;
	}
	
	public void aceptarOferta() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		String idOferta = Integer.toString(this.oferta.getIdOferta());
		String respuesta = vwc.aceptarOferta(this.nombreUsr, this.comentario, idOferta);
		this.comentario="";
		removerOferta(Integer.parseInt(idOferta));
		JSONObject json = new JSONObject(respuesta);
		Severity icono;
		String mensaje;
		if (json.getBoolean("Oferta aceptada")){
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
	
	public void recharzarOferta() throws JSONException{
		VistaWebController vwc = new VistaWebController();
		String idOferta = Integer.toString(this.oferta.getIdOferta());
		String respuesta= vwc.rechazarOferta(this.nombreUsr, this.comentario, idOferta);
		this.comentario="";
		removerOferta(Integer.parseInt(idOferta));
		JSONObject json = new JSONObject(respuesta);
		Severity icono;
		String mensaje;
		if (json.getBoolean("Oferta rechazada")){
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
	
	public void removerOferta(Integer id){
		Iterator <DataOferta> it = this.lista_ofertas.iterator();
		boolean elimine = false;
		int i = 0;
		while (it.hasNext() && (!elimine)){
			DataOferta d = it.next();
			if (d.getIdOferta()==id){
				this.lista_ofertas.remove(i);
				elimine=true;
			}
			i++;
		}
	}
	
	
}
