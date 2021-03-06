package beans;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;

import controladores.VistaWebController;
import tipos.DataGanadoresCamp;
import tipos.DataListaGanadoresCamp;

@ManagedBean
@ViewScoped
public class GanadoresCampeonatosBean {

	private List<DataGanadoresCamp> listaGanadores;
	
	
	@PostConstruct
	void init(){
		VistaWebController vwc = new VistaWebController();
		Gson g = new Gson();
		DataListaGanadoresCamp dlgc = g.fromJson(vwc.listarGanadoresCampeonatos(), DataListaGanadoresCamp.class);
		this.listaGanadores = dlgc.getListaGanadoresCamp();
			for (DataGanadoresCamp dgc:this.listaGanadores){
				Collections.sort(dgc.getListaEquipos());
		}
	}

	public GanadoresCampeonatosBean(){
		
	}

	public List<DataGanadoresCamp> getListaGanadores() {
		return listaGanadores;
	}


	public void setListaGanadores(List<DataGanadoresCamp> listaGanadores) {
		this.listaGanadores = listaGanadores;
	}	
		
}

