package tipos;

public class DataRanking {
	String posicion;
	String nomUsr;
	String nomEquipo;
	
	public DataRanking(String posicion, String nomUsr,String nomEquipo){
		this.posicion=posicion;
		this.nomUsr= nomUsr;
		this.nomEquipo = nomEquipo;
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

	public String getNomEquipo() {
		return nomEquipo;
	}

	public void setNomEquipo(String nomEquipo) {
		this.nomEquipo = nomEquipo;
	}
	
}
