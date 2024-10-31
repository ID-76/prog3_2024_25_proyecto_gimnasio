package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Usuario extends JPanel{
	public Usuario() {
		setLayout(new BorderLayout());
		JPanel encabezados = new JPanel(new GridLayout(6,2,4,4));
		add(encabezados, BorderLayout.WEST);
		String[] encabezados_text = {"Nombre", "Apellido", "DNI", "Telefono", "Edad", "Sexo"};
		for (String encabezado : encabezados_text) {
			encabezados.add(new JLabel(encabezado));
			encabezados.add(new JTextField());
		}
		
		
	}
}
