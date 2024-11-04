
package prog3_2024_25_proyecto_gimnasio;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import prog3_2024_25_proyecto_gimnasio.Usuario.Sexo;

public class PanelActividad extends JPanel implements ActionListener {
	
	
	
    // Datos test
    int puntero = 0;
    JComboBox<String> nombreActividad;
    Actividad actualActividad;
    ArrayList<Actividad> activs;
    private JLabel lIntensidad;
    private JTextArea textArea;
    private JLabel sitiosDisp;
    private JLabel icono;
    private JLabel lDuracion;

    
    
    
    public PanelActividad() {
        Usuario usuario1 = new Usuario("Aitor", "Garcia", "79043212D", 659921098, 21, Sexo.HOMBRE, "aaa");
        Usuario usuario2 = new Usuario("Ander", "Serrano", "67812930T", 66129273, 25, Sexo.HOMBRE, "aaa");
        Usuario usuario3 = new Usuario("Ane", "Bilbao", "89326102A", 608338214, 54, Sexo.MUJER, "aaa");
        Usuario usuario4 = new Usuario("Maider", "Sebastian", "03671284J", 633901881, 19, Sexo.MUJER, "aaa");

        Actividad actividad1 = new Actividad("Andar", 50, null);
        Actividad actividad2 = new Actividad("Gimnasia", 15, null);
        Actividad actividad3 = new Actividad("Equilibrio", 60, null);
        Actividad actividad4 = new Actividad("Core Avanzado", 30, null);

        if (puntero == 0) {
            activs = new ArrayList<>();
            actualActividad = actividad1;
            puntero++;
            activs.add(actividad1);
            activs.add(actividad2);
            activs.add(actividad3);
            activs.add(actividad4);
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
        JLabel lDuracion = new JLabel("Duración:");
        JLabel lDescripcion = new JLabel("Descripción:");

        titulos.add(lIntensidad);
        titulos.add(lDuracion);
        titulos.add(lDescripcion);

        
        
        // DATOS (>>INFORMACION)
        JPanel datos = new JPanel(new GridLayout(4, 1));
        informacion.add(datos, BorderLayout.EAST);

        datos.add(new JLabel(actualActividad.getIntensidad()));
        datos.add(new JLabel(String.valueOf(actualActividad.getDuracion())));

        textArea = new JTextArea(10, 15);
        textArea.setText(actualActividad.getDescripcion());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(UIManager.getColor("Label.background"));
        textArea.setFont(UIManager.getFont("Label.font"));
        textArea.setBorder(UIManager.getBorder("Label.border"));
        datos.add(textArea);

        
        
        // RESERVA (>>ACTIVIDAD)
        JPanel reserva = new JPanel();
        reserva.setLayout(new GridBagLayout());
        add(reserva, BorderLayout.WEST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 15, 5);

        
        // ACTIVIDADES
        String[] actividades = {actividad1.getNombre(), actividad2.getNombre(), actividad3.getNombre(), actividad4.getNombre()};
        nombreActividad = new JComboBox<>(actividades);
        nombreActividad.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(nombreActividad, gbc);

        
        // LOGO
        icono = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        icono.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
        ImageIcon logo = actualActividad.getLogo();
        logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
        icono.setIcon(logo);
        icono.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        reserva.add(icono, gbc);

        
        // DIA
        JLabel dia = new JLabel("Dia");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        reserva.add(dia, gbc);

        
        // SITIOS DISPONIBLES
        sitiosDisp = new JLabel(String.valueOf(actualActividad.getOcupacion()));
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

        
        //DESAPUNTARSE
        JButton desapuntarse = new JButton("Desapuntarse");
        gbc.gridx = 1;
        gbc.gridy = 4;
        desapuntarse.setBackground(Color.RED);
        reserva.add(desapuntarse, gbc);
    }

    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nombreActividad) {
            int punt = nombreActividad.getSelectedIndex();
            actualActividad = activs.get(punt);

            // With the help of Github Copilot:
            lIntensidad.setText("Intensidad: " + actualActividad.getIntensidad());
            textArea.setText(actualActividad.getDescripcion());
            sitiosDisp.setText(String.valueOf(actualActividad.getOcupacion()));
            ImageIcon logo = actualActividad.getLogo();
            logo = new ImageIcon(logo.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
            icono.setIcon(logo);
            lDuracion.setText("Duración: " + actualActividad.getDuracion());

            // Repaint and revalidate to refresh the UI
            revalidate();
            repaint();
        }
    }
}
