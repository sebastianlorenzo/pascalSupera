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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataCambio;
import tipos.DataJugador;
import tipos.DataListaJugador;
import tipos.DataListaPartido;
import tipos.DataResumenPartido;

@ManagedBean
@ViewScoped
public class ProximosPartidosBean {
	
	private List<DataResumenPartido> ldrp;
	private DataResumenPartido drp;
	private String nombreEquipo;
	private List<DataJugador> titulares;
	private List<DataJugador> suplentes;
	private DataJugador sale1; 
	private DataJugador sale2; 
	private DataJugador sale3; 
	private DataJugador entra1; 
	private DataJugador entra2; 
	private DataJugador entra3; 
	private Integer minutos1;
	private Integer minutos2;
	private Integer minutos3;
	
	public ProximosPartidosBean() {
	}
	
	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nombreEquipo = bean.getNomEquipo();
		VistaWebController vwc = new VistaWebController();
		String respuesta = vwc.listarProximosPartidos(this.nombreEquipo);
		Gson g = new Gson();
		DataListaPartido dataPartidos =g.fromJson(respuesta, DataListaPartido.class);
		this.ldrp = dataPartidos.getLstPartidos();
		String respuesta_titualres = vwc.obtenerTitulares(this.nombreEquipo);
		String respuesta_suplentes = vwc.obtenerSuplentes(this.nombreEquipo);
		DataListaJugador tit = g.fromJson(respuesta_titualres, DataListaJugador.class);
		DataListaJugador sup = g.fromJson(respuesta_suplentes, DataListaJugador.class);
		this.titulares= tit.getListJugadores();
		this.suplentes= sup.getListJugadores();
	}

	public List<DataResumenPartido> getLdrp() {
		return ldrp;
	}

	public void setLdrp(List<DataResumenPartido> ldrp) {
		this.ldrp = ldrp;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public List<DataJugador> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<DataJugador> titulares) {
		this.titulares = titulares;
	}

	public List<DataJugador> getSuplentes() {
		return suplentes;
	}

	public void setSuplentes(List<DataJugador> suplentes) {
		this.suplentes = suplentes;
	}

	public DataJugador getSale1() {
		return sale1;
	}

	public void setSale1(DataJugador sale1) {
		this.sale1 = sale1;
	}

	public DataJugador getSale2() {
		return sale2;
	}

	public void setSale2(DataJugador sale2) {
		this.sale2 = sale2;
	}

	public DataJugador getSale3() {
		return sale3;
	}

	public void setSale3(DataJugador sale3) {
		this.sale3 = sale3;
	}

	public DataJugador getEntra1() {
		return entra1;
	}

	public void setEntra1(DataJugador entra1) {
		this.entra1 = entra1;
	}

	public DataJugador getEntra2() {
		return entra2;
	}

	public void setEntra2(DataJugador entra2) {
		this.entra2 = entra2;
	}

	public DataJugador getEntra3() {
		return entra3;
	}

	public void setEntra3(DataJugador entra3) {
		this.entra3 = entra3;
	}

	public Integer getMinutos1() {
		return minutos1;
	}

	public void setMinutos1(Integer minutos1) {
		this.minutos1 = minutos1;
	}

	public Integer getMinutos2() {
		return minutos2;
	}

	public void setMinutos2(Integer minutos2) {
		this.minutos2 = minutos2;
	}

	public Integer getMinutos3() {
		return minutos3;
	}

	public void setMinutos3(Integer minutos3) {
		this.minutos3 = minutos3;
	}

	public DataResumenPartido getDrp() {
		return drp;
	}

	public void setDrp(DataResumenPartido drp) {
		this.drp = drp;
	}

	public void confirmarCambios() throws JSONException{
		if (this.drp!=null){
			DataCambio dc1 = null;
			DataCambio dc2= null;
			DataCambio dc3=null;
		if (this.entra1!=null && this.sale1!=null && this.minutos1!=null){
			dc1 = new DataCambio(entra1.getIdJugador(),sale1.getIdJugador(),minutos1,drp.getNomPartidoEnBase());
		}
		if (this.entra2!=null && this.sale2!=null && this.minutos2!=null){
			dc2 = new DataCambio(entra2.getIdJugador(),sale2.getIdJugador(),minutos2,drp.getNomPartidoEnBase());
		}
		if (this.entra3!=null && this.sale3!=null && this.minutos3!=null){
			dc3 = new DataCambio(entra3.getIdJugador(),sale3.getIdJugador(),minutos3,drp.getNomPartidoEnBase());
		}
		Gson g = new Gson();
		VistaWebController vwc = new VistaWebController();
		String respuesta=vwc.confirmarCambios(drp.getNomPartidoEnBase(), g.toJson(dc1), g.toJson(dc2), g.toJson(dc3));
		JSONObject json = new JSONObject(respuesta);
		Severity icono;
		String mensaje;
		if (json.getString("cambios_realizados").equals("ok")){
			
			icono=FacesMessage.SEVERITY_INFO;
			mensaje = "Cambios realizados con éxito";
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(icono ,"" ,mensaje);
			context.addMessage(null, message);
		}
		else{
			icono=FacesMessage.SEVERITY_ERROR;
			mensaje = "Há ocurrido un error, intentelo de nuevo.";
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(icono ,"" ,mensaje);
			context.addMessage(null, message);
			
		}
		}
		
	}
	
}
