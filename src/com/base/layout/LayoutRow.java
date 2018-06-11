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
	private int rowLeftGap = 0;  //����߿������
	private int rowRightGap = 0; //���ұ߿������
	private int rowVGap = 0;     //�м��
	private int rowCompGap = 10; //������
	private boolean vPulling = false; //��ֱ�������죬Ϊ�����ԣ���һ����������һ���Ϊ��ֱ���죬��������������Ϊ��ֱ���졣
	private float vPullingScale = 0.5f;  //��ֱ�������������

	private Vector<LayoutComp> jComponentList = new Vector<LayoutComp>();  //��д��˳����
	
	/*
	 * ����
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
	 * ����
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
	 * �����������
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
	 * ����������У����λ��������һ���λ�ã���λ����֮�ص�
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
	 * �������������λ��
	 * ���¼���X�������W���
	 */
	public void setCompPos(int rowWidth, int rowHeight){
		//ȡ�ɹ�������ܿ��
		int pullingWidth = rowWidth - getRowLeftGap() - getRowRightGap();
		for(LayoutComp comp:jComponentList){
			if(!comp.ishPulling()){
				pullingWidth = pullingWidth - comp.getComponent().getWidth() - comp.getHgap();
			}
			if(comp.getRefComponent() != null){
				pullingWidth = pullingWidth + comp.getRefComponent().getWidth() + comp.getHgap(); //comp.getHgap--Ӧȡ���������ˮƽ�����������
			}
		}
		
		int curX = getRowLeftGap();  //��¼�������X��λ�á�
		for(LayoutComp comp:jComponentList){
			int x = curX;     						//���¼���X�����ꡣ
			int y = comp.getComponent().getY();     //����һ����㣬�˴����䡣
			int w = comp.getComponent().getWidth(); //���������죬����Ҫʹ�ü���ֵ��
			int h = rowHeight;                      //����߶����и߾������˴����䡣
			
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
		
		//���Ҷ���
		curX = rowWidth - getRowRightGap();  //��¼�������X��λ�á�
		for(int index=jComponentList.size()-1;index >= 0;index--){
			LayoutComp comp = jComponentList.get(index);
			
			int x = comp.getComponent().getX();     //���¼���X�����ꡣ
			int y = comp.getComponent().getY();     //����һ����㣬�˴����䡣
			int w = comp.getComponent().getWidth(); //���������죬����Ҫʹ�ü���ֵ��
			int h = rowHeight;                      //����߶����и߾������˴����䡣
			
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
