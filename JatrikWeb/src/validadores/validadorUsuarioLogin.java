package validadores;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


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
	 
 
	}
		
}
