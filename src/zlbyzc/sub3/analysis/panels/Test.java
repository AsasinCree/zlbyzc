package zlbyzc.sub3.analysis.panels;

import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;

import zlbyzc.sub3.analysis.entities.SwotActor;

public class Test {
	
			public static void main(String[] args)
	{
				JLabel label = new JLabel(); 
				  FontMetrics metrics; 
				  int textH = 0; 
				  float textW = 0; 
				  label.setText("1"); 
				  metrics = label.getFontMetrics(label.getFont()); 
				  textH = metrics.getHeight();//字符串的高,   只和字体有关 
				  textW = metrics.stringWidth(label.getText());//字符串的宽 
				  int[] num = new int[2]; 
				  num[0] = textH; 
				//  num[1] = textW; 
				  System.out.println(textW);
	}
}

