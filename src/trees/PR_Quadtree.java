package trees;

import java.util.List;
import java.util.ArrayList;

public class PR_Quadtree {
    private static class Node {
        double x;
        double y;
        Node NW;
        Node NE;
        Node SE;
        Node SW;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            this.NW = null;
            this.NE = null;
            this.SE = null;
            this.SW = null;
        }
    }

    private static Node root;

    public PR_Quadtree(List<double[]> points) {
        PR_Quadtree.root = buildTree(points, 0, 0, 0, 100000, 100000);
    }

    private Node buildTree(List<double[]> points, int depth, double x, double y, double width, double height) {
        if (points.isEmpty()) {
            return null;
        }
        if (points.size() == 1) {
            double[] point = points.get(0);
            return new Node(point[0], point[1]);
        }

        int medianIndex = points.size() / 2;

        double[] medianPoint = getMedian(points, depth);
        Node node = new Node(medianPoint[0], medianPoint[1]);

        List<double[]> NWPoints = new ArrayList<>();
        List<double[]> NEPoints = new ArrayList<>();
        List<double[]> SEPoints = new ArrayList<>();
        List<double[]> SWPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
        	 if (i == medianIndex) {
                 continue;
             }
            double[] point = points.get(i);
            if (point[0] < x + width/2 && point[1] < y + height/2) {
                NWPoints.add(point);
            } else if (point[0] > x + width/2 && point[1] < y + height/2) {
                NEPoints.add(point);
            } else if (point[0] >= x + width/2 && point[1] >= y + height/2) {
                SEPoints.add(point);
            } else {
                SWPoints.add(point);
            }
        }

        double newWidth = width/2;
        double newHeight = height/2;

        node.NW = buildTree(NWPoints, depth + 1, x, y, newWidth, newHeight);
        node.NE = buildTree(NEPoints, depth + 1, x + newWidth, y, newWidth, newHeight);
        node.SE = buildTree(SEPoints, depth + 1, x + newWidth, y + newHeight, newWidth, newHeight);
        node.SW = buildTree(SWPoints, depth + 1, x, y + newHeight, newWidth, newHeight);

        return node;
    }

    private double[] getMedian(List<double[]> points,int depth) {
    	int dim = points.get(0).length;
    	int medianIndex = points.size() / 2;
        if (depth % dim == 0) {
            points.sort((a, b) -> Double.compare(a[0], b[0]));
        } else {
            points.sort((a, b) -> Double.compare(a[1], b[1]));
        }

        return points.get(medianIndex);
    }

    public boolean contains(double x, double y) {
        return searchContains(x, y, root);
    }
       private boolean searchContains(double x, double y, Node node) {
        if (node == null) {
            return false;
        }

         if (node.x == x && node.y == y) {
            return true;
        }

        if (x < node.x && y < node.y) {
            return searchContains(x, y, node.NW);
        } else if (x >= node.x && y < node.y) {
            return searchContains(x, y, node.NE);
        } else if (x >= node.x && y >= node.y) {
            return searchContains(x, y, node.SE);
        } else {
            return searchContains(x, y, node.SW);
        }
    }
       public static int searchPRTree(double[] points){
       	int depth=search(points[0],points[1],root,0);
       	return depth;
       }
       private static int search(double x, double y, Node node,int depth) {
           if (node == null) {
           	//System.out.println("Didnt Foundddd  "+depth);
               return depth;
           }

            if (node.x == x && node.y == y) {
          	//System.out.println("Found itt  "+depth);
               return depth;
           }

           if (x < node.x && y < node.y) {
        	   depth=depth+1;
               return search(x, y, node.NW,depth);
           } else if (x >= node.x && y < node.y) {
        	   depth=depth+1;
               return search(x, y, node.NE,depth);
           } else if (x >= node.x && y >= node.y) {
        	   depth=depth+1;
               return search(x, y, node.SE,depth);
           } else {
        	   depth=depth+1;
               return search(x, y, node.SW,depth);
           }
       }


}

