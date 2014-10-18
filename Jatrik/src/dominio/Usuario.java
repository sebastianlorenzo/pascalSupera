package dominio;

import javax.persistence.*;
import org.codehaus.jettison.json.JSONObject;

@Entity
@Table (name = "usuario", schema = "public")
public class Usuario implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String login;
	
	private String password;
	
	private String mail;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipo", nullable = true)
	private Equipo equipo;
	
	private Boolean conectado;
	
	private Boolean es_admin;
	
	
	public Usuario(){}
	
	public Usuario(String login, String password, String mail) 
	{
		this.login = login;
		this.password = password;
		this.mail = mail;
		this.equipo = null;
		this.conectado = false;
		this.es_admin = false;
	}
	
	public String getLogin() 
	{
		return login;
	}
	
	public void setLogin(String login) 
	{
		this.login = login;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getMail() 
	{
		return mail;
	}
	
	public void setMail(String mail) 
	{
		this.mail = mail;
	}
	
	public Equipo getEquipo() 
	{
		return equipo;
	}
	
	public void setEquipo(Equipo equipo) 
	{
		this.equipo = equipo;
	}
	
	public Boolean getConectado() 
	{
		return conectado;
	}

	public void setConectado(Boolean conectado) 
	{
		this.conectado = conectado;
	}
	
	public Boolean getEsAdmin() 
	{
		return es_admin;
	}

	public void setEsAdmin(Boolean es_admin) 
	{
		this.es_admin = es_admin;
	}
	
	public JSONObject getJson() 
	{
        JSONObject json = new JSONObject();
        try
        {
            json.put("login",this.login);
            json.put("password",this.password);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return json;
    }
	
}