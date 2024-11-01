package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
	private JPanel principal;
	private List<Usuario> usuarios;
	private Usuario usuario;
	
	public void setUsuario(Usuario u) {
		this.usuario = u;
		this.ActualizarVentana();
	}
	
	public VentanaPrincipal(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
		this.usuario = null;
		
		this.principal = new InicioSesion(this);

		// COMPORATMIENTO VENTANA PRINCIPAL
		add(principal);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 350));
		setLocationRelativeTo(null);

		setTitle("HOME");

	}
	
	public void ActualizarVentana() {
		remove(principal);
		if (usuario == null){
			this.principal = new InicioSesion(this);
		} else {
			this.principal = new JPanel(new BorderLayout(2, 3));

		// SIDEBAR (>> PRINCIPAL)
			JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
			principal.add(sidebar, BorderLayout.WEST);

			String[] clasesText = { "MENU", "ACTIVIDADES", "SALUD", "USUARIO", };

			for (String text : clasesText) {
				JButton boton;
				boton = new JButton(text);

				boton.addActionListener(e -> {
				// ACCION POR IMPLEMENTAR
					principal.removeAll();
					principal.add(sidebar, BorderLayout.WEST);
					switch (text) {
					case "ACTIVIDADES":
						principal.add(new Actividad(), BorderLayout.CENTER);

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
		VentanaPrincipal ventana = new VentanaPrincipal(usuarios);
		ventana.setVisible(true);
	}

}
