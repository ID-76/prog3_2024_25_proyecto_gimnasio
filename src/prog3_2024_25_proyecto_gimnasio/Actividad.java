package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Actividad extends JPanel {

	public Actividad() {
		setLayout(new BorderLayout(3, 3));

		// INFORMACION (>>ACTIVIDAD)
		JPanel informacion = new JPanel(new BorderLayout(10, 3));
		informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
		add(informacion, BorderLayout.EAST);

		// TITULOS (>>INFORMACION)
		/*
		 * GridBagLayout titulos = (new GridBagLayout());
		 * 
		 * GridBagConstraints gbc = new GridBagConstraints();
		 */
		JPanel titulos = new JPanel(new GridLayout(4, 1));
		informacion.add(titulos, BorderLayout.WEST);

		JLabel lIntensidad = new JLabel("Intensidad:");
		JLabel lDuracion = new JLabel("Duración:");
		JLabel lMonitor = new JLabel("Monitor:");
		JLabel lDescripcion = new JLabel("Descripción:");

		titulos.add(lIntensidad);
		titulos.add(lDescripcion);
		titulos.add(lDuracion);
		titulos.add(lMonitor);

		// DATOS (>>INFORMACION)
		JPanel datos = new JPanel(new GridLayout(4, 1));
		informacion.add(datos, BorderLayout.EAST);

		datos.add(new JLabel("Alta (Test)"));
		datos.add(new JLabel("60 minutos (Test)"));
		datos.add(new JLabel("Aitor (Test)"));
		datos.add(new JLabel("<html>Clase de Spinning<br>donde pedalearas al<br>ritmo de la musica!<br>(Test)</html>"));

	}
}
