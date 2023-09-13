package pck;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import trees.*;

public class App {
	static final Integer LOW=0;//This could be our lowest key
	static final Integer N=(65536);//N-1 could be our biggest key(2^16)
	static int[] M={200, 500, 1000, 10000, 30000, 50000, 70000, 100000};//The amount of inputs(x,y)
	private static int sum_depth=0,sum_NEdepth=0,sum_depth_PR=0,sum_NEdepth_PR=0; //These are used to calculate the MO ov our depths
	private static int i;
	private static int j;
	static java.util.Random randomGenerator = new java.util.Random();
	static List<double[]> points = new ArrayList<>();//points(x,y)
	static List<double[]> NEpoints=new ArrayList<>();//Not exists points for our searches
	public static void main(String[] args) throws IOException {
		for(j=0; j<M.length; j++) {	//M.length
			sum_depth=0;
			sum_NEdepth=0;
			sum_depth_PR=0;
			sum_NEdepth_PR=0;
			//Create some random no unique keys double the amount of M[j] because we have x and y in every M
			int[] randomInts = randomGenerator.ints(LOW,N).limit(2*M[j]).toArray();
			
			for(i=0; i<2*M[j]; i=i+2) {
				//Create our points for our trees (x,y)
				points.add(new double[] {randomInts[i], randomInts[i+1]});
				
			}
			//Create our KD_Tree
			@SuppressWarnings("unused")
			K_d_Tree kdTree = new K_d_Tree(points);
			//Create our PR_QuadTree
			@SuppressWarnings("unused")
			PR_Quadtree quadTree = new PR_Quadtree(points);
			
			//Here we are doing our searches
			for(i=0; i<100; i++){
				//Create 100 points that didnt exist in our trees
				NEpoints.add(new double[] {randomInts[i+1],randomInts[i]});
				//This is if our points exist in our trees
				int depth=K_d_Tree.searchKDTree(points.get(i));
				int PRdepth=PR_Quadtree.searchPRTree(points.get(i));
				//This is if our points dont Exist in our trees
				int NEdepth=K_d_Tree.searchKDTree(NEpoints.get(i));
				int NEdepth_PR=PR_Quadtree.searchPRTree(NEpoints.get(i));
				sum_depth=sum_depth+depth;
				sum_NEdepth=sum_NEdepth+NEdepth;
				sum_depth_PR=sum_depth_PR+PRdepth;
				sum_NEdepth_PR=sum_NEdepth_PR+NEdepth_PR;
			}
			//Take the Mo's we are needing
			double MO_depth_KD=(double)sum_depth/100;
			double MO_NEdepth_KD=(double)sum_NEdepth/100;
			double MO_depth_PR=(double)sum_depth_PR/100;
			double MO_NEdepth_PR=(double)sum_NEdepth_PR/100;
			//Akrivia 2 dekadikon psifion
			String formatted_MO_depth_KD = String.format("%.2f", MO_depth_KD);
			String formatted_MO_NEdepth_KD=String.format("%.2f",MO_NEdepth_KD);
			String formatted_MO_depth_PR = String.format("%.2f", MO_depth_PR);
			String formatted_MO_NEdepth_PR=String.format("%.2f",MO_NEdepth_PR);
			//Ektipwnoume ta apotelesmata mas
			System.out.println("MO of depth if the point we are looking for exists in our KD_Tree is  "+formatted_MO_depth_KD+"  if it doesnt exists:  "+formatted_MO_NEdepth_KD+"  for  M = "+M[j]);
			System.out.println("MO of depth if the point we are looking for exists in our PR_QuadTree is  "+formatted_MO_depth_PR+"  if it doesnt exists:  "+formatted_MO_NEdepth_PR+"  for  M = "+M[j]+"\n");

		}

		

	}

	

}
