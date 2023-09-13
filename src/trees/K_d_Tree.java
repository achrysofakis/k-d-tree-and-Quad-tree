package trees;

import java.util.List;
import java.util.ArrayList;

public class K_d_Tree {
    private static class Node {
        double[] point;
        Node left;
        Node right;

        public Node(double[] point) {
            this.point = point;
            this.left = null;
            this.right = null;
        }
    }

    private static Node root;

    public K_d_Tree(List<double[]> points) {
        K_d_Tree.root = buildTree(points, 0);
    }

    private Node buildTree(List<double[]> points, int depth) {
        if (points.isEmpty()) {
            return null;
        }
        
        int dim = points.get(0).length;
        int medianIndex = points.size() / 2;
        double[] medianPoint = getMedian(points, depth);
        Node node = new Node(medianPoint);

        List<double[]> leftPoints = new ArrayList<>();
        List<double[]> rightPoints = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            if (i == medianIndex) {
                continue;
            }
            double[] point = points.get(i);
            if (point[depth % dim] < medianPoint[depth % dim]) {
                leftPoints.add(point);
            } else {
                rightPoints.add(point);
            }
        }

        node.left = buildTree(leftPoints, depth + 1);
        node.right = buildTree(rightPoints, depth + 1);

        return node;
    }

    private double[] getMedian(List<double[]> points, int depth) {
        int dim = points.get(0).length;
        int medianIndex = points.size() / 2;

        points.sort((a, b) -> Double.compare(a[depth % dim], b[depth % dim]));

        return points.get(medianIndex);
    }
    public static int searchKDTree(double[] point) {
    	//Δινουμε σαν ορίσματα την ρίζα το σημείο που ψάχνουμε και το βαθος=0 για να αρχισουμε την αναζήτηση μας απο την αρχή του δενρου 
        int depth = searchNode(root, point, 0);
        return depth;
    }

    private static int searchNode(Node node, double[] point, int depth) {
        if (node == null) {
            //System.out.println("These points dindt exist in our K_d_tree with depth:  "+depth);
            return depth;
        }
        if (node.point[0] == point[0] && node.point[1] == point[1]) {
            //System.out.println("Found these points in our K_d_tree with depth:  "+depth);
            return depth;
        }
        int dim = node.point.length;
        if (point[depth % dim] < node.point[depth % dim]) {
            return searchNode(node.left, point, depth + 1);
        } else {
            return searchNode(node.right, point, depth + 1);
        }
    }



}