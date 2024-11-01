package prog3_2024_25_proyecto_gimnasio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario extends JFrame {
    private static final long serialVersionUID = 1L;
	private JLabel lblMes, lblAno;
    private JButton btnAnterior, btnSiguiente;
    private JTable tabla;
    private JComboBox<String> cmbAno;
    private int anoActual, mesActual, diaActual;
    private final String[] columnas = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
    private final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private final Calendar calendario = new GregorianCalendar();

    public Calendario() {
        // Inicializar componentes
        mesActual = calendario.get(Calendar.MONTH);
        anoActual = calendario.get(Calendar.YEAR);
        diaActual = calendario.get(Calendar.DAY_OF_MONTH);
        

        lblMes = new JLabel(meses[mesActual]);
        lblAno = new JLabel(String.valueOf(anoActual));
        btnAnterior = new JButton("<");
        btnSiguiente = new JButton(">");
        cmbAno = new JComboBox<>();
        tabla = new JTable(6, 7);
        tabla.setRowHeight(38);
        tabla.setEnabled(false);
        
        // Panel de navegación
        JPanel pnlNavegacion = new JPanel();
        pnlNavegacion.setLayout(new FlowLayout());
        pnlNavegacion.add(btnAnterior);
        pnlNavegacion.add(lblMes);
        pnlNavegacion.add(lblAno);
        pnlNavegacion.add(btnSiguiente);

        // Panel del calendario
        JPanel pnlCalendario = new JPanel();
        pnlCalendario.setLayout(new BorderLayout());
        pnlCalendario.add(pnlNavegacion, BorderLayout.NORTH);
        pnlCalendario.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Añadir paneles al frame
        this.setLayout(new BorderLayout());
        this.add(pnlCalendario, BorderLayout.CENTER);

        // Listeners para los botones
        btnAnterior.addActionListener(e -> cambiarMes(-1));
        btnSiguiente.addActionListener(e -> cambiarMes(1));

        actualizarCalendario(mesActual, anoActual);

        // Configuración del JFrame
        this.setTitle("Calendario");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void cambiarMes(int incremento) {
        mesActual += incremento;
        if (mesActual < 0) {
            mesActual = 11;
            anoActual--;
        } else if (mesActual > 11) {
            mesActual = 0;
            anoActual++;
        }
        lblMes.setText(meses[mesActual]);
        lblAno.setText(String.valueOf(anoActual));
        actualizarCalendario(mesActual, anoActual);
    }

    private void actualizarCalendario(int mes, int ano) {
        calendario.set(Calendar.MONTH, mes);
        calendario.set(Calendar.YEAR, ano);
        calendario.set(Calendar.DAY_OF_MONTH, 1);

        int inicioDiaSemana = calendario.get(Calendar.DAY_OF_WEEK) - 1;
        int diasMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[][] data = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                data[i][j] = "";
            }
        }
        
        int dia = 1;
        for (int i = inicioDiaSemana; i < 7; i++) {
            data[0][i] = String.valueOf(dia++);
        }
        
        int fila = 1;
        while (dia <= diasMes) {
            for (int i = 0; i < 7; i++) {
                if (dia > diasMes) {
                    break;
                }
                data[fila][i] = String.valueOf(dia++);
            }
            fila++;
        }
        
        tabla.setModel(new javax.swing.table.DefaultTableModel(data, columnas));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendario::new);
    }
}