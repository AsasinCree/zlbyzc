package img.svg;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import static java.awt.Color.*;
import static java.awt.MultipleGradientPaint.CycleMethod.*;
import static java.awt.MultipleGradientPaint.ColorSpaceType.*;

/**
 * This class has been automatically generated using
 * <a href="https://flamingo.dev.java.net">Flamingo SVG transcoder</a>.
 */
public class Edit18 implements javax.swing.Icon {

    /** The width of this icon. */
    private int width;

    /** The height of this icon. */
    private int height;

    /** The rendered image. */
    private BufferedImage image;

    /**
     * Creates a new transcoded SVG image.
     */
    public Edit18() {
        this(32, 32);
    }

    /**
     * Creates a new transcoded SVG image.
     */
    public Edit18(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (image == null) {
            image = new BufferedImage(getIconWidth(), getIconHeight(), BufferedImage.TYPE_INT_ARGB);
            double coef = Math.min((double) width / (double) 32, (double) height / (double) 32);
            
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.scale(coef, coef);
            paint(g2d);
            g2d.dispose();
        }
        
        g.drawImage(image, x, y, null);
    }

    /**
     * Paints the transcoded SVG image on the specified graphics context.
     * 
     * @param g Graphics context.
     */
    @SuppressWarnings("unused")
	private static void paint(Graphics2D g) {
        Shape shape = null;
        
        float origAlpha = 1.0f;
        
        java.util.LinkedList<AffineTransform> transformations = new java.util.LinkedList<AffineTransform>();
        

        // 

        // _0

        // _0_0

        // _0_0_0

        // _0_0_0_0

        // _0_0_0_0_0
        shape = new GeneralPath();
        ((GeneralPath) shape).moveTo(30.276, 1.722);
        ((GeneralPath) shape).curveTo(29.168, 0.611, 27.69, 0.0, 26.121, 0.0);
        ((GeneralPath) shape).curveTo(24.552, 0.0, 23.076, 0.61, 21.967001, 1.72);
        ((GeneralPath) shape).lineTo(4.294, 19.291);
        ((GeneralPath) shape).curveTo(4.189, 19.395, 4.109, 19.52, 4.059, 19.658);
        ((GeneralPath) shape).lineTo(0.059000015, 30.658);
        ((GeneralPath) shape).curveTo(-0.06999998, 31.013, 0.013000015, 31.414001, 0.27400002, 31.689001);
        ((GeneralPath) shape).curveTo(0.466, 31.891, 0.729, 32.0, 1.0, 32.0);
        ((GeneralPath) shape).curveTo(1.098, 32.0, 1.196, 31.986, 1.293, 31.956);
        ((GeneralPath) shape).lineTo(11.242001, 28.904);
        ((GeneralPath) shape).curveTo(11.398001, 28.856998, 11.540001, 28.771, 11.656, 28.656);
        ((GeneralPath) shape).lineTo(30.277, 10.035);
        ((GeneralPath) shape).curveTo(31.389, 8.926, 32.0, 7.448, 32.0, 5.878);
        ((GeneralPath) shape).curveTo(31.999, 4.309, 31.389, 2.832, 30.276, 1.722);
        ((GeneralPath) shape).closePath();
        ((GeneralPath) shape).moveTo(10.092, 27.165);
        ((GeneralPath) shape).lineTo(6.368, 28.309002);
        ((GeneralPath) shape).curveTo(6.151, 27.672003, 5.813, 27.108002, 5.352, 26.647001);
        ((GeneralPath) shape).curveTo(4.951, 26.248001, 4.486, 25.938002, 3.9960003, 25.686);
        ((GeneralPath) shape).lineTo(5.7, 21.0);
        ((GeneralPath) shape).lineTo(8.0, 21.0);
        ((GeneralPath) shape).lineTo(8.0, 23.0);
        ((GeneralPath) shape).curveTo(8.0, 23.553, 8.447, 24.0, 9.0, 24.0);
        ((GeneralPath) shape).lineTo(10.765, 24.0);
        ((GeneralPath) shape).lineTo(10.092, 27.165);
        ((GeneralPath) shape).closePath();
        ((GeneralPath) shape).moveTo(24.812, 12.671);
        ((GeneralPath) shape).lineTo(12.628, 24.855);
        ((GeneralPath) shape).lineTo(12.978001, 23.208);
        ((GeneralPath) shape).curveTo(13.040001, 22.912, 12.966001, 22.605, 12.776001, 22.371);
        ((GeneralPath) shape).curveTo(12.586, 22.136, 12.301, 22.0, 12.0, 22.0);
        ((GeneralPath) shape).lineTo(10.0, 22.0);
        ((GeneralPath) shape).lineTo(10.0, 20.0);
        ((GeneralPath) shape).curveTo(10.0, 19.448, 9.552, 19.0, 9.0, 19.0);
        ((GeneralPath) shape).lineTo(7.422, 19.0);
        ((GeneralPath) shape).lineTo(19.315, 7.175);
        ((GeneralPath) shape).lineTo(19.327, 7.1860003);
        ((GeneralPath) shape).curveTo(20.059, 6.4530005, 21.034, 6.05, 22.069, 6.05);
        ((GeneralPath) shape).curveTo(23.104, 6.05, 24.08, 6.453, 24.811, 7.1860003);
        ((GeneralPath) shape).curveTo(25.542002, 7.9190006, 25.949001, 8.893001, 25.949001, 9.929001);
        ((GeneralPath) shape).curveTo(25.949, 10.965, 25.546, 11.938, 24.812, 12.671);
        ((GeneralPath) shape).closePath();
        ((GeneralPath) shape).moveTo(28.862, 8.621);
        ((GeneralPath) shape).lineTo(27.93, 9.554);
        ((GeneralPath) shape).curveTo(27.84, 8.125, 27.247, 6.793, 26.227001, 5.772);
        ((GeneralPath) shape).curveTo(25.206001, 4.75, 23.873001, 4.158, 22.44, 4.069);
        ((GeneralPath) shape).lineTo(23.378, 3.1379998);
        ((GeneralPath) shape).lineTo(23.380001, 3.1359997);
        ((GeneralPath) shape).curveTo(24.11, 2.403, 25.085, 2.0, 26.121, 2.0);
        ((GeneralPath) shape).curveTo(27.157001, 2.0, 28.131, 2.4029999, 28.862, 3.1360002);
        ((GeneralPath) shape).curveTo(29.596, 3.869, 30.0, 4.843, 30.0, 5.878);
        ((GeneralPath) shape).curveTo(30.0, 6.915, 29.598, 7.889, 28.862, 8.621);
        ((GeneralPath) shape).closePath();
        ((GeneralPath) shape).moveTo(22.293, 8.293);
        ((GeneralPath) shape).lineTo(12.292999, 18.293);
        ((GeneralPath) shape).curveTo(11.901999, 18.684, 11.901999, 19.316, 12.292999, 19.706999);
        ((GeneralPath) shape).curveTo(12.487, 19.902, 12.744, 20.0, 13.0, 20.0);
        ((GeneralPath) shape).curveTo(13.256, 20.0, 13.511, 19.902, 13.707, 19.707);
        ((GeneralPath) shape).lineTo(23.707, 9.707001);
        ((GeneralPath) shape).curveTo(24.098001, 9.316001, 24.098001, 8.684001, 23.707, 8.293001);
        ((GeneralPath) shape).curveTo(23.315, 7.902, 22.684, 7.902, 22.293, 8.293);
        ((GeneralPath) shape).closePath();

        g.setPaint(BLACK);
        g.fill(shape);

        // _0_1

        // _0_2

        // _0_3

        // _0_4

        // _0_5

        // _0_6

        // _0_7

        // _0_8

        // _0_9

        // _0_10

        // _0_11

        // _0_12

        // _0_13

        // _0_14

        // _0_15

    }
}

