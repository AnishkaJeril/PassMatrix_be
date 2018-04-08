package passmatrix;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.PreparedStatement;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
public class Send_HTTP_Post_Request {
public static void main(String[] args) {
	String value="";
	if(value=="register") {
	try {
		String status=Send_HTTP_Post_Request.call_me();
		
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
	else if(value=="login") {
		try {
			Send_HTTP_Post_Request.call_me();
			} catch (Exception e) {
			e.printStackTrace();
			}
			}
	}
	 private static void register() {
	// TODO Auto-generated method stub
	
}
	public static String call_me() throws Exception {
		 ServerSocket sr=new ServerSocket(4500);
		 System.out.println("listening for connection to port 4500");
		 Socket cn=sr.accept(); 
		 System.out.println("connection established");
		 System.out.println(cn.getLocalAddress());
	    URL url = new URL("http://localhost:4500");
	 
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    conn.setDoOutput(true);
		    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    StringBuilder sb = new StringBuilder();
	    for (int c; (c = in.read()) >= 0;)
	        sb.append((char)c);
	    String response = sb.toString();
	    System.out.println(response);
	    JSONObject myResponse = new JSONObject(response.toString());
	    
	   
	    JSONObject form_data = myResponse.getJSONObject("form");
	    System.out.println("username- "+form_data.getString("username"));
	    System.out.println("image- "+form_data.getString("image"));
	    System.out.println("cell- "+form_data.getString("cell"));
	     String status= connectionToMySQL(form_data.getString("username"),form_data.getString("username"),form_data.getString("username"));
	    cn.close();
	    sr.close();
	    return status;
	}
	 
		
	 public static String connectionToMySQL(String s_username, String s_image, String s_cell) {
	 	try {
	 		Connection con =DriverManager.getConnection("jdbc:mysql://localhost/passmatrix", "root", "root");
	 		System.out.println("success");
	 		String query = " insert into pass (username, image, cell)"
	 		        + " values (?, ?, ?)";

	 		      // create the mysql insert preparedstatement
	 		      PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
	 		      preparedStmt.setString (1, s_username);
	 		      preparedStmt.setString (2, s_image);
	 		      preparedStmt.setString(3, s_cell);	      
	 		      preparedStmt.execute();
	 		      System.out.println("Insert complete");
	 		      con.close();
	 		      return "success";
	 	}catch(Exception e) {
	 		System.out.println("error");
	 		return "error";
	 	}
	 }
}