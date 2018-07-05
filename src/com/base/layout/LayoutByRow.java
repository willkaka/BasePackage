package com.base.layout;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.base.comp.JClosableTabbedPane;

/**
 * ���в���
 * �Ƚ��������add�����֣������setRowPos�������λ��(setBounds)
 * ���������Ҫ����ʾ˳��add.
 */
public class LayoutByRow {
	private Object fatherJcomp = null;
	private String fatherObjectType = null;
	private HashMap<Integer, LayoutRow> rowList = new HashMap<Integer, LayoutRow>();
	private int topGap = 10;   //�����������
	//private int botGap = 10 + 40;   //�ײ��������  40ΪJFrame�ı������߶ȡ�
	private int botGap = 20;   //�ײ��������  40ΪJFrame�ı������߶ȡ�
	private boolean resetPos = true; //�Ƿ���LayoutByRow����λ�ü���С

	public LayoutByRow(JComponent fatherJcomp){
		this.setFatherObjectType("JComponent");
		this.setFatherJcomp(fatherJcomp);
		if(fatherJcomp.getClass().getName().equals("com.base.comp.JClosableTabbedPane") ||
		   fatherJcomp.getClass().getName().equals("javax.swing.JScrollPane")){
			
		}else{
			fatherJcomp.setLayout(null);
		}
		
	}
	
	public LayoutByRow(JFrame fatherJcomp){
		this.setFatherObjectType("JFrame");
		this.setFatherJcomp(fatherJcomp);
		fatherJcomp.setLayout(null);
	}

