	package prog3_2024_25_proyecto_gimnasio;
	
	import java.awt.BorderLayout;
	
	import javax.swing.JButton;
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
			setLayout(new BorderLayout());
			add(matricula, BorderLayout.WEST);
			add(ini_ses, BorderLayout.EAST);
		}
		
		public Usuario getUsuario() {
			return usuario;
		}
	}
