import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Drawer extends JFrame {
    int ysize = 3000;
    int xsize = (int)(1.1*ysize);
    BufferedImage img;
    List<Point> points;

    public Drawer(List<Point> inputs, List<Neuron> neurons) {
        setBounds(0, 0, xsize, ysize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        img = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_RGB);
        points = new ArrayList<Point>();

        for(int i=0; i<neurons.size(); i++) {
            points.add( new Point( xsize * ( neurons.get(i).weights.x + 10.0 ) / 23.0,
                                  -ysize * ( neurons.get(i).weights.y + 12.0 ) / 21.0 + ysize ) );
        }

        int n;
        for(int x=0; x<xsize; x++) {
            for(int y=0; y<ysize; y++) {
                n = 0;

                for(int i=0; i<neurons.size(); i++) {
                    if( euclideanDistance2D( points.get(i), new Point(x, y) ) < euclideanDistance2D( points.get(n), new Point(x, y) ) ) {
                        n = i;
                    }
                }

                img.setRGB( x, y, neurons.get(n).color.getRGB() );
            }
        }

        Graphics2D gen = img.createGraphics();

    // draw pattern
        for(int i=0; i<inputs.size(); i++) {
            gen.setColor( Color.BLACK );
            gen.fill( new Ellipse2D.Double( xsize * ( inputs.get(i).x + 10.0 ) / 23.0 - 2,
                                           -ysize * ( inputs.get(i).y + 12.0 ) / 21.0 + ysize - 2,
                                           4, 4 ) );
        }

    // draw neurons
        for(int i=0; i<neurons.size(); i++) {
            gen.setColor( Color.BLACK );
            gen.fill( new Ellipse2D.Double(points.get(i).x-10, points.get(i).y-10, 20, 20));
            gen.setColor( Color.RED );
            gen.fill( new Ellipse2D.Double(points.get(i).x-7, points.get(i).y-7, 14, 14));
        }

        try {
            ImageIO.write(img, "png", new File("voronoi.png"));
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    public double euclideanDistance2D(Point a, Point b) {
        return Math.sqrt( (a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y) );
    }
}
