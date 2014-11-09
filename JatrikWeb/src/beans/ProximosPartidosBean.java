package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataListaPartido;
import tipos.DataResumenPartido;

@ManagedBean
@RequestScoped
public class ProximosPartidosBean {
	
	private List<DataResumenPartido> ldrp;
	private String nombreEquipo;
	
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
		System.out.println(respuesta);
		Gson g = new Gson();
		DataListaPartido dataPartidos =g.fromJson(respuesta, DataListaPartido.class);
		this.ldrp = dataPartidos.getLstPartidos();
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

	public ProximosPartidosBean() {

	}
}
