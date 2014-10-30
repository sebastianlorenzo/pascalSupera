package negocio;

import javax.ejb.Local;
import tipos.DataCambio;
import tipos.DataResumenPartido;

@Local
public interface IPartidoController 
{

	public void configurarCambiosPartido(String partido, DataCambio[] cambios);
	
	public DataResumenPartido simularPartido(String partido);
	
}
