import java.awt.Graphics;
import java.awt.Polygon;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel
{
    static int XMAX = 1000;
    static int YMAX = 500;
    public static void main(String[] args)
    {
        // create frame for PolygonsJPanel
        JFrame frame = new JFrame( "A* Path Finding" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        ArrayList<Polygon> polygonArrayList = makePolyList();

        Node goal = new Node( (int)(.85 * XMAX), YMAX - (int)(.92 * YMAX) );
        Node start = new Node( (int)(.16 * XMAX), YMAX - (int)(.15 * YMAX) );

        Node[][] space = new Node[XMAX][YMAX];
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
                    Node newNode = new Node(i,j);
                    newNode.setGoalDistance(goal);
                    newNode.setStartDistance(start);
                    //newNode.evaluation = newNode.getStartDistance() + newNode.getGoalDistance();
                    if (newNode.getX() == goal.getX()
                            && newNode.getY() == goal.getY() ) {
                        newNode.isGoal = true;
                        goal = newNode;
                    }
                    if (newNode.getX() == start.getX()
                            && newNode.getY() == start.getY() ) {
                        newNode.isStart = true;
                        start = newNode;
                    }
                    space[i][j] = newNode;
                    if(i > 0 && space[i-1][j]!=null){
                        newNode.west = space[i-1][j];
                        space[i-1][j].east = newNode;
                    }
                    if (j > 0 && space[i][j-1]!=null) {
                        newNode.south = space[i][j-1];
                        space[i][j-1].north = newNode;
                    }
                }
                valid = true;
            }
        }
//        for (Node[] nArray : space) {
//            for (Node n : nArray) {
//                if (n != null) {
//                    if (n.north != null) {
//                        if (n.north.east != null) n.northEast = n.north.east;
//                        if (n.north.west != null) n.northWest = n.north.west;
//                    }
//                    if (n.south != null) {
//                        if (n.south.east != null) n.southEast = n.south.east;
//                        if (n.south.west != null) n.southWest = n.south.west;
//                    }
//                }
//            }
//        }
        System.out.println("done making space");
        ArrayList<Node> path = aStarSearch(start,goal);
        //PolyLinesJPanel polyLines = new PolyLinesJPanel(path);
        PolygonsJPanel polygonsJPanel = new PolygonsJPanel(polygonArrayList, XMAX, YMAX, path);
        frame.add( polygonsJPanel ); // add polygonsJPanel to frame
        //frame.add( polyLines );
        frame.setSize( 1000, 500 ); // set frame size
        frame.setVisible( true ); // display frame
    } // end main

    public static ArrayList<Node> aStarSearch(Node start, Node goal) {
        ArrayList<Node> frontier = new ArrayList<>();
        // key is Node.xyString, value is cost
        Hashtable<String, Double> visited = new Hashtable<>();
        int gcost = 0;

        frontier.add(start);

        while (!frontier.isEmpty()) {
            Node current = getLowestEvaluation(frontier);
            frontier.remove(current);
            if (current.isGoal) {
                return reconstructPath(current);
            }
            gcost++;
            ArrayList<Node> neighbors = getNeighbors(current);
            for (Node child:
                 neighbors) {
                double f = child.getGoalDistance() + gcost;
                if (!visited.containsKey(child.xyString)
                        || f < visited.get(child.xyString)) {
                    visited.put(child.xyString, f);
                    visited.replace(child.xyString, f);
                    child.setParent(current);
                    child.evaluation = f;
                    frontier.add(child);
                }
            }
        }

        return null;
    }
    public static ArrayList<Node> aSearch(Node start, Node goal) {
        ArrayList<Node> openList = new ArrayList<Node>();
        ArrayList<Node> closedList = new ArrayList<Node>();

        double gCost = 0;

        start.evaluation = start.getGoalDistance();

        openList.add(start);

        while(!openList.isEmpty()) {
            Node currentNode = getLowestEvaluation(openList);
            if (currentNode.isGoal) {
                return reconstructPath(currentNode);
            }

            openList.remove(currentNode);
            System.out.println("size of openList: " + openList.size());
            System.out.println("size of closedList" + closedList.size());
            closedList.add(currentNode);

            ArrayList<Node> neighbors = getNeighbors(currentNode);
            if (neighbors.contains(null)) {
                System.out.println("suffering");
            }
            for (Node neighbor : neighbors) {
                if (neighbor != null) {
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighbor.evaluation = gCost + 1 + neighbor.getGoalDistance();
                        neighbor.setParent(currentNode);
                    } else if (neighbor.evaluation > gCost + 1 + neighbor.getGoalDistance()) {
                        neighbor.evaluation = gCost + 1 + neighbor.getGoalDistance();
                        neighbor.setParent(currentNode);
                    }
                }
            }
            neighbors.clear();
            gCost++;
        }
        System.out.println("something went wrong");
        return null;
    }
    public static Node getLowestEvaluation(ArrayList<Node> list) {
        Node lowest = list.get(0);
        for (Node node : list) {
            if (node.evaluation < lowest.evaluation) {
                lowest = node;
            }
        }
        return lowest;
    }

    public static ArrayList<Node> reconstructPath(Node node) {
        ArrayList<Node> path = new ArrayList<Node>();
        int i = 0;
        while (node.isStart != true) {
            path.add(node);
            node = node.getParent();
            System.out.println(i++);
        }
        path.add(node);
        System.out.println(path);
        return path;
    }

    public static ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        if (node.north != null) neighbors.add(node.north);
        if (node.south != null) neighbors.add(node.south);
        if (node.east != null) neighbors.add(node.east);
        if (node.west != null) neighbors.add(node.west);
