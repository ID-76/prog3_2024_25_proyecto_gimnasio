package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import main.Actividad;
import main.VentanaPrincipal;

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

        // Logo
        icono = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        icono.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        if (actividad != null) {
            ImageIcon logo = actividad.getLogo();
            logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
            icono.setIcon(logo);
        }
        icono.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        reserva.add(icono, gbc);

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

        if (actividad != null) {
            updateActividadInfo();
            updateButtonState();
        }

        setResizable(false);
        setLocationRelativeTo(null);
        setSize(600, 300);
        setTitle("Clase de " + actividad.getNombre());
        add(principal);
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

        ImageIcon logo = actividad.getLogo();
        logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        icono.setIcon(logo);
    }

    private void updateButtonState() {
        boolean isUserEnrolled = actividad.getListaUsuarios().contains(VentanaPrincipal.usuario);
        int ocupacion = actividad.getOcupacion();
        int capacidad = actividad.getCapacidad();

        apuntarse.setEnabled(!isUserEnrolled && ocupacion < capacidad);
        desapuntarse.setEnabled(isUserEnrolled);
    }
}