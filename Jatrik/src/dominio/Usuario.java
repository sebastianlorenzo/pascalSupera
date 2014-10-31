package dominio;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table (name = "usuario", schema = "public")
public class Usuario implements java.io.Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String login;	
	private String password;
	
	private String mail;
	private Integer capital;	
	private Boolean conectado;	
	private Boolean es_admin;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipo", nullable = true)
	private Equipo equipo;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_mensajesEnviados")
	private Collection<Mensaje> mensajesEnviados;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_mensajesRecibidos")
	private Collection<Mensaje> mensajesRecibidos;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_notificaciones")
	private Collection<Notificacion> notificacionesRecibidas;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="usuario_amigos")
	private Collection<Usuario> misAmigosChat;
	
	public Usuario(){}

	public Usuario(String login, String password, String mail, Integer capital) 
	{
		this.login = login;
		this.password = password;
		this.mail = mail;
		this.capital = capital;
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

	public Integer getCapital() 
	{
		return capital;
	}

	public void setCapital(Integer capital) 
	{
		this.capital = capital;
	}
	
	public Collection<Mensaje> getMensajesEnviados() 
	{
		return mensajesEnviados;
	}

	public void setMensajesEnviados(Collection<Mensaje> mensajesEnviados) 
	{
		this.mensajesEnviados = mensajesEnviados;
	}

	public Collection<Mensaje> getMensajesRecibidos() 
	{
		return mensajesRecibidos;
	}

	public void setMensajesRecibidos(Collection<Mensaje> mensajesRecibidos) 
	{
		this.mensajesRecibidos = mensajesRecibidos;
	}
		
	public Collection<Notificacion> getNotificacionesRecibidas()
	{
		return notificacionesRecibidas;
	}

	public void setNotificacionesRecibidas(Collection<Notificacion> notificacionesRecibidas)
	{
		this.notificacionesRecibidas = notificacionesRecibidas;
	}

	public Collection<Usuario> getMisAmigosChat() {
		return misAmigosChat;
	}

	public void setMisAmigosChat(Collection<Usuario> misAmigosChat) {
		this.misAmigosChat = misAmigosChat;
	}
	
}