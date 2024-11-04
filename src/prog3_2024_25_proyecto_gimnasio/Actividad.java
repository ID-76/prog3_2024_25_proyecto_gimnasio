package prog3_2024_25_proyecto_gimnasio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;

public class Actividad {

	private String nombre;
	private int capacidad;
	private LocalDateTime fecha;
	private int ocupacion;
	private ImageIcon logo;
	private ArrayList<Usuario> listaUsuarios;
	private int calorias;
	private String intensidad;
	private String descripcion;
	private int duracion;
	
	
	public Actividad(String nombre, LocalDateTime fecha) {
		super();
		this.nombre = nombre;
		this.capacidad = 20 + (new Random()).nextInt(61);
		this.fecha = fecha;
		this.ocupacion = 0;
		this.logo = new ImageIcon("Images/"+nombre+".png");
		this.listaUsuarios = new ArrayList<>();
		this.duracion = 20 + (new Random()).nextInt(61);
		
		if (nombre.contains("Avanzado")|| nombre.contains("HIIT")) {
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
		case "Equilibrio Avanzado": 
			this.descripcion = "En esta clase haremos ejercicios para mejorar el equilibrio";
			break;
			
		case "Gimnasia":
		case "Gimnasia Avanzada":
			this.descripcion = "En esta clase practicaremos varios ejercicios gimnasticos";
			break;
			
		case "HIIT":
			this.descripcion = "En esta clase haremos ejercicios en intervalos de alta intensidad";
			break;
		
		case "Yoga":
		case "Yoga Avanzado":
			this.descripcion = "En esta clase haremos ejercicios de yoga y meditacion";
			break;
		default:
			this.descripcion = "No hay descripcion para esta actividad";
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
	
	
	public void actualizarOcupacion(){
		this.ocupacion = this.listaUsuarios.size();
	}


	@Override
	public String toString() {
		return "Actividad [nombre=" + nombre + ", capacidad=" + capacidad + ", fecha=" + fecha + ", ocupacion="
				+ ocupacion + ", logo=" + logo + ", listaUsuarios=" + listaUsuarios + ", calorias=" + calorias
				+ ", intensidad=" + intensidad + ", descripcion=" + descripcion + ", duracion=" + duracion + "]";
	}



	
}
