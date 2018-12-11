package com.base.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.base.database.SqliteDB;
import com.base.database.Table;
import com.base.database.TableField;
import com.base.function.PrcString;
import com.base.layout.LayoutByRow;

public class TablePanel extends JPanel {

	private LayoutByRow tPanelLayout = new LayoutByRow(this);

	private JButton fstPageBtn = new JButton(new ImageIcon(System.getProperty("user.dir") + "\\images\\firstpage.jpg"));
	private JButton prePageBtn = new JButton(new ImageIcon(System.getProperty("user.dir") + "\\images\\prepage.jpg"));
	private JButton nxtPageBtn = new JButton(new ImageIcon(System.getProperty("user.dir") + "\\images\\nxtpage.jpg"));
	private JButton lstPageBtn = new JButton(new ImageIcon(System.getProperty("user.dir") + "\\images\\lastpage.jpg"));

	private JLabel filterLabel = new JLabel("筛选: ");
	private JTextField filterStrTextField = new JTextField();
	private JLabel promptLabel = new JLabel();

	private JTable jTable = new JTable();
	private JScrollPane jTableScroll = new JScrollPane(jTable);
	private LayoutByRow jTableScrollLayout = new LayoutByRow(jTableScroll);

	private JPopupMenu m_popupMenu = new JPopupMenu();
	private String dbTableName = null;
	private int selectedRowNum = 0;
	private int selectedColNum = 0;
	private Vector<Object> selectRowValue = new Vector<Object>();
	private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();

	private String filterString = null;
	
	private Vector<TableField> aTableFields = null;
	private Vector rows = null;

