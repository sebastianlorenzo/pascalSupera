package beans;

import java.io.Serializable;

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

import chat.ChatUsers;
import controladores.VistaWebController;



@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Size(min=1, message="EL nombre no puede ser vacío")
	private String nombre;
	@Size(min=1, message="La contraseña no puede ser vacía")
	private String pwd;	
	private boolean admin;
	private final EventBus eventBus = EventBusFactory.getDefault().eventBus();
	 
    @ManagedProperty("#{chatUsers}")
    private ChatUsers users;
 
    private String privateMessage;
     
    private String globalMessage;
     
    private boolean loggedIn;
     
    private String privateUser;
     
    private final static String CHANNEL = "/Jatrik/";
     
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
	
	public boolean estaLogueado(){
		return (this.nombre!=null);
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
	 
	    public void sendGlobal() {
	        eventBus.publish(CHANNEL + "*", nombre + ": " + globalMessage);
	         
	        globalMessage = null;
	    }
	     
	    public void sendPrivate() {
	        eventBus.publish(CHANNEL + privateUser, "[PM] " + nombre + ": " + privateMessage);
	         
	        privateMessage = null;
	    }


	public String salir() {
		
		  VistaWebController v = new VistaWebController();
		  if (v.logout(this.nombre)){
			  //remove user and update ui
		        users.remove(nombre);
		        RequestContext.getCurrentInstance().update("form:users");		         
		        //push leave information
		        eventBus.publish(CHANNEL + "*", nombre + " left the channel.");
			  FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			  return "/index.xhtml?faces-redirect=true";
		  }
		  return "/paginas/home.xhtml?faces-redirect=true";
	}
	
	public String login() throws JSONException {
		
		VistaWebController v = new VistaWebController();
		String respuesta = v.login(nombre, pwd);
		String r_desconectados= v.listarDesconectados();		
		System.out.println(r_desconectados);
		JSONObject desconectados = new JSONObject(r_desconectados);	
		System.out.println(desconectados);
		JSONArray lista_desconectados= desconectados.getJSONArray("desconectados");
		System.out.println(lista_desconectados);
		JSONObject json = new JSONObject(respuesta);
		if (json.getBoolean("login")){
			  
			if (json.getBoolean("es_admin")){
				this.admin=true;
				return "/paginas/administrador/home_admin.xhtml?faces-redirect=true";
			}
				
			else{
				 RequestContext requestContext = RequestContext.getCurrentInstance();
				 users.add(nombre);				 
		         requestContext.execute("PF('subscriber').connect('/" + nombre + "')");
		         if (lista_desconectados !=null){
		        	 for (int i=0;i<lista_desconectados.length() ;i++){
		        		 JSONObject ob= lista_desconectados.getJSONObject(i);
		        		 String usuario=ob.get("desconectado").toString();
		        		 System.out.println(usuario);
		        		 if (!users.contains(usuario))
		        			 users.add(usuario);
		        	 }
		         }
		         loggedIn = true;
		         return "/paginas/usuario/home_user.xhtml?faces-redirect=true";
		   }	  	 
		  }
		else {
				RequestContext requestContext = RequestContext.getCurrentInstance();
				String cabecera="Ha ocurrido un error";
				Severity icono=FacesMessage.SEVERITY_ERROR;
		    	String mensaje_box="Compruebe nombre de usuario o contraseña.";
		    	FacesMessage message = new FacesMessage(icono ,cabecera ,mensaje_box);
		        FacesContext.getCurrentInstance().addMessage(null, message);
	            requestContext.update("growl");
		        return "/index.xhtml?faces-redirect=true";		        
		}
			
	}
	
	public void apretar(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('subscriber').connect('/" + this.nombre + "')");    	
    }
	
	public String anotarmeCampeonato(){
		
		return "/paginas/usuario/anotarmeCampeonato_user.xhtml?faces-redirect=true";
		
	}
	
    
}
