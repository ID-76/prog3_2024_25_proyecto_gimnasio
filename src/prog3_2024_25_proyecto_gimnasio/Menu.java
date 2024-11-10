package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Menu extends JPanel {
	
	private JButton btnUsuario, btnSalud, btnClases, btnMenu;
	
	private JPanel pNorte, pSur, pCentro, pEste, pOeste, pCentroArriba,pCentroAbajo;
	
	private JLabel lbMensaje;
	
	private JProgressBar progressBar;
	
	public Menu() {
		
		this.setLayout(new BorderLayout());
			
		pNorte = new JPanel();
		pSur = new JPanel();
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(5, 1));
		pEste = new JPanel();
		pOeste = new JPanel();
		pOeste.setLayout(new GridLayout(4, 1));
		pCentroArriba = new JPanel();
		pCentroAbajo = new JPanel();
		
		this.add(pNorte, BorderLayout.NORTH);
		this.add(pSur, BorderLayout.SOUTH);
		this.add(pCentro, BorderLayout.CENTER);
		this.add(pEste, BorderLayout.EAST);
		this.add(pOeste, BorderLayout.WEST);
		
			
		btnUsuario = new JButton("Usuario");
		btnSalud = new JButton("Salud");
		btnClases = new JButton("Clases");
		btnMenu = new JButton("Menu");
		
		pOeste.add(btnUsuario);
		pOeste.add(btnSalud);
		pOeste.add(btnClases);
		pOeste.add(btnMenu);
		
			
		//lbMensaje = new JLabel("Hola " + VentanaPrincipal.usuario.getNombre()+ ". Estas son tus clases para hoy");
		lbMensaje = new JLabel("Hola nombre. Estas son tus clases para hoy: ");
		lbMensaje.setFont(new Font("Arial", Font.BOLD, 20));
		lbMensaje.setHorizontalAlignment(JLabel.CENTER);
		pCentro.add(lbMensaje, BorderLayout.SOUTH);
		pCentro.add(new JPanel());
		pCentro.add(new JPanel());
		
		
		progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Muestra el porcentaje en la barra
        progressBar.setForeground(Color.BLUE);
        
        

        
        pCentro.add(progressBar, BorderLayout.CENTER);
        pCentro.add(new JPanel());
		
			
		setVisible(true);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(300, 200, 400, 200);
		Menu menu = new Menu();
		f.getContentPane().add(menu);
		f.setVisible(true);
	}
	
	public int contarActividadesporUsuario(Usuario usuario) {
		int contador = 0;
		for (Actividad actividad : VentanaPrincipal.listaActividades) {
			if (actividad.getListaUsuarios().contains(usuario)) {
				contador++;
			}
		}
		return contador;
	}
	
	
}
