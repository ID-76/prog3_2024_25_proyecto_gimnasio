package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import main.Actividad;
import main.Usuario;
import main.VentanaPrincipal;
import persistence.GestorBD;

public class PanelActividadDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JLabel lIntensidad;
    private JTextArea lDescripcion;
    private JLabel sitiosDisp;
    private JLabel icono;
    private JLabel lDuracion;
    private JProgressBar disponibilidadPbar;
    private JButton apuntarse;
    private JButton desapuntarse;
    private Actividad actividad;

    private ImageIcon imagen1;
    private ImageIcon imagen2;
    private Animador animador;

    public PanelActividadDialog(Actividad actividad) {
        super();
        setModal(true);
        this.actividad = actividad;

        JPanel principal = new JPanel(new BorderLayout(3, 3));

        // Panel de información de actividad
        JPanel informacion = new JPanel();
        informacion.setLayout(new GridBagLayout());
        informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 30));
        add(informacion, BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 15, 5);
        gbc.anchor = GridBagConstraints.EAST;

        JLabel tIntensidad = new JLabel("Intensidad:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(tIntensidad, gbc);

        JLabel tDuracion = new JLabel("Duración:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(tDuracion, gbc);

        JLabel tDescripcion = new JLabel("Descripción:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(tDescripcion, gbc);

        lIntensidad = new JLabel();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(lIntensidad, gbc);

        lDuracion = new JLabel();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(lDuracion, gbc);

        lDescripcion = new JTextArea(5, 15);
        lDescripcion.setEditable(false);
        lDescripcion.setLineWrap(true);
        lDescripcion.setWrapStyleWord(true);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        informacion.add(lDescripcion, gbc);

        // Panel de reserva de actividad
        JPanel reserva = new JPanel();
        reserva.setLayout(new GridBagLayout());
        add(reserva, BorderLayout.WEST);

        gbc.insets = new Insets(15, 5, 15, 5);

        // Logo animado
        icono = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        icono.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        reserva.add(icono, gbc);

        // Cargar imágenes para la animación
        imagen1 = new ImageIcon("Images\\Andar.png");
        imagen2 = new ImageIcon("Images\\Core.png");
        imagen1 = escalarImagen(imagen1, 70, 70);
        imagen2 = escalarImagen(imagen2, 70, 70);

        sitiosDisp = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(sitiosDisp, gbc);

        disponibilidadPbar = new JProgressBar(0, actividad.getCapacidad());
        disponibilidadPbar.setStringPainted(true);
        disponibilidadPbar.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(disponibilidadPbar, gbc);

        
        
        apuntarse = new JButton("Apuntarse");
        apuntarse.addActionListener(this);
        apuntarse.setBackground(Color.green);
        apuntarse.setOpaque(true);
        apuntarse.setBorderPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        reserva.add(apuntarse, gbc);

        desapuntarse = new JButton("Desapuntarse");
        desapuntarse.addActionListener(this);
        desapuntarse.setBackground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 6;
        reserva.add(desapuntarse, gbc);
       

        
        
        
		String dni = VentanaPrincipal.usuario.getDni();
		int id = actividad.getIdSesion();
		if(GestorBD.obtenerParticipacionExiste(dni, id)) {
			apuntarse.setEnabled(false);
			desapuntarse.setEnabled(true);
		} else {
			desapuntarse.setEnabled(false);
		}
			
		updateActividadInfo();


        
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(600, 300);
        setTitle("Clase de " + actividad.getNombre());
        add(principal);
        
        // Iniciar animación
        animador = new Animador();
        animador.start();
    }

    private class Animador extends Thread {
        @Override
        public void run() {
            boolean mostrarImagen1 = true;

            while (!isInterrupted()) {
                try {
                    ImageIcon imagenActual = mostrarImagen1 ? imagen1 : imagen2;
                    SwingUtilities.invokeLater(() -> icono.setIcon(imagenActual));
                    mostrarImagen1 = !mostrarImagen1;
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public static ImageIcon escalarImagen(ImageIcon icon, int ancho, int alto) {
        Image img = icon.getImage();
        Image imgEscalada = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imgEscalada);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == apuntarse) {
            if (!actividad.getListaUsuarios().contains(VentanaPrincipal.usuario)) {
                actividad.addUsuario(VentanaPrincipal.usuario);
                actividad.actualizarOcupacion();
                updateActividadInfo();
                updateButtonState();
            }
        }

        if (e.getSource() == desapuntarse) {
            actividad.removeUsuario(VentanaPrincipal.usuario);
            actividad.actualizarOcupacion();
            updateActividadInfo();
            updateButtonState();
        }
    }

    private void updateActividadInfo() {
        actividad.actualizarOcupacion();
        lIntensidad.setText(actividad.getIntensidad());
        lDuracion.setText(String.valueOf(actividad.getDuracion()));
        lDescripcion.setText(actividad.getDescripcion());
        sitiosDisp.setText("Hay " + (actividad.getCapacidad() - actividad.getOcupacion()) + " sitios disponibles");
        disponibilidadPbar.setValue(actividad.getCapacidad() - actividad.getOcupacion());
        disponibilidadPbar.setForeground(actividad.getCapacidad() - actividad.getOcupacion() < 1 ? Color.RED : Color.GREEN);
    }

    private void updateButtonState() {
        boolean isUserEnrolled = actividad.getListaUsuarios().contains(VentanaPrincipal.usuario);
        int ocupacion = actividad.getOcupacion();
        int capacidad = actividad.getCapacidad();

        apuntarse.setEnabled(!isUserEnrolled && ocupacion < capacidad);
        desapuntarse.setEnabled(isUserEnrolled);
    }

    @Override
    public void dispose() {
        if (animador != null && animador.isAlive()) {
            animador.interrupt();
        }
        super.dispose();
    }
}