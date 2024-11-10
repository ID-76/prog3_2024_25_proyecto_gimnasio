package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import prog3_2024_25_proyecto_gimnasio.Actividad.Tipo;

public class PanelActividad extends JPanel {
    private static final long serialVersionUID = 1L;
    DefaultTableModel modelo;
    JTable tabla;
    List<Actividad> listaActividades;
    HashMap<LocalDateTime, ArrayList<ArrayList<Actividad>>> actividades = new HashMap<>();
    String tipo = "ANDAR";
    LocalDateTime fecha = LocalDateTime.of(2024, 11, 4, 9, 00);

    public PanelActividad(List<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
        actualizarMap();

        iniciarTabla();
        loadActividades();

        JPanel principal = new JPanel(new BorderLayout());
        JComboBox<Tipo> tipoActividadCombo = new JComboBox<>(Tipo.values());
        tipoActividadCombo.setPreferredSize(new Dimension(600, 20));
        tipoActividadCombo.addActionListener(e -> {
        	tipo = ((Tipo) tipoActividadCombo.getSelectedItem()).toString();
        	actualizarMap();
            loadActividades();
        	modelo.fireTableDataChanged();
        });
        principal.add(tipoActividadCombo, BorderLayout.NORTH);
        principal.add(new JScrollPane(tabla), BorderLayout.CENTER);
        this.add(principal);
    }
    
    public void actualizarMap() {
    	actividades = new HashMap<>();
    	ArrayList<Actividad> actividadesA = new ArrayList<Actividad>();
        for (Actividad actividad : listaActividades) {
            if (actividad.getTipo().toString().equals(tipo)) {
                actividadesA.add(actividad);
            }
        }
        for (Actividad actividad : actividadesA) {
            int diaSe = actividad.getFecha().getDayOfWeek().getValue();
            LocalDateTime lunesSe = actividad.getFecha().minusDays(diaSe - 1);
            ArrayList<ArrayList<Actividad>> actSe = actividades.getOrDefault(lunesSe, new ArrayList<>());
        
            while (actSe.size() < 7) {
                actSe.add(new ArrayList<>(Arrays.asList(null, null, null, null)));
            }

            ArrayList<Actividad> actDiaSe = actSe.get(diaSe - 1);
            int slotIndex = getSlotIndex(actividad.getFecha().toLocalTime());

            if (slotIndex != -1) {
                actDiaSe.set(slotIndex, actividad);
            }
            actividades.put(lunesSe, actSe);
        }
    }
    private int getSlotIndex(LocalTime time) {
        if (!time.isBefore(LocalTime.of(9, 0)) && time.isBefore(LocalTime.of(11, 0))) return 0;
        if (!time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(14, 0))) return 1;
        if (!time.isBefore(LocalTime.of(16, 0)) && time.isBefore(LocalTime.of(18, 0))) return 2;
        if (!time.isBefore(LocalTime.of(18, 0)) && time.isBefore(LocalTime.of(20, 0))) return 3;
        return -1;
    }

    public void iniciarTabla() {
        Vector<String> diasSemana = new Vector<>(Arrays.asList("Horarios", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"));
        modelo = new DefaultTableModel(diasSemana, 0);
        tabla = new JTable(modelo) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
        	if (value instanceof String) {
        		JLabel label = new JLabel((String) value);
    			
        		label.setBackground(table.getBackground());
        		label.setForeground(table.getForeground());
        			
        		label.setOpaque(true);
        			
        		return label;
        	}else {
        		Actividad actividad = (Actividad) value;
    			if (value == null) {
    				return new JLabel("");
    			}else {
    				JButton result = new JButton(actividad.getNombre());
    				result.addActionListener(e -> {
    					PanelActividadDialog panel = new PanelActividadDialog(actividad);
    					panel.setVisible(true);
    				});
    				
    				result.setBackground(table.getBackground());
    				result.setForeground(table.getForeground());
    				
    				result.setOpaque(true);
    				
    				return result;
        	}
			
			}
	};
	
	TableCellRenderer headersRenderer = (table, value, isSelected, hasFocus, row, column) -> {
		JLabel label = new JLabel((String) value);
			
		label.setBackground(table.getBackground());
		label.setForeground(table.getForeground());
			
		label.setOpaque(true);
			
		return label;
	};

        tabla.setRowHeight(60);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(240);
        for (int i = 1; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(300);
        }
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getTableHeader().setDefaultRenderer(headersRenderer);		
		tabla.setDefaultRenderer(Object.class, cellRenderer);
    }

    public void loadActividades() {
        modelo.setRowCount(0);
        ArrayList<String> horarios = new ArrayList<>(Arrays.asList("9:00-11:00", "12:00-14:00", "16:00-18:00", "18:00-20:00"));

        ArrayList<ArrayList<Actividad>> as = actividades.get(fecha);
        if (as != null) {
            for (int i = 0; i < 4; i++) {
                Vector<Object> row = new Vector<>();
                row.add(horarios.get(i));
                for (int j = 0; j < 7; j++) {
                    Actividad actividad = as.get(j).get(i);
                    row.add(actividad != null ? actividad : "");
                }
                modelo.addRow(row);
            }
        }
    }
}