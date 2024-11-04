package prog3_2024_25_proyecto_gimnasio;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MatriculaDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre, txtApellido, txtDni, txtNum, txtEdad;
	private JComboBox<Usuario.Sexo> comboSexo;
	private boolean aceptado = false;
	
	public MatriculaDialog() {
		setTitle("Formulario para matricularse");
		setModal(true);
		setLayout(new GridLayout(7,2));
		setMinimumSize(new Dimension(800, 350));
		
		txtNombre = new JTextField();
		txtApellido = new JTextField();
		txtDni = new JTextField();
		txtNum = new JTextField();
		txtEdad = new JTextField();
		comboSexo = new JComboBox<>(Usuario.Sexo.values());
		
		add(new JLabel("Nombre:"));
		add(txtNombre);
		add(new JLabel("Apellido:"));
		add(txtApellido);
		add(new JLabel("DNI:"));
		add(txtDni);
		add(new JLabel("Numero de telefono:"));
		add(txtNum);
		add(new JLabel("Edad:"));
		add(txtEdad);
		add(new JLabel("Sexo:"));
		add(comboSexo);
		
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.addActionListener(e -> {
			aceptado = true;
			setVisible(false);
		});
		
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.addActionListener(e -> {
			setVisible(false);
		});
		
		add(botonCancelar);
		add(botonAceptar);
		setLocationRelativeTo(null);
	}
		
		public boolean isAceptado() {
            return aceptado;
        }

        public String getNombre() {
            return txtNombre.getText();
        }

        public String getApellido() {
            return txtApellido.getText();
        }

        public String getDni() {
            return txtDni.getText();
        }

        public int getNumero() {
            try {
                return Integer.parseInt(txtNum.getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        public int getEdad() {
            try {
                return Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        public Usuario.Sexo getSexo() {
            return (Usuario.Sexo) comboSexo.getSelectedItem();
	}
}
