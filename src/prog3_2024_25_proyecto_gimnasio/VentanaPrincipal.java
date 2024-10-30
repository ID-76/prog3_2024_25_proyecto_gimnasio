package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import prog3_2024_25_proyecto_gimnasio.Actividad;



public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
	
    
	public VentanaPrincipal() {
		
		JPanel principal = new JPanel(new BorderLayout(2,3));

		
		//SIDEBAR
		JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
		principal.add(sidebar, BorderLayout.WEST);	
		
		
		String[] buttonsText = {
				"MENU",
				"CLASES",
				"SALUD",
				"USUARIO",
		};
		
		JButton boton;
		for (String text: buttonsText) {
			boton = new JButton(text);
			
			boton.addActionListener( e -> {
				JButton btnPulsado = (JButton) e.getSource();
				// ACCION POR IMPLEMENTAR
				switch(text) {
				  case "CLASES":
					Actividad actPanel = new Actividad();
					principal.add(actPanel.pActividad, BorderLayout.EAST);
					
				    break;
				  case "SALUD":
				    System.out.println("SALUD");
					  
				    break;
				  case "USUARIO":
					System.out.println("USUARIO");
					
					break;
				  case "MENU":
				  default:
					System.out.println("MENU");
				    // code block
				}

			});
			sidebar.add(boton);
		}
		
		
		// COMPORATMIENTO VENTANA PRINCIPAL
		setSize(200, 350);
		add(principal);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 300));
        setLocationRelativeTo(null);

		setTitle("HOME");
		
		
	}
	
	public static void main(String[] args) {
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.setVisible(true);
	}

}
