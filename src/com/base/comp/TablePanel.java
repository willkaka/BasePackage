package com.base.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.base.database.SqliteDB;
import com.base.database.Table;
import com.base.layout.LayoutByRow;

public class TablePanel extends JPanel{
	
	private LayoutByRow tPanelLayout = new LayoutByRow(this);
	
	private JButton fstPageBtn = new JButton();
	private JButton prePageBtn = new JButton();
	private JButton nxtPageBtn = new JButton();
	private JButton lstPageBtn = new JButton();
	
	private JTextField filterStrTextField = new JTextField();
	private JLabel promptLabel = new JLabel("��ʾ��Ϣ");
	
	private JTable jTable = new JTable();
	
	private JPopupMenu m_popupMenu = new JPopupMenu();
    private String dbTableName = null;
    private int selectedRowNum = 0;
    private int selectedColNum = 0;
    private Vector<Object> selectRowValue = new Vector<Object>(); 
    private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
    
	public TablePanel(Vector rows, Vector cols){
		
		tPanelLayout.setTopGap(0);
		tPanelLayout.setBotGap(0);
		tPanelLayout.setRowGap(1, 5, 5, 5);
		tPanelLayout.setRowInfo(1, 20, 5, 5);
		//���
		jTable.setAutoscrolls(true);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setFont(new Font("SimSun", 0, 12));
		
		tPanelLayout.add(fstPageBtn, 1, 30, 'N', 0, 0, 'L');
		tPanelLayout.add(prePageBtn, 1, 30, 'N', 0, 0, 'L');
		tPanelLayout.add(nxtPageBtn, 1, 30, 'N', 0, 0, 'L');
		tPanelLayout.add(lstPageBtn, 1, 30, 'N', 0, 0, 'L');
		
		tPanelLayout.add(filterStrTextField, 1, 50, 'N', 0, 0, 'R');
		
        //�����ݷŽ�table��
        setFieldInfoTableDataFromQuery(rows, cols);
        
        //�������
        this.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
				jTable1MouseClicked(e);  
				TablePanel tempTable = (TablePanel) e.getSource();
				//setFieldPrompt(tempTable);
			}
        });
        
        
        //����
		/* ���ҷ����Ϊ��selectNextColumn
		 * �������Ϊ�� selectPreviousColumn
		 * ���Ϸ����Ϊ��selectPreviousRow
		 * ���·����Ϊ��selectNextRow
		 * �س��¼�Ϊ��selectNextRowCell*/
        ActionMap am1 = (ActionMap)UIManager.get("Table.actionMap");
        ActionMap am2 = (ActionMap)UIManager.get("Table.actionMap");

        am1.put("selectNextColumn", new AbstractAction(){
           //���ҷ����
           @Override
            public void actionPerformed(ActionEvent e) {
               // �Զ����¼��������
        	   JTable tempTable = (JTable) e.getSource();
        	    int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if(col < tempTable.getColumnCount()-1){
					tempTable.setColumnSelectionInterval(col+1, col+1);
					tempTable.changeSelection(row, col+1, false, true);
				}
				//setFieldPrompt(tempTable);
            }
        });
        am2.put("selectPreviousColumn", new AbstractAction(){
            //�������
            @Override
             public void actionPerformed(ActionEvent e) {
                // �Զ����¼��������
            	
            	JTable tempTable = (JTable) e.getSource();
            	//JScrollPane scroll = tempTable.roll
				int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if(col >= 1){
					tempTable.setColumnSelectionInterval(col-1, col-1);
					tempTable.changeSelection(row, col-1, false, true);
				}
				//setFieldPrompt(tempTable);
             }
         });
        jTable.setActionMap(am1);
		jTable.setActionMap(am2);
        
		jTable.repaint();
		jTable.updateUI(); 
		
        tPanelLayout.setRowInfo(2, 300, 5, 5);
		tPanelLayout.add(jTable, 2, 100, 'H', 0, 1, 'L');
		
		tPanelLayout.setRowInfo(3, 50, 5, 5);
		tPanelLayout.add(promptLabel, 3, 100, 'H', 0, 1, 'L');
		
		this.repaint();
		this.updateUI(); 
	}
