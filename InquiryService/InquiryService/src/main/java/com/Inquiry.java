package com;
import java.sql.*;


public class Inquiry {

	//A common method to connect to the DataBase
		private Connection connect()
		 {
			Connection con = null;
		 
			try
			{
				
				Class.forName("com.mysql.jdbc.Driver");

				//Details of the database
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogriddb", "root", "");
			}
		 catch (Exception e)
			{
			 e.printStackTrace();
			 }

			 return con;

		 } 

		
		
		//read method
		
				public String readInquiries()
				{
					String output="";
					
					try {
						
						Connection con = connect();
						
						if(con==null) {
							return "Error while connecting to the database for reading  inquiries";
						}
						//creating a table using html to display the inquires
						
						output ="<table border ='1'><tr><th>Inquiry Title</th><th>Inquiry Description</th><th>Contact Number</th><th>Update</th><th>Remove</th></tr>";
						
						String query = "select * from inquiry";
						Statement stmt = con.createStatement();
						
						ResultSet rs = stmt.executeQuery(query);
						
						//go through all the rows in the result set using a while loop
						
						while(rs.next()) 
						{
							
							String inquiryID = Integer.toString(rs.getInt("inquiryID"));
							String inquiryTitle=rs.getString("inquiryTitle");
							String inquiryDesc =rs.getString("inquiryDesc");
							String contactNum=rs.getString("contactNum");
							
							//add to the above created table
							

							output += "<tr><td>"+inquiryTitle+"</td>";
							output += "<td>"+inquiryDesc+"</td>";
							output += "<td>"+contactNum+"</td>";
							
							// buttons
							output += "<td><input name='btnUpdate' type='button' value='Update' "
							+ "class='btnUpdate btn btn-secondary' data-inquiryid='" + inquiryID + "'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' "
							+ "class='btnRemove btn btn-danger' data-inquiryid='" + inquiryID + "'></td></tr>";
							
							
						}
						con.close();
						
						//completing the created table
						output += "<table>";
						}
						catch (Exception e){
							output +="Error while reading the inquiries";
							System.err.println(e.getMessage());
							
						}
				
					return output;
				}
		
		//Create Insert method
				
				public String insertInquiry(String title,String description, String contact) {
					String output ="";
					
					try 
					{
						Connection con = connect();
						
						if(con ==null)
						{
							return "Error while connecting to the database for inserting.";
						}
						
						//creating prepared statement
						String query = "insert into inquiry (`inquiryID`,`inquiryTitle`,`inquiryDesc`,`contactNum`)" + " values (?, ?, ?, ?)";
						
						PreparedStatement preparedStmt =con.prepareStatement(query);
						
						//binding values
						preparedStmt.setInt(1, 0);
						preparedStmt.setString(2, title);
						preparedStmt.setString(3, description);
						preparedStmt.setString(4, contact);
						
						//execute the statements
						preparedStmt.execute();
						con.close();
						
						String newInquries = readInquiries();
						output = "{\"status\":\"success\", \"data\": \"" + newInquries + "\"}";
					}
					catch(Exception e) {
						output = "{\"status\":\"error\", \"data\":\"Error while inserting the inquiry.\"}";
						System.err.println(e.getMessage());
					}
					
					return output;
					
				}
				
				
				
				//Update inquiry method
				
				public String updateInquiry(String ID,String title, String description,String contact) {
					String output ="";
					
					
					try 
					{
						Connection con = connect();
						
						if(con==null) {
							return "Error While connecting to the database for updating";
						}
						
						//create prepared statement
						
						String query ="UPDATE inquiry SET inquiryTitle=?,inquiryDesc=?,contactNum=? WHERE inquiryID=?";
						
						PreparedStatement preparedStmt = con.prepareStatement(query);
						
						//bind values	
						preparedStmt.setString(1, title);
						preparedStmt.setString(2, description);
						preparedStmt.setString(3, contact);
						preparedStmt.setInt(4, Integer.parseInt(ID));
						
						//execute statement
						preparedStmt.execute();
						con.close();
						
						String newInquries = readInquiries();
		
						output = "{\"status\":\"success\", \"data\": \"" + 
								newInquries + "\"}";
					}
					catch(Exception e)
					{
						output = "{\"status\":\"error\", \"data\":\"Error while updating the inquiry\"}";
						System.err.println(e.getMessage());
					}
					
					return output;
					
				}
				
				//Delete Inquiry
				
				public String deletInquiry(String inquiryID) {
					String output="";
					
					try {
						Connection con =connect();
						
						if(con==null) {
							return"Error while connecting to the database for deleting";
						}
						
						//create prepared statement
						
						String query ="DELETE from inquiry where inquiryID=?";
						
						PreparedStatement preparedStmt =con.prepareStatement(query);
						
						//bindValues
						preparedStmt.setInt(1, Integer.parseInt(inquiryID));
						
						//execute the statement
						preparedStmt.execute();
						con.close();
						
						String newInquries = readInquiries();
						
						output = "{\"status\":\"success\", \"data\": \"" + newInquries + "\"}";
					}
					catch(Exception e)
					{
						output = "{\"status\":\"error\", \"data\":\"Error while Deleting the inquiry\"}";
						System.err.println(e.getMessage());
					}
					
					return output;
					
				}
			
}