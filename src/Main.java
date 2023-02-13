import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
{
    static int XMAX = 1000;
    static int YMAX = 500;
    public static void main(String[] args)
    {
        // create frame for PolygonsJPanel
        JFrame frame = new JFrame( "Drawing Polygons" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        ArrayList<Polygon> polygonArrayList = makePolyList();
        PolygonsJPanel polygonsJPanel = new PolygonsJPanel(polygonArrayList, XMAX, YMAX);

        Node goal = new Node( (int)(.85 * XMAX), YMAX - (int)(.92 * YMAX) );
        Node start = new Node( (int)(.16 * XMAX), YMAX - (int)(.15 * YMAX) );

        ArrayList<Node> space = new ArrayList<>();
        for(int i = 0; i<XMAX; i++){
            for(int j=0; j<YMAX; j++){
                boolean valid = true;
                for(Polygon p : polygonArrayList) {
                    if(p.contains(i,j)) {
                        valid = false;
                        break;
                    }
                }
                if(valid) {
                    space.add(new Node(i,j));
                }
                valid = true;
            }
        }
        frame.add( polygonsJPanel ); // add polygonsJPanel to frame
        frame.setSize( 1000, 500 ); // set frame size
        frame.setVisible( true ); // display frame
    } // end main
    public static ArrayList<Polygon> makePolyList() {
        ArrayList<Polygon> polyList = new ArrayList<>();

        Polygon p1 = new Polygon();
        p1.addPoint( (int)(.26 * XMAX),	YMAX - (int)(.95 * YMAX) );
        p1.addPoint( (int)(.34 * XMAX),	YMAX - (int)(.71 * YMAX) );
        p1.addPoint( (int)(.28 * XMAX),	YMAX - (int)(.40 * YMAX) );
        p1.addPoint( (int)(.17 * XMAX),	YMAX - (int)(.46 * YMAX) );
        p1.addPoint( (int)(.15 * XMAX),	YMAX - (int)(.71 * YMAX) );
        polyList.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint( (int)(.43 * XMAX),	YMAX - (int)(.93 * YMAX) );
        p2.addPoint( (int)(.51 * XMAX),	YMAX - (int)(.96 * YMAX) );
        p2.addPoint( (int)(.56 * XMAX),	YMAX - (int)(.82 * YMAX) );
        p2.addPoint( (int)(.43 * XMAX),	YMAX - (int)(.63 * YMAX) );
        polyList.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint((int)(.38 * XMAX), YMAX - (int) (.75 * YMAX) );
        p3.addPoint((int)(.43 * XMAX), YMAX - (int) (.37 * YMAX) );
        p3.addPoint((int)(.34 * XMAX), YMAX - (int) (.37 * YMAX) );
        polyList.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint( (int)(.18 * XMAX),YMAX - (int)(.28 * YMAX) );
        p4.addPoint( (int)(.50 * XMAX),YMAX - (int)(.28 * YMAX) );
        p4.addPoint( (int)(.49 * XMAX),YMAX - (int)(.03  * YMAX) );
        p4.addPoint( (int)(.18 * XMAX),YMAX - (int)(.03  * YMAX) );
        polyList.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint( (int)(.51 * XMAX), YMAX - (int)(.50 * YMAX) );
        p5.addPoint( (int)(.60 * XMAX), YMAX - (int)(.30 * YMAX) );
        p5.addPoint( (int)(.54 * XMAX), YMAX - (int)(.15 * YMAX) );
        polyList.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint( (int)(.58 * XMAX), YMAX - (int)(.43 * YMAX) );
        p6.addPoint( (int)(.71 * XMAX), YMAX - (int)(.43 * YMAX) );
        p6.addPoint( (int)(.71 * XMAX), YMAX - (int)(.93 * YMAX) );
        p6.addPoint( (int)(.58 * XMAX), YMAX - (int)(.93 * YMAX) );
        polyList.add(p6);

        Polygon p7 = new Polygon();
        p7.addPoint( (int)(.73 * XMAX), YMAX - (int)(.87 * YMAX) );
        p7.addPoint( (int)(.77 * XMAX), YMAX - (int)(.94 * YMAX) );
        p7.addPoint( (int)(.81 * XMAX), YMAX - (int)(.84 * YMAX) );
        p7.addPoint( (int)(.79 * XMAX), YMAX - (int)(.38 * YMAX) );
        polyList.add(p7);

        Polygon p8 = new Polygon();
        p8.addPoint( (int)(.72 * XMAX), YMAX - (int)(.40 * YMAX) );
        p8.addPoint( (int)(.78 * XMAX), YMAX - (int)(.29 * YMAX) );
        p8.addPoint( (int)(.78 * XMAX), YMAX - (int)(.10 * YMAX) );
        p8.addPoint( (int)(.72 * XMAX), YMAX - (int)(.02 * YMAX) );
        p8.addPoint( (int)(.66 * XMAX), YMAX - (int)(.11 * YMAX) );
        p8.addPoint( (int)(.66 * XMAX), YMAX - (int)(.29 * YMAX) );
        polyList.add(p8);

        return polyList;
    }
}

// draw polygons and polylines
class PolygonsJPanel extends JPanel{
    ArrayList<Polygon> polyList;
    int xMax;
    int yMax;
    public PolygonsJPanel(ArrayList<Polygon> pList, int x, int y) {
        this.polyList = pList;
        this.xMax = x;
        this.yMax = y;

    }
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g ); // call superclass's paintComponent
        for (Polygon polygon : polyList) {
            g.fillPolygon(polygon);
        }

    } // end method paintComponent

} // end class PolygonsJPanel