	public TablePanel(Vector<TableField> aTableFields, Vector rows) {
		this.aTableFields = aTableFields;
		this.rows = rows;

		tPanelLayout.setTopGap(10);
		tPanelLayout.setBotGap(0);
		tPanelLayout.setRowGap(1, 10, 25, 5);
		tPanelLayout.setRowInfo(1, 20, 5, 5);
		// 表格
		jTable.setAutoscrolls(true);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setFont(new Font("SimSun", 0, 12));

		tPanelLayout.add(fstPageBtn, 1, 20, 'N', 0, 0, 'L');
		tPanelLayout.add(prePageBtn, 1, 20, 'N', 0, 0, 'L');
		tPanelLayout.add(nxtPageBtn, 1, 20, 'N', 0, 0, 'L');
		tPanelLayout.add(lstPageBtn, 1, 20, 'N', 0, 0, 'L');

		tPanelLayout.add(filterLabel, 1, 40, 'N', 0, 0, 'R');
		
		//筛选
		filterStrTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTableData(getRows(), getaTableFields());
			}
		});
    	//获取与编辑器关联的模型  
        Document doc = filterStrTextField.getDocument();  
          
        //添加DocumentListener监听器  
        doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Document doc = e.getDocument();  
		        try {
					setFilterString(doc.getText(0, doc.getLength()));
					setTableData(getRows(), getaTableFields());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //返回文本框输入的内容
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Document doc = e.getDocument();  
		        try {
		        	setFilterString(doc.getText(0, doc.getLength()));
					setTableData(getRows(), getaTableFields());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //返回文本框输入的内容
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Document doc = e.getDocument();  
		        try {
		        	setFilterString(doc.getText(0, doc.getLength()));
					setTableData(getRows(), getaTableFields());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //返回文本框输入的内容
			}
		}); 
		tPanelLayout.add(filterStrTextField, 1, 100, 'N', 0, 0, 'R');

		// 将数据放进table中
		setTableData(rows, aTableFields);

		// 监听鼠标
		jTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				jTable1MouseClicked(e);
				JTable tempTable = (JTable) e.getSource();
				setFieldPrompt(tempTable);
			}
		});

		// 左右
		/*
		 * 向右方向键为：selectNextColumn 向左方向键为： selectPreviousColumn
		 * 向上方向键为：selectPreviousRow 向下方向键为：selectNextRow 回车事件为：selectNextRowCell
		 */
		ActionMap am1 = (ActionMap) UIManager.get("Table.actionMap");
		ActionMap am2 = (ActionMap) UIManager.get("Table.actionMap");

		am1.put("selectNextColumn", new AbstractAction() {
			// 向右方向键
			@Override
			public void actionPerformed(ActionEvent e) {
				// 自定义事件处理代码
				JTable tempTable = (JTable) e.getSource();
				int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if (col < tempTable.getColumnCount() - 1) {
					tempTable.setColumnSelectionInterval(col + 1, col + 1);
					tempTable.changeSelection(row, col + 1, false, true);
				}
				setFieldPrompt(tempTable);
			}
		});
		am2.put("selectPreviousColumn", new AbstractAction() {
			// 向左方向键
			@Override
			public void actionPerformed(ActionEvent e) {
				// 自定义事件处理代码

				JTable tempTable = (JTable) e.getSource();
				// JScrollPane scroll = tempTable.roll
				int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if (col >= 1) {
					tempTable.setColumnSelectionInterval(col - 1, col - 1);
					tempTable.changeSelection(row, col - 1, false, true);
				}
				setFieldPrompt(tempTable);
			}
		});
		jTable.setActionMap(am1);
		jTable.setActionMap(am2);

		jTable.repaint();
		jTable.updateUI();

		tPanelLayout.setRowInfo(2, 300, 5, 5);
		tPanelLayout.add(jTableScroll, 2, 100, 'B', 1, 1, 'L');
		tPanelLayout.setCompLayout(jTableScroll, jTableScrollLayout);

		jTableScrollLayout.setRowInfo(1, 100, 10, 10);
		jTableScrollLayout.add(jTable, 1, 100, 'B', 1, 1, 'L');

		jTableScroll.getVerticalScrollBar().setUnitIncrement(20); // 设置滚动条滚动量

		tPanelLayout.setRowInfo(3, 50, 5, 5);
		tPanelLayout.add(promptLabel, 3, 100, 'H', 0, 1, 'L');
		this.repaint();
		this.updateUI();
	}

	public void setFieldPrompt(JTable table) {
		int colnum = table.getSelectedColumn();
		if (colnum >= 0) {

			TableField field = aTableFields.get(colnum);
			String fieldMessage = PrcString.ConvertToCamelCase(field.getFieldName()) + " : " + field.getFieldType()
					+ "(" + field.getFieldLen();

			if (field.getFieldType().equals("NUMBER"))
				fieldMessage = fieldMessage + "," + field.getFieldDec() + ")";
			else
				fieldMessage = fieldMessage + ")";
			fieldMessage = fieldMessage + "  " + field.getFieldDsc();
			promptLabel.setText(fieldMessage);
			this.repaint();
		} else {
			promptLabel.setText(null);
		}
	}

	public void setTableData(Vector rowData, Vector aTableFields) {

		this.aTableFields = aTableFields;
		DefaultTableModel model = new DefaultTableModel(rowData, getTableTitles(aTableFields));
		jTable.setModel(model);
		
		HeaderRendererHh renderer = new HeaderRendererHh();
		TableColumnModel cmodel = jTable.getColumnModel();
		for (int i=0;i<cmodel.getColumnCount();i++){
			cmodel.getColumn(i).setHeaderRenderer(renderer);
		}
			
		String filterText = filterStrTextField.getText();
		if (filterText == null || filterText.equals("")) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter(filterText));
		}
		// 为JTable设置排序器
		sorter.setModel(model);
		jTable.setRowSorter(sorter);

		// 设置列宽
		fitTableColumns(jTable);
		// 设置表格颜色
		setTableColor(jTable);
	}

	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

		mouseRightButtonClick(evt);
	}

	public LayoutByRow getTablePanelLayout() {
		return tPanelLayout;
	}

	// 鼠标右键点击事件
	private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
		// 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
		if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
			// 通过点击位置找到点击为表格中的行
			int focusedRowIndex = jTable.rowAtPoint(evt.getPoint());
			if (focusedRowIndex == -1) {
				return;
			}
			selectRowValue.removeAllElements();
			selectedRowNum = jTable.rowAtPoint(evt.getPoint());
			selectedColNum = jTable.columnAtPoint(evt.getPoint());
			for (int i = 0; i < jTable.getColumnCount(); i++) {
				selectRowValue.addElement(jTable.getValueAt(selectedRowNum, i));
			}
			// System.out.println("selected:row="+selectedRowNum + "
			// col="+selectedColNum + " value:"+selectedCellValue);
			// 将表格所选项设为当前右键点击的行
			jTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
			// 弹出菜单
			// createPopupMenu();
			m_popupMenu.show(this, evt.getX(), evt.getY());
		}

	}

	public Vector getTableTitles(Vector<TableField> aTableFields) {
		if(aTableFields == null) return null;
		Vector cols = new Vector<Object>();
		for (TableField field : aTableFields) {
			//JLabel tableTitleName = new JLabel();
			//tableTitleName.setText(field.getFieldDsc()==null||field.getFieldDsc().isEmpty()?
			char linebreak = '\n';
			cols.add(field.getFieldDsc()==null||field.getFieldDsc().isEmpty()?
					field.getFieldName():
					(field.getFieldDsc()+linebreak+PrcString.ConvertToCamelCase(field.getFieldName())) );
			//cols.add(tableTitleName);
			//cols.add(field.getFieldDsc());
		}
		return cols;
	}

	public void setTablePanelData(Vector<TableField> aTableFields, Vector rows) {
		// 将数据放进table中
		this.rows = rows;
		this.aTableFields = aTableFields;
		
		setTableData(rows, aTableFields);
		this.repaint();
		this.updateUI();
	}

	private void createPopupMenu() {
		m_popupMenu = new JPopupMenu();

		JMenuItem updMenItem = new JMenuItem();
		updMenItem.setText("  编辑该记录  ");
		updMenItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事

			}
		});
		m_popupMenu.add(updMenItem);

		JMenuItem insertMenItem = new JMenuItem();
		insertMenItem.setText("  新增记录  ");
		insertMenItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事

			}
		});
		m_popupMenu.add(insertMenItem);

		JMenuItem delMenItem = new JMenuItem();
		delMenItem.setText("  删除该记录  ");
		delMenItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// 该操作需要做的事

			}
		});
		m_popupMenu.add(delMenItem);
	}

	// 自动调整表格的列宽
	public static void fitTableColumns(JTable myTable) {
		JTableHeader header = myTable.getTableHeader();
		int rowCount = myTable.getRowCount();
		int fitLookSize = 10; // 为了美观，加大宽度

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
	
	public Vector<TableField> getaTableFields() {
		return aTableFields;
	}

	public Vector getRows() {
		return rows;
	}
	
	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}
	
	 
	public class HeaderRendererHh extends DefaultTableCellRenderer {
	 
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
	 
			JTableHeader header = table.getTableHeader();
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	 
			// 得到列的宽度
			TableColumnModel columnModel = table.getColumnModel();
			int width = columnModel.getColumn(column).getWidth();
	 
			value = getShowValue(value.toString(), width);
			setText(value.toString());
			setSize(new Dimension(width, this.getHeight()));
	 
			setHorizontalAlignment(JLabel.CENTER);
	 
			return this;
		}
	 
		private Object getShowValue(String value, int colWidth) {
			// 根据当前的字体和显示值得到需要显示的宽度
			FontMetrics fm = this.getFontMetrics(this.getFont());
			int width = fm.stringWidth(value.toString());
			int test = fm.stringWidth("好");
			//System.out.println(test * value.length());
			//System.out.println(width);
			if (width < colWidth) {
				return value;
			}
			StringBuffer sb = new StringBuffer("<html>");
			char str;
			int tempW = 0;
			for (int i = 0; i < value.length(); i++) {
				str = value.charAt(i);
				tempW += fm.charWidth(str);
				if (tempW > colWidth) {
					sb.append("<br>");
					tempW = 0;
				}
				sb.append(str);
			}
			sb.append("</html>");
			return sb.toString();
		}
	}

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("test");
		LayoutByRow frameLayout = new LayoutByRow(frame);
		frameLayout.setRowInfo(1, 500, 10, 10);

		Connection conn = SqliteDB.getConnection();

		String tableName = "envdatabaseinfo";
		Vector<TableField> aTableFields = null;
		Vector rows = null;
		try {
			aTableFields = Table.geTableFields(tableName, null, conn);
			rows = Table.getTableRecords(tableName, null, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TablePanel tPanel = new TablePanel(aTableFields, rows);
		frameLayout.add(tPanel, 1, 500, 'H', 0, 1, 'L');
		frameLayout.setCompLayout(tPanel, tPanel.getTablePanelLayout());

		// frame.add(tPanel);
		frame.setBounds(450, 200, 530, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frameLayout.setRowPos();

		frame.setVisible(true);
		frame.repaint();
	}
}
