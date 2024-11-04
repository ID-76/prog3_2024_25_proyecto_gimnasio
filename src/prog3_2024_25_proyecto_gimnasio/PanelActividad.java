
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
    private JTextArea textArea;
    private JLabel sitiosDisp;
    private JLabel icono;
    private JLabel lDuracion;
    private ArrayList<Actividad> listaActividades;

    public PanelActividad(ArrayList<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
        this.actividadesTipoActual = new ArrayList<>();
        this.actividadesTipoActualFecha = new ArrayList<>();

        if (!listaActividades.isEmpty()) {
            actualActividad = listaActividades.get(0);
        }

        if (!actividadesTipoActualFecha.isEmpty()) {
            actividadesTipoActualFecha.add(listaActividades.get(0).getFecha().toString());
        }
        setLayout(new BorderLayout(3, 3));

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        
        // INFORMACION (>>ACTIVIDAD)
        JPanel informacion = new JPanel(new BorderLayout(10, 3));
        informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
        add(informacion, BorderLayout.EAST);

        
        
        // TITULOS (>>INFORMACION)
        JPanel titulos = new JPanel(new GridLayout(4, 1));
        informacion.add(titulos, BorderLayout.WEST);

        lIntensidad = new JLabel("Intensidad:");
        lDuracion = new JLabel("Duración:");
        JLabel lDescripcion = new JLabel("Descripción:");

        titulos.add(lIntensidad);
        titulos.add(lDuracion);
        titulos.add(lDescripcion);

        
        
        // DATOS (>>INFORMACION)
        JPanel datos = new JPanel(new GridLayout(4, 1));
        informacion.add(datos, BorderLayout.EAST);

        textArea = new JTextArea();
        textArea.setEditable(false);
        datos.add(new JLabel());
        datos.add(new JLabel());
        datos.add(new JScrollPane(textArea));
        datos.add(new JLabel());

        
        
        // RESERVA (>>ACTIVIDAD)
        JPanel reserva = new JPanel();
        reserva.setLayout(new GridBagLayout());
        add(reserva, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
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
        if (actualActividad != null) {
            sitiosDisp.setText(String.valueOf(actualActividad.getOcupacion()));
        }
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(sitiosDisp, gbc);

        // PROGRESS BAR
        JLabel sitiosDispProg = new JLabel("Sitios Disponibles Progress Bar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(sitiosDispProg, gbc);

        // APUNTARSE
        JButton apuntarse = new JButton("Apuntarse");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        apuntarse.setBackground(Color.green);
        apuntarse.setOpaque(true);
        apuntarse.setBorderPainted(false);
        reserva.add(apuntarse, gbc);

        // DESAPUNTARSE
        JButton desapuntarse = new JButton("Desapuntarse");
        gbc.gridx = 1;
        gbc.gridy = 4;
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
        lIntensidad.setText("Intensidad: " + actualActividad.getIntensidad());
        lDuracion.setText("Duración: " + actualActividad.getDuracion());
        textArea.setText(actualActividad.getDescripcion());
        sitiosDisp.setText(String.valueOf(actualActividad.getOcupacion()));
        
        ImageIcon logo = actualActividad.getLogo();
        logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        icono.setIcon(logo);
    }
}
