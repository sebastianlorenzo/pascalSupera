package beans;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataListaOferta;
import tipos.DataOferta;


@ManagedBean
@RequestScoped
public class OfertasRealizadasBean {

	private List<DataOferta> lista_ofertas;
	private DataOferta oferta;
	private String comentario;
	private String nombreUsr;
	
	public OfertasRealizadasBean() {		
		this.comentario="";
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		this.nombreUsr = bean.getNombre();
		String respuesta = vwc.obtenerOfertasRealizadas(this.nombreUsr);
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
}
