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
 * 自定义显示表格类
 * @author huangyuanwei
 *
 */
public class JTablePanel extends JPanel{
	
	//表格panel及布局
	//public JPanel tablePanel = new JPanel();
	public LayoutByRow panelLayout = new LayoutByRow(this);

	//翻页按钮
	public JButton fstPageButton = new JButton();
	public JButton prePageButton = new JButton();
	public JButton nxtPageButton = new JButton();
	public JButton lstPageButton = new JButton();
	public JTextField gotoPageTextField = new JTextField();
	
	//筛选表格内数据
	public JLabel fillerLabel = new JLabel("筛选：");
	public JTextField fillerTextField = new JTextField();
	
	//表格及滚动条
	public JScrollPane tableScrollPane = new JScrollPane();
	public JTable table = new JTable();
	
	//表格内右键菜单
	public JPopupMenu m_popupMenu = new JPopupMenu();
	
	//提示信息栏
	public JLabel messageLabel = new JLabel();
    
    //表格显示数据
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
		//表格翻页按扭：首页/上一页/下一页/尾页
		panelLayout.setRowInfo(1, 20, 5, 0);
		
		fstPageButton.setFont(new Font("宋体", Font.BOLD, 10));
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
    	gotoPageTextField.setFont(new Font("宋体", Font.BOLD, 10));
    	gotoPageTextField.addActionListener(null);
    	panelLayout.add(gotoPageTextField, 1, 60,'N',0f,0f,'L');
    	
    	panelLayout.add(fillerLabel, 1, 50, 'N', 0, 0, 'R');
    	panelLayout.add(fillerTextField, 1, 80, 'N', 0, 0, 'R');
    	
		//表格属性
		table.setAutoscrolls(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFont(new Font("SimSun", 0, 12));
                
        //将数据放进table中
        DefaultTableModel model = new DefaultTableModel(recordsList, titleList);
        table.setModel(model);
        
		//设置列宽
        fitTableColumns(table);
        //设置表格颜色
        setTableColor(table);
        
        //监听鼠标
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
        
        //左右
		/* 向右方向键为：selectNextColumn
		 * 向左方向键为： selectPreviousColumn
		 * 向上方向键为：selectPreviousRow
		 * 向下方向键为：selectNextRow
		 * 回车事件为：selectNextRowCell*/
        ActionMap am1 = (ActionMap)UIManager.get("Table.actionMap");
        ActionMap am2 = (ActionMap)UIManager.get("Table.actionMap");

        am1.put("selectNextColumn", new AbstractAction(){
           //向右方向键
           @Override
            public void actionPerformed(ActionEvent e) {
               // 自定义事件处理代码
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
            //向左方向键
            @Override
             public void actionPerformed(ActionEvent e) {
                // 自定义事件处理代码
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
            //向上方向键
            @Override
             public void actionPerformed(ActionEvent e) {
                // 自定义事件处理代码
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
            //向下方向键
            @Override
             public void actionPerformed(ActionEvent e) {
                // 自定义事件处理代码
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
        
        //表格垂直滚动条
        tableScrollPane.setViewportView(table);
        panelLayout.add(tableScrollPane, 2, 100, 'B', 1, 1, 'L');
        
        panelLayout.add(messageLabel, 3, 100, 'H', 0, 1, 'L');
        
        panelLayout.setRowPos();
	}
	
	public void setFieldPrompt(){
		int colnum = table.getSelectedColumn();
		int rownum = table.getSelectedRow();
		
		messageLabel.setText("已选择：("+rownum+","+colnum+")");
	}
	
	/**
	 * 设置表格数据筛选器
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
        //为JTable设置排序器
        sorter.setModel(model);
        table.setRowSorter(sorter); 
	}
	
	 /**
	  * 鼠标右键点击事件
	  * @param evt
	  */
	 private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
	     //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
	     if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
	         //通过点击位置找到点击为表格中的行
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
	         //将表格所选项设为当前右键点击的行
	         table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
	         //弹出菜单
	         createPopupMenu();
	         m_popupMenu.show(table, evt.getX(), evt.getY());
	     }
	
	 }
 
	 private void createPopupMenu() {
	     m_popupMenu = new JPopupMenu();
	     
	     JMenuItem updMenItem = new JMenuItem();
	     updMenItem.setText("  编辑该记录  ");
	     updMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //该操作需要做的事
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'U', connection);
	         }
	     });
	     m_popupMenu.add(updMenItem);
	     
	     JMenuItem insertMenItem = new JMenuItem();
	     insertMenItem.setText("  新增记录  ");
	     insertMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //该操作需要做的事
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'I', connection);
	         }
	     });
	     m_popupMenu.add(insertMenItem);
	     
	     JMenuItem delMenItem = new JMenuItem();
	     delMenItem.setText("  删除该记录  ");
	     delMenItem.addActionListener(new java.awt.event.ActionListener() {
	         public void actionPerformed(java.awt.event.ActionEvent evt) {
	             //该操作需要做的事
	        	 JTablePanel_OprTable oprTable = new JTablePanel_OprTable(tableName, selectRowValue, 'D', connection);
	         }
	     });
	     m_popupMenu.add(delMenItem);
	 }
	 
	// 自动调整表格的列宽
	public static void fitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		int fitLookSize = 10;  //为了美观，加大宽度

		Enumeration<?> columns = myTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn column = (TableColumn) columns.nextElement();
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) myTable.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(myTable, column.getIdentifier(), false, false, -1, col)
					.getPreferredSize().getWidth(); // 列标题的宽度
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth(); //
				width = Math.max(width, preferedWidth);

				myTable.getCellRenderer(row, col)
						.getTableCellRendererComponent(myTable, myTable.getValueAt(row, col), false, false, row, col)
						.setBackground(Color.red);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + myTable.getIntercellSpacing().width + fitLookSize);
		}
	}

	// 设置表格颜色
	public static void setTableColor(JTable myTable) {
		int rowCount = myTable.getRowCount();// 行数
		int colCount = myTable.getColumnCount();// 列数

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
