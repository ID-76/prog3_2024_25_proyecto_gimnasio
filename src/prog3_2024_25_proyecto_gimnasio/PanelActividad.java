package prog3_2024_25_proyecto_gimnasio;

import java.awt.BorderLayout;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import prog3_2024_25_proyecto_gimnasio.Actividad.Tipo;

public class PanelActividad extends JPanel{
	private static final long serialVersionUID = 1L;
	DefaultTableModel modelo;
	JTable tabla;
	List<Actividad> listaActividades;
	HashMap<LocalDateTime,ArrayList<ArrayList<Actividad>>> actividades = new HashMap<>();
	String tipo = "Core";
	LocalDateTime fecha = LocalDateTime.of(2024, 11, 4, 9, 00);
	
	public PanelActividad(List<Actividad> listaActividades) {
		this.listaActividades = listaActividades;
		ArrayList<Actividad> actividadesA = new ArrayList<Actividad>();
		for(Actividad actividad:listaActividades) {
			if (actividad.getNombre().equals(tipo)) {
				actividadesA.add(actividad);
			}
		}
		for(Actividad actividad:actividadesA) {
			
			int diaSe = actividad.getFecha().getDayOfWeek().getValue();
			LocalDateTime lunesSe = actividad.getFecha().minusDays(diaSe-1);
			ArrayList<ArrayList<Actividad>> actSe = actividades.get(lunesSe);
			ArrayList<Actividad> actDiaSe = null;
			if (actSe == null) {
				actSe = new ArrayList<ArrayList<Actividad>>();
				actDiaSe = new ArrayList<Actividad>();
				for (int i=0;i<7;i++) {
					actSe.add(null);
				}
			}else {
				actDiaSe = actSe.get(diaSe);
				
			}
			if(!actividad.getFecha().toLocalTime().isBefore(LocalTime.of(9, 0)) && !actividad.getFecha().toLocalTime().isAfter(LocalTime.of(11, 0))) {
				actDiaSe.set(0, actividad);
			} else if(!actividad.getFecha().toLocalTime().isBefore(LocalTime.of(12, 0)) && !actividad.getFecha().toLocalTime().isAfter(LocalTime.of(14, 00))) {
				actDiaSe.set(1, actividad);
			} else if(!actividad.getFecha().toLocalTime().isBefore(LocalTime.of(16, 0)) && !actividad.getFecha().toLocalTime().isAfter(LocalTime.of(18, 00))){
				actDiaSe.set(2, actividad);
			}else {
				actDiaSe.set(3, actividad);
			}
			actSe.set(diaSe, actDiaSe);
			actividades.put(lunesSe, actSe);
		}
		iniciarTabla();
		loadActividades();
		JPanel principal = new JPanel(new BorderLayout());
		JComboBox<Tipo> tipoActividadCombo = new JComboBox<>(Tipo.values());
		principal.add(tipoActividadCombo,BorderLayout.NORTH);
		principal.add(tabla,BorderLayout.CENTER);
	}
	
	public void iniciarTabla() {
		Vector<String> diasSemana = new Vector<String>(Arrays.asList("Horarios","Lunes","Martes","Miercoles","Jueves","Viernes"));
		modelo = new DefaultTableModel(new Vector<Vector<Object>>(),diasSemana);
		tabla = new JTable(modelo) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			Actividad actividad = (Actividad) value;
			JButton result = new JButton("clase de " + actividad.getNombre());
			result.addActionListener(e -> {
				PanelActividadDialog panel = new PanelActividadDialog(actividad);
				panel.setVisible(true);
			});
			
			result.setBackground(table.getBackground());
			result.setForeground(table.getForeground());
			
			result.setOpaque(true);
			
			return result;
	};
		tabla.setRowHeight(26);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.getTableHeader().setResizingAllowed(false);
		tabla.setAutoCreateRowSorter(true);
		tabla.getTableHeader().setDefaultRenderer(cellRenderer);		
		tabla.setDefaultRenderer(Object.class, cellRenderer);
	}
	
	public void loadActividades() {
		modelo.setRowCount(0);
		ArrayList<ArrayList<Actividad>> as = actividades.get(fecha);
		modelo.addColumn(new String[] {"9:00-11:00","12:00-14:00","15:00-17:00","18:00-20:00"});
		as.forEach(aSe -> {
			ArrayList<Actividad> aH = new ArrayList<Actividad>();
			for (Actividad a:aSe) {
				aH.add(a);
			}
			modelo.addColumn(aH);
		});
	}
}
