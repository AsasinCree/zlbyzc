package cn.whu.forum.analysis.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;

/**
 * SWOT法新建面板
 * 
 * @author asasi
 *
 */
public class SwotNewPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// SWOT法的数据操作接口
	SwotTaskDAOInterface swotTaskDAO;

	// 依附的主框架
	JFrame frame;

	// 情景分析法案例名称面板
	private JPanel panelLayTaskName;
	// 情景分析法案例描述面板
	private JPanel panelLayTaskDescription;
	// 情景分析法案例研讨时间面板
	private JPanel panelLayArgueTime;
	// 情景分析法案例地点面板
	private JPanel panelLayTaskLocation;
	// 情景分析法案例按钮面板
	private JPanel panelLayButton;

	// 情景分析法案例示例按钮面板
	private JPanel panelLayExampleButton;
	private Calendar calendar;
	// 是否为示例日期（日期控件不能设置日期[显示的日期不是设置日期]）
	private int flag;

	// 四个标签
	private JLabel labelTaskName;
	private JLabel labelTaskDescription;
	private JLabel labelArgueTime;
	private JLabel labelTaskLocation;

	// 两个文本框、日期选取框、地点下拉框
	private JTextField textfieldfTaskName;
	private JTextField textfieldTaskDescription;
	private JDatePicker datePickerArgueTime;
	private JComboBox<String> comboBoxTaskLocation;

	// 按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonCancel;
	private JButton buttonExample;

	/**
	 * 构造函数：初始化操作接口、控件及布局。
	 * 
	 * @param jif
	 *            表示依附的主框架。
	 */
	public SwotNewPanel(JFrame jif) {
		flag = 0;
		swotTaskDAO = new SwotTaskDAO();
		this.frame = jif;

		initializeComponent();

		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	private void initializeComponent() {
		// JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// 初始化面板
		panelLayTaskName = new JPanel();
		panelLayTaskDescription = new JPanel();
		panelLayArgueTime = new JPanel();
		panelLayTaskLocation = new JPanel();
		panelLayButton = new JPanel();
		panelLayExampleButton = new JPanel();

		// 设置面板布局
		panelLayTaskName.setLayout(new BoxLayout(panelLayTaskName, BoxLayout.X_AXIS));
		panelLayTaskDescription.setLayout(new BoxLayout(panelLayTaskDescription, BoxLayout.X_AXIS));
		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		panelLayExampleButton.setLayout(new BoxLayout(panelLayExampleButton, BoxLayout.X_AXIS)); // 【Test】

		// 初始化标签
		labelTaskName = new JLabel("案例名称");
		labelTaskName.setFont(StaticConfig.FONT_NEWTASK_LABEL);
		labelTaskDescription = new JLabel("案例描述");
		labelTaskDescription.setFont(StaticConfig.FONT_NEWTASK_LABEL);
		labelArgueTime = new JLabel("研讨日期");
		labelArgueTime.setFont(StaticConfig.FONT_NEWTASK_LABEL);
		labelTaskLocation = new JLabel("研讨地点");
		labelTaskLocation.setFont(StaticConfig.FONT_NEWTASK_LABEL);

		// 初始化文本域
		textfieldfTaskName = new JTextField();
		textfieldfTaskName.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);
		textfieldTaskDescription = new JTextField();
		textfieldTaskDescription.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);

		// 初始化日期选取器
		datePickerArgueTime = new JDatePicker();
		datePickerArgueTime.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);
		datePickerArgueTime.setPreferredSize(StaticConfig.WIDTH_JDATEPICKER_BUTTON,
				StaticConfig.FONT_NEWTASK_TEXTFIELD.getSize() + 11); // width对应按钮占比宽度

		// 初始化下拉列表
		comboBoxTaskLocation = new JComboBox<String>();
		// 可输入。得到输入值的方法：getEditor().getItem();
		comboBoxTaskLocation.setEditable(true);
		List<String> swotTaskLocationList = swotTaskDAO.getAllSwotTasksLocation();
		for (String location : swotTaskLocationList)
			comboBoxTaskLocation.addItem(location);
		comboBoxTaskLocation.setSelectedItem(null);
		comboBoxTaskLocation.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);

		// 设置文本域大小
		textfieldfTaskName.setMinimumSize(new Dimension(100, 25));
		textfieldfTaskName.setMaximumSize(new Dimension(150, 30));
		textfieldTaskDescription.setMinimumSize(new Dimension(100, 25));
		textfieldTaskDescription.setMaximumSize(new Dimension(150, 30));
		((Component) datePickerArgueTime).setMinimumSize(new Dimension(100, 25));
		((Component) datePickerArgueTime).setMaximumSize(new Dimension(150, 30));
		comboBoxTaskLocation.setMinimumSize(new Dimension(100, 25));
		comboBoxTaskLocation.setMaximumSize(new Dimension(150, 30));

		// 初始化按钮
		buttonConfirm = new JButton("确定");
		buttonConfirm.setFont(StaticConfig.FONT_NEWTASK_BUTTON);
		buttonReset = new JButton("重置");
		buttonReset.setFont(StaticConfig.FONT_NEWTASK_BUTTON);
		buttonCancel = new JButton("取消");
		buttonCancel.setFont(StaticConfig.FONT_NEWTASK_BUTTON);
		buttonExample = new JButton("示例");
		buttonExample.setFont(StaticConfig.FONT_NEWTASK_BUTTON);

		// 按钮事件注册
		buttonConfirm.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonCancel.addActionListener(this);
		buttonExample.addActionListener(this);
	}

	/**
	 * 初始化布局
	 */
	public void layoutComponent() {
		panelLayTaskName.add(labelTaskName);
		panelLayTaskName.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayTaskName.add(textfieldfTaskName);

		panelLayTaskDescription.add(labelTaskDescription);
		panelLayTaskDescription.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayTaskDescription.add(textfieldTaskDescription);

		panelLayArgueTime.add(labelArgueTime);
		panelLayArgueTime.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayArgueTime.add((Component) datePickerArgueTime);

		panelLayTaskLocation.add(labelTaskLocation);
		panelLayTaskLocation.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayTaskLocation.add(comboBoxTaskLocation);

		panelLayButton.add(buttonConfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayButton.add(buttonReset);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayButton.add(buttonCancel);

		panelLayExampleButton.add(buttonExample);

		add(Box.createVerticalGlue());
		add(panelLayTaskName);
		add(Box.createVerticalStrut(10));
		add(panelLayTaskDescription);
		add(Box.createVerticalStrut(10));
		add(panelLayArgueTime);
		add(Box.createVerticalStrut(10));
		add(panelLayTaskLocation);
		add(Box.createVerticalStrut(10));
		add(panelLayButton);
		add(Box.createVerticalStrut(10));
		add(panelLayExampleButton);
		add(Box.createVerticalGlue());
	}

	/*
	 * 综合处理按钮事件
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 确认按钮
		if (e.getSource() == buttonConfirm) {
			// 不是示例
			if (flag == 0) {
				calendar = Calendar.getInstance();
				calendar.set(datePickerArgueTime.getModel().getYear(), datePickerArgueTime.getModel().getMonth(),
						datePickerArgueTime.getModel().getDay(), 0, 0, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			}
			SwotTask swotTask = new SwotTask();

			if(textfieldfTaskName.getText() != null)
				swotTask.setTaskName(textfieldfTaskName.getText());
			else 
				swotTask.setTaskName("");
			
			if(textfieldTaskDescription.getText() != null)
				swotTask.setTaskDescription(textfieldTaskDescription.getText());
			else 
				swotTask.setTaskDescription("");
		
			if(datePickerArgueTime.getModel().getValue() != null)
				swotTask.setArgueTime(calendar.getTime());
			else 
				swotTask.setArgueTime(null);
			
			if(comboBoxTaskLocation.getSelectedItem() != null)
				swotTask.setTaskLocation(comboBoxTaskLocation.getSelectedItem().toString());
			else
				swotTask.setTaskLocation("");
			
			swotTaskDAO.addTask(swotTask);	

			frame.setContentPane(new SwotEditPanel(swotTask));
			frame.revalidate();
			frame.repaint();
			// 重置按钮
		} else if (e.getSource() == buttonReset) {
			// 清空
			textfieldfTaskName.setText("");
			textfieldTaskDescription.setText("");
			datePickerArgueTime.getModel().setValue(null);
			comboBoxTaskLocation.setSelectedItem(null);
			// 取消按钮
		} else if (e.getSource() == buttonCancel) {
			// 取消新建后续步骤
			frame.setContentPane(new JPanel());
			frame.revalidate();
			frame.repaint();
			// 示例按钮
		} else if (e.getSource() == buttonExample) {
			flag = 1;
			// 快速填写示例
			textfieldfTaskName.setText("发展军事逆向物流的SWOT分析");
			textfieldTaskDescription.setText("发展军事逆向物流的SWOT分析");

			// 默认当前日期
			calendar = Calendar.getInstance();
			datePickerArgueTime.setCustomTextFieldValue(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));

			comboBoxTaskLocation.setSelectedItem("武汉");
		}
	}
}
