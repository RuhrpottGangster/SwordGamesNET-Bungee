/**
 * Die Klasse heißt: Maintenance.java
 * Die Klasse wurde am: 09.05.2017 | 13:31:28 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.APIs;

import de.bySwordGames.Bungee.Files;

public class Maintenance {
	
	public static String getCancelledScreen() {
		return "§6SwordGamesNET §8▼ §cFehler\n§cDas Netzwerk befindet sich momentan in Wartungsarbeiten.\n\n§7Grund der Wartungen §8» §e" + Files.cfg.getString("Administration.Maintenance.Reason").replaceAll("&", "§") + "\n\n§8§m------------------------§r\n§8• §7Teamspeak §8► §aSwordGames.net §8§l┃ §7Twitter §8► §b@SwordGamesNET §8•\n";
	}
	
	public static void setConfigMaintenanceBoolean(boolean value) {
		Files.cfg.set("Administration.Maintenance.Status", value);
		Files.saveConfig();
	}
	
	public static void setConfigMaintenanceReason(String reason) {
		Files.cfg.set("Administration.Maintenance.Reason", reason);
		Files.saveConfig();
	}

}
