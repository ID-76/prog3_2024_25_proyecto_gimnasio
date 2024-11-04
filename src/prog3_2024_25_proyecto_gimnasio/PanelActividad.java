
package prog3_2024_25_proyecto_gimnasio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.*;

import prog3_2024_25_proyecto_gimnasio.Actividad.Tipo;

public class PanelActividad extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    int puntero = 0;
    JComboBox<Tipo> tipoActividadCombo;
    JComboBox<String> diaActividadCombo;
    Tipo actualTipo;
    ArrayList<Actividad> actividadesTipoActual;
    ArrayList<String> actividadesTipoActualFecha;
    Actividad actualActividad;
    private JLabel lIntensidad;
    private JTextArea lDescripcion;
    private JLabel sitiosDisp;
    private JLabel icono;
    private JLabel lDuracion;
    private JProgressBar disponibilidadPbar;
    private ArrayList<Actividad> listaActividades;

    public PanelActividad(ArrayList<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
        this.actividadesTipoActual = new ArrayList<>();
        this.actividadesTipoActualFecha = new ArrayList<>();

        if (!listaActividades.isEmpty()) {
            actualActividad = listaActividades.get(0);
            actividadesTipoActualFecha.add(listaActividades.get(0).getFecha().toString());
        }

        setLayout(new BorderLayout(3, 3));

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // INFORMACION (>>ACTIVIDAD)
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
        
        
        
        
        // RESERVA (>>ACTIVIDAD)
        JPanel reserva = new JPanel();
        reserva.setLayout(new GridBagLayout());
        add(reserva, BorderLayout.WEST);

        gbc.insets = new Insets(15, 5, 15, 5);

        // ACTIVIDADES
        tipoActividadCombo = new JComboBox<>(Tipo.values());
        tipoActividadCombo.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(tipoActividadCombo, gbc);

        // DIA
        diaActividadCombo = new JComboBox<>(actividadesTipoActualFecha.toArray(new String[0]));
        diaActividadCombo.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(diaActividadCombo, gbc);

        // LOGO
        icono = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        icono.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        if (actualActividad != null) {
            ImageIcon logo = actualActividad.getLogo();
            logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
            icono.setIcon(logo);
        }
        icono.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        reserva.add(icono, gbc);

        // SITIOS DISPONIBLES
        sitiosDisp = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(sitiosDisp, gbc);

        // PROGRESS BAR
        disponibilidadPbar = new JProgressBar(0, actualActividad.getCapacidad());
        disponibilidadPbar.setStringPainted(true);
        disponibilidadPbar.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(disponibilidadPbar, gbc);

        // APUNTARSE
        JButton apuntarse = new JButton("Apuntarse");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        apuntarse.setBackground(Color.green);
        apuntarse.setOpaque(true);
        apuntarse.setBorderPainted(false);
        reserva.add(apuntarse, gbc);

        // DESAPUNTARSE
        JButton desapuntarse = new JButton("Desapuntarse");
        gbc.gridx = 1;
        gbc.gridy = 6;
        desapuntarse.setBackground(Color.RED);
        reserva.add(desapuntarse, gbc);

        if (actualActividad != null) {
            updateActividadInfo();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tipoActividadCombo) {
            actividadesTipoActual.clear();
            actividadesTipoActualFecha.clear(); // Clear the dates list before populating

            for (Actividad actividad : listaActividades) {
                if (actividad.getTipo() == tipoActividadCombo.getSelectedItem()) {
                    actividadesTipoActual.add(actividad);
                    actividadesTipoActualFecha.add(actividad.getFecha().toString());
                }
            }

            // Update the diaActividadCombo model
            diaActividadCombo.setModel(new DefaultComboBoxModel<>(actividadesTipoActualFecha.toArray(new String[0])));
        }

        if (e.getSource() == diaActividadCombo) {
            actualActividad = actividadesTipoActual.get(diaActividadCombo.getSelectedIndex());
            if (actualActividad != null) {
                updateActividadInfo();
            }

            revalidate();
            repaint();
        }
    }

    private void updateActividadInfo() {
        lIntensidad.setText(actualActividad.getIntensidad());
        lDuracion.setText(String.valueOf(actualActividad.getDuracion()));
        lDescripcion.setText(actualActividad.getDescripcion());
        sitiosDisp.setText("Hay "+ String.valueOf(actualActividad.getCapacidad() - actualActividad.getOcupacion())+" sitios disponibles");
        disponibilidadPbar.setValue(actualActividad.getCapacidad()-actualActividad.getOcupacion());
        if (actualActividad.getCapacidad() - actualActividad.getOcupacion() < 1) {
            disponibilidadPbar.setForeground(Color.RED);
        } else {
            disponibilidadPbar.setForeground(Color.GREEN);
        }

        ImageIcon logo = actualActividad.getLogo();
        logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        icono.setIcon(logo);
    }
}
