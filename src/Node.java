import java.awt.*;
import java.util.ArrayList;

public class Node {
    private int x;
    private int y;
    public Node(int xp, int yp) {
        setX(xp);
        setY(yp);
    }

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
