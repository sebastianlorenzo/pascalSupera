package beans;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.codehaus.jettison.json.JSONException;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader; 

@ManagedBean
public class carouselVistaNoticiasBean {

	//private List<DataNoticia> list_noticias;
	private List<String> lista_rss;
	
	@PostConstruct
	public void init() throws JSONException{
		try {
			URL feedUrl = new URL("http://localhost:8080/JatrikWeb/PublicadorRSS");

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			
			//feeds list
			List<SyndEntry> entradas = new ArrayList<SyndEntry>();
			entradas = feed.getEntries();

			//list iterator
			Iterator<SyndEntry> it = entradas.iterator();
			lista_rss = new ArrayList<String>();
			while (it.hasNext()) {
				SyndEntry entrada = it.next();
				lista_rss.add(entrada.getDescription().getValue());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/*public List<DataNoticia> getList_noticias() {
		return list_noticias;
	}

	public void setList_noticias(List<DataNoticia> list_noticias) {
		this.list_noticias = list_noticias;
	}*/
	
	public List<String> getLista_rss() {
		return lista_rss;
	}

	public void setLista_rss(List<String> lista_rss) {
		this.lista_rss = lista_rss;
	}
	
}
