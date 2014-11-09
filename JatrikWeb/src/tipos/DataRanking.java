package tipos;

public class DataRanking {
	String posicion;
	String nomUsr;
	
	public DataRanking(String posicion, String nomUsr){
		this.posicion=posicion;
		this.nomUsr= nomUsr;
	}
	
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public String getNomUsr() {
		return nomUsr;
	}
	public void setNomUsr(String nomUsr) {
		this.nomUsr = nomUsr;
	}
	
}
