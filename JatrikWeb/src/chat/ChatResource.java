package chat;

import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;

import javax.inject.Inject;
import javax.servlet.ServletContext;
 
@PushEndpoint("/{room}/{user}")
@Singleton
public class ChatResource {
 
    @PathParam("room")
    private String room;
 
    @PathParam("user")
    private String username;
 
    @Inject
    private ServletContext ctx;
 
    @OnOpen
    public void onOpen(RemoteEndpoint r, EventBus eventBus) {
    	eventBus.publish(room + "/*", new Message(String.format("<a Style=\"color:#173B0D\">%s ha entrado a la sala '%s'</a>",  username, room), true));
    }
 
    @OnClose
    public void onClose(RemoteEndpoint r, EventBus eventBus) {
        ChatUsers users= (ChatUsers) ctx.getAttribute("chatUsers");
        users.remove(username);
         
        eventBus.publish(room + "/*", new Message(String.format("<a Style=\"color:red\">%s ha abandonado la sala</a>", username), true));
    }
 
    @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
    public Message onMessage(Message message) {
        return message;
    }
 
}