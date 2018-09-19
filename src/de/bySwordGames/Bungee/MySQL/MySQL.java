/**
 * Die Klasse heißt: MySQL.java
 * Die Klasse wurde am: 13.05.2017 | 01:23:59 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;

public class MySQL {
	
	private String host;
	private Integer port;
	private String datenbank;
	private String benutzername;
	private String passwort;
	public static Connection connection;
	
	@SuppressWarnings("deprecation")
	public MySQL(String host, Integer port, String datenbank, String benutzername, String passwort) {
		this.host = host;
		this.port = port;
		this.datenbank = datenbank;
		this.benutzername = benutzername;
		this.passwort = passwort;
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.datenbank + "?autoReconnect=true", this.benutzername, this.passwort);
			BungeeCord.getInstance().getConsole().sendMessage(Bungee.prefix + "§2Die §aMySQL-Verbindung §2wurde erfolgreich aufgebaut.");
			
			PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Bans(id INT AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Bis VARCHAR(100))");
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Mutes(id INT AUTO_INCREMENT PRIMARY KEY, UUID VARCHAR(100), Grund VARCHAR(100), Von VARCHAR(100), Bis VARCHAR(100))");
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void disconnect() {
		try {
			connection.close();
			BungeeCord.getInstance().getConsole().sendMessage(Bungee.prefix + "§2Die §aMySQL-Verbindung §2wurde erfolgreich geschlossen.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
