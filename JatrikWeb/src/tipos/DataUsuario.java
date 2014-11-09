package tipos;

public class DataUsuario 
{
	private String nomUsuario;	

	private String mail;
	private Integer capital;
	private String nomEquipo;
	private String ubicacion;
	private String nomEstadio;
	
	public DataUsuario(){}
	
	public DataUsuario(String login, String mail, Integer capital, String nomEquipo, String ubicacion, 
			String nomEstadio) 
	{
		this.nomUsuario = login;
		this.mail = mail;
		this.capital = capital;
		this.nomEquipo = nomEquipo;
		this.ubicacion = ubicacion;
		this.nomEstadio = nomEstadio;
	}

	public String getNomUsuario() 
	{
		return nomUsuario;
	}
	public void setNomUsuario(String login) 
	{
		this.nomUsuario = login;
	}
	
	public String getMail() 
	{
		return mail;
	}
	public void setMail(String mail) 
	{
		this.mail = mail;
	}
	
	public Integer getCapital() 
	{
		return capital;
	}
	public void setCapital(Integer capital) 
	{
		this.capital = capital;
	}
	
	public String getNomEquipo()
	{
		return nomEquipo;
	}
	public void setNomEquipo(String nomEquipo) 
	{
		this.nomEquipo = nomEquipo;
	}
	
	public String getUbicacion() 
	{
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) 
	{
		this.ubicacion = ubicacion;
	}
	
	public String getNomEstadio() 
	{
		return nomEstadio;
	}
	public void setNomEstadio(String nomEstadio) 
	{
		this.nomEstadio = nomEstadio;
	}

}
