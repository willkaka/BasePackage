package com.base.comp;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.PrivilegedActionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.base.database.Table;
import com.base.database.TableField;
import com.base.layout.LayoutByRow;

public class JTablePanel_OprTable {
	private Connection connection = null;
	private JFrame frame= new JFrame();
	private JPanel tableInfoPanel = new JPanel();
	private JPanel fieldListPanel = new JPanel();
	private JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 1000);

	private String table = "menuconfig";
	private Vector<Object> valueList = new Vector<Object>();
	private char oprCode = 'U'; //U-���¼�¼/I-д��¼��
	
	private LayoutByRow titleBoundLayout = null;
	private LayoutByRow detailBoundLayout = null;
	private LayoutByRow frameLayout = null;
	
	public JTablePanel_OprTable(String table, Vector<Object> valueList, char oprCode, Connection connection) {
		
		this.connection = connection;
		// ����Frame����
		this.table = table;
		this.valueList = valueList;
		frame.setTitle("MaintainTable");
		frame.setBounds(450, 200, 530, 625);
		// setResizable(false); //������ı䴰�ڴ�С
		// �����˳�
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		// ȡ����ܸ�ʽ
		frame.setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().createImage("./images/icon.jpg"));
		
		//panel
		tableInfoPanel.setLayout(null);
		tableInfoPanel.setBounds(5, 5, frame.getWidth()-15, 60);
		fieldListPanel.setLayout(null);
		//fieldListPanel.setBounds(5, tableInfoPanel.getY()+tableInfoPanel.getHeight()+10, frame.getWidth()-15, frame.getHeight()-tableInfoPanel.getY()-20);
		fieldListPanel.setBounds(5, 500, frame.getWidth()-15, frame.getHeight()-tableInfoPanel.getY()-20);
		
		// ���ò���
		titleBoundLayout = new LayoutByRow(tableInfoPanel);
		detailBoundLayout = new LayoutByRow(fieldListPanel);
		
		// ---------��ʼ�������---------------

		// �ı��������������������
		JLabel tableNameLabel = new JLabel("���ݱ����ƣ�");
		titleBoundLayout.add(tableNameLabel, 1, 100, 'N', 0f,0f,'L');

		JTextField tableNameTextField = new JTextField();
		tableNameTextField.setEditable(true);
		tableNameTextField.setText(table);
		tableNameTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
		});
		titleBoundLayout.add(tableNameTextField, 1, 150, 'N', 0f,0f,'L');
		
		String buttonText = null;
		if(oprCode == 'U'){
			buttonText = "update";
		}else if(oprCode == 'I'){
			buttonText = "insert";
		}else if(oprCode == 'D'){
			buttonText = "delete";
		}
		
		//��ť
		JButton oprButton = new JButton(buttonText);
		oprButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton actionButton = (JButton) e.getSource();
				if(actionButton.getText().equals("update")){
					updateRecord(getTable(),getConnection());
				}else if(actionButton.getText().equals("insert")){
					insertNewRecord(getTable(),getConnection());
				}else if(actionButton.getText().equals("delete")){
					deleteRecord(getTable(),getConnection());
				}
				
			}
		});
		titleBoundLayout.add(oprButton, 1, 100, 'N', 0f,0f,'R');
				
		JLabel specLabel = new JLabel("-----------------------");
		titleBoundLayout.add(specLabel, 2, 200, 'N', 0f,0f,'L');
		titleBoundLayout.setRowPos(frame.getWidth()-15, 60);
		
		//���ر���ֶ�
		addLabelTextfield(table, detailBoundLayout, 1,oprCode,connection);
		
		detailBoundLayout.setRowPos(frame.getWidth(), frame.getHeight());
		
		
		//������
		JScrollPane scrollPane = new JScrollPane(fieldListPanel);
		//scrollPane.setBounds(0, 0, frame.getWidth()-18, frame.getHeight()-40);
		int w=frame.getWidth()-30;
		int h=frame.getHeight()-tableInfoPanel.getY()-tableInfoPanel.getHeight()-60;
		//System.out.println("scroll width:"+w+ "   h:"+h);
		scrollPane.setBounds(5, tableInfoPanel.getY()+tableInfoPanel.getHeight()+10, w, h);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); //���ù�����������
		/**
		 * Ҫ�ӹ�������Ҫ��panel�Ŀ�ߴ���scrollPane�Ŀ��..��ֻҪ���µ�..ֻҪ�ߴ��ھ�����..
		 * panel�ĸ߻�Ӱ����ʾ���߶Ȳ���ᵼ����ʾ��ȫ����
		 */
		//System.out.println("panel width:"+frame.getWidth()+ "   h:"+detailBoundLayout.getLayoutHeight());
		fieldListPanel.setPreferredSize(new Dimension(frame.getWidth(), detailBoundLayout.getLayoutHeight()+60));
		fieldListPanel.revalidate(); // ������������,�ҵĿ�߱���
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//frame_org.resetFiledInfoTableData();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		frame.add(tableInfoPanel);
		frame.add(scrollPane);
		frame.setVisible(true);
	}
	

	public void updateRecord(String tableName, Connection connection){
		Vector<TableField> tableFields = new Vector<TableField>();
		try {
			tableFields = Table.geTableFields(tableName, null, connection);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Vector<Object> fieldValues = detailBoundLayout.getCompValues("javax.swing.JTextField");
		Vector<Object> fieldKeySelected = detailBoundLayout.getCompValues("javax.swing.JCheckBox");
		
		String sqlstm = "UPDATE " + table + " SET ";
		int i = 0;
		for (TableField field : tableFields) {
			sqlstm = sqlstm + (i==0?"":",") + field.getFieldName().trim() + " = ";
			
			if(fieldValues != null){
				System.out.println(i + "   " + field.getFieldName() + " " +field.getFieldType());
				if(field.getFieldType().equals("NUMBER")){
					sqlstm = sqlstm + fieldValues.get(i).toString();
				}else{
					if (fieldValues.get(i) == null){
						sqlstm = sqlstm + (String) fieldValues.get(i);
					}else{
						sqlstm = sqlstm + "'"+(String) fieldValues.get(i) + "'";
					}
				}
			}
			i++;
		}
		sqlstm = sqlstm + " WHERE ";
				
		int k = 0;int j = 0;
		for (Object KeyName :fieldKeySelected){
			String fieldName = (String) KeyName;
			i = 0;
			for (TableField field : tableFields) {
				if (field.getFieldName().toUpperCase().equals(fieldName.trim().toUpperCase())){
					sqlstm = sqlstm + (j==0?"":" AND ") + field.getFieldName().trim() + " = ";
					if(valueList != null){
						//System.out.println(i + "   " + field.getFieldName() + " " +field.getFieldType());
						if(field.getFieldType().equals("NUMBER")){
							sqlstm = sqlstm + valueList.get(i).toString();
						}else{
							if (valueList.get(i) == null){
								sqlstm = sqlstm + (String) valueList.get(i);
							}else{
								sqlstm = sqlstm + "'"+(String) valueList.get(i) + "'";
							}
						}
					}
					j++;
					break;
				}
				i++;
			}
		}
		if(j==0) {
			JOptionPane.showMessageDialog(null, "���¼�¼����Ҫѡ��KEY");
			return;
		}
		
		System.out.println("[sql]" + sqlstm);
		
		try{
			//OracleDB.exeSql(sqlstm);
			PreparedStatement psql = connection.prepareStatement(sqlstm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "���¼�¼�ɹ���");
	}
	
	public void insertNewRecord(String tableName, Connection connection){
		Vector<TableField> tableFields = new Vector<TableField>();
		try {
			tableFields = Table.geTableFields(tableName, null, connection);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //QueryTableInfo.getVectorTableFieldListForX(table);
		Vector<Object> fieldValues = detailBoundLayout.getCompValues("javax.swing.JTextField");
		
		String sqlstm = "INSERT INTO " + table + "(";
		int i = 0;
		for (TableField field : tableFields) {
			sqlstm = sqlstm + (i++==0?"":",") + field.getFieldName().trim();
		}
		sqlstm = sqlstm + ") VALUES(";
		
		i = 0;
		for (TableField field : tableFields) {
			if(fieldValues != null){
				//System.out.println(i + "   " + field.getFieldName() + " " +field.getFieldType());
				if(field.getFieldType().equals("NUMBER")){
					sqlstm = sqlstm + (i==0?"":",") + fieldValues.get(i).toString();
				}else{
					if (fieldValues.get(i) == null){
						sqlstm = sqlstm + (i==0?"":",") + (String) fieldValues.get(i);
					}else{
						sqlstm = sqlstm + (i==0?"":",") + "'"+(String) fieldValues.get(i) + "'";
					}
					
				}
			}
			i++;
		}
		sqlstm = sqlstm + ")";
		
		System.out.println("[sql]" + sqlstm);
		
		try{
			//OracleDB.exeSql(sqlstm);
			PreparedStatement psql = connection.prepareStatement(sqlstm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "insert��¼�ɹ���");
	}
	
	public void deleteRecord(String tableName, Connection connection){
		Vector<TableField> tableFields = new Vector<TableField>();
		try {
			tableFields = Table.geTableFields(tableName, null, connection);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //QueryTableInfo.getVectorTableFieldListForX(table);
		//Vector<Object> fieldValues = detailBoundLayout.getCompValues("javax.swing.JTextField");
		Vector<Object> fieldKeySelected = detailBoundLayout.getCompValues("javax.swing.JCheckBox");
		
		String sqlstm = "DELETE FROM " + table + " WHERE ";
		int j = 0;
		for (Object KeyName :fieldKeySelected){
			String fieldName = (String) KeyName;
			int i = 0;
			for (TableField field : tableFields) {
				if (field.getFieldName().toUpperCase().equals(fieldName.trim().toUpperCase())){
					sqlstm = sqlstm + (j==0?"":" AND ") + field.getFieldName().trim() + " = ";
					if(valueList != null){
						//System.out.println(i + "   " + field.getFieldName() + " " +field.getFieldType());
						if(field.getFieldType().equals("NUMBER")){
							sqlstm = sqlstm + valueList.get(i).toString();
						}else{
							if (valueList.get(i) == null){
								sqlstm = sqlstm + (String) valueList.get(i);
							}else{
								sqlstm = sqlstm + "'"+(String) valueList.get(i) + "'";
							}
							
						}
					}
					j++;
					break;
				}
				i++;
			}
		}
		if(j==0) {
			JOptionPane.showMessageDialog(null, "ɾ����¼����Ҫѡ��KEY��");
			return;
		}
		
		System.out.println("[sql]" + sqlstm);
		
		try{
			//OracleDB.exeSql(sqlstm);
			PreparedStatement psql = connection.prepareStatement(sqlstm);
			psql.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "ɾ����¼�ɹ���");
	}

	public void addLabelTextfield(String table, LayoutByRow defBoundLayout, int startRow, char oprCode, Connection connection) {
		Vector<TableField> tableFields = new Vector<TableField>();
		try {
			tableFields = Table.geTableFields(table, null, connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //QueryTableInfo.getVectorTableFieldListForX(table);
		
		int i = 0;
		for (TableField field : tableFields) {
			
			//�ֶ�����
			JLabel tableNameLabel = new JLabel(
					(field.getFieldDsc() == null ? field.getFieldName() : field.getFieldDsc()) + ":");
			//defBoundLayout.setRowHeight(startRow, 25);
			defBoundLayout.add(tableNameLabel, startRow, 200, 'N', 0f,0f,'L');
			
			//�ֶ�ֵ
			if(oprCode == 'I' || oprCode == 'U'){
			JTextField fieldValue = new JTextField();
			if(valueList != null){
				if(valueList.get(i) == null){
					fieldValue.setText("");
				}else{
					fieldValue.setText(getValueByString(valueList.get(i).toString(),field.getFieldType().toUpperCase(),field.getFieldLen(), field.getFieldDec()));
				}
			}
			fieldValue.setEditable(true);
			defBoundLayout.add(fieldValue, startRow, 150, 'N', 0f,0f,'L');
			}
			
			//Keyѡ��
			if(oprCode == 'U' || oprCode == 'D'){
				JCheckBox keySelected = new JCheckBox("isKey?");
				keySelected.setName(field.getFieldName());
				defBoundLayout.add(keySelected, startRow, 100, 'N', 0f,0f,'L');
			}
			startRow++;
			i++;
		}
	}
	
	public static String getValueByString(String value, String type, int len, int dec){
		String returnValue = null;
		if(value == null){
			returnValue = "";
		}else if(type.equals("NUMBER")){
			if(dec == 0 & value.trim().indexOf('.')>0){
				returnValue = value.trim().substring(0,value.trim().indexOf('.'));
			}else{
				returnValue = value;
			}
		}else if(type.equals("VARCHAR2")){
			returnValue = value;
		}else if(type.equals("VARCHAR")){
			returnValue = value;
		}else if(type.equals("CHAR")){
			returnValue = value.substring(len);
		}else if(type.equals("LONG")){
			returnValue = value;
		}else if(type.equals("DOUBLE")){
			returnValue = value;
		}else if(type.equals("INT")){
			returnValue = value;
		}else if(type.equals("FLOAT")){
			if(dec == 0 & value.trim().indexOf('.')>0){
				returnValue = value.trim().substring(0,value.trim().indexOf('.'));
			}else{
				returnValue = value;
			}
		}else{
			System.out.println("-------------"+type+"-----------");
		}
		return returnValue;
	}
	
	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}

}

