package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	private DataSource dataSource;
	static Database database;
	
	public Database(String jndiname) throws SQLException {
		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/"+jndiname);
		}
			catch (NamingException e) {
				throw new SQLException(jndiname+" is missing in JNDI ! :"+e.getMessage());			
			}
		}
	

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException {
		if(DBStatic.mysql_pooling==false) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return(DriverManager.getConnection("jdbc:mysql://"
		+DBStatic.mysql_host+"/"+DBStatic.mysql_db,
		DBStatic.mysql_username,DBStatic.mysql_password));
			
		}
		else {
			if (database==null) {
				database=new Database("jdbc/db");
			}
			return (database.getConnection());
		}
	}
	
}
