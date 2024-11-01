package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelUsuario extends JPanel{
	public PanelUsuario(Usuario usuario) {
		setLayout(new BorderLayout());
		JPanel encabezados = new JPanel(new GridLayout(6,2,4,4));
		add(encabezados, BorderLayout.WEST);
		
		String[] encabezados_text = {"Nombre", "Apellido", "DNI", "Telefono", "Edad", "Sexo"};
		Map<String, String> usuario_info = new HashMap<>();
		usuario_info.put(encabezados_text[0], usuario.getNombre());
		usuario_info.put(encabezados_text[1], usuario.getApellido());
		usuario_info.put(encabezados_text[2], usuario.getDni());
		usuario_info.put(encabezados_text[3], String.valueOf(usuario.getTelefono()));
		usuario_info.put(encabezados_text[4], String.valueOf(usuario.getEdad()));
		usuario_info.put(encabezados_text[5], usuario.getSexo().toString());
		for (String encabezado : encabezados_text) {
			encabezados.add(new JLabel(encabezado));
			encabezados.add(new JLabel(usuario_info.get(encabezado)));
		}
		
		
	}
}
