package negocio;

import javax.ejb.Local;

@Local
public interface IPartidoController 
{
	
	public DataResumenPartido simularPartido(String partido);

}