/*	public void setFieldPrompt(JTablePanel table){
		int colnum = table.getSelectedColumn();
		if(colnum > 0 && (frame.getSelectedModel().equals("Sql") && frame.getSqlstm()!=null || frame.getSelectedModel().equals("Record"))){
			Vector<TableField> tableFields = new Vector<>();
			if (frame.getSelectedModel().equals("Sql")) {
				String ssql = frame.getSqlstm().replace('\n',' ');
				tableFields = SqlStatementParser.getSelectField(ssql);
			} else if (frame.getSelectedModel().equals("Record")) {
				tableFields = QueryTableInfo.getVectorTableFieldListForX(frame.getSelectedTable());
			}
			//System.out.println("size:"+tableFields.size()+"  colnum:"+colnum);
			TableField field = tableFields.get(colnum-1);
			String fieldMessage = PrcString.ConvertToCamelCase(field.getFieldName()) + " : " + field.getFieldType() +"("+field.getFieldLen();
			
			if (field.getFieldType().equals("NUMBER"))
				fieldMessage = fieldMessage + "," + field.getFieldDec() + ")";
			else
				fieldMessage = fieldMessage + ")";
			fieldMessage = fieldMessage + "  " + field.getFieldDsc();
			frame.getMessageLabel().setText(fieldMessage);
			frame.repaint();
		}else{
			frame.getMessageLabel().setText(null);
		}
	}*/
	
	public void setFieldInfoTableDataFromQuery(Vector rowData, Vector colData){
		        		
		DefaultTableModel model = new DefaultTableModel(rowData, colData);
        jTable.setModel(model);
        
        String filterText = filterStrTextField.getText();
        if(filterText == null || filterText.equals("")){
        	sorter.setRowFilter(null);
        }
        else{
        	sorter.setRowFilter(RowFilter.regexFilter(filterText));
        }
        //ΪJTable����������
        sorter.setModel(model);
        jTable.setRowSorter(sorter); 
		
		//�����п�
        fitTableColumns(jTable);
        //���ñ����ɫ
        setTableColor(jTable);
	}
	
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

	       mouseRightButtonClick(evt);
	}
	
	public LayoutByRow getTablePanelLayout(){
		return tPanelLayout;
	}
	
	 //����Ҽ�����¼�
 private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
     //�ж��Ƿ�Ϊ����BUTTON3��ť��BUTTON3Ϊ����Ҽ�
     if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
         //ͨ�����λ���ҵ����Ϊ����е���
         int focusedRowIndex = jTable.rowAtPoint(evt.getPoint());
         if (focusedRowIndex == -1) {
             return;
         }
         selectRowValue.removeAllElements();
         selectedRowNum = jTable.rowAtPoint(evt.getPoint());
         selectedColNum = jTable.columnAtPoint(evt.getPoint());
         for (int i=0; i<jTable.getColumnCount(); i++){
        	 selectRowValue.addElement(jTable.getValueAt(selectedRowNum, i));
         }
         //System.out.println("selected:row="+selectedRowNum + " col="+selectedColNum + " value:"+selectedCellValue);
         //�������ѡ����Ϊ��ǰ�Ҽ��������
         jTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
         //�����˵�
         //createPopupMenu();
         m_popupMenu.show(this, evt.getX(), evt.getY());
     }

 }
 
 /*private void createPopupMenu() {
     m_popupMenu = new JPopupMenu();
     
     JMenuItem updMenItem = new JMenuItem();
     updMenItem.setText("  �༭�ü�¼  ");
     updMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //�ò�����Ҫ������
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'U');
         }
     });
     m_popupMenu.add(updMenItem);
     
     JMenuItem insertMenItem = new JMenuItem();
     insertMenItem.setText("  ������¼  ");
     insertMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //�ò�����Ҫ������
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'I');
         }
     });
     m_popupMenu.add(insertMenItem);
     
     JMenuItem delMenItem = new JMenuItem();
     delMenItem.setText("  ɾ���ü�¼  ");
     delMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //�ò�����Ҫ������
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'D');
         }
     });
     m_popupMenu.add(delMenItem);
 }*/
 
	// �Զ����������п�
	public static void fitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		int fitLookSize = 10;  //Ϊ�����ۣ��Ӵ���

		Enumeration<?> columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) myTable.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth(); // �б���Ŀ��
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth(); //
				width = Math.max(width, preferedWidth);

				myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.setBackground(Color.red);
			}
			header.setResizingColumn(column); // ���к���Ҫ
			column.setWidth(width + myTable.getIntercellSpacing().width + fitLookSize);
		}
	}

	// ���ñ����ɫ
	public static void setTableColor(JTable myTable) {
		int rowCount = myTable.getRowCount();// ����
		int colCount = myTable.getColumnCount();// ����

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if ((row + 1) % 2 != 0)
					setBackground(new Color(0xee, 0xee, 0xee));
				else
					setBackground(new Color(255, 255, 255));
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};

		for (int i = 0; i < colCount; i++) {
			myTable.getColumn(myTable.getColumnName(i)).setCellRenderer(tcr);
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("test");
		LayoutByRow frameLayout = new LayoutByRow(frame);
		frameLayout.setRowInfo(1, 500, 10, 10);
		
		Connection conn = SqliteDB.getConnection();
				
		String tableName = "envdatabaseinfo";
		Vector cols = null;
		Vector rows = null;
		try{
			cols = Table.getTableFieldsComment(tableName, conn);
			rows = Table.getTableRecords(tableName, null, conn);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		TablePanel tPanel = new TablePanel(rows, cols);
		frameLayout.add(tPanel, 1, 500, 'H', 0, 1, 'L');
		frameLayout.setCompLayout(tPanel, tPanel.getTablePanelLayout());
		
		//frame.add(tPanel);
		frame.setBounds(450, 200, 530, 625);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		frameLayout.setRowPos();
		
		frame.setVisible(true);
    	frame.repaint();
	}
}

