package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
	private JPanel principal;

	public VentanaPrincipal() {

		// PRINCIPAL
		JPanel principal = new JPanel(new BorderLayout(2, 3));

		// SIDEBAR (>> PRINCIPAL)
		JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
		principal.add(sidebar, BorderLayout.WEST);

		String[] clasesText = { "MENU", "ACTIVIDADES", "SALUD", "USUARIO", };

		JButton boton;
		for (String text : clasesText) {
			boton = new JButton(text);

			boton.addActionListener(e -> {
				// ACCION POR IMPLEMENTAR
				switch (text) {
				case "ACTIVIDADES":
					principal.removeAll();
					principal.add(sidebar, BorderLayout.WEST);
					principal.add(new Actividad(), BorderLayout.CENTER);
					principal.revalidate();
					principal.repaint();

					break;
				case "SALUD":
					principal.removeAll();
					principal.add(sidebar, BorderLayout.WEST);
					principal.add(new Salud(), BorderLayout.CENTER);
					principal.revalidate();
					principal.repaint();

					break;
				case "USUARIO":
					principal.removeAll();
					principal.add(sidebar, BorderLayout.WEST);
					principal.add(new Usuario(), BorderLayout.CENTER);
					principal.revalidate();
					principal.repaint();

					break;
				case "MENU":
				default:
					principal.removeAll();
					principal.add(sidebar, BorderLayout.WEST);
					principal.add(new Menu(), BorderLayout.CENTER);
					principal.revalidate();
					principal.repaint();

				}
			});
			sidebar.add(boton);
		}

		// COMPORATMIENTO VENTANA PRINCIPAL
		add(principal);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 350));
		setLocationRelativeTo(null);

		setTitle("HOME");

	}

	public static void main(String[] args) {
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.setVisible(true);
	}

}
