package com.base.layout;

import java.util.Vector;

import javax.swing.JComponent;

public class LayoutRow {
	private final int DEF_ROW_HEIGHT = 25;
	private final int DEF_ROW_LEFT_GAP = 10;
	private final int DEF_ROW_RIGHT_GAP = 30; //
	private final int DEF_ROW_V_GAP = 10;
	
	private int rowNum = 0;
	private int rowY = 0;
	private int rowHeight = 0;
	private int rowLeftGap = 0;  //与左边框保留间隔
	private int rowRightGap = 0; //与右边框保留间隔
	private int rowVGap = 0;     //行间距
	private int rowCompGap = 10; //组件间隔
	private boolean vPulling = false; //垂直方向拉伸，为行属性，即一行中若其中一组件为垂直拉伸，则该行所有组件都为垂直拉伸。
	private float vPullingScale = 0.5f;  //垂直方向拉伸比例。

	private Vector<LayoutComp> jComponentList = new Vector<LayoutComp>();  //按写入顺序处理
	
	/*
	 * 构造
	 */
	public LayoutRow(int rowNum, int rowY){
		setRowNum(rowNum);
		setRowY(rowY);
		setRowHeight(DEF_ROW_HEIGHT);
		setRowLeftGap(DEF_ROW_LEFT_GAP);
		setRowRightGap(DEF_ROW_RIGHT_GAP);
		setRowVGap(DEF_ROW_V_GAP);
	}
	
	/*
	 * 构造
	 */
	public LayoutRow(int rowNum, int rowY, boolean pulling, float vPullingScale){
		setRowNum(rowNum);
		setRowY(rowY);
		setRowHeight(DEF_ROW_HEIGHT);
		setRowLeftGap(DEF_ROW_LEFT_GAP);
		setRowRightGap(DEF_ROW_RIGHT_GAP);
		setRowVGap(DEF_ROW_V_GAP);
		setVPulling(pulling);
		setVPullingScale(vPullingScale);
	}
	
	/*
	 * 加载组件到行
	 */
	public void add(JComponent component, int width, char pullingFlag,float vPullingScale, float hPullingScale, char align){
		component.setBounds(getRowCurX(), getRowY(), width, getRowHeight());
		LayoutComp layoutComp = new LayoutComp(component);
		layoutComp.setHgap(getRowCompGap());
		if(pullingFlag == 'B' || pullingFlag == 'V'){
			setVPulling(true);
			setVPullingScale(vPullingScale);
		}
		if(pullingFlag == 'B' || pullingFlag == 'H'){
			layoutComp.sethPulling(true);
			layoutComp.sethPullingScale(hPullingScale);  
		}
		if(pullingFlag == 'N'){
			layoutComp.sethPulling(false);
			layoutComp.sethPullingScale(0.0f);  
		}
		layoutComp.setAlign(align);
		jComponentList.addElement(layoutComp);
	}
	
	/*
	 * 加载组件到行，组件位置引用另一组件位置，即位置与之重叠
	 */
	public void addByRef(JComponent component, int width, char pullingFlag,float vPullingScale, float hPullingScale, char align, JComponent refComp){
		component.setBounds(refComp.getX(), refComp.getY(), width, getRowHeight());
		LayoutComp layoutComp = new LayoutComp(component);
		layoutComp.setHgap(getRowCompGap());
		if(pullingFlag == 'B' || pullingFlag == 'V'){
			setVPulling(true);
			setVPullingScale(vPullingScale);
		}
		if(pullingFlag == 'B' || pullingFlag == 'H'){
			layoutComp.sethPulling(true);
			layoutComp.sethPullingScale(hPullingScale);  
		}
		if(pullingFlag == 'N'){
			layoutComp.sethPulling(false);
			layoutComp.sethPullingScale(0.0f);  
		}
		layoutComp.setAlign(align);
		layoutComp.setRefComponent(refComp);
		jComponentList.addElement(layoutComp);
	}
	
