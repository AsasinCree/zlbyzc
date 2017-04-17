package zlbyzc.sub3.stats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.util.dbIO;

public class testmG {
	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		mgraphxEx c=new mgraphxEx(false,22,45,true);
		c.setSize(580, 800);
		JPanel panel_button = new JPanel();
		frame.add(panel_button, BorderLayout.SOUTH);	
		JButton btnNewNodeButton = new JButton("readdb");
		btnNewNodeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				    	dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/db_zlpj",
				    			"root","wipm");	      
				    	c.gpanel.readGfromDB(dbio,2);
				    	dbio.close();			    	
				    } catch ( Exception e ) {
				      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				      //System.exit(0);
				    }
			}
		});
		panel_button.add(btnNewNodeButton);
		
		JButton btnSaveButton = new JButton("savedb");
		btnSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
					 dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/db_zlpj",
				    			"root","wipm");	     	      			    	
				    	c.gpanel.saveG2DB("hoho",2,dbio);			    	
				    	dbio.close();		    	
				    } catch ( Exception e ) {
				      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				      //System.exit(0);
				    }
			}
		});
		panel_button.add(btnSaveButton);
		frame.getContentPane().add(c,BorderLayout.CENTER);
		frame.pack();
		frame.setSize(600, 820);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
