package negocio;

import javax.ejb.Local;
import tipos.DataResumenPartido;

@Local
public interface IPartidoController 
{
	
	public DataResumenPartido simularPartido(String partido);

}
