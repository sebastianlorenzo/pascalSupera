package rss;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.LoginBean;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

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
		
		SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("Techdive New Articles");
        feed.setLink("http://techdive.in/");
        feed.setDescription("Articles Uploaded in Techdive");

        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle("Articles");
        entry.setLink("http://techdive.in/technology/java");
        entry.setPublishedDate(new Date());

        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue("Publish Rss Feeds Using Java!");
        entry.setDescription(description);

        List<SyndCategory> categories = new ArrayList<SyndCategory>();
        SyndCategory category = new SyndCategoryImpl();
        category.setName("TechDive");
        categories.add(category);
        entry.setCategories(categories);

        List lstEntry = new ArrayList();
        lstEntry.add(entry);
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
