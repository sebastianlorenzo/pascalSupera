package rss;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

import controladores.VistaWebController;
import dataTypes.DataJugador;
import dataTypes.DataListaJugador;
import dataTypes.DataListaPartido;
import dataTypes.DataResumenPartido;

/**
 * Servlet implementation class PublicadorRSS
 */
@WebServlet("/PublicadorRSS")
public class PublicadorRSS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicadorRSS() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		VistaWebController vwc = new VistaWebController();
		String info = vwc.obtenerInfoRss();
		
		Gson g = new Gson();
		DataListaPartido dlp = g.fromJson(info, DataListaPartido.class);
		
		SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("Noticias Jatrik");
        feed.setLink("http://localhost:8080/JatrikWeb/");
        feed.setDescription("Ultimos resultados de Jatrik");
        
        Iterator<DataResumenPartido> it = dlp.getLstPartidos().iterator();
        List<SyndEntry> lstEntry = new ArrayList<SyndEntry>();
		while (it.hasNext()){
			DataResumenPartido drp =(DataResumenPartido) it.next();
	        SyndEntry entry = new SyndEntryImpl();
	        entry.setTitle("Resultados");
	        entry.setLink("http://localhost:8080/JatrikWeb/");
	        entry.setPublishedDate(new Date());

	        SyndContent description = new SyndContentImpl();
	        description.setType("text/html");
	        description.setValue("<table style=\"width:100%\">"+  
									  "<tr>"+
									    "<th style=\"text-align: center\">"+drp.campeonato+"</th>"+		
									  "</tr>"+
									  "<tr>"+
									    "<th style=\"text-align: center\">"+drp.nomPartido+"</th>"+		
									  "</tr>"+
									"</table>"+
									"<table style=\"width:100%\">"+
									  "<tr>"+
									    "<td style=\"text-align: center\">"+drp.golesEquipoLocalEquipoVisitante[0]+"</td>"+
									    "<td style=\"text-align: center\">Goles</td>"+
									    "<td style=\"text-align: center\">"+drp.golesEquipoLocalEquipoVisitante[1]+"</td>"+
									  "</tr>"+
									  "<tr>"+
									    "<td style=\"text-align: center\">"+drp.tarjetasAmarillasEquipoLocalEquipoVisitante[0]+"</td>"+
									    "<td style=\"text-align: center\">Tarjetas amarillas</td>"+
									    "<td style=\"text-align: center\">"+drp.tarjetasAmarillasEquipoLocalEquipoVisitante[1]+"</td>"+
									  "</tr>"+
									  "<tr>"+
									    "<td style=\"text-align: center\">"+drp.tarjetasRojasEquipoLocalEquipoVisitante[0]+"</td>"+
									    "<td style=\"text-align: center\">Tarjetas Rojas</td>"+
									    "<td style=\"text-align: center\">"+drp.tarjetasRojasEquipoLocalEquipoVisitante[1]+"</td>"+
									  "</tr>"+
								"</table>");
	        entry.setDescription(description);
	        
	        List<SyndCategory> categories = new ArrayList<SyndCategory>();
	        SyndCategory category = new SyndCategoryImpl();
	        category.setName("Resultado");
	        categories.add(category);
	        entry.setCategories(categories);

	        lstEntry.add(entry);
		}    
        feed.setEntries(lstEntry);
        
        Writer writer = response.getWriter();
        response.setContentType("application/xml; charset=UTF-8");
        SyndFeedOutput output = new SyndFeedOutput();
        try
        {
                output.output(feed, writer);
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
        writer.flush();
        writer.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

}
