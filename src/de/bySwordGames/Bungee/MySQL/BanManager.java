/**
 * Die Klasse heißt: BanManager.java
 * Die Klasse wurde am: 13.05.2017 | 01:36:54 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BanManager {
	
	public static boolean isPlayerBanned(UUID UUID) {
		try {
			PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
			ps.setString(1, UUID.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return(rs.getString("UUID") != null);
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void setIP(UUID UUID, String IPAdresse) {
		if(isPlayerBanned(UUID)) {
			if(getIP(UUID) == "-") {
				try {
					PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE Bans SET IP = ? WHERE UUID = ?");
					ps.setString(1, IPAdresse);
					ps.setString(2, UUID.toString());
					ps.executeUpdate();
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void banPlayer(UUID UUID, String Grund, String Von, long Zeit) {
		if(isPlayerBanned(UUID) == false) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("INSERT INTO Bans(UUID, Grund, Von, Bis) VALUES (?, ?, ?, ?)");
				ps.setString(1, UUID.toString());
				ps.setString(2, Grund);
				ps.setString(3, Von);
				ps.setLong(4, Zeit);
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void unbanPlayer(UUID UUID) {
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("DELETE FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getPlayername(UUID UUID) {
		String output = "-";
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					output = rs.getString("Spielername");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static String getGrund(UUID UUID) {
		String output = "-";
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					output = rs.getString("Grund");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static String getWhoBanned(UUID UUID) {
		String output = "-";
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					output = rs.getString("Von");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static long getTime(UUID UUID) {
		long output = -1;
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					output = rs.getLong("Bis");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static String getIP(UUID UUID) {
		String output = "-";
		if(isPlayerBanned(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Bans WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					output = rs.getString("IP");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

}
