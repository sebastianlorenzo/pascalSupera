package tipos;

public class Constantes {
	
	/* Campeonato */
	public static final int DIAS_APLAZO         = 15;
	public static final int DIAS_ENTRE_PARTIDOS = 7;
	public static final int HORA_PARTIDOS       = 16;
	
	/* Equipo */
	public static final Integer CONST_CANT_MAX_CAMBIOS     = 3;
	public static final int MAX_CAPACIDAD                  = 10000;
	public static final int MAX_ALTURA_ESTADIO             = 4500;
	public static final int MIN_ALTURA_ESTADIO             = 0;
	public static final int MAX_CANT_PORTEROS              = 2;
	public static final int MAX_CANT_DEFENSAS          	   = 6;
	public static final int MAX_CANT_MEDIOCAMPISTAS    	   = 6;
	public static final int MAX_CANT_DELANTEROS        	   = 4;
	public static final int MAX_RESULTADOS_PARTIDOS        = 10;
	public static final int[] CONST_FORMACION_POR_DEFECTO  = {4, 4, 2}; // [Defensa, Mediocampo, Ataque]
	
	/* Jugador */
	public static final String CONST_DELANTERO     = "delantero";
	public static final String CONST_MEDIOCAMPISTA = "mediocampista";
	public static final String CONST_DEFENSA 	   = "defensa";
	public static final String CONST_PORTERO 	   = "portero";
	public static final String CONST_TITULAR	   = "titular";
	public static final String CONST_SUPLENTE	   = "suplente";
	public static final String CONST_LESIONADO	   = "lesionado";
	public static final String CONST_EXPULSADO	   = "expulsado";
	
	/* Partido */
	public static final Integer MIN_JUGADAS_PARTIDO    = 10;
	public static final Integer MAX_JUGADAS_PARTIDO    = 20;
	public static final Integer CONST_DURACION_PARTIDO = 90;
	public static final Float CONST_GOL 			   = (float) 0.6;  // Probabilidad mínima para que haya gol
	public static final Float CONST_TARJETA		       = (float) 0.4;  // Probabilidad mínima para que haya tarjeta
	public static final Float CONST_TARJETA_AMARILLA   = (float) 0.75; // Probabilidad máxima para que sea tarjeta amarilla
	public static final Float CONST_MIN_LESION         = (float) 0.65; // Probabilidad mínima para que sea lesión
	public static final Float CONST_MAX_LESION         = (float) 0.85; // Probabilidad máxima para que sea lesión
	public static final Integer CONST_REGATE_BAJO      = 35;
	public static final Integer CONST_REGATE_MEDIO     = 70;
	public static final Integer MIN_CANT_COMENTARIOS   = 0;
	public static final Integer MAX_CANT_COMENTARIOS   = 3;
	public static final String[] CONST_MENSAJES_REGATE_BAJO  = {"Tiro sin dirección que no tiene destino de arco.", "Disparo que se va tres metros desviado.", "Potente disparo de larga distancia que controla el arquero.", "Tiro que sale por encima del travesaño."};
	public static final String[] CONST_MENSAJES_REGATE_MEDIO = {"Disparo que sale desviado.", "Tiro desviado que sale hacia la banda derecha.", "Remate controlado sin problemas por el arquero.", "Mano a mano con el arquero, que tapa bien con su pierna izquierda."};
	public static final String[] CONST_MENSAJES_REGATE_ALTO  = {"Potente remate desde afuera del área.", "Gran atajada del arquero.", "Gran atajada del arquero que genera un tiro de esquina.", "Potente disparo que da rebote y casi cambia el marcador."};
	public static final Integer CONST_PUNTOS_GANADOR   = 3;
	public static final Integer CONST_PUNTOS_EMPATE    = 1;
	public static final Integer CONST_PUNTOS_PERDEDOR  = 0;
	public static final Integer CONT_PORCENTAJE_CAPITAL = 1;
	
	/* Usuario */
	public static final int CAPITAL_USUARIO = 10000;
	
}
