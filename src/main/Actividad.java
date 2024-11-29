package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Actividad {

	public enum Tipo {
		ANDAR, CORE, CORE_AVANZADO, EQUILIBRIO, GIMNASIA, HIIT, YOGA
		} 

	
	private String nombre;
	private int capacidad;
	private LocalDateTime fecha;
	private int ocupacion;
	private ImageIcon logo;
	protected ArrayList<Usuario> listaUsuarios;
	private int calorias;
	private String intensidad;
	private String descripcion;
	private int duracion;
	private Tipo tipo;
	
	
	public Actividad(String nombre, LocalDateTime fecha) {
		super();
		this.nombre = nombre;
		this.capacidad = 20 + (new Random()).nextInt(61);
		this.fecha = fecha;
		this.ocupacion = 0;
		this.logo = new ImageIcon("Images/"+nombre+".png");
		this.listaUsuarios = new ArrayList<>();
		this.duracion = 20 + (new Random()).nextInt(61);
		
		if (nombre.contains("Core")|| nombre.contains("HIIT")){
			this.intensidad = "Alta";
		} else {
			this.intensidad = "Normal";
		}
		
		if (nombre.contains("Yoga") || nombre.contains("Andar") || nombre.contains("Gimnasia")) {
			if (intensidad.contains("Alta")) {
				this.calorias = 350;
			} else {
				this.calorias = 200;
			}
		} else {
			if (intensidad.contains("Alta")) {
				this.calorias = 650;
			} else {
				this.calorias = 450;
			}
		}
		
		switch (nombre) {
		case "Andar":
			this.descripcion = "En esta clase andaremos en las cintas con un monitor";
			break;
			
		case "Core":
		case "Core Avanzado":
			this.descripcion = "En esta clase haremos ejercicios para fortalecer los abdominales";
			break;
			
		case "Equilibrio":
			this.descripcion = "En esta clase entrenaremos el equilibrio y la flexibilidad";
			break;
			
		case "Gimnasia":
			this.descripcion = "En esta clase practicaremos varios ejercicios gimnasticos";
			break;
			
		case "HIIT":
			this.descripcion = "En esta clase haremos ejercicios en intervalos de alta intensidad";
			break;
		
		case "Yoga":
			this.descripcion = "En esta clase haremos ejercicios de yoga y meditacion";
			break;
		default:
			this.descripcion = "No hay descripcion para esta actividad";
			break;
		}
	
		switch (nombre) {
		case "Andar":
			this.tipo = Tipo.ANDAR;
			break;
			
		case "Core":
			this.tipo = Tipo.CORE;
			break;
			
		case "Core Avanzado":
			this.tipo = Tipo.CORE_AVANZADO;
			break;
			
		case "Equilibrio":
			this.tipo = Tipo.EQUILIBRIO;
			break;
			
		case "Gimnasia":
			this.tipo = Tipo.GIMNASIA;
			break;
			
		case "HIIT":
			this.tipo = Tipo.HIIT;
			break;
		
		case "Yoga":
			this.tipo = Tipo.YOGA;
			break;

		default:
			this.tipo = null;
			break;
		}
	}

	public int getDuracion() {
		return duracion;
	}


	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}


	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}


	public String getNombre() {
		return nombre;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public LocalDateTime getFecha() {
		return fecha;
	}


	public int getOcupacion() {
		return ocupacion;
	}


	public ImageIcon getLogo() {
		return logo;
	}


	public int getCalorias() {
		return calorias;
	}


	public String getIntensidad() {
		return intensidad;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void addUsuario(Usuario usuario) {
		this.listaUsuarios.add(usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		this.listaUsuarios.remove(usuario);
	}
	
	public void actualizarOcupacion(){
		this.ocupacion = this.listaUsuarios.size();
	}


	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return "Actividad [nombre=" + nombre + ", capacidad=" + capacidad + ", fecha=" + fecha + ", ocupacion="
				+ ocupacion + ", logo=" + logo + ", listaUsuarios=" + listaUsuarios + ", calorias=" + calorias
				+ ", intensidad=" + intensidad + ", descripcion=" + descripcion + ", duracion=" + duracion + ", tipo="
				+ tipo + "]";
	}

	
}
