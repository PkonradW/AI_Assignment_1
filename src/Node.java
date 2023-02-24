import java.awt.*;
import java.util.ArrayList;
import java.lang.Math;

public class Node {
    private int x;
    private int y;
    String xyString;

    private double goalDistance;
    private double startDistance;
    public double evaluation;

    boolean isStart = false;
    boolean isGoal = false;

    Node north = null;
    Node south = null;
    Node east = null;
    Node west = null;
    Node northEast = null;
    Node northWest = null;
    Node southEast = null;
    Node southWest = null;
    Node parent = null;

    public Node(int xp, int yp) {
        setX(xp);
        setY(yp);
        xyString = Integer.toString(xp) + Integer.toString(yp);
    }
    public double getGoalDistance() {
        return goalDistance;
    }

    public void setGoalDistance(Node goal) {
        int gx = goal.getX();
        int gy = goal.getY();
        int dx = Math.abs(this.getX() - gx);
        int dy = Math.abs(this.getY() - gy);
        this.goalDistance = Math.sqrt( (dx*dx) + (dy*dy) );
    }

    public double getStartDistance() {
        return startDistance;
    }

    public void setStartDistance(Node start) {
        int gx = start.getX();
        int gy = start.getY();
        int dx = Math.abs(this.getX() - gx);
        int dy = Math.abs(this.getY() - gy);
        this.startDistance = Math.sqrt( (dx*dx) + (dy*dy) );
    }

    public Node getParent() { return parent; }
    public void setParent(Node parent) { this.parent = parent;}
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
