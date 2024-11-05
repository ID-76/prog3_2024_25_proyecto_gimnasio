package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JPanel{
	
	private JPanel pNorte, pSur, pCentro, pEste, pOeste;
	
	private JButton btnUsuario, btnSalud, btnClases, btnMenu;
	
	private JLabel lbMensaje;
	
	public Menu() {
		setName("MENU");
		setBounds(500, 600, 800, 700);
		
		pNorte = new JPanel();
		pSur = new JPanel();
		pEste = new JPanel();
		pOeste = new JPanel();
		pOeste.setLayout(new GridLayout(4, 1));
		pCentro = new JPanel();
		
		getRootPane().add(pCentro, BorderLayout.CENTER);
		getRootPane().add(pEste, BorderLayout.EAST);
		getRootPane().add(pNorte, BorderLayout.NORTH);
		getRootPane().add(pOeste, BorderLayout.WEST);
		getRootPane().add(pSur, BorderLayout.SOUTH);
		
		btnUsuario = new JButton("Usuario");
		btnSalud= new JButton("Salud");
		btnClases = new JButton("Clases");
		btnMenu = new JButton("Menu");
		
		pOeste.add(btnUsuario);
		pOeste.add(btnSalud);
		pOeste.add(btnClases);
		pOeste.add(btnMenu);

		lbMensaje = new JLabel("Hola USUARIO, estas son tus clases para hoy: ");
		//lbMensaje.setFont(new Font("Arial", Font.BOLD, 30));
		pCentro.add(lbMensaje);
		
		
		setVisible(true); 
	}
}
