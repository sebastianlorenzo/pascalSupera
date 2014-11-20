package negocio;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.faces.bean.ManagedBean;
import dominio.Partido;

@Startup
@ManagedBean
@Stateless
public class TimerController {

	@EJB
	private  IEquipoController iEquipoController;

	@EJB
	private  IPartidoController iPartidoController;
	
	public TimerController() {
    }
	
	@Schedule(second="0", minute="0", hour="2", dayOfWeek="*", dayOfMonth="*", month="*", year="*", info="Entrenamiento")
    private void scheduledTimeout(final Timer t) {
        System.out.println("***************ENTRENAMIENTO **************");
        iEquipoController.ejecutarEntrenamiento();
        System.out.println("***************FIN ENTRENAMIENTO **************");
    }
	
	@Schedule(second="0", minute="0", hour="*", dayOfWeek="*", dayOfMonth="*", month="*", year="*", info="SimularPartido",persistent=false)
    private void scheduledSimularPartidos(final Timer t) {
		System.out.print("***********************************\n");
		System.out.print("***** INICIO SIMULAR PARTIDOS *****\n");
		System.out.print("***********************************\n");
        
		Date fecha_actual = new Date();
		List<Partido> partidos = iPartidoController.listaPartidosSimular(fecha_actual);
		Iterator<Partido> it = partidos.iterator();
		while (it.hasNext()) {
			Partido p = it.next();
			iPartidoController.simularPartido(p.getPartido());
		}
		
		System.out.print("***********************************\n");
		System.out.print("******* FIN SIMULAR PARTIDOS ******\n");
		System.out.print("***********************************\n");
    }
	
}