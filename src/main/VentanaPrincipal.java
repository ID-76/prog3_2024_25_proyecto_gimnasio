
package main;



import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import gui.Calendario;
import gui.InicioSesion;
import gui.PanelActividad;
import gui.PanelUsuario;
import main.Usuario.Sexo;
import persistence.GestorBD;

@SuppressWarnings("static-access")


public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel principal;
    public static Usuario usuario;
    public ArrayList<Actividad> listaActividades;
    public ArrayList<Usuario> listaUsuarios;
    public String[] nombreClases;
	public void setUsuario(Usuario u) {
        this.usuario = u;
        this.ActualizarVentana();
    }
    
    public List<Usuario> getUsuarios() {
        return listaUsuarios;
    }
    
    public List<Actividad> getActividades(){
    	return listaActividades;
    }
    
    public void setUsuarios(ArrayList<Usuario> usuarios) {
    	this.listaUsuarios = usuarios;
    }
    
    public Usuario getUsuario() {
    	return usuario;
    }

    public VentanaPrincipal() {
        this.usuario = null;
        this.listaUsuarios = new ArrayList<>();
        this.listaActividades = new ArrayList<>(); // Initialize listaActividades

        this.principal = new InicioSesion(this);

        // COMPORATMIENTO VENTANA PRINCIPAL
        add(principal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("GIMNASIO");
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Detectar Ctrl + E
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                    abrirVentanaSecundaria();
                }
            }
        });
        
    }
    private void abrirVentanaSecundaria() {
        // Instancia y muestra la ventana secundaria
        SwingUtilities.invokeLater(() -> {
            Calendario calendario = new Calendario();
            calendario.setVisible(true);
            calendario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }
    

    public void ActualizarVentana() {
        remove(principal);
        if (usuario == null) {
            this.principal = new InicioSesion(this);
        } else {
            this.principal = new JPanel(new BorderLayout(2, 3));
            principal.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));

            // SIDEBAR (>> PRINCIPAL)
            JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
            sidebar.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
            principal.add(sidebar, BorderLayout.WEST);

            String[] clasesText = {"ACTIVIDADES", "SALUD", "USUARIO", "MENU" };

            for (String text : clasesText) {
            	
            	ImageIcon iconoBtn = new ImageIcon("Images/"+text+".png");
            	if (text.equals("MENU")) {
            		iconoBtn = new ImageIcon(iconoBtn.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT));
            	} else if(text.equals("ACTIVIDADES")){
            		iconoBtn = new ImageIcon(iconoBtn.getImage().getScaledInstance(52, 52, Image.SCALE_DEFAULT));
            	} else {
            		iconoBtn = new ImageIcon(iconoBtn.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            	}
                JButton boton = new JButton(text);
                boton.setHorizontalTextPosition(SwingConstants.CENTER);
                boton.setVerticalTextPosition(SwingConstants.BOTTOM);
                boton.setContentAreaFilled(false);
                boton.setBorderPainted(false);
                boton.setIcon(iconoBtn);
                
                boton.addActionListener(e -> {
                    principal.removeAll();
                    principal.add(sidebar, BorderLayout.WEST);
                    switch (text) {
                    	case "MENU":
                    		principal.add(new Menu(listaActividades,usuario), BorderLayout.CENTER);
                    		break;
                        case "ACTIVIDADES":
                            principal.add(new PanelActividad(listaActividades), BorderLayout.CENTER);
                            break;
                        case "SALUD":
                            principal.add(new Salud(), BorderLayout.CENTER);
                            break;
                        case "USUARIO":
                        	default:
                            principal.add(new PanelUsuario(usuario), BorderLayout.CENTER);
                            break;
                    }
                    principal.revalidate();
                    principal.repaint();
                    principal.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));
                });
                sidebar.add(boton);
            }
        }
        add(principal);
        revalidate();
        repaint();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }
    
    private void onWindowClosing() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir? Los datos se guardarán en la base de datos.",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
        	GestorBD gestor = new GestorBD();
        	try {
                 // Ejemplo: Actualizar actividades
                 for (Actividad actividad : listaActividades) {
                     gestor.insertarActividades(actividad);
                 }

                 // Ejemplo: Actualizar usuarios
                 for (Usuario usuario : listaUsuarios) {
                     gestor.insertarUsuarios(usuario);
                 }

                 System.out.println("Todos los cambios se han guardado correctamente en la base de datos.");
             } catch (Exception e) {
                 JOptionPane.showMessageDialog(
                         this,
                         "Ocurrió un error al guardar los datos: " + e.getMessage(),
                         "Error",
                         JOptionPane.ERROR_MESSAGE
                 );
             }
            dispose(); // Cerrar la ventana
        }
    }

    public static void main(String[] args) {
    	
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        String[] nombreClases = {"Andar", "Core", "Core Avanzado", "Equilibrio", "Gimnasia", "HIIT", "Yoga"};
        
       /** Usuario usuario1 = new Usuario("Aitor", "Garcia", "", 659921098, 21, Usuario.Sexo.HOMBRE, "");
        Usuario usuario2 = new Usuario("Ander", "Serrano", "67812930T", 66129273, 25, Usuario.Sexo.HOMBRE, "");
        Usuario usuario3 = new Usuario("Ane", "Bilbao", "89326102A", 608338214, 54, Usuario.Sexo.MUJER, "");
        Usuario usuario4 = new Usuario("Maider", "Sebastian", "03671284J", 633901881, 19, Usuario.Sexo.MUJER, "");
        Usuario usuario5 = new Usuario("Jon", "Lopez", "12345678A", 600000000, 30, Usuario.Sexo.HOMBRE, "");
        Usuario usuario6 = new Usuario("Andoni", "Perez", "10293840A", 680123045, 42, Usuario.Sexo.HOMBRE, "");
        Usuario usuario7 = new Usuario("Mikel", "Garcia", "102846573F", 633901881, 19, Usuario.Sexo.HOMBRE, "");
        Usuario usuario8 = new Usuario("Julen", "Gonzalez", "28284938L", 659921098, 21, Usuario.Sexo.HOMBRE, "");
        Usuario usuario9 = new Usuario("June", "Lopez", "16383020A", 66129273, 25, Usuario.Sexo.MUJER, "");
        Usuario usuario10 = new Usuario("Malen", "Bikandi", "98126102A", 682012371, 44, Usuario.Sexo.MUJER, "");
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        listaUsuarios.add(usuario4);
        listaUsuarios.add(usuario5);
        listaUsuarios.add(usuario6);
        listaUsuarios.add(usuario7);
        listaUsuarios.add(usuario8);
        listaUsuarios.add(usuario9);
        listaUsuarios.add(usuario10);
        **/
        
        //GestorBD gestorBD = new GestorBD();		
		
		//CREATE DATABASE: Se crea la BBDD
		//gestorBD.crearTablaUsuarios();
		
		//INSERT: Insertar datos en la BBDD		
		//List<Usuario> usuarios = initUsuarios();
		
		//gestorBD.insertarUsuarios(usuarios.toArray(new Usuario[usuarios.size()]));
		// Contar usuarios masculinos
	    //int hombres = gestorBD.contarUsuariosPorSexo(Sexo.HOMBRE);
	    //System.out.println("Total de hombres: " + hombres);

	    // Contar usuarios femeninos
	    //int mujeres = gestorBD.contarUsuariosPorSexo(Sexo.MUJER);
	    //System.out.println("Total de mujeres: " + mujeres);
		
		//SELECT: Se obtienen datos de la BBDD
		//clientes = gestorBD.obtenerDatos();
		//printClientes(clientes);
		
		//UPDATE: Se actualiza la password de un cliente
		//String newPassword = "hWaPvd6R28%1";
		//gestorBD.actualizarPassword(clientes.get(0), newPassword);

		//SELECT: Se obtienen datos de la BBDD
		//clientes = gestorBD.obtenerDatos();
		//printClientes(clientes);
	    
	 // Crear una instancia de GestorBD
        GestorBD gestor = new GestorBD();

        try {
            // Crear la tabla usuarios si no existe
            gestor.crearTablaUsuarios();

            // Insertar algunos usuarios
            Usuario usuario1 = new Usuario("Juan", "Pérez", "12345678A", 600123456, 25, Usuario.Sexo.HOMBRE, "contraseña1");
            Usuario usuario2 = new Usuario("María", "Gómez", "87654321B", 600654321, 30, Usuario.Sexo.MUJER, "contraseña2");
            Usuario usuario3 = new Usuario("Carlos", "López", "11223344C", 600987654, 35, Usuario.Sexo.HOMBRE, "contraseña3");

            gestor.insertarUsuarios(usuario1, usuario2, usuario3);

            // Obtener y mostrar todos los usuarios
            List<Usuario> usuarios2 = gestor.obtenerTodosLosUsuarios();
            System.out.println("\nUsuarios en la base de datos:");
            usuarios2.forEach(System.out::println);
            listaUsuarios = (ArrayList<Usuario>) usuarios2;
            // Actualizar un usuario
            usuario1.setApellido("Pérez Actualizado");
            usuario1.setEdad(26);
            boolean actualizado = gestor.actualizarUsuario(usuario1);
            System.out.println("\nUsuario actualizado: " + actualizado);

            // Obtener un usuario por DNI
            Usuario usuarioPorDni = gestor.obtenerUsuarioPorDni("12345678A");
            System.out.println("\nUsuario con DNI 12345678A:");
            System.out.println(usuarioPorDni);

            // Eliminar un usuario
            boolean eliminado = gestor.eliminarUsuario("87654321B");
            System.out.println("\nUsuario eliminado: " + eliminado);
            
            //Actualizar contraseña
            String dni = "12345678A";
            String nuevaContraseña = "NuevaContraseña123";

            boolean exito = gestor.actualizarContraseña(dni, nuevaContraseña);

            if (exito) {
                System.out.println("La contraseña fue actualizada con éxito.");
            } else {
                System.out.println("No se pudo actualizar la contraseña. Verifica el DNI.");
            }

            // Contar usuarios por sexo
            int totalHombres = gestor.contarUsuariosPorSexo(Usuario.Sexo.HOMBRE);
            int totalMujeres = gestor.contarUsuariosPorSexo(Usuario.Sexo.MUJER);
            System.out.println("\nTotal de hombres: " + totalHombres);
            System.out.println("Total de mujeres: " + totalMujeres);

            // Obtener desglose por sexo
            Map<Usuario.Sexo, Integer> desglosePorSexo = gestor.desgloseUsuariosPorSexo();
            System.out.println("\nDesglose de usuarios por sexo:");
            desglosePorSexo.forEach((sexo, total) -> System.out.println(sexo + ": " + total));

            // Obtener usuarios en un rango de edad
            List<Usuario> usuariosRangoEdad = gestor.obtenerUsuariosPorRangoDeEdad(20, 30);
            System.out.println("\nUsuarios entre 20 y 30 años:");
            usuariosRangoEdad.forEach(System.out::println);
        } finally {
            // Cerrar la conexión
            gestor.cerrarConexion();
            System.out.println("\nConexión cerrada.");
        }

       
        for (String nombreClase : nombreClases) {
            LocalDateTime fecha1 = LocalDateTime.of(2024, 11, 1, 9, 00);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    Actividad actividad = new Actividad(nombreClase, fecha1);
                    for (int l = 0; l < (2 + (new Random()).nextInt(6)); l++) {
                        if (!listaUsuarios.isEmpty()) {
                            actividad.addUsuario(listaUsuarios.get((new Random()).nextInt(listaUsuarios.size())));
                        } else {
                            System.err.println("La lista de usuarios está vacía. No se pueden agregar usuarios a la actividad.");
                        }
                    }
                    listaActividades.add(actividad);
                    fecha1 = fecha1.plusHours(8);
                }
                fecha1 = fecha1.plusDays(1);
            }
        }
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);

        ventana.listaActividades = listaActividades;
        ventana.listaUsuarios = listaUsuarios;
        
        
    }
    public static List<Usuario> initUsuarios() {
		List<Usuario> listaUsuarios = new ArrayList<>();		
		
		Usuario usuario1 = new Usuario("Aitor", "Garcia", "", 659921098, 21, Usuario.Sexo.HOMBRE, "");
        Usuario usuario2 = new Usuario("Ander", "Serrano", "67812930T", 66129273, 25, Usuario.Sexo.HOMBRE, "");
        Usuario usuario3 = new Usuario("Ane", "Bilbao", "89326102A", 608338214, 54, Usuario.Sexo.MUJER, "");
        Usuario usuario4 = new Usuario("Maider", "Sebastian", "03671284J", 633901881, 19, Usuario.Sexo.MUJER, "");
        Usuario usuario5 = new Usuario("Jon", "Lopez", "12345678A", 600000000, 30, Usuario.Sexo.HOMBRE, "");
        Usuario usuario6 = new Usuario("Andoni", "Perez", "10293840A", 680123045, 42, Usuario.Sexo.HOMBRE, "");
        Usuario usuario7 = new Usuario("Mikel", "Garcia", "102846573F", 633901881, 19, Usuario.Sexo.HOMBRE, "");
        Usuario usuario8 = new Usuario("Julen", "Gonzalez", "28284938L", 659921098, 21, Usuario.Sexo.HOMBRE, "");
        Usuario usuario9 = new Usuario("June", "Lopez", "16383020A", 66129273, 25, Usuario.Sexo.MUJER, "");
        Usuario usuario10 = new Usuario("Malen", "Bikandi", "98126102A", 682012371, 44, Usuario.Sexo.MUJER, "");
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        listaUsuarios.add(usuario4);
        listaUsuarios.add(usuario5);
        listaUsuarios.add(usuario6);
        listaUsuarios.add(usuario7);
        listaUsuarios.add(usuario8);
        listaUsuarios.add(usuario9);
        listaUsuarios.add(usuario10);
		
        try (BufferedReader in = new BufferedReader(new FileReader("resources/data/usuarios.csv"))) {
            String linea;
            String[] campos;

            in.readLine(); // Leer y descartar la primera línea (cabecera)

            while ((linea = in.readLine()) != null) {
                campos = linea.split(";");
                
                try {
                    String campo0 = campos[0]; // Supongamos que es String
                    String campo1 = campos[1]; // Supongamos que es String
                    String campo2 = campos[2]; // Supongamos que es String
                    int campo3 = Integer.parseInt(campos[3]); // Convertir a int
                    int campo4 = Integer.parseInt(campos[4]); // Convertir a int
                    Usuario.Sexo campo5 = Usuario.Sexo.valueOf(campos[5]); // Convertir a ENUM
                    String campo6 = campos[6]; // Supongamos que es String

                    listaUsuarios.add(new Usuario(campo0, campo1, campo2, campo3, campo4, campo5, campo6));
                } catch(Exception ex) {
        			System.err.format("- Error leyecto CSV: %s", ex.getMessage());
        		}
            }
        } catch (Exception ex) {
            System.err.format("- Error leyendo CSV: %s%n", ex.getMessage());
        }

        return listaUsuarios;

	}
}
