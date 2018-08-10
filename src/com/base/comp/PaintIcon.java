package com.base.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class PaintIcon implements Icon {
    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;
    
    public PaintIcon(Icon fileIcon) {
        this.fileIcon = fileIcon;
        width = 16;
        height = 16;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;
        Color col = g.getColor();
        g.setColor(Color.black);
        int y_p = y + 2;
        //g.drawLine(x + 1, y_p, x + 12, y_p);
        //g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
        //g.drawLine(x, y_p + 1, x, y_p + 12);
        //g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }
    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }
    public int getIconHeight() {
        return height;
    }
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
    
	public static void main(String args[]) {
		PaintIcon icon = new PaintIcon(null);
		JLabel j = new JLabel("≤‚ ‘", icon, SwingConstants.CENTER);
		JFrame jf = new JFrame();
		Container c = jf.getContentPane();
		c.add(j);
		jf.setVisible(true);
		
		
	}
	
	public void paintComponent(Graphics g){
		g.drawLine(10, 10, 200, 200);
	}
}
