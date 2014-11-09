package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import beans.ProximosPartidosBean;
import tipos.DataJugador;

// Dada una lista de DataJugador convierte a los suplentes para mostrar en el SelectOneMenu
@FacesConverter("converterSuplentes")
public class ConverterSuplentes implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                ProximosPartidosBean ppb = (ProximosPartidosBean) fc.getExternalContext().getApplicationMap().get("proximosPartidosBean");
                return ppb.getSuplentes().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No es un valido suplente."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if((object != null)&&(object!="")) {
            return ((DataJugador) object).getNomJugador();
        }
        else {
            return null;
        }
    }  
} 