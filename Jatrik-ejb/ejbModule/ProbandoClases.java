import negocio.ServiciosSeguridad;

public class ProbandoClases {
	
	static Fabrica f = new Fabrica();
	static ServiciosSeguridad ss = f.getServiciosSeguridad();
	    
	public static void main(String[] args)
	{
		System.out.println("VOY A USAR SS");
		Boolean b = ss.existeUsuario("marce", "marce");
		
		System.out.println("VOY A PREGUNTAR SI TODO OK");
		if (b){
			System.out.println("ESTA TODO OK!!!");
		}
		else{
			System.out.println("ESTA TODO MAL!!!");
		}
		
	}
	
}
