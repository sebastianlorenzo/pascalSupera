package dominio;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "usuario", schema = "public")
public class Usuario implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String login;
	
	private String password;
	
	private String mail;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipo", nullable = true)
	private Equipo equipo;
	
	private boolean conectado;
	private boolean es_admin;
		
	public Usuario() {
	}
	
	public Usuario(String login, String password, String mail, Equipo equipo, boolean conectado, boolean es_admin) {
		this.login = login;
		this.password = password;
		this.mail = mail;
		this.equipo = equipo;
		this.conectado = true;
		this.es_admin = false;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	public boolean getConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
	
	public boolean getEsAdmin() {
		return es_admin;
	}

	public void setEsAdmin(boolean es_admin) {
		this.es_admin = es_admin;
	}
	
}