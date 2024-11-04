
package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel principal;
    private List<Usuario> usuarios;
    private Usuario usuario;
    public ArrayList<Actividad> listaActividades;
    public ArrayList<Usuario> listaUsuarios;

    public void setUsuario(Usuario u) {
        this.usuario = u;
        this.ActualizarVentana();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public VentanaPrincipal(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
        this.usuario = null;
        this.listaActividades = new ArrayList<>(); // Initialize listaActividades
        this.listaUsuarios = new ArrayList<>(); // Initialize listaUsuarios

        this.principal = new InicioSesion(this);

        // COMPORATMIENTO VENTANA PRINCIPAL
        add(principal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(775, 350));
        setMinimumSize(new Dimension(800, 350));
        setLocationRelativeTo(null);
        setTitle("HOME");
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

            String[] clasesText = { "MENU", "ACTIVIDADES", "SALUD", "USUARIO", };

            for (String text : clasesText) {
                JButton boton = new JButton(text);
                boton.addActionListener(e -> {
                    principal.removeAll();
                    principal.add(sidebar, BorderLayout.WEST);
                    switch (text) {
                        case "ACTIVIDADES":
                            principal.add(new PanelActividad(listaActividades), BorderLayout.CENTER);
                            break;
                        case "SALUD":
                            principal.add(new Salud(), BorderLayout.CENTER);
                            break;
                        case "USUARIO":
                            principal.add(new PanelUsuario(usuario), BorderLayout.CENTER);
                            break;
                        case "MENU":
                        default:
                            principal.add(new Menu(), BorderLayout.CENTER);
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
    }

    public static void main(String[] args) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        VentanaPrincipal ventana = new VentanaPrincipal(usuarios);
        ventana.setVisible(true);

        Usuario usuario1 = new Usuario("Aitor", "Garcia", "79043212D", 659921098, 21, Usuario.Sexo.HOMBRE, null);
        Usuario usuario2 = new Usuario("Ander", "Serrano", "67812930T", 66129273, 25, Usuario.Sexo.HOMBRE, null);
        Usuario usuario3 = new Usuario("Ane", "Bilbao", "89326102A", 608338214, 54, Usuario.Sexo.MUJER, null);
        Usuario usuario4 = new Usuario("Maider", "Sebastian", "03671284J", 633901881, 19, Usuario.Sexo.MUJER, null);
        Usuario usuario5 = new Usuario("Jon", "Lopez", "12345678A", 600000000, 30, Usuario.Sexo.HOMBRE, null);
        Usuario usuario6 = new Usuario("Andoni", "Perez", "89326102A", 680123045, 42, Usuario.Sexo.HOMBRE, null);
        Usuario usuario7 = new Usuario("Mikel", "Garcia", "03691284J", 633901881, 19, Usuario.Sexo.HOMBRE, null);
        Usuario usuario8 = new Usuario("Julen", "Gonzalez", "79043212D", 659921098, 21, Usuario.Sexo.HOMBRE, null);
        Usuario usuario9 = new Usuario("June", "Lopez", "67812930A", 66129273, 25, Usuario.Sexo.MUJER, null);
        Usuario usuario10 = new Usuario("Malen", "Bikandi", "98126102A", 682012371, 44, Usuario.Sexo.MUJER, null);
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

        String[] nombreClases = { "Andar", "Core", "Core Avanzado", "Equilibrio", "Equilibrio Avanzado", "Gimnasia", "Gimnasia Avanzada", "HIIT", "Yoga", "Yoga Avanzado" };

        for (String nombreClase : nombreClases) {
            LocalDateTime fecha1 = LocalDateTime.of(2024, 11, 1, 10, 00);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    Actividad actividad = new Actividad(nombreClase, fecha1);
                    for (int l = 0; l < (2 + (new Random()).nextInt(8)); l++) {
                        actividad.addUsuario(listaUsuarios.get((new Random()).nextInt(listaUsuarios.size())));
                    }
                    listaActividades.add(actividad);
                    fecha1 = fecha1.plusHours(8);
                }
                fecha1 = fecha1.plusDays(1);
            }
        }
        ventana.listaActividades = listaActividades; // Ensure listaActividades is set
        System.out.println(listaUsuarios);
        System.out.println(listaActividades);
    }
}
