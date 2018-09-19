/**
 * Die Klasse heißt: PlayerDisconnectListener.java
 * Die Klasse wurde am: 11.05.2017 | 02:27:42 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent e) {
		final ProxiedPlayer player = e.getPlayer();
		
		Bungee.getInstance().messageCooldown.remove(player);
		Bungee.getInstance().reportCooldown.remove(player);
		Bungee.getInstance().notifyToggle.remove(player);
		Bungee.getInstance().msgToggle.remove(player);
		Bungee.getInstance().joinMe.remove(player);
		Bungee.getInstance().werbungCount.remove(player);
		
		if(Bungee.getInstance().reports.containsKey(player)) {
			ProxiedPlayer reporter = Bungee.getInstance().reports.get(player).getReporter();
			ProxiedPlayer reporter2 = BungeeCord.getInstance().getPlayer(reporter.getUniqueId());
			Bungee.getInstance().reports.remove(player);

			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
					if(!(Bungee.getInstance().notifyToggle.contains(all))) {
						all.sendMessage(Bungee.teamPrefix + "§7Der §cReport §7bezüglich " + player.getDisplayName() + " §7wurde §cgelöscht§7.");	
					}
				}
			}
			
			if(reporter2 != null) {
				reporter2.sendMessage(Bungee.prefix + "§7Dein §cReport §7bezüglich " + player.getDisplayName() + " §7wurde §cgelöscht§7.");
			}
		}
		
		if(Bungee.getInstance().onlineStaff.contains(player)) {
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if(Bungee.getInstance().onlineStaff.contains(all)) {
					all.sendMessage(Bungee.teamPrefix + "§7Das Teammitglied §r" + player.getDisplayName() + "§7 ist nun §coffline§7.");
				}
			}
		}
		
		Bungee.getInstance().onlineStaff.remove(player);
		
	}

}
