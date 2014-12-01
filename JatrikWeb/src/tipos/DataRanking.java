package tipos;

public class DataRanking {
	String posicion;
	String nomUsr;
	String nomEquipo;
	Integer puntos;
	
	public DataRanking(String posicion, String nomUsr,String nomEquipo, Integer puntos){
		this.posicion=posicion;
		this.nomUsr= nomUsr;
		this.nomEquipo = nomEquipo;
		this.puntos= puntos;
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

	public Integer getPuntos() {
		return puntos;
	}

	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	
}
