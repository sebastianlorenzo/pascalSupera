package persistencia;

import dominio.Partido;

public interface PartidoDAO 
{
	
	public Partido insertarPartido(Partido p);
	
	public Partido getPartido(String partido);
	
	public void borrarPartido(Partido p);
	
	
	
}
