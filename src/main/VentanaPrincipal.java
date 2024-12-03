package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
import persistence.GestorBD;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel principal;
    public static Usuario usuario;
    public ArrayList<Actividad> listaActividades;
    public ArrayList<Usuario> listaUsuarios;
<<<<<<< Updated upstream
    public static String[] nombreClases = {"Andar", "Core", "Core Avanzado", "Equilibrio", "Gimnasia", "HIIT", "Yoga"};
=======
    public static final String[] nombreClases = {"Andar", "Core", "Core Avanzado", "Equilibrio", "Gimnasia", "HIIT", "Yoga"};
>>>>>>> Stashed changes

    public void setUsuario(Usuario u) {
        usuario = u;
        this.actualizarVentana();
    }

    public List<Usuario> getUsuarios() {
        return listaUsuarios;
    }

    public List<Actividad> getActividades() {
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
        this.listaActividades = new ArrayList<>();

        this.principal = new InicioSesion(this);

        add(principal);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E) {
                    abrirVentanaSecundaria();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClosing();
            }
        });
    }

    private void abrirVentanaSecundaria() {
        SwingUtilities.invokeLater(() -> {
            Calendario calendario = new Calendario();
            calendario.setVisible(true);
            calendario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }

    public void actualizarVentana() {
        remove(principal);
        if (usuario == null) {
            this.principal = new InicioSesion(this);
        } else {
            this.principal = new JPanel(new BorderLayout(2, 3));
            principal.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 10));

            JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
            sidebar.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
            principal.add(sidebar, BorderLayout.WEST);

            String[] clasesText = {"ACTIVIDADES", "SALUD", "USUARIO", "MENU"};

            for (String text : clasesText) {
                ImageIcon iconoBtn = new ImageIcon("Images/" + text + ".png");
                iconoBtn = new ImageIcon(iconoBtn.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

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
                            principal.add(new Menu(listaActividades, usuario), BorderLayout.CENTER);
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
            gestor.limpiarTablas();
            try {
                for (Actividad actividad : listaActividades) {
                    gestor.insertarActividades(actividad);
                }
                for (Usuario usuario : listaUsuarios) {
                    gestor.insertarUsuarios(usuario);
                }
                System.out.println(listaActividades);
                System.out.println("Datos guardados correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al guardar los datos: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            dispose();
        }
    }

    public static void main(String[] args) {
        GestorBD gestorBD = new GestorBD();

        //gestorBD.crearTablaUsuarios();
        //gestorBD.crearTablaActividades();

        List<Usuario> usuarios = gestorBD.obtenerTodosLosUsuarios();
        List<Actividad> actividades = gestorBD.obtenerTodasLasActividades();

        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setUsuarios(new ArrayList<>(usuarios));
        ventana.listaActividades = new ArrayList<>(actividades);

        ventana.setVisible(true);
    }
}
