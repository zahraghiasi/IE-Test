import java.util.Scanner;

class Point{

    int x, y;

    Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
//boundary or rectangle
class Node {

    int x1, y1, x2, y2;

    public Node(int x1, int y1, int x2, int y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}

class QuadTree{

    int n;
    // n:0 -> 0 point in quadtree
    // n:1 -> 1 point in quadtree
    // n:2 -> more than 2 point
    Node boundary;
    Point tempPoint ;

    Node sw;
    Node se;
    Node nw;
    Node ne;
    QuadTree swqt;
    QuadTree seqt;
    QuadTree nwqt;
    QuadTree neqt;

    QuadTree(Node node){
        boundary = new Node(node.x1, node.y1, node.x2, node.y2);
        sw = null;
        se = null;
        nw = null;
        ne = null;
        swqt = null;
        seqt = null;
        nwqt = null;
        neqt = null;
        n = 0;
        tempPoint = null;
    }
}

public class Main {

    static void Subdivide(QuadTree qt){
        int x1 = qt.boundary.x1;
        int x2 = qt.boundary.x2;
        int y1 = qt.boundary.y1;
        int y2 = qt.boundary.y2;
        qt.sw = new Node(x1, y1, (x2 - x1)/2 + x1, (y2 - y1)/2 + y1);
        qt.swqt = new QuadTree(qt.sw);
        qt.se = new Node((x2 - x1)/2 + x1, y1, x2, (y2 - y1)/2 + y1);
        qt.seqt = new QuadTree(qt.se);
        qt.nw = new Node(x1, (y2 - y1)/2 + y1, (x2 - x1)/2 + x1, y2);
        qt.nwqt = new QuadTree(qt.nw);
        qt.ne = new Node((x2 - x1)/2 + x1, (y2 - y1)/2 + y1, x2, y2);
        qt.neqt = new QuadTree(qt.ne);
    }

    static void insert(Point p, QuadTree qt){

        if (p.x >= qt.boundary.x1 && p.y >= qt.boundary.y1 && p.x <= qt.boundary.x2 && p.y <= qt.boundary.y2) {

            if (qt.n == 0) {
                qt.tempPoint = new Point(p.x, p.y);
                qt.n++;
            }
            else {
                int x1 = qt.boundary.x1;
                int x2 = qt.boundary.x2;
                int y1 = qt.boundary.y1;
                int y2 = qt.boundary.y2;

                if (qt.n == 1) {
                    Subdivide(qt);
                    qt.n++;
                    if (qt.tempPoint.x >= x1 && qt.tempPoint.y >= y1 && qt.tempPoint.x <= (x2 - x1)/2 + x1 && qt.tempPoint.y <= (y2 - y1)/2 + y1)
                        insert(qt.tempPoint, qt.swqt);
                    else if (qt.tempPoint.x >= (x2 - x1)/2 + x1 && qt.tempPoint.y >= y1 && qt.tempPoint.x <= x2 && qt.tempPoint.y <= (y2 - y1)/2 + y1)
                        insert(qt.tempPoint, qt.seqt);
                    else if (qt.tempPoint.x >= x1 && qt.tempPoint.y >= (y2 - y1)/2 + y1 && qt.tempPoint.x <= (x2 - x1)/2 + x1 && qt.tempPoint.y <= y2)
                        insert(qt.tempPoint, qt.nwqt);
                    else if (qt.tempPoint.x >= (x2 - x1)/2 + x1 && qt.tempPoint.y >= (y2 - y1)/2 + y1 && qt.tempPoint.x <= x2 && qt.tempPoint.y <= y2)
                        insert(qt.tempPoint, qt.neqt);
                    qt.tempPoint = null;
                }
                if (p.x >= x1 && p.y >= y1 && p.x <= (x2 - x1)/2 + x1 && p.y <= (y2 - y1)/2 + y1)
                    insert(p, qt.swqt);
                else if (p.x >= (x2 - x1)/2 + x1 && p.y >= y1 && p.x <= x2 && p.y <= (y2 - y1)/2 + y1)
                    insert(p, qt.seqt);
                else if (p.x >= x1 && p.y >= (y2 - y1)/2 + y1 && p.x <= (x2 - x1)/2 + x1 && p.y <= y2)
                    insert(p, qt.nwqt);
                else if (p.x >= (x2 - x1)/2 + x1 && p.y >= (y2 - y1)/2 + y1 && p.x <= x2 && p.y <= y2)
                    insert(p, qt.neqt);
            }
        }
    }

    static boolean search(Point p, QuadTree qt){
        if (p == null)
            return false;
        if (p.x >= qt.boundary.x1 && p.y >= qt.boundary.y1 && p.x <= qt.boundary.x2 && p.y <= qt.boundary.y2) {

            if (qt.n == 0) {
                return false;
            } else {
                if (qt.n == 1 && qt.tempPoint.x == p.x && qt.tempPoint.y == p.y) {
                    return true;
                }
                if (qt.n > 1) {
                    return (search(p, qt.swqt) || search(p, qt.seqt) || search(p, qt.nwqt) || search(p, qt.neqt));
                }
            }
        }
        return false;
    }

    static int area(Point swp, Point nep, QuadTree qt){

        if(!(swp.x > qt.boundary.x1 && swp.x > qt.boundary.x2 && nep.x < qt.boundary.x1 && nep.x < qt.boundary.x2) ||
                !(swp.y > qt.boundary.y1 && swp.y > qt.boundary.y2 && nep.y < qt.boundary.y1 && nep.y < qt.boundary.x2)){
            if (qt.n == 0){
                return 0;
            }else if (qt.n == 1 && qt.tempPoint != null && swp.x <= qt.tempPoint.x && swp.y <= qt.tempPoint.y && nep.x >= qt.tempPoint.x && nep.y >= qt.tempPoint.y){
                return 1;
            }else if (qt.swqt != null && qt.seqt != null && qt.nwqt != null && qt.neqt != null){
                return area(swp, nep, qt.swqt) + area(swp, nep, qt.seqt) + area(swp, nep, qt.nwqt) + area(swp, nep, qt.neqt);
            }else
                return 0;
        }else{
            return 0;
        }
    }


    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        String commands = scanner.next();
        Node root = new Node(-100000,-100000,100000,100000);
        QuadTree qt = new QuadTree(root);

        boolean i = true;
        while (i){
            switch (commands){
                case "Insert":{
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    Point p = new Point(x,y);
                    insert(p, qt);
                    break;
                }
                case "Search":{
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    Point p = new Point(x,y);
                    boolean b = search(p, qt);
                    if (b)
                        System.out.println("TRUE");
                    else
                        System.out.println("FALSE");
                    break;
                }
                case "Area":{
                    int x1 = scanner.nextInt();
                    int y1 = scanner.nextInt();
                    int x2 = scanner.nextInt();
                    int y2 = scanner.nextInt();
                    Point swp = new Point(x1,y1);
                    Point nep = new Point(x2,y2);
                    System.out.println(area(swp, nep, qt));
                    break;
                }
            }
            if (scanner.hasNext()){
                commands = scanner.next();
            }else {
                i = false;
            }
        }
    }
}
