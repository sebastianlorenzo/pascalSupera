package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataCampeonato;
import tipos.DataListaCampeonato;

@ManagedBean
@RequestScoped
public class CampeonatosEnEjecucionBean {
	
	private List<DataCampeonato> campeonatos;
	
	public CampeonatosEnEjecucionBean (){
		VistaWebController vwc = new VistaWebController();
		Gson g = new Gson();
		DataListaCampeonato dlc = g.fromJson(vwc.campeonatosEnEjecucion(), DataListaCampeonato.class);
		this.campeonatos= dlc.getListCampeonatos();		
	}
	
	@PostConstruct
	public void init(){
	
	}

	public List <DataCampeonato> getDlc() {
		return campeonatos;
	}

	public void setDlc(List <DataCampeonato>dlc) {
		this.campeonatos = dlc;
	}
	
	public String listarCampeonatos(){
		
		return "/paginas/usuario/campeonatosEnEjecucion_user.xhtml?faces-redirect=true";
		
	}
	

}
