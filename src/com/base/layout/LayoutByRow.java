package com.base.layout;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.base.comp.JClosableTabbedPane;

/**
 * 按行布局
 * 先将所有组件add进布局，最后再setRowPos设置组件位置(setBounds)
 * 所有组件需要按显示顺序add.
 */
public class LayoutByRow {
	private Object fatherJcomp = null;
	private String fatherObjectType = null;
	private HashMap<Integer, LayoutRow> rowList = new HashMap<Integer, LayoutRow>();
	private int topGap = 10;   //顶部保留间隔
	//private int botGap = 10 + 40;   //底部保留间隔  40为JFrame的标题栏高度。
	private int botGap = 20;   //底部保留间隔  40为JFrame的标题栏高度。
	private boolean resetPos = true; //是否由LayoutByRow设置位置及大小

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
	 * 将组件加进布局中
	 * @param jComp 被加载的组件
	 * @param rowNum 行号
	 * @param compWidth 组件宽度
	 * @param pullingFlag 组件是否可拉伸 H/V/B
	 * @param vPullingScale 垂直拉伸比例
	 * @param hPullingScale 水平拉伸比例
	 * @param align 对齐方式 L/R
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
				//未处理不连续行问题
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
				//未处理不连续行问题
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
	 * 设置组件的布局
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
	 * 设置组件的附加信息
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
	 * 从布局中移除组件
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
	 * 从布局中移除所有组件
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
	 * 配置行信息
	 * @param rowNum行号
	 * @param rowheight行高
	 * @param rowVGap行间距
	 * @param rowCompHGap行内组件间距
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
				//未处理不连续行问题
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		if(rowHeight != -1) layoutRow.setRowHeight(rowHeight);  //行高
		if(rowVGap != -1)layoutRow.setRowVGap(rowVGap);         //行间距
		if(rowCompHGap != -1)layoutRow.setRowCompGap(rowCompHGap);      //行内组件间间距
	}
	

	/**
	 * 配置行信息
	 * @param rowNum行号
	 * @param rowLeftGap行左边间隔
	 * @param rowRightGap行右边间隔
	 * @param rowCompHGap行内组件间距
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
				//未处理不连续行问题
			}
			layoutRow = new LayoutRow(rowNum, y);
			rowList.put(rowNum, layoutRow);
		}
		if(rowLeftGap != -1) layoutRow.setRowLeftGap(rowLeftGap);        //行左间距
		if(rowRightGap != -1)layoutRow.setRowRightGap(rowRightGap);      //行右间距
		if(rowCompHGap != -1)layoutRow.setRowCompGap(rowCompHGap);       //行内组件间间距
	}
	
	/**
	 * 根据容器组件的宽和高，计算各个组件的位置及大小
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
				if(comp.getCompLayout() != null) comp.getCompLayout().setRowPos();  //递归
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
	 * 根据容器组件的宽和高，计算各个组件的位置及大小
	 * @param width布局容器 宽度
	 * @param height布局容器 高度
	 */
	public void setRowPos(int width, int height) {
		LayoutRow layoutRow = null;
		//System.out.println("rowList.size:"+rowList.size());
		
		//计算可拉伸行的总高度
		int pullingHeight = height - topGap - botGap;
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			if(!layoutRow.isVPulling()){
				pullingHeight = pullingHeight - layoutRow.getRowHeight() - layoutRow.getRowVGap();
			}
		}
		
		//遍历行
		int rowY = topGap;  //一行中所有组件的Y坐标，在此计算。
		for (int i = 1; i <= rowList.size(); i++) {
			layoutRow = rowList.get(i);
			
			//设置组件位置
			if(layoutRow.isVPulling()){
				//允许行垂直拉伸时，高度 = 可拉伸的总高度 * 拉伸比例。
				layoutRow.setCompPos(width, (int) (pullingHeight * layoutRow.getVPullingScale()));
				layoutRow.setRowHeight((int) (pullingHeight * layoutRow.getVPullingScale()));
			}else{
				layoutRow.setCompPos(width, layoutRow.getRowHeight());
			}
			
			//遍历行组件，设置组件Y坐标，加载组件
			for (LayoutComp comp : layoutRow.getjComponentList()) {
				//if(getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")) return;
				//组件的Y轴坐标，在此处计算，其它不变。
				comp.getComponent().setBounds(
						comp.getComponent().getX(), 
						rowY, 
						comp.getComponent().getWidth(), 
						comp.getComponent().getHeight());
				
				//将组件加载到父容器组件中
				//if(getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane") ) continue;
				if(getFatherObjectType().equals("JComponent")  &&
						!getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")){
					((JComponent) fatherJcomp).add(comp.getComponent());
				}else if(getFatherObjectType().equals("JComponent")  &&
						getFatherJcomp().getClass().getName().equals("com.base.comp.JClosableTabbedPane")){
					//将TAB名称保存在CompOthInfo中
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
		int height = topGap;  //顶部间隔/底部间隔
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
