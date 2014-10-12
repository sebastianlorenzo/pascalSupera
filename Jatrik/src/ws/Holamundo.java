package ws;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import negocio.ServiciosSeguridad;

@ManagedBean
@ViewScoped
@Path("/saludo")
public class Holamundo {
	
	@EJB
	private ServiciosSeguridad servicioSeguridad;
	
    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_HTML)
    public String getSaludoHTML(@PathParam("param") String nombre) {
        return "<html> " + "<title>" + "Hola Mundo" + "</title>"  
             + "<body><h1>" + "Hola Mundo en HTML : " + nombre 
             + "</body></h1>" + "</html> ";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSaludoPlain() {
    	Boolean b =  servicioSeguridad.existeUsuario("marce", "marce");
    	if (b){
    		return "true";
    	}
    	else{
    		return "false";
    	}
    }
}
