package timers;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.faces.bean.ManagedBean;

import negocio.IEquipoController;

@Startup
@ManagedBean
@Stateless
public class TimerEntrenamiento {

	@EJB
	private  IEquipoController iEquipoController;
	
	public TimerEntrenamiento() {
    }
	
	@Schedule(second="0", minute="0", hour="2", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*", info="Entrenamiento")
    private void scheduledTimeout(final Timer t) {
        System.out.println("***************ENTRENAMIENTO **************");
        iEquipoController.ejecutarEntrenamiento();
        System.out.println("***************FIN ENTRENAMIENTO **************");
    }
}