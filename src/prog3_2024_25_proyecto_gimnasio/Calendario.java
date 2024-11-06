package prog3_2024_25_proyecto_gimnasio;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;



import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel lblMes, lblAno;
    private JButton btnAnterior, btnSiguiente, highlightTodayButton;
    private JTable tabla;
    private int anoActual, mesActual, diaActual;
    private final String[] columnas = {"Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
    private final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private final Calendar calendario = new GregorianCalendar();
    private LocalDate currentDate;

    public Calendario() {
        setTitle("Calendario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        currentDate = LocalDate.now();
        mesActual = calendario.get(Calendar.MONTH);
        anoActual = calendario.get(Calendar.YEAR);
        diaActual = calendario.get(Calendar.DAY_OF_MONTH);

        DefaultTableModel tableModel = new DefaultTableModel(6, 7) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setColumnIdentifiers(columnas);
        tabla = new JTable(tableModel);
        tabla.setRowHeight(38);

        // Add custom cell renderer for highlighting today's date
       // tabla.setDefaultRenderer(Object.class, new CalendarCellRenderer());

        lblMes = new JLabel(meses[mesActual]);
        lblAno = new JLabel(String.valueOf(anoActual));
        btnAnterior = new JButton("<");
        btnSiguiente = new JButton(">");
        highlightTodayButton = new JButton("Highlight Today");

        // Navigation panel
        JPanel pnlNavegacion = new JPanel(new FlowLayout());
        pnlNavegacion.add(btnAnterior);
        pnlNavegacion.add(lblMes);
        pnlNavegacion.add(lblAno);
        pnlNavegacion.add(btnSiguiente);

        // Calendar panel
        JPanel pnlCalendario = new JPanel(new BorderLayout());
        pnlCalendario.add(pnlNavegacion, BorderLayout.NORTH);
        pnlCalendario.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Add panels to frame
        add(pnlCalendario, BorderLayout.CENTER);
        add(highlightTodayButton, BorderLayout.SOUTH);

        // Button actions
        btnAnterior.addActionListener(e -> cambiarMes(-1));
        btnSiguiente.addActionListener(e -> cambiarMes(1));
        highlightTodayButton.addActionListener(e ->highlight() );

        actualizarCalendario(mesActual, anoActual);

        setVisible(true);
    }

    private void highlight() {
    	highlightTodayButton.setEnabled(false);
    	tabla.setDefaultRenderer(Object.class, new CalendarCellRenderer());
    	tabla.repaint();
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

        // Clear table data
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                model.setValueAt(null, i, j);
            }
        }

        // Fill table with days of the month
        int dia = 1;
        for (int i = inicioDiaSemana; i < 7; i++) {
            model.setValueAt(dia++, 0, i);
        }

        int fila = 1;
        while (dia <= diasMes) {
            for (int i = 0; i < 7; i++) {
                if (dia > diasMes) {
                    break;
                }
                model.setValueAt(dia++, fila, i);
            }
            fila++;
        }
    }

    private class CalendarCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Reset background color
            cell.setBackground(Color.WHITE);

            // Highlight today's date if it matches the cell value
            if (value != null && (int) value == diaActual &&
                    mesActual == currentDate.getMonthValue() - 1 &&
                    anoActual == currentDate.getYear()) {
                cell.setBackground(Color.GREEN);
            }

            return cell;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendario::new);
    }
}