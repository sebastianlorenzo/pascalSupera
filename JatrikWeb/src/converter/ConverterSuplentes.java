package converter;

import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
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
        if(value != null && (!value.equals("--")) && value.trim().length() > 0) {
            try {
                //ProximosPartidosBean ppb = (ProximosPartidosBean) fc.getExternalContext().getApplicationMap().get("proximosPartidosBean");
                ELContext contextoEL = fc.getELContext( );
        		Application apli  = fc.getApplication( );		 
        		ExpressionFactory ef = apli.getExpressionFactory( );
        		ValueExpression ve = ef.createValueExpression(contextoEL, "#{proximosPartidosBean}",ProximosPartidosBean.class);
        		ProximosPartidosBean ppb = (ProximosPartidosBean) ve.getValue(contextoEL);
                return dameJugador(Integer.parseInt(value),ppb.getSuplentes());
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
            return String.valueOf(((DataJugador) object).getIdJugador());
        }
        else {
            return null;
        }
    }  
    
    public DataJugador dameJugador(Integer idJugador, List<DataJugador> lista){
    	Iterator<DataJugador> it = lista.iterator();
    	boolean encontre=false;    
    	DataJugador dj = null;
    	while (it.hasNext()&& !encontre){
    		dj= (DataJugador)it.next();
    		Integer i = dj.getIdJugador();
    		if (i.equals(idJugador))
    			encontre=true;
    	}
    	return dj;
    }
} 