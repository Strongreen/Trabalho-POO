package modulos;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.ArrayList;

public class TableTabajara extends JTable{
	Object [][] content;
	String [] header;
	boolean [] permissions;
	int [] columWidth;
	JTable table;
	ArrayList<Object> editados;
	ArrayList<Integer> idDosEditados;
	
	public TableTabajara(Object[][] content, String[] header, boolean[] permissions, int[] columWidth){
		this.content = content;
		this.header = header;
		this.permissions = permissions;
		this.columWidth = columWidth;
		
		editados = new ArrayList<Object>();
		idDosEditados = new ArrayList<Integer>();
		
		start();
	}
	public void start(){
		setModel(new TableModelTabajara());

		setAutoCreateRowSorter(true);
		setFillsViewportHeight(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ajustColumsWidth();
		getModel().addTableModelListener(new TableListenerTabajara());
		
		setEditors();
	}
	public void start(Object[][] content){
		this.content = content;
		
		start();
	}
	
	private void ajustColumsWidth(){
		TableColumn column = null;
		for (int i = 0; i < columWidth.length; i++) {
			column = getColumnModel().getColumn(i);
			
			column.setPreferredWidth(columWidth[i]);
		}
	}
	
	public void setEditors(){
		
	}
	public void update(int linha){
		JOptionPane.showMessageDialog(null,"Método não implementado","Erro",0);
	}
	public void insert(int linha){
		JOptionPane.showMessageDialog(null,"Método não implementado","Erro",0);
	}
	public void delete(int linha){
		JOptionPane.showMessageDialog(null,"Método não implementado","Erro",0);
	}
	
	private class TableModelTabajara extends DefaultTableModel{
		public TableModelTabajara() {
			super(content,header);
		}
		
		public boolean isCellEditable(int rowIndex, int mColIndex) {  
			return permissions[mColIndex];  
		}
	}
	private class TableListenerTabajara implements TableModelListener {
		public void tableChanged(TableModelEvent e) {
			int linha = e.getFirstRow();
			switch(e.getType()){  
				case TableModelEvent.UPDATE:
					update(linha);
					break;
				case TableModelEvent.INSERT:
					insert(linha);
					break;
				case TableModelEvent.DELETE:
					delete(linha);
					break;
			}
		}
	}
}
