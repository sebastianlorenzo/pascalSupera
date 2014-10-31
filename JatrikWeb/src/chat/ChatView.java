package chat;

import java.io.Serializable;

import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
 





import beans.LoginBean;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean
@SessionScoped
public class ChatView implements Serializable {
     
    //private final PushContext pushContext = PushContextFactory.getDefault().getPushContext();
 
	private static final long serialVersionUID = 1L;

	private final EventBus eventBus = EventBusFactory.getDefault().eventBus();
 
    @ManagedProperty("#{chatUsers}")
    private ChatUsers users;
 
    private String privateMessage;
     
    private String globalMessage;
     
    private String username;
     
    private boolean loggedIn;
     
    private String privateUser;
     
    private final static String CHANNEL = "/Jatrik/";
    
    private boolean paso;
    
    @PostConstruct
	public void init(){
    	
    	FacesContext context = FacesContext.getCurrentInstance();
		ELContext contextoEL = context.getELContext( );
		Application apli  = context.getApplication( );		 
		ExpressionFactory ef = apli.getExpressionFactory( );
		ValueExpression ve = ef.createValueExpression(contextoEL, "#{loginBean}",LoginBean.class);
		LoginBean bean = (LoginBean) ve.getValue(contextoEL);
		String nombre= bean.getNombre();
		this.username=nombre;	
		this.users.add(nombre);
        this.loggedIn=true;
        this.paso=false;
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
     
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
     
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }    
 
    public boolean isPaso() {
		return paso;
	}

	public void setPaso(boolean paso) {
		this.paso = paso;
	}

	public void sendGlobal() {
        eventBus.publish(CHANNEL + "*", username + ": " + globalMessage);         
        globalMessage = null;
    }
     
    public void sendPrivate() {
        eventBus.publish(CHANNEL + privateUser, "[PM]" + username + ": " + privateMessage); 
        privateMessage = null;
    }
     
    public void login() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        if(users.contains(username)) {
            loggedIn = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Try with another username."));
            requestContext.update("growl");
        }
        else {
            users.add(username);
            requestContext.execute("PF('subscriber').connect('/" + username + "')");
            loggedIn = true;
        }
    }
     
    public void disconnect() {
        //remove user and update ui
        users.remove(username);
        RequestContext.getCurrentInstance().update("form:users");
         
        //push leave information
        eventBus.publish(CHANNEL + "*","<a Style=\"color:red\">" +username + " dejo la sala.</a>");
         
        //reset state
        loggedIn = false;
        username = null;
    }    
    
    public void apretar(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('subscriber').connect('/" + this.username + "')");    	
    }
}