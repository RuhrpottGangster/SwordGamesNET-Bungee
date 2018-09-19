/**
 * Die Klasse heißt: MuteManager.java
 * Die Klasse wurde am: 13.05.2017 | 01:51:08 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MuteManager {
	
	public static boolean isPlayerMuted(UUID UUID) {
		try {
			PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
		if(isPlayerMuted(UUID)) {
			if(getIP(UUID) == "-") {
				try {
					PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE Mutes SET IP = ? WHERE UUID = ?");
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
	
	public static void mutePlayer(UUID UUID, String Grund, String Von, long Zeit) {
		if(isPlayerMuted(UUID) == false) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("INSERT INTO Mutes(UUID, Grund, Von, Bis) VALUES (?, ?, ?, ?)");
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
	
	public static void unmutePlayer(UUID UUID) {
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("DELETE FROM Mutes WHERE UUID = ?");
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
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
	
	public static String getWhoMuted(UUID UUID) {
		String output = "-";
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
		if(isPlayerMuted(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM Mutes WHERE UUID = ?");
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
