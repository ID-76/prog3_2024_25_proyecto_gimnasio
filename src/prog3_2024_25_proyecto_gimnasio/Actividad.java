package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;

public class Actividad extends JFrame{
	private static final long serialVersionUID = 1L;
	public JPanel pActividad = new JPanel(new BorderLayout(3, 3));

	public Actividad() {

		
		
		// INFORMACION (>>PACTIVIDAD)
		JPanel informacion = new JPanel(new BorderLayout(10, 3));
		informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
		pActividad.add(informacion, BorderLayout.EAST);
		

		
		
		// TITULOS (>>INFORMACION)
		JPanel titulos = new JPanel(new GridLayout(4, 1));
		informacion.add(titulos, BorderLayout.WEST);
		
		
		titulos.add(new JLabel("Intensidad:"));
		titulos.add(new JLabel("Duración:"));
		titulos.add(new JLabel("Monitor:"));
		titulos.add(new JLabel("Descripción:"));
		
		
		
		// DATOS (>>INFORMACION) 
		JPanel datos = new JPanel(new GridLayout(4, 1));
		informacion.add(datos, BorderLayout.EAST);
		
		datos.add(new JLabel("Alta (Test)"));
		datos.add(new JLabel("60 minutos (Test)"));
		datos.add(new JLabel("Aitor (Test)"));
		datos.add(new JLabel("<html>Clase de Spinning<br>donde pedalearas al<br>ritmo de la musica!<br>(Test)</html>"));
		
		
		
		// COMPORTAMIENTO DE ACTIVIDAD
		setSize(200, 350);
		add(pActividad);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);

		setTitle("HOME");
	}
	public static void main(String[] args) {
		Actividad actividad = new Actividad();
		actividad.setVisible(true);
	}
}
