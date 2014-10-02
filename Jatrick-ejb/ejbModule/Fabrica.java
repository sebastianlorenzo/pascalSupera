import negocio.ServiciosSeguridad;
import negocio.ServiciosSeguridadImpl;


public class Fabrica {

	public ServiciosSeguridad getServiciosSeguridad(){
		ServiciosSeguridad cc = new ServiciosSeguridadImpl();
        return cc;
    }
	
}
