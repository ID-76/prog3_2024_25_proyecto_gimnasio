package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import prog3_2024_25_proyecto_gimnasio.Actividad;



public class VentanaPrincipal extends JFrame {
    private JPanel principal;
    
	public VentanaPrincipal() {
		
		JPanel principal = new JPanel(new BorderLayout(2,3));

		JPanel contenido = new JPanel();
		contenido.setBackground(Color.red);
		principal.add(contenido, BorderLayout.CENTER);
		
		
		// SIDEBAR (>> PRINCIPAL)
		JPanel sidebar = new JPanel(new GridLayout(4, 1, 2, 2));
		principal.add(sidebar, BorderLayout.WEST);	
		

		
        JButton bUsuario = new JButton("USUARIO");
        bUsuario.addActionListener(e -> switchPanel("USUARIO"));
        sidebar.add(bUsuario);

        JButton bActividades = new JButton("ACTIVIDADES");
        bActividades.addActionListener(e -> switchPanel("ACTIVIDADES"));
        sidebar.add(bActividades);

        JButton bSalud = new JButton("SALUD");
        bSalud.addActionListener(e -> switchPanel("SALUD"));
        sidebar.add(bSalud);

        JButton bMenu = new JButton("MENU");
        bMenu.addActionListener(e -> switchPanel("MENU"));
        sidebar.add(bMenu);
		
		
        
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
	
    private void switchPanel(String panelType) {
        principal.removeAll(); 
        switch (panelType) {
            case "USUARIO":
                principal.add(new Actividad(), BorderLayout.CENTER);
                break;
            case "ACTIVIDADES":
                principal.add(new Actividad());
                break;
            case "SALUD":
                principal.add(new Actividad());
                break;
            case "MENU":
                principal.add(new Actividad());
                break;
        }
        principal.revalidate();
        principal.repaint();
    }

}
