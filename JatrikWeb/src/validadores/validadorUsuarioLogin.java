package validadores;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.codehaus.jettison.json.JSONException;

import controladores.VistaWebController;

@FacesValidator("validadores.validadorUsuarioLogin")
public class validadorUsuarioLogin implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component,
		Object value) throws ValidatorException {
 
	  String password = value.toString();
	  System.out.println(password);
	 
	  Map<String,String> params = context.getExternalContext().getRequestParameterMap();
	  String nomlogin= params.get("nomLogin2");
	  
	  //String nomlogin = (String) component.getAttributes().get("nomLogin2");
			   
	  if (nomlogin==null)
		  System.out.println("esta vacioooooooooooooooo");
	  else
		  System.out.println("nnnnnnnnnooooooooooooooooooooooooooo");
	 
	  
	  
	  VistaWebController v = new VistaWebController();
	  try {
		if (!v.checkLogin(nomlogin, password)) {
			FacesMessage msg = new FacesMessage("El nombre de Usuario o la contraseña no son válidos.");
		    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    throw new ValidatorException(msg);  
		  }
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
	}
		
}
