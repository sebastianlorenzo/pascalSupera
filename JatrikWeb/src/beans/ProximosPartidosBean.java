package beans;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controladores.VistaWebController;
import dataTypes.DataRanking;

@ManagedBean
@RequestScoped
public class ProximosPartidosBean {
	
	private String nombreEquipo;
	
	@PostConstruct
	public void init() throws JSONException {
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
		JSONObject json = new JSONObject(respuesta);
		JSONArray array = json.getJSONArray("partidos");
		for (int i = 0; i < array.length(); i++) {
			JSONObject ob = array.getJSONObject(i);
		}
	}

	public ProximosPartidosBean() {

	}
}
