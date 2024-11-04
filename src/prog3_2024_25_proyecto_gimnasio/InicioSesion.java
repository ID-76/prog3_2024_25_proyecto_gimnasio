	package prog3_2024_25_proyecto_gimnasio;
	
	import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
	import prog3_2024_25_proyecto_gimnasio.Usuario.Sexo;
	
	public class InicioSesion extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Usuario usuario;
		
		public InicioSesion(VentanaPrincipal ventana){
			JButton matricula = new JButton("Matricularse");
			matricula.addActionListener(e -> {
				MatriculaDialog matDialog = new MatriculaDialog();
				matDialog.setVisible(true);
				if (matDialog.isAceptado()) {
					this.usuario = new Usuario(matDialog.getNombre(), matDialog.getApellido(), matDialog.getDni(), matDialog.getNumero(), matDialog.getEdad(), matDialog.getSexo(), matDialog.getCont());
					ventana.setUsuario(usuario);
				}
				  
				
			});
			JButton ini_ses = new JButton("Iniciar Sesion");
			ini_ses.addActionListener(e -> {
				IniSesDialog iniDialog = new IniSesDialog();
				iniDialog.setVisible(true);
				if (iniDialog.isAceptado()) {
					Usuario u = null;
					for(Usuario usuario:ventana.getUsuarios()) {
						if (usuario.getDni() == iniDialog.getDni()) {
							u = usuario;
						}
					}
					if (u == null) {
						JOptionPane.showMessageDialog(null, "Usuario no existente");
					}
					if (u.getContraseña() == iniDialog.getDni()) {
						this.usuario = u;
						ventana.setUsuario(usuario);
						ventana.getUsuarios().add(usuario);
					}
				}
				});
			ImageIcon iconoGym = new ImageIcon("/Users/asier.gomez/GitHub/prog3_2024_25_proyecto_gimnasio/Images/gym.png");
			JLabel icono = new JLabel(iconoGym);
			setLayout(null);
			icono.setBounds(30,30,250,250);
			matricula.setBounds(500, 90, 150,50);
			ini_ses.setBounds(500, 150, 150,50);
			add(icono);;
			add(matricula);
			add(ini_ses);
		}
		
		public Usuario getUsuario() {
			return usuario;
		}
	}
