package ws;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import negocio.IUsuarioController;

@ManagedBean
@ViewScoped
@Path("/login")
public class UsuarioWS {
	@EJB
	private  IUsuarioController iusuarioController;
	  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getLoginUsuario() {
    	Boolean b =  iusuarioController.existeUsuario("marce", "234");
    	JSONObject json = new JSONObject();
    	 try{
             json.put("respuesta", b);
         }
         catch(Exception ex){
             ex.printStackTrace();
         }
         return json.toString();
    }
    
}
