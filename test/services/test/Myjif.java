package services.test;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

/* Used by InternalFrameDemo.java. */
public class Myjif
    extends JInternalFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
static int openFrameCount = 0;
  static final int xOffset = 30, yOffset = 30;

  public Myjif() {
    super("Document #" + (++openFrameCount),
          true, //resizable
          true, //closable
          true, //maximizable
          true); //iconifiable

    //...Create the GUI and put it in the window...

    //...Then set the window size or call pack...
    setSize(300, 300);

    //Set the window's location.
    setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jButton1.setText("单击此处新增子窗口");
    jButton1.addActionListener(new MyInternalFrame_jButton1_actionAdapter(this));
  }

  JButton jButton1 = new JButton();

  void jButton1_actionPerformed(ActionEvent e) {
    System.out.println("adfas");
    Myjif a = new Myjif();
    this.getParent().add(a);
    a.setVisible(true);
    try {
      a.setSelected(true);
    }
    catch (PropertyVetoException ex) {
    }
    //  JOptionPane.showMessageDialog(null,"dfd");

  }
}

class MyInternalFrame_jButton1_actionAdapter
    implements java.awt.event.ActionListener {
  Myjif adaptee;

  MyInternalFrame_jButton1_actionAdapter(Myjif adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton1_actionPerformed(e);
  }
}