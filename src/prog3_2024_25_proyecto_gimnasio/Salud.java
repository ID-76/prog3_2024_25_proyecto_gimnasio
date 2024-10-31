package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Salud extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Salud(){
		setLayout(new BorderLayout(3, 3));

		// INFORMACION (>>ACTIVIDAD)
		JPanel informacion = new JPanel(new BorderLayout(10, 3));
		informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
		add(informacion, BorderLayout.WEST);

		
				
	}

}
