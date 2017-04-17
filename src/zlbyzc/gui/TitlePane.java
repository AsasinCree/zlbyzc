package zlbyzc.gui;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.internal.ui.SubstanceRootPaneUI;
import org.pushingpixels.substance.internal.utils.SubstanceTitlePane;

public class TitlePane extends SubstanceTitlePane {

	private static final long serialVersionUID = 1L;

	public TitlePane(JRootPane root, SubstanceRootPaneUI ui) {
		super(root, ui);
	}

	public static void editTitleBar(JFrame frame, String titleText ) {
		JComponent title = SubstanceLookAndFeel.getTitlePaneComponent(frame);
		JLabel titleLabel = new JLabel("<html><b><font style=\"font-size:12px;\">"+titleText+"</font></b></html>");
		//磅值大小是基于排字磅值 的，大约为 1/72 英寸
		//以常见1024像素对比: 1024像素=3.413英寸=8.67厘米         (300像素/英寸dpi     每像素≈0.003333英寸)
		//所以1024像素的厘米尺寸就是:1024*0.003333*2.54
		//1024像素=14.222英寸=36.12厘米         (72像素/英寸dpi     每像素≈0.013889英寸)
		int size = (int)(titleLabel.getFont().getSize2D() / 72 * 300);
		titleLabel.setBounds(200, 0, size * titleText.length(),23);
		titleLabel.putClientProperty(
				"substancelaf.internal.titlePane.extraComponentKind",
				ExtraComponentKind.TRAILING);
		title.add(titleLabel, 0);
	}

	public static void editTitleBar(JFrame frame, Icon icon) {
		JComponent title = SubstanceLookAndFeel.getTitlePaneComponent(frame);
		((ImageIcon)icon).getImage().getScaledInstance(24,24,Image.SCALE_DEFAULT);
		JLabel titleLabel = new JLabel(icon);
		titleLabel.setBounds(32, 0, 24,24);
		titleLabel.putClientProperty(
				"substancelaf.internal.titlePane.extraComponentKind",
				ExtraComponentKind.TRAILING);
		title.add(titleLabel, -1);
	}

	public static void editTitleBar(JFrame frame, JComponent component) {
		JComponent title = SubstanceLookAndFeel.getTitlePaneComponent(frame);
		component.putClientProperty(
				"substancelaf.internal.titlePane.extraComponentKind",
				ExtraComponentKind.TRAILING);
		title.add(component, 0);
	}
}