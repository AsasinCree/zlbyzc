package zlbyzc.gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * ���������Գ���
 *
 * @author ygh
 *
 */
public class TestProgressBar implements ActionListener {

    private static final String DEFAULT_STATUS = "Please Waiting";
    private JDialog dialog;
    private JProgressBar progressBar;
    private JLabel lbStatus;
    private JButton btnCancel;
    private Window parent;
    private Thread thread; // ����ҵ����߳�
    private String statusInfo;
    private String resultInfo;
    private String cancelInfo;
    /**
     * ��ʾ���������ԶԻ���
     * @param parent
     * @param thread
     */
    public static void show(Window parent, Thread thread) {
        new TestProgressBar(parent, thread, DEFAULT_STATUS, null, null);
    }
    /**
     * ��ʾ���������ԶԻ���
     * @param parent
     * @param thread
     * @param statusInfo
     */
    public static void show(Window parent, Thread thread, String statusInfo) {
        new TestProgressBar(parent, thread, statusInfo, null, null);
    }

    /**
     * ��ʾ�Ի���
     * @param parent
     * @param thread
     * @param statusInfo
     * @param resultInfo
     * @param cancelInfo
     */
    public static void show(Window parent, Thread thread, String statusInfo,
            String resultInfo, String cancelInfo) {
        new TestProgressBar(parent, thread, statusInfo, resultInfo, cancelInfo);
    }

    /**
     * �Ի����캯��
     * @param parent
     * @param thread
     * @param statusInfo
     * @param resultInfo
     * @param cancelInfo
     */
    private TestProgressBar(Window parent, Thread thread, String statusInfo,
            String resultInfo, String cancelInfo) {
        this.parent = parent;
        this.thread = thread;
        this.statusInfo = statusInfo;
        this.resultInfo = resultInfo;
        this.cancelInfo = cancelInfo;
        initUI();
        startThread();
        dialog.setVisible(true);
    }
    /**
     * ������ʾ�������ĶԻ���
     */
    private void initUI() {
        if (parent instanceof Dialog) {
            dialog = new JDialog((Dialog) parent, true);
        } else if (parent instanceof Frame) {
            dialog = new JDialog((Frame) parent, true);
        } else {
            dialog = new JDialog((Frame) null, true);
        }

        final JPanel mainPane = new JPanel(null);
        progressBar = new JProgressBar();
        lbStatus = new JLabel("" + statusInfo);
        btnCancel = new JButton("Cancel");
        progressBar.setIndeterminate(true);
        btnCancel.addActionListener(this);

        mainPane.add(progressBar);
        mainPane.add(lbStatus);
        // mainPane.add(btnCancel);

        dialog.getContentPane().add(mainPane);
        dialog.setUndecorated(true); // ��ȥtitle
        dialog.setResizable(true);
        dialog.setSize(390, 150);
        dialog.setLocationRelativeTo(parent); // ���ô˴��������ָ�������λ��

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // ������ر�

        mainPane.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layout(mainPane.getWidth(), mainPane.getHeight());
            }
        });
    }
    /**
     * �����̣߳����Խ�����
     */
    private void startThread() {
        new Thread() {
            public void run() {
                try {
                    thread.start(); // �����ʱ����
                    // �ȴ��������߳̽���
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // �رս�����ʾ��
                    dialog.dispose();

                    if (resultInfo != null && !resultInfo.trim().equals("")) {
                        String title = "Info";
                        JOptionPane.showMessageDialog(parent, resultInfo);
                                //title,JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }.start();
    }
    /**
     * ���ÿؼ���λ�úʹ�С
     * @param width
     * @param height
     */
    private void layout(int width, int height) {
        progressBar.setBounds(20, 20, 350, 15);
        lbStatus.setBounds(20, 50, 350, 25);
        btnCancel.setBounds(width - 85, height - 31, 75, 21);
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        resultInfo = cancelInfo;
        thread.stop();
    }

    /**
     * ���������
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // ���ڲ��Խ��������߳�
        Thread thread = new Thread() {
          public void run() {
                int index = 0;

                while (index < 3) {
                    try {
                        sleep(1000);
                        System.out.println(++index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //��ʾ���������ԶԻ���
        TestProgressBar.show((Frame) null, thread, "Status", "Result", "Cancel");

    }
}