	/*
	 * 设置行内组件的位置
	 * 重新计算X轴坐标和W宽度
	 */
	public void setCompPos(int rowWidth, int rowHeight){
		//取可供拉伸的总宽度
		int pullingWidth = rowWidth - getRowLeftGap() - getRowRightGap();
		for(LayoutComp comp:jComponentList){
			if(!comp.ishPulling()){
				pullingWidth = pullingWidth - comp.getComponent().getWidth() - comp.getHgap();
			}
			if(comp.getRefComponent() != null){
				pullingWidth = pullingWidth + comp.getRefComponent().getWidth() + comp.getHgap(); //comp.getHgap--应取引用组件的水平间隔。！！！
			}
		}
		
		int curX = getRowLeftGap();  //记录行组件的X轴位置。
		for(LayoutComp comp:jComponentList){
			int x = curX;     						//重新计算X轴坐标。
			int y = comp.getComponent().getY();     //由上一层计算，此处不变。
			int w = comp.getComponent().getWidth(); //若允许拉伸，则需要使用计算值。
			int h = rowHeight;                      //组件高度由行高决定，此处不变。
			
			if(comp.ishPulling()){
				w = (int) (pullingWidth * comp.gethPullingScale());
			}
			
			curX = curX + w + comp.getHgap();
			if(comp.getRefComponent() != null){
				x = comp.getRefComponent().getX();
				y = comp.getRefComponent().getY();
			}
			//System.out.println("y="+y+";x="+x+";w="+w+";h="+h+" comp:"+comp.getComponent().getClass().getTypeName());
			comp.getComponent().setBounds(x,y,w,h);
		}
		
		//若右对齐
		curX = rowWidth - getRowRightGap();  //记录行组件的X轴位置。
		for(int index=jComponentList.size()-1;index >= 0;index--){
			LayoutComp comp = jComponentList.get(index);
			
			int x = comp.getComponent().getX();     //重新计算X轴坐标。
			int y = comp.getComponent().getY();     //由上一层计算，此处不变。
			int w = comp.getComponent().getWidth(); //若允许拉伸，则需要使用计算值。
			int h = rowHeight;                      //组件高度由行高决定，此处不变。
			
			if(comp.getAlign()=='R'){
				x = curX - comp.getComponent().getWidth() - comp.getHgap();
			}
			
			curX = curX - comp.getComponent().getWidth() - comp.getHgap();
			
			if(comp.getRefComponent() != null){
				x = comp.getRefComponent().getX();
				y = comp.getRefComponent().getY();
			}
			//System.out.println("y="+y+";x="+x+";w="+w+";h="+h+"  vgap="+getRowVGap()+"  comp:"+comp.getComponent().getClass().getTypeName());
			comp.getComponent().setBounds(x,y,w,h);
		}
	}
	
	public int getRowCurX(){
		int curx = 0;
		curx = curx + getRowLeftGap();
		for(LayoutComp comp:jComponentList){
			curx = curx + comp.getComponent().getWidth() + comp.getHgap();
		}
		return curx;
	}
	////////////////////////////////////////////////////////////

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int getRowY() {
		return rowY;
	}

	public void setRowY(int rowY) {
		this.rowY = rowY;
	}

	public int getRowLeftGap() {
		return rowLeftGap;
	}

	public void setRowLeftGap(int rowLeftGap) {
		this.rowLeftGap = rowLeftGap;
	}

	public int getRowCompGap() {
		return rowCompGap;
	}

	public void setRowCompGap(int rowCompGap) {
		this.rowCompGap = rowCompGap;
		for (LayoutComp comp:getjComponentList()){
			comp.setHgap(rowCompGap);
		}
	}

	public Vector<LayoutComp> getjComponentList() {
		return jComponentList;
	}

	public void setjComponentList(Vector<LayoutComp> jComponentList) {
		this.jComponentList = jComponentList;
	}

	public int getRowRightGap() {
		return rowRightGap;
	}

	public void setRowRightGap(int rowRightGap) {
		this.rowRightGap = rowRightGap;
	}

	public int getRowVGap() {
		return rowVGap;
	}

	public void setRowVGap(int rowVGap) {
		this.rowVGap = rowVGap;
	}
	public boolean isVPulling() {
		return vPulling;
	}

	public void setVPulling(boolean vPulling) {
		this.vPulling = vPulling;
	}

	public float getVPullingScale() {
		return vPullingScale;
	}

	public void setVPullingScale(float vPullingScale) {
		this.vPullingScale = vPullingScale;
	}
}
