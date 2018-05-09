public class Point {
    double x;
    double y;

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
