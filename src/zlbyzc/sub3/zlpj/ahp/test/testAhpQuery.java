package zlbyzc.sub3.zlpj.ahp.test;

import java.awt.EventQueue;

import javax.swing.JFrame;

import zlbyzc.sub3.zlpj.ahp.AhpQuerySub3Frame;

public class testAhpQuery {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AhpQuerySub3Frame ifrmAhpQuery = new AhpQuerySub3Frame();
					ifrmAhpQuery.setMaximizable(true);
					ifrmAhpQuery.pack();
					
					JFrame frame = new JFrame();
					frame.setContentPane(ifrmAhpQuery);

					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
