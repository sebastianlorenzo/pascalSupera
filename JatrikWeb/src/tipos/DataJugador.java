package tipos;

public class DataJugador 
{
	private Integer idJugador;
	private String nomJugador;
	private String posicion;
	private String posicion_ideal;
	private Integer velocidad;
	private Integer tecnica; // Equivale al regate
	private Integer ataque;  // Equivale a potencia
	private Integer defensa;
	private Integer porteria;
	private String  estado_jugador;
	
	// Para las estadísticas del jugador
	private Integer historicoTarjetasAmarillas;
	private Integer historicoTarjetasRojas;
	private Integer historicoGoles;
	private Integer historicoLesiones;
	

	public DataJugador(){}
	
	public DataJugador(Integer idJugador, String nomJugador, String posicion, String posicion_ideal, Integer velocidad, Integer tecnica, Integer ataque,
			Integer defensa, Integer porteria, String  estado_jugador, Integer historicoTarjetasAmarillas, Integer historicoTarjetasRojas, 
			Integer historicoGoles, Integer historicoLesiones)
	{
		this.idJugador      = idJugador;
		this.nomJugador     = nomJugador;
		this.posicion       = posicion;
		this.posicion_ideal = posicion_ideal;
		this.velocidad      = velocidad;
		this.tecnica 		= tecnica;
		this.ataque 		= ataque;
		this.defensa 		= defensa;
		this.porteria		= porteria;
		this.estado_jugador = estado_jugador;
		
		this.historicoTarjetasAmarillas = historicoTarjetasAmarillas;
		this.historicoTarjetasRojas 	= historicoTarjetasRojas;
		this.historicoGoles				= historicoGoles;
		this.historicoLesiones			= historicoLesiones;
	}

	public Integer getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}
	
	public String getNomJugador() 
	{
		return nomJugador;
	}
	
	public void setNomJugador(String nomJugador) 
	{
		this.nomJugador = nomJugador;
	}
	
	public String getPosicion() 
	{
		return posicion;
	}
	
	public void setPosicion(String posicion) 
	{
		this.posicion = posicion;
	}
	
	public Integer getVelocidad() 
	{
		return velocidad;
	}
	
	public void setVelocidad(Integer velocidad) 
	{
		this.velocidad = velocidad;
	}
	
	public Integer getTecnica() 
	{
		return tecnica;
	}
	
	public void setTecnica(Integer tecnica) 
	{
		this.tecnica = tecnica;
	}
	
	public Integer getAtaque() 
	{
		return ataque;
	}
	
	public void setAtaque(Integer ataque) 
	{
		this.ataque = ataque;
	}
	
	public Integer getDefensa() 
	{
		return defensa;
	}
	
	public void setDefensa(Integer defensa) 
	{
		this.defensa = defensa;
	}
	
	public Integer getPorteria() 
	{
		return porteria;
	}
	
	public void setPorteria(Integer porteria) 
	{
		this.porteria = porteria;
	}
	
	public String getEstado_jugador() 
	{
		return estado_jugador;
	}
	
	public void setEstado_jugador(String estado_jugador) 
	{
		this.estado_jugador = estado_jugador;
	}

	public String getPosicion_ideal() 
	{
		return posicion_ideal;
	}

	public void setPosicion_ideal(String posicion_ideal) 
	{
		this.posicion_ideal = posicion_ideal;
	}
	

	public Integer getHistoricoTarjetasAmarillas() 
	{
		return historicoTarjetasAmarillas;
	}

	public void setHistoricoTarjetasAmarillas(Integer historicoTarjetasAmarillas) 
	{
		this.historicoTarjetasAmarillas = historicoTarjetasAmarillas;
	}

	public Integer getHistoricoTarjetasRojas()
	{
		return historicoTarjetasRojas;
	}

	public void setHistoricoTarjetasRojas(Integer historicoTarjetasRojas) 
	{
		this.historicoTarjetasRojas = historicoTarjetasRojas;
	}

	public Integer getHistoricoGoles() 
	{
		return historicoGoles;
	}

	public void setHistoricoGoles(Integer historicoGoles)
	{
		this.historicoGoles = historicoGoles;
	}

	public Integer getHistoricoLesiones()
	{
		return historicoLesiones;
	}

	public void setHistoricoLesiones(Integer historicoLesiones)
	{
		this.historicoLesiones = historicoLesiones;
	}
	
}
