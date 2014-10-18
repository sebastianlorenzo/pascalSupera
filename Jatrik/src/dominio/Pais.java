package dominio;

import javax.persistence.*;

@Entity
@Table (name = "pais", schema = "public")
public class Pais implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;

	private String pais;
	
	@Id
	private String localidad;
	
	
	public String getPais() 
	{
		return pais;
	}
	
	public void setPais(String pais)
	{
		this.pais = pais;
	}
	
	public String getLocalidad() 
	{
		return localidad;
	}
	
	public void setLocalidad(String localidad)
	{
		this.localidad = localidad;
	}

}
