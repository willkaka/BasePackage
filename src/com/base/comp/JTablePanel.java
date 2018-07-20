package com.base.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
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

import com.base.layout.LayoutByRow;

/**
 * �Զ�����ʾ�����
 * @author huangyuanwei
 *
 */
public class JTablePanel extends JPanel{
	
	//���panel������
	//public JPanel tablePanel = new JPanel();
	public LayoutByRow panelLayout = new LayoutByRow(this);

	//��ҳ��ť
	public JButton fstPageButton = new JButton();
	public JButton prePageButton = new JButton();
	public JButton nxtPageButton = new JButton();
	public JButton lstPageButton = new JButton();
	public JTextField gotoPageTextField = new JTextField();
	
	//ɸѡ���������
	public JLabel fillerLabel = new JLabel("ɸѡ��");
	public JTextField fillerTextField = new JTextField();
	
	//��񼰹�����
	public JScrollPane tableScrollPane = new JScrollPane();
	public JTable table = new JTable();
	
	//������Ҽ��˵�
	public JPopupMenu m_popupMenu = new JPopupMenu();
	
	//��ʾ��Ϣ��
	public JLabel messageLabel = new JLabel();
    
    //�����ʾ����
    public Vector<Object> selectRowValue = new Vector<Object>(); 
    public TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
    
