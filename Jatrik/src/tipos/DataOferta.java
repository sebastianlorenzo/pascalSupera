package tipos;

public class DataOferta 
{	
	public String usuarioOferente;
	public String equipoOferente;
	public String jugador;//nombre
	public Integer idJugador;
	public Integer precio;
	public String fecha;
	public String comentario;
	
	public DataOferta(){}
	
	public DataOferta(String equipoOferente, String jugador, Integer idJugador, Integer precio, String fecha) 
	{
		this.equipoOferente = equipoOferente;
		this.jugador = jugador;
		this.precio = precio;
		this.fecha = fecha;
		this.idJugador = idJugador;
	}
	
	public String getUsuarioOferente() 
	{
		return usuarioOferente;
	}

	public void setUsuarioOferente(String usuarioOferente) 
	{
		this.usuarioOferente = usuarioOferente;
	}

	public String getEquipoOferente() 
	{
		return equipoOferente;
	}

	public void setEquipoOferente(String equipoOferente) 
	{
		this.equipoOferente = equipoOferente;
	}

	public String getJugador() 
	{
		return jugador;
	}

	public void setJugador(String jugador) 
	{
		this.jugador = jugador;
	}

	public Integer getPrecio() 
	{
		return precio;
	}

	public void setPrecio(Integer precio) 
	{
		this.precio = precio;
	}

	public String getFecha() 
	{
		return fecha;
	}

	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}

	public String getComentario() 
	{
		return comentario;
	}

	public void setComentario(String comentario) 
	{
		this.comentario = comentario;
	}
	
	public Integer getIdJugador() 
	{
		return idJugador;
	}

	public void setIdJugador(Integer idJugador) 
	{
		this.idJugador = idJugador;
	}

}
