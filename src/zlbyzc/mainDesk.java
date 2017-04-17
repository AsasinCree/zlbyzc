package zlbyzc;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;

import org.pushingpixels.flamingo.internal.utils.RenderingUtils;
import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceColorScheme;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;


import icy.resource.ResourceUtil;



public class mainDesk extends JDesktopPane implements ContainerListener, MouseListener, 
										MouseMotionListener,MouseWheelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8554763159520738010L;
	 //offset,scaleJDesktopPane  
    public void cascadeWindows(int offset , double scale)  
    {  
        //  
        int width = (int)(getWidth() * scale);  
        int height = (int)(getHeight() * scale);  
        //  
        int x = 0;  
        int y = 0;  
        for (JInternalFrame frame : getAllFrames())  
        {    
            try  
            {    
                //,  
                frame.setMaximum(false);  
                frame.setIcon(false);  
                //  
                frame.reshape(x, y, width, height);  
                x += offset;  
                y += offset;  
                //  
                if (x + width > getWidth()) x = 0;  
                if (y + height > getHeight()) y = 0;  
            }  
            catch (PropertyVetoException e)  
            {}  
        }  
    }  
    //  
    public void tileWindows()  
    {    
        //  
        int frameCount = 0;  
        for (JInternalFrame frame : getAllFrames())  
        {  
            frameCount++;  
        }  
        //?  
        int rows = (int) Math.sqrt(frameCount);  
        int cols = frameCount / rows;  
        //  
        int extra = frameCount % rows;  
        //  
        int width = getWidth() / cols;  
        int height = getHeight() / rows;  
        //?  
        int x = 0;  
        int y = 0;  
        for (JInternalFrame frame : getAllFrames())  
        {    
            try  
            {  
                //,  
                frame.setMaximum(false);  
                frame.setIcon(false);  
                //  
                frame.reshape(x * width, y * height, width, height);  
                y++;  
                //  
                if (y == rows)  
                {  
                    //  
                    y = 0;  
                    x++;  
                    //?  
                    if (extra == cols - x)  
                    {  
                        rows++;  
                        height = getHeight() / rows;  
                    }  
                }  
            }  
            catch (PropertyVetoException e)  
            {}  
        }  
    }  
    //  
    public void selectNextWindow()  
    {    
        JInternalFrame[] frames = getAllFrames();  
        for (int i = 0; i < frames.length; i++)  
        {    
            if (frames[i].isSelected())  
            {    
                // ??  
                //?  
                int next = (i + 1) % frames.length;  
                while (next != i)  
                {  
                    //  
                    if (!frames[next].isIcon())  
                    {  
                        try  
                        {    
                            frames[next].setSelected(true);  
                            frames[next].toFront();  
                            frames[i].toBack();  
                            return;  
                        }  
                        catch (PropertyVetoException e)  
                        {}  
                    }  
                    next = (next + 1) % frames.length;  
                }  
            }  
        }  
    }  
	public static Rectangle2D getStringBounds(Graphics g, String text)
    {
        return getStringBounds(g, null, text);
    }
	 public static Rectangle2D getStringBounds(Graphics g, Font f, String text)
	    {
	        Rectangle2D result = new Rectangle2D.Double();

	        if (g != null)
	        {
	            final FontMetrics fm;

	            if (f == null)
	                fm = g.getFontMetrics();
	            else
	                fm = g.getFontMetrics(f);

	            for (String s : text.split("\n"))
	            {
	                final Rectangle2D r = fm.getStringBounds(s, g);

	                if (result.isEmpty())
	                    result = r;
	                else
	                    result.setRect(result.getX(), result.getY(), Math.max(result.getWidth(), r.getWidth()),
	                            result.getHeight() + r.getHeight());
	            }
	        }

	        return result;
	    }
	
    public static interface DesktopOverlay extends MouseListener, MouseMotionListener, MouseWheelListener
    {
        public void paint(Graphics g, int width, int height);
    }
    
    public static SubstanceSkin getSkin(Component c)
    {
        return SubstanceLookAndFeel.getCurrentSkin(c);
    }

    public static DecorationAreaType getDecoration(Component c)
    {
        return SubstanceLookAndFeel.getDecorationType(c);
    }
    public static SubstanceColorScheme getDisabledColorScheme(Component c)
    {
        final SubstanceSkin skin = getSkin(c);
        final DecorationAreaType decoration = getDecoration(c);

        if ((skin != null) && (decoration != null))
            return skin.getDisabledColorScheme(decoration);

        return null;
    }

    public static SubstanceColorScheme getEnabledColorScheme(Component c)
    {
        final SubstanceSkin skin = getSkin(c);
        final DecorationAreaType decoration = getDecoration(c);

        if ((skin != null) && (decoration != null))
            return skin.getEnabledColorScheme(decoration);

        return null;
    }
    
    public static Color getBackground(Component c)
    {
        final SubstanceColorScheme colorScheme;

        if (c.isEnabled())
            colorScheme = getEnabledColorScheme(c);
        else
            colorScheme = getDisabledColorScheme(c);

        if (colorScheme != null)
            return new ColorUIResource(colorScheme.getBackgroundFillColor());

        return c.getBackground();
    }
    public static class AbstractDesktopOverlay extends MouseAdapter implements DesktopOverlay
    {
        @Override
        public void paint(Graphics g, int width, int height)
        {
            // nothing by default
        }
    }

    /**
     * Background overlay.
     */
    public static class BackgroundDesktopOverlay extends AbstractDesktopOverlay implements Runnable
    {
        //private final static String BACKGROUND_PATH = "background/";

        private final BufferedImage backGround;
        private final BufferedImage icyLogo;
        private final int bgImgWidth;
        private final int bgImgHeight;
        private final Color textColor;
        private final Color bgTextColor;

        // cached background image
        private BufferedImage cachedImage;
        private int cachedImgWidth;
        private int cachedImgHeight;
        private Color lastBGColor;

        public BackgroundDesktopOverlay()
        {
            super();

            // load random background (nor really random as we have only one right now)
            backGround = ResourceUtil.getImage("blogo.png");
            // load Icy logo
            icyLogo = ResourceUtil.getImage("logoICY.png");

            // default text colors
            textColor = new Color(0, 0, 0, 0.5f);
            bgTextColor = new Color(1, 1, 1, 0.5f);

            bgImgWidth = backGround.getWidth();
            bgImgHeight = backGround.getHeight();

            cachedImgWidth = bgImgWidth * 2;
            cachedImgHeight = bgImgHeight * 2;
            lastBGColor = Color.gray;

            // build background image
            run();
        }

        @Override
        public void paint(Graphics g, int width, int height)
        {
            final double scale = Math.max(2d,
                    Math.max((double) width / (double) bgImgWidth, (double) height / (double) bgImgHeight));

            // compute size of cached background image
            final int imgWidth = (int) (scale * bgImgWidth);
            final int imgHeight = (int) (scale * bgImgHeight);

            // size changed ?
            if ((imgWidth != cachedImgWidth) || (imgHeight != cachedImgHeight))
            {
                cachedImgWidth = imgWidth;
                cachedImgHeight = imgHeight;
                // refresh image
                ThreadUtil.bgRunSingle(this);
            }
            else
            {
                final mainDesk desktop = bar.getDesktopPane();
                if (desktop != null)
                {
                    final Color bgColor = getBackground(desktop);

                    // background color changed ?
                    if (!bgColor.equals(lastBGColor))
                    {
                        lastBGColor = bgColor;
                        // refresh image
                        ThreadUtil.bgRunSingle(this);
                    }
                }
            }

            // draw background image
            g.drawImage(cachedImage, 0, 0, null);

            final String text = "版本 v1.1 2016.4";
            final int textWidth = (int) getStringBounds(g, text).getWidth();

            final Graphics2D g2 = (Graphics2D) g.create();

            // draw version text
            g2.setColor(bgTextColor);
            g2.drawString(text, width - (textWidth + 31), height - 8);
            g2.setColor(textColor);
            g2.drawString(text, width - (textWidth + 30), height - 9);
            // and draw Icy text logo
            g2.drawImage(icyLogo, width - 220, height - 220, null);

            g2.dispose();
        }

        @Override
        public void run()
        {
            final int w = cachedImgWidth;
            final int h = cachedImgHeight;
            final BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // compute image scaling
            final double scale = Math.max((double) w / (double) bgImgWidth, (double) h / (double) bgImgHeight);

            final Graphics2D g = img.createGraphics();

            // fill background color
            g.setBackground(lastBGColor);
            g.clearRect(0, 0, w, h);

            // paint image over background in transparency
            g.scale(scale, scale);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f));
            g.drawImage(backGround, 0, 0, null);

            g.dispose();

            // assign new cached image
            cachedImage = img;

            // request repaint
            final mainDesk desktop = bar.getDesktopPane();
            if (desktop != null)
                desktop.repaint();
        }
    }

    

    private final ComponentAdapter componentAdapter;
    // private final InternalFrameAdapter internalFrameAdapter;

    private final ArrayList<DesktopOverlay> overlays;
    static BasicRibbon bar;
	//public static class RulerPanel extends JPanel {
	    public mainDesk(BasicRibbon br){
	        super();
	        	        
	        //setLayout(new BorderLayout());
	        bar=br;
	        
	        

	        overlays = new ArrayList<DesktopOverlay>();

	        // setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

	        componentAdapter = new ComponentAdapter()
	        {
	            @Override
	            public void componentResized(ComponentEvent e)
	            {
	                checkPosition((JInternalFrame) e.getSource());
	            }

	            @Override
	            public void componentMoved(ComponentEvent e)
	            {
	                checkPosition((JInternalFrame) e.getSource());
	            }
	        };

	        addMouseListener(this);
	        addMouseMotionListener(this);
	        addMouseWheelListener(this);
	        addContainerListener(this);

	        // add the background overlay
	        overlays.add(new BackgroundDesktopOverlay());
	    }
	    @Override
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);

	        final int w = getWidth();
	        final int h = getHeight();

	        // paint overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.paint(g, w, h);

	        // System.out.println("paint background " + i++);
	    }

	    private void registerFrame(JInternalFrame frame)
	    {
	        frame.addComponentListener(componentAdapter);
	    }

	    void unregisterFrame(JInternalFrame frame)
	    {
	        frame.removeComponentListener(componentAdapter);
	    }

	    void checkPosition(JInternalFrame frame)
	    {
	        final Rectangle rect = frame.getBounds();

	        if (fixPosition(rect))
	            frame.setBounds(rect);
	    }

	    boolean fixPosition(Rectangle rect)
	    {
	        final int limit = getY();
	        if (rect.y < limit)
	        {
	            rect.y = limit;
	            return true;
	        }

	        return false;
	    }

	    /**
	     * Returns the list of internal viewers.
	     * 
	     * @param wantNotVisible
	     *        Also return not visible viewers
	     * @param wantIconized
	     *        Also return iconized viewers
	     */
	  

	    /**
	     * Organize all internal viewers in cascade
	     */
	   

	    /**
	     * Organize all internal viewers in tile.
	     * 
	     * @param type
	     *        tile type<br>
	     *        MainFrame.TILE_HORIZONTAL, MainFrame.TILE_VERTICAL or
	     *        MainFrame.TILE_GRID
	     */
	 

	    /**
	     * @deprecated Use {@link #organizeTile(int)} instead.
	     */
	    @Deprecated
	   
	    /**
	     * Add the specified overlay to the desktop.
	     */
	    public void addOverlay(DesktopOverlay overlay)
	    {
	        if (!overlays.contains(overlay))
	            overlays.add(overlay);
	    }

	    /**
	     * remove the specified overlay from the desktop.
	     */
	    public boolean removeOverlay(DesktopOverlay overlay)
	    {
	        return overlays.remove(overlay);
	    }

	    @Override
	    public void componentAdded(ContainerEvent e)
	    {
	        final Component comp = e.getChild();

	        if (comp instanceof JInternalFrame)
	            registerFrame((JInternalFrame) comp);
	    }

	    @Override
	    public void componentRemoved(ContainerEvent e)
	    {
	        final Component comp = e.getChild();

	        if (comp instanceof JInternalFrame)
	            unregisterFrame((JInternalFrame) comp);
	    }

	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseWheelMoved(e);
	    }

	    @Override
	    public void mouseDragged(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseDragged(e);
	    }

	    @Override
	    public void mouseMoved(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseMoved(e);
	    }

	    @Override
	    public void mouseClicked(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseClicked(e);
	    }

	    @Override
	    public void mousePressed(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mousePressed(e);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseReleased(e);
	    }

	    @Override
	    public void mouseEntered(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseEntered(e);
	    }

	    @Override
	    public void mouseExited(MouseEvent e)
	    {
	        // send to overlays
	        for (DesktopOverlay overlay : overlays)
	            overlay.mouseExited(e);
	    }
	}
