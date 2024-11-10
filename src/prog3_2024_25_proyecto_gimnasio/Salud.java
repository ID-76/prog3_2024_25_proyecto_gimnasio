package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Salud extends JPanel {
    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;
    private int kcalActuales = 0;
    private final int kcalObjetivo = 5000; // Meta semanal en kilocalorías
    private JLabel lblRacha;
    private JButton btnRegistrarDia;
    private Set<LocalDate> diasAsistidos;
    private int rachaActual = 0;
    private int rachaMaxima = 0;
    private int totalSessions = 7;
    private int attendedSessions = 0;
    private JLabel attendanceLabel;
    private Calendario Calendario;
    private ClaseCalendario clasecalendario;
    private JTextArea infoArea; // Nueva área de texto para información adicional

    public Salud(Calendario Calendario) {
        this.Calendario = Calendario;
        setLayout(new BorderLayout(3, 3));

        // Porcentaje de asistencia
        attendanceLabel = new JLabel("Attendance: 0%", SwingConstants.CENTER);
        attendanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Barra de progreso de kilocalorías
        progressBar = new JProgressBar(0, 5000);
        progressBar.setStringPainted(true);
        progressBar.setString(kcalActuales + " / " + kcalObjetivo + " kcal quemadas");

        // Panel de información (izquierda)
        JPanel informacion = new JPanel(new BorderLayout(10, 3));
        informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
        add(informacion, BorderLayout.WEST);

        // Panel de títulos
        JPanel titulos = new JPanel(new GridLayout(5, 1));
        informacion.add(titulos, BorderLayout.WEST);

        JLabel lActividad = new JLabel("Actividad:");
        JLabel lConstancia = new JLabel("Constancia:");
        JLabel lRacha = new JLabel("Constancia:");
        JLabel lMRacha = new JLabel("Registro de actividad");
        JButton lCalendario = new JButton("Calendario");

        lCalendario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendario calendario = new Calendario();
                calendario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ClaseCalendario clasecalendario = new ClaseCalendario();
                clasecalendario.setVisible(true);
            }
        });

        titulos.add(lActividad);
        titulos.add(lConstancia);
        titulos.add(lRacha);
        titulos.add(lMRacha);
        titulos.add(lCalendario);

        // Inicialización de racha y días asistidos
        diasAsistidos = new HashSet<>();
        lblRacha = new JLabel("Racha actual: 0 días, Racha máxima: 0 días");

        // Botón para registrar día de actividad
        btnRegistrarDia = new JButton("Actividad de hoy");
        btnRegistrarDia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate hoy = LocalDate.now();
                if (diasAsistidos.add(hoy)) {
                    calcularRacha();
                    incrementAttendance();
                    registrarEjercicio();
                } else {
                    JOptionPane.showMessageDialog(null, "Ya registraste actividad de hoy.");
                }
            }
        });

        // Panel de datos
        JPanel datos = new JPanel(new GridLayout(5, 1));
        informacion.add(datos, BorderLayout.EAST);

        datos.add(progressBar);
        datos.add(attendanceLabel);
        datos.add(lblRacha);
        datos.add(btnRegistrarDia);

        // Área de texto adicional (derecha)
        infoArea = new JTextArea(10, 20);
        infoArea.setEditable(false);
        infoArea.setBorder(BorderFactory.createTitledBorder("Actividades apuntadas:"));
        add(new JScrollPane(infoArea), BorderLayout.EAST);
    }

    private void registrarEjercicio() {
        String[] ejercicios = {"Andar (350 kcal)", "Core (400 kcal)", "Core Avanzado (650 kcal)", "Equilibrio (250 kcal)", "Gimnasia (500 kcal)", "Hiit (550 kcal)", "Yoga (200 kcal)"};
        int[] calorias = {350, 400, 650, 250, 500, 550, 200};

        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "¿Qué ejercicio hiciste hoy?",
                "Seleccionar Ejercicio",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ejercicios,
                ejercicios[0]
        );

        if (seleccion != null) {
            for (int i = 0; i < ejercicios.length; i++) {
                if (seleccion.equals(ejercicios[i])) {
                    int nuevoValor = progressBar.getValue() + calorias[i];
                    progressBar.setString(nuevoValor + " / " + kcalObjetivo + " kcal quemadas");
                    progressBar.setStringPainted(true);
                    progressBar.setValue(nuevoValor);
                    infoArea.append("Ejercicio: " + seleccion + "\n"); // Añadir al área de texto
                    break;
                }
            }
        }
    }

    private void incrementAttendance() {
        if (attendedSessions < totalSessions) {
            attendedSessions++;
            int attendancePercentage = (int) ((attendedSessions / (double) totalSessions) * 100);
            attendanceLabel.setText(attendancePercentage + "% de días asistidos esta semana");
        }
    }

    private void calcularRacha() {
        rachaActual = 0;
        LocalDate fecha = LocalDate.now();

        while (diasAsistidos.contains(fecha)) {
            rachaActual++;
            fecha = fecha.minusDays(1);
        }

        if (rachaActual > rachaMaxima) {
            rachaMaxima = rachaActual;
        }

        lblRacha.setText("Racha actual: " + rachaActual + " días, Racha máxima: " + rachaMaxima + " días");
    }

    public static void main(String[] args) {
        Calendario Calendario = new Calendario();
        Calendario.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            Salud app = new Salud(Calendario);
            JFrame frame = new JFrame("Panel de Salud");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(app);
            frame.setSize(800, 400); // Ajustar tamaño para ver el área de texto completa
            frame.setVisible(true);
        });
    }
}
