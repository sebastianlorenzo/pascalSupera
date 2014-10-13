package ws;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import negocio.IUsuarioController;

@ManagedBean
@ViewScoped
@Path("/login")
public class Login {
	@EJB
	private  IUsuarioController iusuarioController;
	  
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getSaludoPlain() {
    	Boolean b =  iusuarioController.existeUsuario("marce", "234");
    	if (b){    		
    		/*Gson g = new Gson();
    		String r = g.toJson(true);*/

            return "true";
    	}
    	else{
    		/*Gson g = new Gson();        
            return g.toJson(false);*/
    		return "false";
    	}
    }
    
}
