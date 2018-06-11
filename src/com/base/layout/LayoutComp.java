package com.base.layout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class LayoutComp{
	private JComponent component = null;
	
	private char align = 'L';  //���뷽ʽ��L-left/R-right
	private boolean hPulling = false; //���ֻ�����Ƿ�ˮƽ���졣
	private float hPullingScale = 1; //�������
	private int hgap = 10;  //ˮƽ��������һ����ļ����Ĭ��Ϊ20 
	private JComponent refComponent = null;

	public LayoutComp(){
		
	}
	
	public LayoutComp(JComponent component){
		this.component = component;
	}
	
	public int getHgap() {
		return hgap;
	}

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public char getAlign() {
		return align;
	}

	public void setAlign(char align) {
		this.align = align;
	}


	public boolean ishPulling() {
		return hPulling;
	}

	public void sethPulling(boolean hPulling) {
		this.hPulling = hPulling;
	}

	public JComponent getComponent() {
		return component;
	}

	public void setComponent(JComponent component) {
		this.component = component;
	}
	
	public String getComponectValue(String compType){
		if (compType != null && compType.equals("javax.swing.JTextField")){
			JTextField txtfield = (JTextField) this.component;
			return txtfield.getText();
		}else if (compType != null && compType.equals("javax.swing.JCheckBox")){
			JCheckBox txtfield = (JCheckBox) this.component;
			if(txtfield.isSelected()){
				return txtfield.getName();
			}else{
				return null;
			}
				
		}else{
			return null;
		}
	}

	public JComponent getRefComponent() {
		return refComponent;
	}

	public void setRefComponent(JComponent refComponent) {
		this.refComponent = refComponent;
	}

	public float gethPullingScale() {
		return hPullingScale;
	}

	public void sethPullingScale(float hPullingScale) {
		this.hPullingScale = hPullingScale;
	}
}
