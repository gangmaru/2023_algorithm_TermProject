package Algorithm;

import java.sql.*;
import java.util.Scanner;
import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class algorithm1 {

	static final int Count_Vertice = 7;
	static int [][] Seoul = new int [Count_Vertice][Count_Vertice];
	static int [][] Busan = new int [Count_Vertice][Count_Vertice];
	static int [][] Gyeongju = new int [Count_Vertice][Count_Vertice];
	
	static int[][] D = new int[Count_Vertice][Count_Vertice];
	static int[][] P = new int[Count_Vertice][Count_Vertice];
	
	public static void floyd(int array[][]) {
	    for (int i = 0; i < Count_Vertice; i++)
	        for (int j = 0; j < Count_Vertice; j++) {
	            P[i][j] = -1;
	            D[i][j] = array[i][j];
	        }
	    for (int k = 0; k < Count_Vertice; k++)
            for (int i = 0; i < Count_Vertice; i++)
                for (int j = 0; j < Count_Vertice; j++)
                    if (D[i][j] > D[i][k] + D[k][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                        P[i][j] = k;
                    }
	}
    
	public static void printPath(int a, int b) {
        if (P[a][b] != -1) {
            printPath(a, P[a][b]);
            System.out.print(P[a][b] + " ");
            printPath(P[a][b], b);
        }
    }
	
    
	public static void main(String args[])
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/algorithm";
			String user = "root", passwd = "****";
			con = DriverManager.getConnection(url, user, passwd);
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//distance
		
		Statement stmt=null;
		ResultSet rs = null;
		PreparedStatement pstm=null;
		Scanner s = new Scanner(System.in);
		int i=0;
		int j=0;
		int count=0;
		
		try {
		    String selectSql = "SELECT node_x, node_y FROM node_inf WHERE n_id = ?";
		    String updateSql = "UPDATE distance SET result = ? WHERE d_id = ?";

		    pstm = con.prepareStatement(selectSql);
		    PreparedStatement updatePstm = con.prepareStatement(updateSql);

		    for (i = 0; i < 7; i++) {
		        pstm.setInt(1, i);
		        rs = pstm.executeQuery();
		        System.out.println(i+" start");
		        // Check if there is a result for the given n_id
		        if (rs.next()) {
		            int x1 = rs.getInt("node_x");
		            int y1 = rs.getInt("node_y");

		            for (j = 0; j < 7; j++) {
		                pstm.setInt(1, j);
		                rs = pstm.executeQuery();
		                System.out.println(j+1);
		                // Check if there is a result for the given n_id
		                if (rs.next()) {
		                    int x2 = rs.getInt("node_x");
		                    int y2 = rs.getInt("node_y");		                   
		                    int distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		                    distance = (int) Math.sqrt(distance);
		                    System.out.println("x1 : "+x1+", y1: "+y1+"\nx2: "+x2+", y2: "+y2+", distance: "+distance);
		                    
		                    if((i==5&&j==6)||(i==6&&j==5)||(i==2&&j==3)||(i==3&&j==2)||(i==6&&j==1)||(i==1&&j==6))
		                    {
		                    	distance = 20000;
		                    }
		                    Seoul[i][j]=distance;
		                    updatePstm.setInt(1, distance);
		                    updatePstm.setInt(2, count);
		                    updatePstm.executeUpdate();

		                    count++;
		                }
		            }
		        }
		    }
		    for (i = 7; i < 14; i++) {
		        pstm.setInt(1, i);
		        rs = pstm.executeQuery();
		        System.out.println(i+" start");
		        // Check if there is a result for the given n_id
		        if (rs.next()) {
		            int x1 = rs.getInt("node_x");
		            int y1 = rs.getInt("node_y");

		            for (j = 7; j < 14; j++) {
		                pstm.setInt(1, j);
		                rs = pstm.executeQuery();
		                System.out.println(j+1);
		                // Check if there is a result for the given n_id
		                if (rs.next()) {
		                    int x2 = rs.getInt("node_x");
		                    int y2 = rs.getInt("node_y");
		                   
		                    int distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		                    distance = (int) Math.sqrt(distance);
		                    Busan[i-7][j-7]=distance;
		                    System.out.println("x1 : "+x1+", y1: "+y1+"\nx2: "+x2+", y2: "+y2+", distance: "+distance);
		                    if((i-7==5&&j-7==6)||(i-7==6&&j-7==5)||(i-7==2&&j-7==3)||(i-7==3&&j-7==2)||(i-7==6&&j-7==1)||(i-7==1&&j-7==6))
		                    {
		                    	distance = 20000;
		                    }
		                    updatePstm.setInt(1, distance);
		                    updatePstm.setInt(2, count);
		                    updatePstm.executeUpdate();

		                    count++;
		                }
		            }
		        }
		    }
		    for (i = 14; i < 21; i++) {
		        pstm.setInt(1, i);
		        rs = pstm.executeQuery();
		        System.out.println(i+" start");
		        // Check if there is a result for the given n_id
		        if (rs.next()) {
		            int x1 = rs.getInt("node_x");
		            int y1 = rs.getInt("node_y");

		            for (j = 14; j < 21; j++) {
		                pstm.setInt(1, j);
		                rs = pstm.executeQuery();
		                System.out.println(j+1);
		                // Check if there is a result for the given n_id
		                if (rs.next()) {
		                    int x2 = rs.getInt("node_x");
		                    int y2 = rs.getInt("node_y");
		                   
		                    int distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		                    distance = (int) Math.sqrt(distance);
		                    if((i-14==5&&j-14==6)||(i-14==6&&j-14==5)||(i-14==2&&j-14==3)||(i-14==3&&j-14==2)||(i-14==6&&j-14==1)||(i-14==1&&j-14==6))
		                    {
		                    	distance = 20000;
		                    }
		                    Gyeongju[i-14][j-14]=distance;
		                    System.out.println("x1 : "+x1+", y1: "+y1+"\nx2: "+x2+", y2: "+y2+", distance: "+distance);
		                    updatePstm.setInt(1, distance);
		                    updatePstm.setInt(2, count);
		                    updatePstm.executeUpdate();

		                    count++;
		                }
		            }
		        }
		    }
		    // Close the PreparedStatement after use
		    updatePstm.close();

		} catch (SQLException e1) {
		    e1.printStackTrace();
		} finally {
		    // Close the ResultSet and PreparedStatement
		    try {
		        if (rs != null) rs.close();
		        if (pstm != null) pstm.close();
		        if (con != null && !con.isClosed()) con.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		 

		// database operations ...
		try {
		if (con != null && !con.isClosed()) con.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}

	}
}
