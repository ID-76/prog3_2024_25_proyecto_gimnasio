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


public class Salud extends JPanel {
	/**
	 * 
	 */
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
	    private int attendedSessions = 0;  // Track attended sessions
	    private JLabel attendanceLabel;
	    private Calendario Calendario;

	public Salud(Calendario Calendario){
		this.Calendario = Calendario;
		setLayout(new BorderLayout(3, 3));
		
		//PORCENTAJE
		attendanceLabel = new JLabel("Attendance: 0%", SwingConstants.CENTER);
	    attendanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		//PROGRESSBAR
	    progressBar = new JProgressBar(0, 5000);  // Máximo de 5000 kilocalorías
        progressBar.setStringPainted(true);
		//progressBar = new JProgressBar(0, kcalObjetivo);
       // progressBar.setValue(kcalActuales);
        //progressBar.setStringPainted(true);
        progressBar.setString(kcalActuales + " / " + kcalObjetivo + " kcal quemadas");

		// INFORMACION (>>ACTIVIDAD)
		JPanel informacion = new JPanel(new BorderLayout(10, 3));
		informacion.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 20));
		add(informacion, BorderLayout.WEST);
		
		// TITULOS (>>INFORMACION)
		/*
		 * GridBagLayout titulos = (new GridBagLayout());
		 * 
		 * GridBagConstraints gbc = new GridBagConstraints();
		 */
		JPanel titulos = new JPanel(new GridLayout(5, 1));
		informacion.add(titulos, BorderLayout.WEST);

		JLabel lActividad = new JLabel("Actividad:");
		JLabel lConstancia = new JLabel("Constancia:");
		JLabel lRacha= new JLabel("Constancia:");
		JLabel lMRacha = new JLabel("Registro");
		JButton lCalendario = new JButton("Calendario");
		
		 lCalendario.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Crear y mostrar una nueva instancia de VentanaSecundaria
	                Calendario calendario = new Calendario();
	                calendario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            }
	        });
		 
		
		titulos.add(lActividad);
		titulos.add(lConstancia);
		titulos.add(lRacha);
		titulos.add(lMRacha);	
		titulos.add(lCalendario);
		
		//racha
		// Inicializar el conjunto de días asistidos y la interfaz
        diasAsistidos = new HashSet<>();
        lblRacha = new JLabel("Racha actual: 0 días, Racha máxima: 0 días");
     

        // Botón para registrar asistencia diaria
        btnRegistrarDia = new JButton("Registrar Asistencia Hoy");
        
        // Evento para el botón de registrar día
        btnRegistrarDia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate hoy = LocalDate.now();
                if (diasAsistidos.add(hoy)) {
                    calcularRacha();
                    incrementAttendance();
                    registrarEjercicio();
                    //Calendario.highlightToday();
                } else {
                    JOptionPane.showMessageDialog(null, "Ya registraste asistencia para hoy.");
                }
                
            }
        });
        
      
		// DATOS (>>INFORMACION)
				JPanel datos = new JPanel(new GridLayout(5, 1));
				informacion.add(datos, BorderLayout.EAST);

				datos.add(progressBar);
				datos.add(attendanceLabel);
				datos.add(lblRacha);
				datos.add(btnRegistrarDia);
      
		
	}
	
	private void registrarEjercicio() {
        // Opciones de ejercicio y sus calorías aproximadas
        String[] ejercicios = {"Andar (350 kcal)", "Core (400 kcal)", "Core_Avanzado (650 kcal)", "Equilibrio (250 kcal)", "Gimnasia (500 kcal)", "Hiit (550 kcal)", "Yoga (200 kcal)"};
        int[] calorias = {350, 400, 650, 250, 500 ,550, 200};

        // Mostrar las opciones en un diálogo
        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "¿Qué ejercicio hiciste hoy?",
                "Seleccionar Ejercicio",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ejercicios,
                ejercicios[0]
        );

        // Verificar la selección y actualizar la barra de progreso
        if (seleccion != null) {
            for (int i = 0; i < ejercicios.length; i++) {
                if (seleccion.equals(ejercicios[i])) {
                    int nuevoValor = progressBar.getValue() + calorias[i];
                    progressBar.setString(nuevoValor + " / " + kcalObjetivo + " kcal quemadas");
                    progressBar.setStringPainted(true);// No excede el máximo
                    progressBar.setValue(nuevoValor);
                    break;
                }
            }
        }
    }
	 private void incrementAttendance() {
	        if (attendedSessions < totalSessions) {
	            attendedSessions++;
	            int attendancePercentage = (int) ((attendedSessions / (double) totalSessions) * 100);
	            attendanceLabel.setText( attendancePercentage + "% de dias asistidos esta semana");

	            // Optional: Disable button if goal is reached
	            /**if (attendedSessions == totalSessions) {
	                attendButton.setEnabled(false);
	            }**/
	        }
	    }
	private void calcularRacha() {
        rachaActual = 0;
        LocalDate fecha = LocalDate.now();

        // Contamos los días consecutivos hacia atrás
        while (diasAsistidos.contains(fecha)) {
            rachaActual++;
            fecha = fecha.minusDays(1);
        }

        // Actualizamos la racha máxima si la racha actual es mayor
        if (rachaActual > rachaMaxima) {
            rachaMaxima = rachaActual;
        }
        
        // Actualizar la etiqueta de racha
        lblRacha.setText("Racha actual: " + rachaActual + " días, Racha máxima: " + rachaMaxima + " días");
    }
	public static void main(String[] args) {
		Calendario Calendario = new Calendario();
        Calendario.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            Salud app = new Salud(Calendario);
            app.setVisible(true);
        });
	}
	
}