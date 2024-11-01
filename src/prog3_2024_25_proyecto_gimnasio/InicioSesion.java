package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import prog3_2024_25_proyecto_gimnasio.Usuario.Sexo;

public class InicioSesion extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public InicioSesion(VentanaPrincipal ventana){
		JButton matricula = new JButton("Matricularse");
		matricula.addActionListener(e -> {
			this.usuario = new Usuario("Felipe", "Fernandez", "18936382Y", 929382937, 54, Sexo.HOMBRE);
			ventana.setUsuario(usuario);
		});
		JButton ini_ses = new JButton("Iniciar Sesion");
		ini_ses.addActionListener(e -> {
			this.usuario = new Usuario("Felipe", "Fernandez", "18936382Y", 929382937, 54, Sexo.HOMBRE);
			ventana.setUsuario(usuario);
			});
		setLayout(new BorderLayout());
		add(matricula, BorderLayout.WEST);
		add(ini_ses, BorderLayout.EAST);
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
