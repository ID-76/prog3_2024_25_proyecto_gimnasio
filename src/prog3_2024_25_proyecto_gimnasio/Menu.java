package prog3_2024_25_proyecto_gimnasio;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel {
	
	public Menu() {
		JLabel titulo = new JLabel("Bienvenido al gimnasio");
		JButton matricularse = new JButton("Matricularse");
		JButton iniciarSesion = new JButton("Iniciar Sesion");
		JButton salir = new JButton("Salir");

		add(titulo);
		add(matricularse);
		add(iniciarSesion);
		add(salir);
		
	}
	
	
}
