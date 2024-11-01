package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Salud extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;

	public Salud(){
		setLayout(new BorderLayout(3, 3));
		 progressBar = new JProgressBar(0, 100);
	     progressBar.setStringPainted(true);

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
		JPanel titulos = new JPanel(new GridLayout(5, 1));
		informacion.add(titulos, BorderLayout.WEST);

		JLabel lActividad = new JLabel("Actividad:");
		JLabel lConstancia = new JLabel("Constancia:");
		JLabel lRacha= new JLabel("Constancia:");
		JLabel lMRacha = new JLabel("Mejor racha:");
		JButton lCalendario = new JButton("Calendario");
		
		 lCalendario.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Crear y mostrar una nueva instancia de VentanaSecundaria
	                new Calendario();
	            }
	        });
		
		titulos.add(lActividad);
		titulos.add(lConstancia);
		titulos.add(lRacha);
		titulos.add(lMRacha);	
		titulos.add(lCalendario);
		
		// DATOS (>>INFORMACION)
				JPanel datos = new JPanel(new GridLayout(5, 1));
				informacion.add(datos, BorderLayout.EAST);

				datos.add(progressBar);
				datos.add(new JLabel("50% clases asistidas"));
				datos.add(new JLabel("llevas una racha de X dias"));
				datos.add(new JLabel("Tu mejor racha ha sido X dias"));
		
	}

}