//        if (node.northWest != null) neighbors.add(node.northWest);
//        if (node.northEast != null) neighbors.add(node.northEast);
//        if (node.southWest != null) neighbors.add(node.southWest);
//        if (node.southEast != null) neighbors.add(node.southWest);

        return neighbors;
    }
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
class PolyLinesJPanel extends JPanel {
    ArrayList<Node> nodesList;
    int[] xPoints;
    int[] yPoints;
    int nPoints;
    public PolyLinesJPanel(ArrayList<Node> nodes) {
        this.nodesList = nodes;
        this.nPoints = nodes.size();
        this.xPoints = new int[nodesList.size()];
        this.yPoints = new int[nodesList.size()];
        fillArrays(nodesList);
    }
    public void fillArrays(ArrayList<Node> nodes) {
        for (int i = 0; i<nodes.size(); i++) {
            xPoints[i] = nodes.get(i).getX();
            yPoints[i] = nodes.get(i).getY();
        }
    }
    public void paintComponent( Graphics g ) {
        super.paintComponent(g);
        g.drawPolyline(xPoints,yPoints,nPoints);
    }
}
class PolygonsJPanel extends JPanel{
    ArrayList<Polygon> polyList;
    int xMax;
    int yMax;
    ArrayList<Node> nodesList;
    int[] xPoints;
    int[] yPoints;
    int nPoints;
    public PolygonsJPanel(ArrayList<Polygon> pList, int x, int y, ArrayList<Node> nodes) {
        this.polyList = pList;
        this.xMax = x;
        this.yMax = y;
        this.nodesList = nodes;
        this.nPoints = nodes.size();
        this.xPoints = new int[nodesList.size()];
        this.yPoints = new int[nodesList.size()];
        fillArrays(nodesList);
    }
    public void fillArrays(ArrayList<Node> nodes) {
        for (int i = 0; i<nodes.size(); i++) {
            xPoints[i] = nodes.get(i).getX();
            yPoints[i] = nodes.get(i).getY();
        }
    }

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g ); // call superclass's paintComponent
        for (Polygon polygon : polyList) {
            g.fillPolygon(polygon);
        }
        g.drawPolyline(xPoints,yPoints,nPoints);
    } // end method paintComponent

} // end class PolygonsJPanel
