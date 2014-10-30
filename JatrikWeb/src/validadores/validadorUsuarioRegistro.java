package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validadores.validadorUsuarioRegistro")
public class validadorUsuarioRegistro implements Validator {

   @Override
   public void validate(FacesContext context,
      UIComponent component, Object value)
      throws ValidatorException {
	   
	   String usuarioNuevo = value.toString();
       if (usuarioNuevo.equals("seba")){
    	   FacesMessage msg = new FacesMessage("El nombre de Usuario ya existe");
    	    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    	    throw new ValidatorException(msg);  
       }
         
      }
 }

