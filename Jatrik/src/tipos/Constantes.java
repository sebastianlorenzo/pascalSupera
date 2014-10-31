package tipos;

public class Constantes {


	/* Jugador */
	public static final String CONST_DELANTERO     = "delantero";
	public static final String CONST_MEDIOCAMPISTA = "mediocampista";
	public static final String CONST_DEFENSA 		= "defensa";
	public static final String CONST_PORTERO 		= "portero";
	public static final String CONST_TITULAR		= "titular";
	public static final String CONST_SUPLENTE		= "suplente";
	public static final String CONST_LESIONADO		= "lesionado";
	public static final String CONST_EXPULSADO		= "expulsado";
	
	/* Partido */
	public static final Integer MIN_JUGADAS_PARTIDO    = 10;
	public static final Integer MAX_JUGADAS_PARTIDO    = 20;
	public static final Integer CONST_DURACION_PARTIDO = 90;
	public static final Float CONST_GOL 			   = (float) 0.8;  // Probabilidad mínima para que haya gol
	public static final Float CONST_TARJETA		       = (float) 0.4;  // Probabilidad mínima para que haya tarjeta
	public static final Float CONST_TARJETA_AMARILLA   = (float) 0.75; // Probabilidad máxima para que sea tarjeta amarilla
	public static final Float CONST_MIN_LESION         = (float) 0.65; // Probabilidad mínima para que sea lesión
	public static final Float CONST_MAX_LESION         = (float) 0.85; // Probabilidad máxima para que sea lesión
	
}
