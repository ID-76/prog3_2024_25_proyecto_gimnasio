package prog3_2024_25_proyecto_gimnasio;


import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Salud extends JPanel {
    private static final long serialVersionUID = 1L;
    private JProgressBar progressBar;
    private int kcalActuales = 0;
    private int kcalObjetivo = 5000;
    private JLabel lblRacha;
    private JButton btnRegistrarDia;
    private Set<LocalDate> diasAsistidos;
    private int rachaActual = 0;
    private int rachaMaxima = 0;
    private int totalSessions = 7;
    private int attendedSessions = 0;
    private JLabel attendanceLabel;
    private JTextArea infoArea;
    private List<String> historialActividades;
    private int rachaObjetivo = 5; // Nueva meta de racha

    public Salud() {
        setLayout(new BorderLayout(3, 3));

        // Inicializar historial de actividades
        historialActividades = new ArrayList<>();

        // Porcentaje de asistencia
        attendanceLabel = new JLabel("Attendance: 0%", SwingConstants.CENTER);
        attendanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Barra de progreso de kilocalorías
        progressBar = new JProgressBar(0, kcalObjetivo);
        progressBar.setStringPainted(true);
        progressBar.setString(kcalActuales + " / " + kcalObjetivo + " kcal quemadas");

        // Panel de información (izquierda)
        JPanel informacion = new JPanel(new BorderLayout(10, 3));
        informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
        add(informacion, BorderLayout.WEST);

        // Panel de títulos
        JPanel titulos = new JPanel(new GridLayout(6, 1));
        informacion.add(titulos, BorderLayout.WEST);

        JLabel lActividad = new JLabel("Actividad:");
        JLabel lConstancia = new JLabel("Constancia:");
        JLabel lRAcha = new JLabel("Racha:");
        lblRacha = new JLabel("Racha actual: 0 días, Racha máxima: 0 días");
      
        JLabel lMRacha = new JLabel("Registro de actividad:");
        JButton lCalendario = new JButton("Calendario:");
        
        titulos.add(lActividad);
        titulos.add(lConstancia);
        titulos.add(lRAcha);
        titulos.add(lMRacha);
        titulos.add(lCalendario);
        lCalendario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendario calendario = new Calendario();
                calendario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
            }
        });

        // Inicialización de racha y días asistidos
        diasAsistidos = new HashSet<>();

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
                    checkAchievements(); // Verificar logros
                } else {
                    JOptionPane.showMessageDialog(null, "Ya registraste actividad de hoy.");
                }
            }
        });

        // Botón de resumen semanal
        JButton btnResumenSemanal = new JButton("Resumen Semanal");
        btnResumenSemanal.addActionListener(e -> mostrarResumenSemanal());

        // Panel de datos
        JPanel datos = new JPanel(new GridLayout(6, 1));
        informacion.add(datos, BorderLayout.EAST);

        datos.add(progressBar);
        datos.add(attendanceLabel);
        datos.add(lblRacha);
        datos.add(btnRegistrarDia);
        datos.add(btnResumenSemanal);

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
                    progressBar.setValue(nuevoValor);
                    infoArea.append("Ejercicio: " + seleccion + "\n");
                    historialActividades.add(seleccion + " - " + calorias[i] + " kcal");
                    
                    if (nuevoValor >= kcalObjetivo * 0.9) {
                        JOptionPane.showMessageDialog(this, "¡Estás cerca de tu meta semanal de kilocalorías!");
                    }
                    break;
                }
            }
        }
    }

    private void mostrarResumenSemanal() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen Semanal:\n")
                .append("Kilocalorías quemadas: ").append(progressBar.getValue()).append("\n")
                .append("Días de actividad: ").append(diasAsistidos.size()).append("\n")
                .append("Racha más larga: ").append(rachaMaxima).append(" días\n")
                .append("Ejercicios realizados:\n");
        for (String actividad : historialActividades) {
            resumen.append("- ").append(actividad).append("\n");
        }
        JOptionPane.showMessageDialog(this, resumen.toString());
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
            JOptionPane.showMessageDialog(this, "¡Nueva racha máxima: " + rachaMaxima + " días!");
        }

        lblRacha.setText("Racha actual: " + rachaActual + " días, Racha máxima: " + rachaMaxima + " días");

        if (rachaActual >= rachaObjetivo) {
            JOptionPane.showMessageDialog(this, "¡Has alcanzado tu objetivo de racha de " + rachaObjetivo + " días!");
        }
    }

    private void checkAchievements() {
        if (progressBar.getValue() >= kcalObjetivo) {
            infoArea.append("¡Logro desbloqueado: Meta de kilocalorías alcanzada!\n");
        }
        if (rachaActual == rachaObjetivo) {
            infoArea.append("¡Logro desbloqueado: Objetivo de racha alcanzado!\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Salud app = new Salud();
            JFrame frame = new JFrame("Panel de Salud");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(app);
            frame.setSize(800, 400);
            frame.setVisible(true);
        });
    }
}
