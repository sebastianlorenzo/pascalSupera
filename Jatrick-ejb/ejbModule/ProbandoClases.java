import negocio.ServiciosSeguridad;
import negocio.ServiciosSeguridadImpl;

public class ProbandoClases {
	
	static Fabrica f = new Fabrica();
	static ServiciosSeguridad ss = f.getServiciosSeguridad();
	    
	public static void main(String[] args)
	{
		System.out.println("VOY A USAR SS");
		Boolean b = ss.existeUsuario("marce", "marce");
		
		System.out.println("ESTA TODO OK!!!");
		/*if (b){
			System.out.println("ESTA TODO OK!!!");
		}
		else{
			System.out.println("ESTA TODO MAL!!!");
		}*/
		
	}
	
}
