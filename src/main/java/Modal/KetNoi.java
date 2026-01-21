package Modal;

import java.sql.Connection;
import java.sql.DriverManager;

public class KetNoi {
	public Connection cn;
	public void ketnoi() throws Exception{
	   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	   cn= DriverManager.getConnection("jdbc:sqlserver://ADMIN-PC:1433;databaseName=QlSach;user=sa; password=12345");
	   System.out.println("Da ket noi");
	}
}
