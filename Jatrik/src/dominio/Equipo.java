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
	private String localidad;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="equipo")
	private Estadio estadio;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy="equipo")
	private Usuario usuario;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Jugador> jugadores;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Collection<Partido> partidos;
	
	@ManyToMany(mappedBy="equipos", fetch = FetchType.LAZY)
	private Collection<Campeonato> campeonatos;
	
	public Equipo(){}

	public Equipo(String equipo, String pais, String localidad) 
	{
		this.equipo = equipo;
		this.pais = pais;
		this.localidad = localidad;
		this.estadio = null;
		this.usuario = null;
		Collection<Jugador> jugadores = new ArrayList<Jugador>();
		this.jugadores = jugadores;
		Collection<Partido> partidos = new ArrayList<Partido>();
		this.partidos = partidos;
		Collection<Campeonato> campeonatos = new ArrayList<Campeonato>();
		this.campeonatos = campeonatos;
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

	public String getLocalidad() 
	{
		return localidad;
	}

	public void setLocalidad(String localidad) 
	{
		this.localidad = localidad;
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
	
}
