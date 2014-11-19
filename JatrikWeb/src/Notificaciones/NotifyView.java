package Notificaciones;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import controladores.VistaWebController;
import chat.Message;
 
@ManagedBean
@RequestScoped
public class NotifyView {
     
    private final static String CHANNEL = "/notify/";
     
    private String summary;
     
    private String detail;
    
     
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
     
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
     
	public void send(String nomUsuario) {
		VistaWebController vwc = new VistaWebController();
		String cantNotificaciones = vwc.obtenerCantNotificaciones(nomUsuario) + 1;
		
		EventBus eventBus = EventBusFactory.getDefault().eventBus();
		// el "si" es leido en la funcion de javascript handleMessage2, para saber si es realmente un llamado
		// porque cuando se refresca la pagina tambien hace un llamado en falso
		eventBus.publish(CHANNEL + nomUsuario, new Message(String.format("si_" + cantNotificaciones, nomUsuario, "si")));
    }
}
