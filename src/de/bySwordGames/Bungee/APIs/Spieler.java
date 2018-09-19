/**
 * Die Klasse heißt: Spieler.java
 * Die Klasse wurde am: 10.05.2017 | 22:36:52 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.APIs;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import de.bySwordGames.Bungee.MySQL.MySQL;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Spieler {
	
    public static void setDisplayName(ProxiedPlayer p, String name) {
        UserConnection conn = (UserConnection) p;
        try {
            Field f = UserConnection.class.getDeclaredField("displayName");
            f.setAccessible(true);
            f.set(conn, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static String getPlayerGroup(UUID UUID) {
		try {
			PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM permissions_inheritance WHERE child = ?");
			ps.setString(1, UUID.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String rank = rs.getString("parent");
		        return rank;
			}
	        ps.close();
	        rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Spieler";
	}
	
	public static String getPlayerGroupUntil(UUID UUID, String group) {
		try {
			PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM permissions WHERE name = ? AND permission = ?");
			ps.setString(1, UUID.toString());
			ps.setString(2, "group-" + group + "-until");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String until = rs.getString("value");
		        return until;
			}
	        ps.close();
	        rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "-1";
	}
    
	public static boolean isSystemPlayerExisting(UUID UUID) {
		try {
			PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM System WHERE UUID = ?");
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
	
	public static Integer getCoins(UUID UUID) {
		Integer value = -1;
		if(isSystemPlayerExisting(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM System WHERE UUID = ?");
				ps.setString(1, UUID.toString());
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					value = rs.getInt("Coins");
				}
				ps.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	public static void setCoins(UUID UUID, Integer Coins) {
		if(isSystemPlayerExisting(UUID)) {
			try {
				PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE System SET Coins = ? WHERE UUID = ?");
				ps.setInt(1, Coins);
				ps.setString(2, UUID.toString());
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
