package dominio;

import javax.persistence.*;

import dominio.Jugador;
import dominio.Estadio;
import dominio.Usuario;

import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "equipo", schema = "public")
public class Equipo implements java.io.Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String equipo;	
	private String pais;
	private Integer tacticaDefensa;
	private Integer tacticaMediocampo;
	private Integer tacticaAtaque;
	private Integer puntaje;
	private Integer ranking;
	private Integer cant_cambios_realizados;
	private Integer entrenamientoPorteria;
	private Integer entrenamientoOfensivo;
	private Integer entrenamientoDefensivo;
	private Integer entrenamientoFisico;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="equipo")
	private Estadio estadio;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="equipo")
	private Usuario usuario;
	
	@OneToMany(fetch=FetchType.EAGER)
	private Collection<Jugador> jugadores;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	@ManyToMany(mappedBy="equipos", fetch = FetchType.LAZY)
	private Collection<Campeonato> campeonatos;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="equipo_ofertas_realizadas")
	private Collection<Oferta> ofertasRealizadas;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="equipo_ofertas_recibidas")
	private Collection<Oferta> ofertasRecibidas;
	
	public Equipo(){}

	public Equipo(String equipo, String pais) 
	{
		this.equipo = equipo;
		this.pais = pais;
		this.estadio = null;
		this.usuario = null;
		this.puntaje = -1;
		this.ranking = 0;
		Collection<Jugador> jugadores = new ArrayList<Jugador>();
		this.jugadores = jugadores;
		Collection<Partido> partidos = new ArrayList<Partido>();
		this.partidos = partidos;
		Collection<Campeonato> campeonatos = new ArrayList<Campeonato>();
		this.campeonatos = campeonatos;
		this.cant_cambios_realizados = 0; 
		this.entrenamientoOfensivo = 0;
		this.entrenamientoDefensivo = 0;
		this.entrenamientoPorteria = 0;
		this.entrenamientoFisico = 0;
	}

	public String getEquipo() 
	{
		return equipo;
	}

	public void setEquipo(String equipo) 
	{
		this.equipo = equipo;
	}

	public String getPais() 
	{
		return pais;
	}

	public void setPais(String pais) 
	{
		this.pais = pais;
	}
	
	public Integer getTacticaMediocampo() 
	{
		return tacticaMediocampo;
	}

	public void setTacticaMediocampo(Integer tacticaMediocampo) 
	{
		this.tacticaMediocampo = tacticaMediocampo;
	}

	public Integer getTacticaAtaque() 
	{
		return tacticaAtaque;
	}

	public void setTacticaAtaque(Integer tacticaAtaque) 
	{
		this.tacticaAtaque = tacticaAtaque;
	}
	
	public Integer getTacticaDefensa() 
	{
		return tacticaDefensa;
	}

	public void setTacticaDefensa(Integer tacticaDefensa) 
	{
		this.tacticaDefensa = tacticaDefensa;
	}

	public Estadio getEstadio()
	{
		return estadio;
	}

	public void setEstadio(Estadio estadio) 
	{
		this.estadio = estadio;
	}

	public Usuario getUsuario() 
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario) 
	{
		this.usuario = usuario;
	}
	
	public void setJugadores(Collection<Jugador> jugadores)
	{
		this.jugadores = jugadores;
	}

	public Collection<Jugador> getJugadores() 
	{
		return jugadores;
	}
	
	public void setPartidos(Collection<Partido> partidos) 
	{
		this.partidos = partidos;
	}

	public Collection<Partido> getPartidos() 
	{
		return this.partidos;
	}
	
	public void setCampeonatos(Collection<Campeonato> campeonatos)
	{
		this.campeonatos = campeonatos;
	}

	public Collection<Campeonato> getCampeonatos()
	{
		return this.campeonatos;
	}

	public Integer getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Integer puntaje) {
		this.puntaje = puntaje;
	}
	
	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	public Collection<Oferta> getOfertasRealizadas() {
		return ofertasRealizadas;
	}

	public void setOfertasRealizadas(Collection<Oferta> ofertasRealizadas) {
		this.ofertasRealizadas = ofertasRealizadas;
	}

	public Collection<Oferta> getOfertasRecibidas() {
		return ofertasRecibidas;
	}

	public void setOfertasRecibidas(Collection<Oferta> ofertasRecibidas) {
		this.ofertasRecibidas = ofertasRecibidas;
	}

	public Integer getEntrenamientoDefensivo() {
		return entrenamientoDefensivo;
	}

	public void setEntrenamientoDefensivo(Integer entrenamientoDefensivo) {
		this.entrenamientoDefensivo = entrenamientoDefensivo;
	}

	public Integer getEntrenamientoOfensivo() {
		return entrenamientoOfensivo;
	}

	public void setEntrenamientoOfensivo(Integer entrenamientoOfensivo) {
		this.entrenamientoOfensivo = entrenamientoOfensivo;
	}

	public Integer getEntrenamientoPorteria() {
		return entrenamientoPorteria;
	}

	public void setEntrenamientoPorteria(Integer entrenamientoPorteria) {
		this.entrenamientoPorteria = entrenamientoPorteria;
	}

	public Integer getEntrenamientoFisico() {
		return entrenamientoFisico;
	}

	public void setEntrenamientoFisico(Integer entrenamientoFisico) {
		this.entrenamientoFisico = entrenamientoFisico;
	}
	
	public Integer getCant_cambios_realizados() 
	{
		return cant_cambios_realizados;
	}

	public void setCant_cambios_realizados(Integer cant_cambios_realizados) 
	{
		this.cant_cambios_realizados = cant_cambios_realizados;
	}
	
}
