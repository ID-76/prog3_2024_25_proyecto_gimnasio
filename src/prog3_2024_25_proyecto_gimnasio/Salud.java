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
		
		// TITULOS (>>INFORMACION)
		/*
		 * GridBagLayout titulos = (new GridBagLayout());
		 * 
		 * GridBagConstraints gbc = new GridBagConstraints();
		 */
		JPanel titulos = new JPanel(new GridLayout(4, 1));
		informacion.add(titulos, BorderLayout.WEST);

		JLabel lActividad = new JLabel("Actividad:");
		JLabel lConstancia = new JLabel("Constancia:");
		JLabel lRacha= new JLabel("Constancia:");
		JLabel lMRacha = new JLabel("Mejor racha:");
		JLabel lCalendario = new JLabel("Calendario:");
		
		titulos.add(lActividad);
		titulos.add(lConstancia);
		titulos.add(lCalendario);
		
		// DATOS (>>INFORMACION)
				JPanel datos = new JPanel(new GridLayout(4, 1));
				informacion.add(datos, BorderLayout.EAST);

				datos.add(new JLabel("3500/5000 kcal por semana"));
				datos.add(new JLabel("50% clases asistidas"));
		
	}

}
