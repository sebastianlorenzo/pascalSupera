package tipos;

public class DataMensaje 
{
	private String texto;
	
	private String emisor;

	public DataMensaje()
	{
		
	}
	
	public DataMensaje(String texto, String emisor)
	{
		this.texto = texto;
		this.emisor = emisor;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	
	
}
