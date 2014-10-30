package filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.LoginBean;

@WebFilter("/paginas/usuario/*")
public class LoginFiltroUsuario implements Filter {

    
    public LoginFiltroUsuario() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 
		
		LoginBean loginBean = (LoginBean)((HttpServletRequest)request).getSession().getAttribute("loginBean");
		String contextPath = ((HttpServletRequest)request).getContextPath();
		
		// sino esta logueado o no hay sesion vuelve a la pagina inicial
	    if (loginBean == null || !loginBean.estaLogueado()) {
	         	         
	         ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
	    }     
	    // si es un administrador se reedirige a la pagina de home del administrado
	    else if (loginBean.isAdmin())
	    	((HttpServletResponse)response).sendRedirect(contextPath + "/paginas/administrador/home_admin.xhtml");
	    
	    // en otro caso sigue la navegacion por su camino
		else	         
	        chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
