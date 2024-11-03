package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Actividad extends JPanel {

	public Actividad() {
		setLayout(new BorderLayout(3, 3));
		
		 try {
			    UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
			 } catch (Exception e) {
			            e.printStackTrace();
			 }
		 
		// INFORMACION (>>ACTIVIDAD)
		JPanel informacion = new JPanel(new BorderLayout(10, 3));
		informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
		add(informacion, BorderLayout.EAST);

		// TITULOS (>>INFORMACION)
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

		
		
		// RESERVA (>>ACTIVIDAD)
		
		 JPanel reserva = new JPanel();
		 reserva.setLayout(new GridBagLayout());
		 add(reserva, BorderLayout.WEST);
		 
		 GridBagConstraints gbc = new GridBagConstraints();
		 
		 gbc.insets = new Insets(15, 5, 15, 5);
		 
		 JButton nombreActividad= new JButton("Nombre Actividad");
		 gbc.gridx = 0;
		 gbc.gridy = 0;
		 gbc.gridwidth = 1;
		 gbc.fill = GridBagConstraints.HORIZONTAL;
		 reserva.add(nombreActividad, gbc);
		 
		 JLabel icono= new JLabel("Icono");
		 gbc.gridx = 1;
		 gbc.gridy = 0;
		 gbc.gridwidth = 2;
		 gbc.gridheight = 2;
		 gbc.fill = GridBagConstraints.BOTH;
		 icono.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
		 reserva.add(icono, gbc);
		 
		 JButton dia = new JButton("Dia y Hora");
		 gbc.gridx = 0;
		 gbc.gridy = 1;
		 gbc.gridwidth = 1;
		 gbc.gridheight = 1;
		 gbc.fill = GridBagConstraints.HORIZONTAL;
		 reserva.add(dia, gbc);
		 
		 JButton sitiosDisp = new JButton("Sitios Disponibles");
		 gbc.gridx = 0;
		 gbc.gridy = 2;
		 gbc.gridwidth = 2;
		 gbc.fill = GridBagConstraints.HORIZONTAL;
		 reserva.add(sitiosDisp, gbc);
		 
		 JButton sitiosDispProg = new JButton("Sitios Disponibles Progress Bar");
		 gbc.gridx = 0;
		 gbc.gridy = 3;
		 gbc.gridwidth = 2;
		 gbc.fill = GridBagConstraints.HORIZONTAL;
		 reserva.add(sitiosDispProg, gbc);
		 
		 JButton apuntarse = new JButton("Apuntarse");
		 gbc.gridx = 0;
		 gbc.gridy = 4;
		 gbc.gridwidth = 1;
		 apuntarse.setBackground(Color.green);
		 apuntarse.setOpaque(true);
		 apuntarse.setBorderPainted(false);
		 
		 reserva.add(apuntarse, gbc);
		 
		 JButton desapuntarse= new JButton("Desapuntarse");
		 gbc.gridx = 1;
		 gbc.gridy = 4;
		 desapuntarse.setBackground(Color.RED);
		 reserva.add(desapuntarse, gbc);
		 
		 
	}
}
