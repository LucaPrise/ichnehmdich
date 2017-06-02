package eu.unrealdev.clashofclans.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import org.bukkit.Bukkit;

import eu.unrealdev.clashofclans.COCMain;

public class AsyncMySQL {

	
	private ExecutorService executor;
	private COCMain plugin = COCMain.getInstance();
	private MySQL mySQL;
	
	
	public AsyncMySQL(String host, int port, String user, String password, String database) {
		
		try {
			
			this.mySQL = new MySQL(host, port, user, password, database);
			this.executor = Executors.newCachedThreadPool();
			System.out.println("Successfully connected to MySQL Database!");
		} catch(Exception ex) {
			System.out.println("Error occured while connecting to MySQL Database!");
			System.out.println("MySQL Error: " + ex.getMessage());
		}
	}
	
	
	public void update(PreparedStatement statement) {
		
		executor.execute(() -> this.mySQL.queryUpdate(statement));
	}
	
	
	public void update(String statement) {
		
		executor.execute(() -> this.mySQL.queryUpdate(statement));
	}
	
	
	public void query(PreparedStatement statement, Consumer<ResultSet> consumer) {
		
		executor.execute(() -> {
			
			ResultSet result = this.mySQL.query(statement);
			Bukkit.getScheduler().runTask(this.plugin, () -> consumer.accept(result));
		});
	}
	
	
	public void query(String statement, Consumer<ResultSet> consumer) {
		
		executor.execute(() -> {
			
			ResultSet result = this.mySQL.query(statement);
			Bukkit.getScheduler().runTask(this.plugin, () -> consumer.accept(result));
		});
	}
	
	
	public PreparedStatement prepare(String query) {
		
		try {
			
			return this.mySQL.getConnection().prepareStatement(query);
			
		} catch(Exception ex) {
			
			System.out.println("MySQL Error: " + ex.getMessage());
		}
		
		return null;
	}
	
	
	public MySQL getMySQL() {
	
		return mySQL;
	}
	
	public static class MySQL {
		
		private String host, user, password, database;
		private int port;
		
		private Connection connection;
		
		public MySQL(String host, int port, String user, String password, String database) throws Exception {
			
			this.host = host;
			this.port = port;
			this.user = user;
			this.password = password;
			this.database = database;
			
			this.openConnection();
		}
		
		
		public void queryUpdate(String query) {
			
			checkConnection();
			
			try (PreparedStatement statement = this.connection.prepareStatement(query)) {
				
				queryUpdate(statement);
				
			} catch(Exception ex) {
				
				System.out.println("MySQL Error: " + ex.getMessage());
			}
		}
		
		
		public void queryUpdate(PreparedStatement statement) {
			
			checkConnection();
			
			try {
				
				statement.executeUpdate();
				
			} catch (Exception ex) {
			
				System.out.println("MySQL Error: " + ex.getMessage());
			} finally {
				
				try {
					
					statement.close();
					
				} catch (Exception ex) {
				
					System.out.println("MySQL Error: " + ex.getMessage());
				}
			}
		}
		
		
		public ResultSet query(String query) {
			
			checkConnection();
			
			try {
				
				return query(this.connection.prepareStatement(query));
				
			} catch(Exception ex) {
				
				System.out.println("MySQL Error: " + ex.getMessage());
			}
			
			return null;
		}
		
		
		public ResultSet query(PreparedStatement statement) {
			
			checkConnection();
			
			try {
				
				return statement.executeQuery();
				
			} catch(Exception ex) {
				
				System.out.println("MySQL Error: " + ex.getMessage());
			}
			
			return null;
		}
		
		
		public Connection getConnection() {
		
			return this.connection;
		}
		
		
		public void checkConnection() {
			
			try {
				
				if(this.connection == null || !this.connection.isValid(10) || this.connection.isClosed()) openConnection();
				
			} catch(Exception ex) {
				
				System.out.println("MySQL Error: " + ex.getMessage());
			}
		}
		
		
		public Connection openConnection() throws Exception {
			
			Class.forName("com.mysql.jdbc.Driver");
			return this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
		}
		
		public void closeConnection() {
			
			try {
				
				this.connection.close();
				
			} catch (SQLException e) {
				
				System.out.println("MySQL Error: " + e.getMessage());
				
			} finally {
				
				this.connection = null;
			}
		}
	}
}
