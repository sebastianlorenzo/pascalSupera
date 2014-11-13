package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataEquipo;
import tipos.DataJugador;
import tipos.DataListaEquipo;

@ManagedBean
@ViewScoped
public class VerJugadoresBean 
{
	private List<DataEquipo> lista_equipos;
	private DataJugador jugador;
	
	@PostConstruct
	public void init(){
		VistaWebController vwc = new VistaWebController();
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		String nomEquipo = bean.getNomEquipo();
		String respuesta = vwc.listarJugadoresDeMiEquipo(nomEquipo);
		Gson g = new Gson();
		DataListaEquipo dle = g.fromJson(respuesta, DataListaEquipo.class);
		this.setLista_equipos(dle.getListEquipos());
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
}
