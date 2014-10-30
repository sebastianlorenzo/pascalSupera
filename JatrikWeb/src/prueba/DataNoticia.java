package prueba;

public class DataNoticia {

	private String goles_1;
	private String goles_2;
	private Integer amarillas_1;
	private Integer amarillas_2;
	private Integer rojas_1;
	private Integer rojas_2;
	private String nomEstadio;
	
	
	
	public DataNoticia(String goles_1, String goles_2, Integer amarillas_1,
			Integer amarillas_2, Integer rojas_1, Integer rojas_2,
			String nomEstadio) {
		super();
		this.goles_1 = goles_1;
		this.goles_2 = goles_2;
		this.amarillas_1 = amarillas_1;
		this.amarillas_2 = amarillas_2;
		this.rojas_1 = rojas_1;
		this.rojas_2 = rojas_2;
		this.nomEstadio = nomEstadio;
	}
	
	public String getGoles_1() {
		return goles_1;
	}
	public void setGoles_1(String goles_1) {
		this.goles_1 = goles_1;
	}
	public String getGoles_2() {
		return goles_2;
	}
	public void setGoles_2(String goles_2) {
		this.goles_2 = goles_2;
	}
	public Integer getAmarillas_1() {
		return amarillas_1;
	}
	public void setAmarillas_1(Integer amarillas_1) {
		this.amarillas_1 = amarillas_1;
	}
	public Integer getAmarillas_2() {
		return amarillas_2;
	}
	public void setAmarillas_2(Integer amarillas_2) {
		this.amarillas_2 = amarillas_2;
	}
	public Integer getRojas_1() {
		return rojas_1;
	}
	public void setRojas_1(Integer rojas_1) {
		this.rojas_1 = rojas_1;
	}
	public Integer getRojas_2() {
		return rojas_2;
	}
	public void setRojas_2(Integer rojas_2) {
		this.rojas_2 = rojas_2;
	}
	public String getNomEstadio() {
		return nomEstadio;
	}
	public void setNomEstadio(String nomEstadio) {
		this.nomEstadio = nomEstadio;
	}
	
	
	
}
