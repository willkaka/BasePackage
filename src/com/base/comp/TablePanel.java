package com.base.comp;

import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.PublicKey;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.openxml4j.util.ZipSecureFile.ThresholdInputStream;

import com.base.layout.LayoutByRow;
import com.tablequery.bean.TableField;
import com.tablequery.function.PrcString;
import com.tablequery.function.QueryTableInfo;
import com.tablequery.sqlparser.SqlStatementParser;
import com.tablequery.view.MntTableWindow;
import com.tablequery.sqlparser.SqlStatementParser;

public class TablePanel extends JPanel{
	
	private LayoutByRow tPanelLayout = new LayoutByRow(this);
	
	private JButton fstPageBtn = new JButton();
	private JButton prePageBtn = new JButton();
	private JButton nxtPageBtn = new JButton();
	private JButton lstPageBtn = new JButton();
	
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
		//表格
		jTable.setAutoscrolls(true);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTable.setFont(new Font("SimSun", 0, 12));
                
        //将数据放进table中
        setFieldInfoTableDataFromQuery(table,frame.getSelectedModel(),frame.getSqlstm(),frame.getBegRow(),frame.getEndRow());
        
        //监听鼠标
        this.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(e.getSource());
				//System.out.println(e.getComponent());
				jTable1MouseClicked(e);  
				TablePanel tempTable = (TablePanel) e.getSource();
				//System.out.println("mouseClicked: col="+tempTable.getSelectedColumn()+",row="+tempTable.getSelectedRow());
				setFieldPrompt(tempTable);
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
        	    TablePanel tempTable = (TablePanel) e.getSource();
        	    int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if(col < tempTable.getColumnCount()-1){
					tempTable.setColumnSelectionInterval(col+1, col+1);
					tempTable.changeSelection(row, col+1, false, true);
				}
				setFieldPrompt(tempTable);
            }
        });
        am2.put("selectPreviousColumn", new AbstractAction(){
            //向左方向键
            @Override
             public void actionPerformed(ActionEvent e) {
                // 自定义事件处理代码
            	
            	TablePanel tempTable = (TablePanel) e.getSource();
            	//JScrollPane scroll = tempTable.roll
				int col = tempTable.getSelectedColumn();
				int row = tempTable.getSelectedRow();
				if(col >= 1){
					tempTable.setColumnSelectionInterval(col-1, col-1);
					tempTable.changeSelection(row, col-1, false, true);
				}
				setFieldPrompt(tempTable);
             }
         });

        this.setActionMap(am1);
        this.setActionMap(am2);
        
        this.repaint();
        this.updateUI(); 
	}
	public void setFieldPrompt(TablePanel table){
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
	}
	
	public void setFieldInfoTableDataFromQuery(String table,String model,String ssql,int begRow,int endRow){
		if(model.equals("Sql")){
			if(ssql == null || ssql.isEmpty()) return ;
		ssql = ssql.replace('\n',' ');
		}
        		
		setFieldInfoTableData(
				QueryTableInfo.getTableRecordsRute(table,model,ssql,begRow,endRow), 
				QueryTableInfo.getVectorShowColRute(table,model,ssql));
		
		//设置列宽
        Utils.fitTableColumns(this);
        //设置表格颜色
        Utils.setTableColor(this);
	}
	
	public void setFieldInfoTableData(Vector<?> rowData, Vector<?> colData){
		DefaultTableModel model = new DefaultTableModel(rowData, colData);
        this.setModel(model);
        
        String filterText = frame.getFilterString();
        if(filterText == null || filterText.equals("")){
        	sorter.setRowFilter(null);
        }
        else{
        	sorter.setRowFilter(RowFilter.regexFilter(filterText));
        }
        //为JTable设置排序器
        sorter.setModel(model);
		this.setRowSorter(sorter); 
	}
	
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

	       mouseRightButtonClick(evt);
	}
	
	 //鼠标右键点击事件
 private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
     //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
     if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
         //通过点击位置找到点击为表格中的行
         int focusedRowIndex = this.rowAtPoint(evt.getPoint());
         if (focusedRowIndex == -1) {
             return;
         }
         selectRowValue.removeAllElements();
         selectedRowNum = this.rowAtPoint(evt.getPoint());
         selectedColNum = this.columnAtPoint(evt.getPoint());
         for (int i=0; i<this.getColumnCount(); i++){
        	 selectRowValue.addElement(this.getValueAt(selectedRowNum, i));
         }
         //System.out.println("selected:row="+selectedRowNum + " col="+selectedColNum + " value:"+selectedCellValue);
         //将表格所选项设为当前右键点击的行
         this.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
         //弹出菜单
         createPopupMenu();
         m_popupMenu.show(this, evt.getX(), evt.getY());
     }

 }
 
 private void createPopupMenu() {
     m_popupMenu = new JPopupMenu();
     
     JMenuItem updMenItem = new JMenuItem();
     updMenItem.setText("  编辑该记录  ");
     updMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //该操作需要做的事
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'U');
         }
     });
     m_popupMenu.add(updMenItem);
     
     JMenuItem insertMenItem = new JMenuItem();
     insertMenItem.setText("  新增记录  ");
     insertMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //该操作需要做的事
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'I');
         }
     });
     m_popupMenu.add(insertMenItem);
     
     JMenuItem delMenItem = new JMenuItem();
     delMenItem.setText("  删除该记录  ");
     delMenItem.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
             //该操作需要做的事
        	 MntTableWindow mntwindow = new MntTableWindow(frame,selectRowValue,'D');
         }
     });
     m_popupMenu.add(delMenItem);
 }
}