    public Connection connection = null;
    public String tableName = null;
    public int selectedRowNum = 0;
    public int selectedColNum = 0;
    
    
	public JTablePanel(String tableName, Vector<?> titleList, Vector<?> recordsList, Connection connection){
		this.tableName = tableName;
		this.connection = connection;
		
		this.setBounds(1, 1, 500, 500);
		//���ҳ��Ť����ҳ/��һҳ/��һҳ/βҳ
		panelLayout.setRowInfo(1, 20, 5, 0);
		
		fstPageButton.setFont(new Font("����", Font.BOLD, 10));
		fstPageButton.setName("fstPageButton");
		fstPageButton.setIcon(new ImageIcon("./images/firstpage.jpg"));
		panelLayout.add(fstPageButton, 1, 30,'N',0f,0f,'L');
    	
    	prePageButton.setName("prePageButton");
    	prePageButton.setIcon(new ImageIcon("./images/prepage.jpg"));
    	panelLayout.add(prePageButton, 1, 30,'N',0f,0f,'L');
    	
    	nxtPageButton.setName("nxtPageButton");
    	nxtPageButton.setIcon(new ImageIcon("./images/nxtpage.jpg"));
    	panelLayout.add(nxtPageButton, 1, 30,'N',0f,0f,'L');
    	
    	lstPageButton.setName("lstPageButton");
    	lstPageButton.setIcon(new ImageIcon("./images/lastpage.jpg"));
    	panelLayout.add(lstPageButton, 1, 30,'N',0f,0f,'L');
    	
    	gotoPageTextField.setName("gotoPageTextField");
    	gotoPageTextField.setFont(new Font("����", Font.BOLD, 10));
    	gotoPageTextField.addActionListener(null);
    	panelLayout.add(gotoPageTextField, 1, 60,'N',0f,0f,'L');
    	
    	panelLayout.add(fillerLabel, 1, 50, 'N', 0, 0, 'R');
    	panelLayout.add(fillerTextField, 1, 80, 'N', 0, 0, 'R');
    	
		//�������
		table.setAutoscrolls(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("SimSun", 0, 12));
                
        //�����ݷŽ�table��
        DefaultTableModel model = new DefaultTableModel(recordsList, titleList);
        table.setModel(model);
        
		//�����п�
        fitTableColumns(table);
        //���ñ����ɫ
        setTableColor(table);
        
        //�������
        table.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				mouseRightButtonClick(e);  
				JTable tempTable = (JTable) e.getSource();
				setFieldPrompt();
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
        	    int colmax = tempTable.getColumnCount();
				int row = tempTable.getSelectedRow();
				int rowmax = tempTable.getRowCount();
				if(col < tempTable.getColumnCount() -1 ){
					tempTable.setColumnSelectionInterval(col+1, col+1);
					tempTable.changeSelection(row, col+1, false, true);
				}
				setFieldPrompt();
            }
        });
        am2.put("selectPreviousColumn", new AbstractAction(){
            //�������
            @Override
             public void actionPerformed(ActionEvent e) {
                // �Զ����¼��������
            	JTable tempTable = (JTable) e.getSource();
				int col = tempTable.getSelectedColumn();
				int colmax = tempTable.getColumnCount();
				int row = tempTable.getSelectedRow();
				int rowmax = tempTable.getRowCount();
				if(col > 0 && col < colmax ){
					tempTable.setColumnSelectionInterval(col-1, col-1);
					tempTable.changeSelection(row, col-1, false, true);
				}
				setFieldPrompt();
             }
         });
        am2.put("selectPreviousRow", new AbstractAction(){
            //���Ϸ����
            @Override
             public void actionPerformed(ActionEvent e) {
                // �Զ����¼��������
            	JTable tempTable = (JTable) e.getSource();
				int col = tempTable.getSelectedColumn();
				int colmax = tempTable.getColumnCount();
				int row = tempTable.getSelectedRow();
				int rowmax = tempTable.getRowCount();
				if(row >= 1){
					tempTable.setRowSelectionInterval(row-1, row-1);
					tempTable.changeSelection(row-1, col, false, true);
				}
				setFieldPrompt();
             }
         });
        am2.put("selectNextRow", new AbstractAction(){
            //���·����
            @Override
             public void actionPerformed(ActionEvent e) {
                // �Զ����¼��������
            	JTable tempTable = (JTable) e.getSource();
				int col = tempTable.getSelectedColumn();
				int colmax = tempTable.getColumnCount();
				int row = tempTable.getSelectedRow();
				int rowmax = tempTable.getRowCount();
				if(row >= 0 && row < rowmax -1){
					tempTable.setRowSelectionInterval(row+1, row+1);
					tempTable.changeSelection(row+1, col, false, true);
				}
				setFieldPrompt();
             }
         });

        table.setActionMap(am1);
        table.setActionMap(am2);
        
        table.repaint();
        table.updateUI(); 
        
        //���ֱ������
        tableScrollPane.setViewportView(table);
        panelLayout.add(tableScrollPane, 2, 100, 'B', 1, 1, 'L');
        
        panelLayout.add(messageLabel, 3, 100, 'H', 0, 1, 'L');
        
        panelLayout.setRowPos();
	}
	
	public void setFieldPrompt(){
		int colnum = table.getSelectedColumn();
		int rownum = table.getSelectedRow();
		
		messageLabel.setText("��ѡ��("+rownum+","+colnum+")");
	}
	
	/**
	 * ���ñ������ɸѡ��
	 */
	public void setFieldInfoTableData(){
		/*DefaultTableModel model = new DefaultTableModel(rowData, colData);
		table.setModel(model);*/
		DefaultTableModel model = (DefaultTableModel) table.getModel();
        String filterText = fillerTextField.getText();
        if(filterText == null || filterText.equals("")){
        	sorter.setRowFilter(null);
        }
        else{
        	sorter.setRowFilter(RowFilter.regexFilter(filterText));
        }
        //ΪJTable����������
        sorter.setModel(model);
        table.setRowSorter(sorter); 
	}
	
	 /**
	  * ����Ҽ�����¼�
	  * @param evt
	  */
	 private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
	     //�ж��Ƿ�Ϊ����BUTTON3��ť��BUTTON3Ϊ����Ҽ�
	     if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
	         //ͨ�����λ���ҵ����Ϊ����е���
	         int focusedRowIndex = table.rowAtPoint(evt.getPoint());
	         if (focusedRowIndex == -1) {
	             return;
	         }
	         selectRowValue.removeAllElements();
	         selectedRowNum = table.rowAtPoint(evt.getPoint());
	         selectedColNum = table.columnAtPoint(evt.getPoint());
	         for (int i=0; i<table.getColumnCount(); i++){
	        	 selectRowValue.addElement(table.getValueAt(selectedRowNum, i));
	         }
	         //System.out.println("selected:row="+selectedRowNum + " col="+selectedColNum + " value:"+selectedCellValue);
	         //�������ѡ����Ϊ��ǰ�Ҽ��������
	         table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
	         //�����˵�
	         createPopupMenu();
	         m_popupMenu.show(table, evt.getX(), evt.getY());
	     }
	
	 }
 
	 private void createPopupMenu() {
	     m_popupMenu = new JPopupMenu();
	     
	     JMenuItem updMenItem = new JMenuItem();
	     updMenItem.setText("  �༭�ü�¼  ");
	     updMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //�ò�����Ҫ������
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'U', connection);
	         }
	     });
	     m_popupMenu.add(updMenItem);
	     
	     JMenuItem insertMenItem = new JMenuItem();
	     insertMenItem.setText("  ������¼  ");
	     insertMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //�ò�����Ҫ������
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'I', connection);
	         }
	     });
	     m_popupMenu.add(insertMenItem);
	     
	     JMenuItem delMenItem = new JMenuItem();
	     delMenItem.setText("  ɾ���ü�¼  ");
	     delMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //�ò�����Ҫ������
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'D', connection);
	         }
	     });
	     m_popupMenu.add(delMenItem);
	 }
	 
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
	
	
	public LayoutByRow getPanelLayout() {
		return panelLayout;
	}
}
