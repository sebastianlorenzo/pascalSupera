package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import com.google.gson.Gson;

import chat.ChatUsers;
import chat.Message;
import controladores.VistaWebController;
import tipos.DataListaMensaje;
import tipos.DataMensaje;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Size(min = 1, message = "EL nombre no puede ser vacío")
	private String nombre;
	@Size(min = 1, message = "La contraseña no puede ser vacía")
	private String pwd;
	private boolean admin;
	private String nomEquipo;
	private boolean apreto;
	private String chat;

	private final EventBus eventBus = EventBusFactory.getDefault().eventBus();

	@ManagedProperty("#{chatUsers}")
	private ChatUsers users;

	private List<String> usuariosDesconectados;
	
	private List<String> usuariosDesconectadosEstatica;

	private String privateMessage;

	private String globalMessage;

	private boolean loggedIn;

	private String privateUser;

	private final static String CHANNEL = "/Jatrik/";
	
	private List<DataMensaje> mensajes; //mensajes que me enviaron mientras no estuve conectado

	public LoginBean() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public boolean estaLogueado() {
		return (this.nombre != null);
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public ChatUsers getUsers() {
		return users;
	}

	public void setUsers(ChatUsers users) {
		this.users = users;
	}

	public String getPrivateUser() {
		return privateUser;
	}

	public void setPrivateUser(String privateUser) {
		this.privateUser = privateUser;
	}

	public String getGlobalMessage() {
		return globalMessage;
	}

	public void setGlobalMessage(String globalMessage) {
		this.globalMessage = globalMessage;
	}

	public String getPrivateMessage() {
		return privateMessage;
	}

	public void setPrivateMessage(String privateMessage) {
		this.privateMessage = privateMessage;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public List<String> getUsuariosDesconectados() {
		return usuariosDesconectados;
	}

	public void setUsuariosDesconectados(List<String> usuariosDesconectados) {
		this.usuariosDesconectados = usuariosDesconectados;
	}

	public String getNomEquipo() {
		return nomEquipo;
	}

	public void setNomEquipo(String nomEquipo) {
		this.nomEquipo = nomEquipo;
	}

	public List<DataMensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<DataMensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public List<String> getUsuariosDesconectadosEstatica() {
		return usuariosDesconectadosEstatica;
	}

	public void setUsuariosDesconectadosEstatica(
			List<String> usuariosDesconectadosEstatica) {
		this.usuariosDesconectadosEstatica = usuariosDesconectadosEstatica;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public void sendGlobal() {
		eventBus.publish(CHANNEL + "*",new Message(String.format("<b> %s </b>: '%s'",
				this.nombre, this.globalMessage)));
		globalMessage = null;
	}

	public void sendPrivate() {
		if (this.users.contains(this.privateUser)) {
			//agrego mi nuevo amigo
			if (!this.usuariosDesconectadosEstatica.contains(this.privateUser))
				this.usuariosDesconectadosEstatica.add(this.privateUser);
			
			eventBus.publish(CHANNEL + privateUser, new Message(String.format("<a Style=\"color:#173B0D\">[PM] %s </a>: '%s'",
					this.nombre, this.privateMessage)));
			privateMessage = null;
		} else {
			VistaWebController v = new VistaWebController();
			String respuesta = v.enviarChat(this.nombre, this.privateUser,
					this.privateMessage);
		}
	}

	public String salir() throws JSONException {
		VistaWebController v = new VistaWebController();
		if (!this.admin){
		JSONArray jdesconectados = new JSONArray();
		JSONObject ob;
	
		for (int i=0;i<this.usuariosDesconectadosEstatica.size();i++) 
		{
			ob = new JSONObject();
			ob.put("desconectado", this.usuariosDesconectadosEstatica.get(i));
			jdesconectados.put(ob);		
		}
		if (v.logout(this.nombre,jdesconectados.toString(),false)) {
			// remove user and update ui
			users.remove(nombre);
			RequestContext.getCurrentInstance().update("form:users");
			// push leave information
			eventBus.publish(CHANNEL + "*", nombre + " dejo la sala.");
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			return "/index.xhtml?faces-redirect=true";
		}
		return "/paginas/home.xhtml?faces-redirect=true";
		}
		else {
			
			v.logout(this.nombre,"vacio",true);
			return "/index.xhtml?faces-redirect=true";
		}
	}

	public String login() throws JSONException {

		VistaWebController v = new VistaWebController();
		String respuesta = v.login(nombre, pwd);
		JSONObject json = new JSONObject(respuesta);
		if (json.getBoolean("login")) {

			if (json.getBoolean("es_admin")) {
				this.admin = true;
				return "/paginas/administrador/home_admin.xhtml?faces-redirect=true";
			}

			else {
				this.nomEquipo= json.getString("nomEquipo");
				this.usuariosDesconectados = new ArrayList<String>();				
				users.add(nombre);
				this.loggedIn = true;
				this.apreto = false;
				Gson g = new Gson();
				DataListaMensaje dlm = g.fromJson(json.getString("mensajesNuevos"), DataListaMensaje.class);
				this.mensajes = dlm.getLstMensajes();
				
				this.usuariosDesconectadosEstatica= new ArrayList<String>();
				String r_desconectados = v.listarDesconectados(this.nombre);
				JSONObject desconectados = new JSONObject(r_desconectados);
				JSONArray lista_desconectados = desconectados.getJSONArray("desconectados");
				if (lista_desconectados != null) {
					for (int i = 0; i < lista_desconectados.length(); i++) {
						JSONObject ob = lista_desconectados.getJSONObject(i);
						String usuario = ob.get("desconectado").toString();
						this.usuariosDesconectadosEstatica.add(usuario);
					}
				}				
				
				return "/paginas/usuario/home_user.xhtml?faces-redirect=true";
			}
		} else {
			String cabecera = "Ha ocurrido un error";
			Severity icono = FacesMessage.SEVERITY_ERROR;
			String mensaje = "Compruebe nombre de usuario o contraseña.";
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(icono ,cabecera ,mensaje);
			context.addMessage(null, message);
			return "/index.xhtml?faces-redirect=true";
		}

	}
	// despliega los mensajes sin leer
	public void obtenerMensajes(){
		Iterator<DataMensaje> it = this.mensajes.iterator();
		while(it.hasNext()){
			DataMensaje m = it.next();
			eventBus.publish(CHANNEL + this.nombre,new Message(String.format("<a> <b Style=\"color:#9C80A3\">[PM] %s</b> : '%s'</a>",
						m.getEmisor(), m.getTexto())));
			
		}	
		this.mensajes.clear();
	}

	public void apretar() {
		if (!this.apreto){
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('subscriber').connect('/" + this.nombre
				+ "')");
		this.apreto= true;
		}
		else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage( null,"Ud. ya esta en la sala") );
		}
	}

	public String anotarmeCampeonato() {

		return "/paginas/usuario/anotarmeCampeonato_user.xhtml?faces-redirect=true";

	}
	
	// Copio la lista de desconectadosEstatica (amigos) y la actualizo contra los que estan conectados
	// si un usuario se conecto remuevo el usuario de la lista
	public void onTabShowDesconectados() throws JSONException {		
		if (!this.usuariosDesconectadosEstatica.isEmpty()) {
			this.usuariosDesconectados = new ArrayList<String>();
			for (int i = 0; i < this.usuariosDesconectadosEstatica.size(); i++) {
				if (!users.contains(this.usuariosDesconectadosEstatica.get(i)))
					this.usuariosDesconectados.add(this.usuariosDesconectadosEstatica.get(i));
			}
		}

	}

}
