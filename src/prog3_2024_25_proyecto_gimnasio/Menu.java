package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Menu extends JFrame {
	
	private JButton btnUsuario, btnSalud, btnClases, btnMenu;
	
	private JPanel pNorte, pSur, pCentro, pEste, pOeste;
	
	private JLabel lbMensaje;
	
	private JProgressBar progressBar;
	
	public Menu() {
		
		//setTitle("MENU");
		setBounds(300, 400, 800, 600);
			
		pNorte = new JPanel();
		pSur = new JPanel();
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(2, 1));
		pEste = new JPanel();
		pOeste = new JPanel();
		pOeste.setLayout(new GridLayout(4, 1));
		
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pEste, BorderLayout.EAST);
		getContentPane().add(pOeste, BorderLayout.WEST);
		
			
		btnUsuario = new JButton("Usuario");
		btnSalud = new JButton("Salud");
		btnClases = new JButton("Clases");
		btnMenu = new JButton("Menu");
		
		pOeste.add(btnUsuario);
		pOeste.add(btnSalud);
		pOeste.add(btnClases);
		pOeste.add(btnMenu);
		
			
		lbMensaje = new JLabel("Hola nombre. Estas son tus clases para hoy");
		pCentro.add(lbMensaje, BorderLayout.SOUTH);
		
		progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Muestra el porcentaje en la barra
        progressBar.setForeground(Color.BLUE);
        
        

        
        pCentro.add(progressBar, BorderLayout.CENTER);
        
		
			
		setVisible(true);
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
	}
	
	
}
