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
	
	
	/* Campeonato */
	public static final int DIAS_APLAZO = 15;
	public static final int DIAS_ENTRE_PARTIDOS = 7;
	public static final int HORA_PARTIDOS = 18;
	
	/*Equipo*/
	public static final Integer CONST_CANT_MAX_CAMBIOS = 3;
	public static final int MAX_CAPACIDAD = 10000;
	public static final int MAX_ALTURA_ESTADIO = 4500;
	public static final int MIN_ALTURA_ESTADIO = 0;
	public static final int MAX_CANT_PORTEROS = 2;
	public static final int MAX_CANT_DEFENSAS = 6;
	public static final int MAX_CANT_MEDIOCAMPISTAS = 6;
	public static final int MAX_CANT_DELANTEROS = 4;
	
	/*Usuario*/
	public static final int CAPITAL_USUARIO = 10000;
	
}
