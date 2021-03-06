package tipos;

public class DataOferta 
{	
	public String usuarioOferente;
	public String equipoOferente;
	public String jugador;//nombre
	public Integer precio;
	public String fecha;
	public String comentario;

	public Integer idJugador;
	public Integer idOferta;
	
	public String usuarioActual;//Due�o
	public String equipoActual;
	public String estado;

	public DataOferta(){}
	
	public DataOferta(String equipoOferente, String jugador, Integer idJugador, Integer precio, String fecha) 
	{
		this.equipoOferente = equipoOferente;
		this.jugador = jugador;
		this.precio = precio;
		this.fecha = fecha;
		this.idJugador = idJugador;
	}
	
	public DataOferta(String jugador, Integer precio, String fecha, String estado) 
	{
		this.jugador = jugador;
		this.precio = precio;
		this.fecha = fecha;
		this.estado = estado;
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
	
	
	public Integer getIdOferta() 
	{
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) 
	{
		this.idOferta = idOferta;
	}
	
	public String getUsuarioActual() 
	{
		return usuarioActual;
	}

	public void setUsuarioActual(String usuarioActual) 
	{
		this.usuarioActual = usuarioActual;
	}

	public String getEquipoActual() 
	{
		return equipoActual;
	}

	public void setEquipoActual(String equipoActual) 
	{
		this.equipoActual = equipoActual;
	}

	public String getEstado() 
	{
		return estado;
	}

	public void setEstado(String estado) 
	{
		this.estado = estado;
	}

}
