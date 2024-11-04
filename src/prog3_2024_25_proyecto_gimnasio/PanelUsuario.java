package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PanelUsuario extends JPanel{
	public PanelUsuario(Usuario usuario) {
		
		String[] encabezados_text = {"Nombre", "Apellido", "DNI", "Telefono", "Edad", "Sexo"};
		setLayout(null);
		
		JLabel nom = new JLabel("Nombre");
		nom.setBounds(300, 10, 90, 30);
		add(nom);
		JLabel noml = new JLabel(usuario.getNombre());
		noml.setBounds(300, 42, 150, 30);
		Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
        noml.setBorder(border);
		add(noml);
		
		JLabel ape = new JLabel("Apellido");
		ape.setBounds(460, 10, 90, 30);
		add(ape);
		JLabel apel = new JLabel(usuario.getApellido());
		apel.setBounds(460, 42, 150, 30);
        apel.setBorder(border);
		add(apel);
		
		JSlider ageSlider = new JSlider(0, 100);
        ageSlider.setValue(usuario.getEdad()); 
        ageSlider.setEnabled(false);
        JLabel pointLabel = new JLabel(String.valueOf(usuario.getEdad()));
        ageSlider.setBounds(300, 130, 300, 50);
        pointLabel.setBounds(300 + (int)(usuario.getEdad() * 3.0), 110, 30, 20);//IA used to know how to put the point at the same level of the slider
        add(ageSlider);
        add(pointLabel);
        JLabel label = new JLabel("Edad");
        label.setBounds(300, 80, 90, 30);
        add(label);
        
        JLabel dni = new JLabel("DNI");
		dni.setBounds(300, 170, 90, 30);
		add(dni);
		JLabel dnil = new JLabel(usuario.getDni());
		dnil.setBounds(300, 202, 150, 30);
        dnil.setBorder(border);
		add(dnil);
		
		
	}
}
