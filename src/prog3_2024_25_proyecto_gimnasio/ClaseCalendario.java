package prog3_2024_25_proyecto_gimnasio;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClaseCalendario extends JFrame {
    private JTextArea areaCalendario;

    public ClaseCalendario() {
        // Configuración de la ventana
        setTitle("Calendario de Clases Inscritas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Ocultar en lugar de cerrar al presionar "Cerrar"
        setLocationRelativeTo(null);

        // Inicializar área de texto
        areaCalendario = new JTextArea();
        areaCalendario.setEditable(false);

        // Añadir componentes a la ventana
        add(new JScrollPane(areaCalendario), BorderLayout.CENTER);
    }

    public void actualizarCalendario(ArrayList<Actividad> actividadesTipoActual) {
        areaCalendario.setText(""); // Limpia el área de texto
        for (Actividad clase : actividadesTipoActual) {
            areaCalendario.append(clase + "\n"); // Añade cada clase inscrita al área de texto
        }
    }
}