	/**
	 * ������ӽ�������
	 * @param jComp �����ص����
	 * @param rowNum �к�
	 * @param compWidth ������
	 * @param pullingFlag ����Ƿ������ H/V/B
	 * @param vPullingScale ��ֱ�������
	 * @param hPullingScale ˮƽ�������
	 * @param align ���뷽ʽ L/R
	 */
	public void add(JComponent jComp, int rowNum, int compWidth, char pullingFlag, float vPullingScale, float hPullingScale, char align){
		LayoutRow layoutRow = null;
		if(rowList.containsKey(rowNum)){
			layoutRow = rowList.get(rowNum);
		}else{
			int y = topGap;
			for (int i=1;i<=rowList.size() && i<rowNum;i++){
				LayoutRow layoutRow0 = rowList.get(i);
				y = y + layoutRow0.getRowHeight() + layoutRow0.getRowVGap();
				//δ��������������
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		
		layoutRow.add(jComp, compWidth, pullingFlag,vPullingScale,hPullingScale,align);
	}
	
	public void addReplace(JComponent jComp,JComponent refComp, int rowNum, int compWidth, char pullingFlag, float vPullingScale, float hPullingScale){
		LayoutRow layoutRow = null;
		if(rowList.containsKey(rowNum)){
			layoutRow = rowList.get(rowNum);
		}else{
			int y = topGap;
			for (int i=1;i<=rowList.size() && i<rowNum;i++){
				LayoutRow layoutRow0 = rowList.get(i);
				y = y + layoutRow0.getRowHeight() + layoutRow0.getRowVGap();
				//δ��������������
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		
		char align = 'L';
		LayoutComp refLayOutComp = null;
		for(LayoutComp comp:layoutRow.getjComponentList()){
			if(comp.getComponent().equals(refComp)){
				refLayOutComp = comp;
				break;
			}
		}
		layoutRow.addByRef(jComp, compWidth, pullingFlag, vPullingScale, hPullingScale,align,refComp);
		
		if(getFatherObjectType().equals("JComponent")){
			((JComponent) fatherJcomp).add(jComp);
		}else if(getFatherObjectType().equals("JFrame")){
			((JFrame) fatherJcomp).add(jComp);
		}
	}
	
	
	/**
	 * ��������Ĳ���
	 * @param jComp
	 * @param layoutByRow
	 */
	public void setCompLayout(JComponent jComp, LayoutByRow layoutByRow) {
		for (int i = 1; i <= rowList.size(); i++) {
			for (LayoutComp comp : rowList.get(i).getjComponentList()) {
				if (comp.getComponent().equals(jComp)) {
					comp.setCompLayout(layoutByRow);
					break;
				}
			}
		}
	}
	
	/**
	 * ��������ĸ�����Ϣ
	 * @param jComp
	 * @param layoutByRow
	 */
	public void setCompOthInfo(JComponent jComp, String info) {
		for (int i = 1; i <= rowList.size(); i++) {
			for (LayoutComp comp : rowList.get(i).getjComponentList()) {
				if (comp.getComponent().equals(jComp)) {
					comp.setCompOthInfo(info);
					break;
				}
			}
		}
	}
	
	/**
	 * �Ӳ������Ƴ����
	 * @param jComp
	 * @param layoutByRow
	 */
	public void removeComp(JComponent jComp) {
		for (int i = 1; i <= rowList.size(); i++) {
			for (LayoutComp comp : rowList.get(i).getjComponentList()) {
				if (comp.getComponent().equals(jComp)) {
					rowList.get(i).getjComponentList().remove(jComp);
					break;
				}
			}
		}
	}
	
	/**
	 * �Ӳ������Ƴ��������
	 * @param jComp
	 * @param layoutByRow
	 */
	public void removeAllComp() {
		for (int i = 1; i <= rowList.size(); i++) {
			for (LayoutComp comp : rowList.get(i).getjComponentList()) {
				comp = null;
			}
		}
		rowList.clear();
	}
	
	/**
	 * ��������Ϣ
	 * @param rowNum�к�
	 * @param rowheight�и�
	 * @param rowVGap�м��
	 * @param rowCompHGap����������
	 */
	public void setRowInfo(int rowNum, int rowHeight, int rowVGap, int rowCompHGap) {
		LayoutRow layoutRow = null;
		if(rowList.containsKey(rowNum)){
			layoutRow = rowList.get(rowNum);
		}else{
			int y = topGap;
			for (int i=1;i<=rowList.size() && i<rowNum;i++){
				LayoutRow layoutRow0 = rowList.get(i);
				y = y + layoutRow0.getRowHeight() + layoutRow0.getRowVGap();
				//δ��������������
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		if(rowHeight != -1) layoutRow.setRowHeight(rowHeight);  //�и�
		if(rowVGap != -1)layoutRow.setRowVGap(rowVGap);         //�м��
		if(rowCompHGap != -1)layoutRow.setRowCompGap(rowCompHGap);      //�����������
	}
	

	/**
	 * ��������Ϣ
	 * @param rowNum�к�
	 * @param rowLeftGap����߼��
	 * @param rowRightGap���ұ߼��
	 * @param rowCompHGap����������
	 */
	public void setRowGap(int rowNum, int rowLeftGap, int rowRightGap, int rowCompHGap) {
		LayoutRow layoutRow = null;
		if(rowList.containsKey(rowNum)){
			layoutRow = rowList.get(rowNum);
		}else{
			int y = topGap;
			for (int i=1;i<=rowList.size() && i<rowNum;i++){
				LayoutRow layoutRow0 = rowList.get(i);
				y = y + layoutRow0.getRowHeight() + layoutRow0.getRowVGap();
				//δ��������������
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		if(rowLeftGap != -1) layoutRow.setRowLeftGap(rowLeftGap);        //������
		if(rowRightGap != -1)layoutRow.setRowRightGap(rowRightGap);      //���Ҽ��
		if(rowCompHGap != -1)layoutRow.setRowCompGap(rowCompHGap);       //�����������
	}
	
	/**
	 * ������������Ŀ�͸ߣ�������������λ�ü���С
	 */
	public void setRowPos() {
		int width = 0;
		int height = 0;
		if(this.getFatherObjectType().equals("JComponent")){
			width = ((JComponent) fatherJcomp).getWidth();
			height = ((JComponent) fatherJcomp).getHeight();
		}else if(this.getFatherObjectType().equals("JFrame")){
			width = ((JFrame) fatherJcomp).getWidth();
			height = ((JFrame) fatherJcomp).getHeight();
		}
		
		if(isResetPos()) setRowPos(width, height);
		
		for (int i = 1; i <= rowList.size(); i++) {
			for (LayoutComp comp : rowList.get(i).getjComponentList()) {
				if(comp.getCompLayout() != null) comp.getCompLayout().setRowPos();  //�ݹ�
			}
		}
		
		if(isResetPos()){
			if(this.getFatherObjectType().equals("JComponent")){
				((JComponent) fatherJcomp).revalidate();
				((JComponent) fatherJcomp).repaint();
			}else if(this.getFatherObjectType().equals("JFrame")){
				((JFrame) fatherJcomp).repaint();
			}
		}
	}
	
	/**
	 * ������������Ŀ�͸ߣ�������������λ�ü���С
	 * @param width�������� ���
	 * @param height�������� �߶�
	 */
	public void setRowPos(int width, int height) {
		LayoutRow layoutRow = null;
		//System.out.println("rowList.size:"+rowList.size());
		
		//����������е��ܸ߶�
		int pullingHeight = height - topGap - botGap;
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			if(!layoutRow.isVPulling()){
				pullingHeight = pullingHeight - layoutRow.getRowHeight() - layoutRow.getRowVGap();
			}
		}
		
		//������
		int rowY = topGap;  //һ�������������Y���꣬�ڴ˼��㡣
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			
			//�������λ��
			if(layoutRow.isVPulling()){
				//�����д�ֱ����ʱ���߶� = ��������ܸ߶� * ���������
				layoutRow.setCompPos(width, (int) (pullingHeight * layoutRow.getVPullingScale()));
				layoutRow.setRowHeight((int) (pullingHeight * layoutRow.getVPullingScale()));
			}else{
				layoutRow.setCompPos(width, layoutRow.getRowHeight());
			}
			
			//������������������Y���꣬�������
			for (LayoutComp comp : layoutRow.getjComponentList()) {
				//if(getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")) return;
				//�����Y�����꣬�ڴ˴����㣬�������䡣
				comp.getComponent().setBounds(
						comp.getComponent().getX(), 
						rowY, 
						comp.getComponent().getWidth(), 
						comp.getComponent().getHeight());
				
				//��������ص������������
				//if(getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane") ) continue;
				if(getFatherObjectType().equals("JComponent")  &&
						!getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")){
					((JComponent) fatherJcomp).add(comp.getComponent());
				}else if(getFatherObjectType().equals("JComponent")  &&
						getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")){
					//��TAB���Ʊ�����CompOthInfo��
					//((JClosableTabbedPane) fatherJcomp).addTab(comp.getCompOthInfo(), comp.getComponent());
				}else if(getFatherObjectType().equals("JFrame")){
					((JFrame) fatherJcomp).add(comp.getComponent());
				}else{
					System.out.println("=================error!!!!!!!!!!!!!!!!!!!!!");
				}
				System.out.println("y="+comp.getComponent().getY()+"\t "+
								   "x="+comp.getComponent().getX()+"\t "+
								   "w="+comp.getComponent().getWidth()+"\t "+
								   "h="+comp.getComponent().getHeight()+"\t "+
								   "  "+comp.getComponent().getClass() + " "+
								   "  "+getFatherJcomp().getClass()
										);
			}
			rowY = rowY + layoutRow.getRowHeight() + layoutRow.getRowVGap();
		}
	}
	
	public int getLayoutHeight() {
		int height = topGap;  //�������/�ײ����
		LayoutRow layoutRow = null;
		for (int i = 1; i < rowList.size(); i++) {
			layoutRow = rowList.get(i);
			height = height + layoutRow.getRowHeight() + layoutRow.getRowVGap();
		}
		return height;
	}
	
	public Vector<Object> getCompValues(Object obj){
		Vector<Object> values = new Vector<Object>();
		LayoutRow layoutRow = null;
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			for (LayoutComp comp:layoutRow.getjComponentList()) {
				//System.out.println("name:"+ comp.getComponent().getClass().getName());
				if ((obj.equals("javax.swing.JTextField") || obj.equals("javax.swing.JCheckBox") )
						&& comp.getComponent().getClass().getName().equals((String) obj) ){ 
					if(comp.getComponectValue(comp.getComponent().getClass().getName()) != null)
						values.add(comp.getComponectValue(comp.getComponent().getClass().getName()));
				}
			}
		}
		
		return values;
	}
	
	public Vector getCompValuesByLine(){
		Vector values = new Vector();
		LayoutRow layoutRow = null;
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			Vector<String> value = new Vector<String>();
			for (LayoutComp comp:layoutRow.getjComponentList()) {
				//System.out.println("name:"+ comp.getComponent().getClass().getName());
				if ( comp.getComponent().getClass().getName().equals("javax.swing.JTextField") ){ 
					value.add( ((javax.swing.JTextField) comp.getComponent()).getText());
				} else if (comp.getComponent().getClass().getName().equals("javax.swing.JComboBox")){
					value.add( ((javax.swing.JComboBox) comp.getComponent()).getSelectedItem().toString());
				}
			}
			values.add(value);
		}
		
		return values;
	}
	
	public Vector getCompValuesAtLine(int lineNum){
		Vector values = new Vector();
		LayoutRow layoutRow = null;
			layoutRow = rowList.get(lineNum);
			Vector<String> value = new Vector<String>();
			for (LayoutComp comp:layoutRow.getjComponentList()) {
				//System.out.println("name:"+ comp.getComponent().getClass().getName());
				if ( comp.getComponent().getClass().getName().equals("javax.swing.JTextField") ){ 
					value.add( ((javax.swing.JTextField) comp.getComponent()).getText());
				} else if (comp.getComponent().getClass().getName().equals("javax.swing.JComboBox")){
					value.add( ((javax.swing.JComboBox) comp.getComponent()).getSelectedItem().toString());
				}
			values.add(value);
		}
		
		return values;
	}
	
	public void resetCompInfo(JComponent jComp, int rowNum, int compWidth, char pullingFlag, float vPullingScale, float hPullingScale, char align){
		LayoutRow layoutRow = null;
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			for (LayoutComp comp:layoutRow.getjComponentList()) {
				if(comp.getComponent() == jComp){
					if(pullingFlag == 'B'){
						layoutRow.setVPulling(true);
						comp.sethPulling(true);
					}else if(pullingFlag == 'V'){
						layoutRow.setVPulling(true);
						comp.sethPulling(false);
					}else if(pullingFlag == 'H'){
						layoutRow.setVPulling(false);
						comp.sethPulling(true);
					}
					layoutRow.setVPullingScale(vPullingScale);
					comp.sethPullingScale(hPullingScale);
					comp.setAlign(align);
					break;
				}
			}
		}
	}
	
	public int getRowCount() {
		return this.rowList.size();
	}
	
	public Object getFatherJcomp() {
		return fatherJcomp;
	}

	public void setFatherJcomp(Object fatherJcomp) {
		this.fatherJcomp = fatherJcomp;
	}

	public String getFatherObjectType() {
		return fatherObjectType;
	}

	public void setFatherObjectType(String fatherObjectType) {
		this.fatherObjectType = fatherObjectType;
	}
	
	public int getTopGap() {
		return topGap;
	}

	public void setTopGap(int topGap) {
		this.topGap = topGap;
	}
	
	public int getBotGap() {
		return botGap;
	}

	public void setBotGap(int botGap) {
		this.botGap = botGap;
	}

	public boolean isResetPos() {
		return resetPos;
	}

	public void setResetPos(boolean resetPos) {
		this.resetPos = resetPos;
	}

}
