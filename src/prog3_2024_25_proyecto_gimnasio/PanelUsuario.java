package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelUsuario extends JPanel{
	public PanelUsuario() {
		setLayout(new BorderLayout());
		JPanel encabezados = new JPanel(new GridLayout(6,2,4,4));
		add(encabezados, BorderLayout.WEST);
		
		String[] encabezados_text = {"Nombre", "Apellido", "DNI", "Telefono", "Edad", "Sexo"};
		Map<String, String> usuario = new HashMap<>();
		usuario.put(encabezados_text[0], "Asier");
		usuario.put(encabezados_text[1], "Gomez");
		usuario.put(encabezados_text[2], "18918829R");
		usuario.put(encabezados_text[3], "682734625");
		usuario.put(encabezados_text[4], "19");
		usuario.put(encabezados_text[5], "H");
		for (String encabezado : encabezados_text) {
			encabezados.add(new JLabel(encabezado));
			encabezados.add(new JLabel(usuario.get(encabezado)));
		}
	}
}
