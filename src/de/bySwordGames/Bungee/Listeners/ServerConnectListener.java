/**
 * Die Klasse heißt: ServerConnectListener.java
 * Die Klasse wurde am: 11.05.2017 | 02:14:25 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import java.util.concurrent.TimeUnit;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Maintenance;
import de.bySwordGames.Bungee.APIs.Spieler;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerConnectEvent(ServerConnectEvent e) {
		final ProxiedPlayer player = e.getPlayer();
		
		if(player.getServer() == null) {
			
			if(player.hasPermission("Server.Administrator")) {
				Spieler.setDisplayName(player, "§4" + player.getName());
			} else if(player.hasPermission("Server.Head-Developer")) {
				Spieler.setDisplayName(player, "§b" + player.getName());
			} else if(player.hasPermission("Server.Developer")) {
				Spieler.setDisplayName(player, "§b" + player.getName());
			} else if(player.hasPermission("Server.Content")) {
				Spieler.setDisplayName(player, "§b" + player.getName());
			} else if(player.hasPermission("Server.Head-Moderator")) {
				Spieler.setDisplayName(player, "§c" + player.getName());
			} else if(player.hasPermission("Server.Moderator")) {
				Spieler.setDisplayName(player, "§c" + player.getName());
			} else if(player.hasPermission("Server.Supporter")) {
				Spieler.setDisplayName(player, "§e" + player.getName());
			} else if(player.hasPermission("Server.Builder")) {
				Spieler.setDisplayName(player, "§2" + player.getName());
			} else if(player.hasPermission("Server.Youtuber")) {
				Spieler.setDisplayName(player, "§5" + player.getName());
			} else if(player.hasPermission("Server.PremiumPlus")) {
				Spieler.setDisplayName(player, "§6" + player.getName());
			} else if(player.hasPermission("Server.Premium")) {
				Spieler.setDisplayName(player, "§6" + player.getName());
			} else {
				Spieler.setDisplayName(player, "§9" + player.getName());
			}
			
			if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter") | player.hasPermission("Server.Builder")) {
				Bungee.getInstance().onlineStaff.add(player);
				
				BungeeCord.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
							if(Bungee.getInstance().onlineStaff.contains(all)) {
								all.sendMessage(Bungee.teamPrefix + "§7Das Teammitglied §r" + player.getDisplayName() + "§7 ist nun §aonline§7.");
							}
						}
					}
					
				}, 1, TimeUnit.SECONDS);
			}
			
			if(Bungee.getInstance().maintenance == true) {
				if(!(Bungee.getInstance().allowedMaintenacePlayers.contains(player.getUniqueId()) | player.hasPermission("Server.Administrator"))) {
					e.setCancelled(true);
					player.disconnect(Maintenance.getCancelledScreen());
					return;
				}
			}
			
			player.sendMessage(" ");
			player.sendMessage(" ");
			player.sendMessage("§8§m-----------------------------------------------------");
			player.sendMessage("                  §7Herzlich Willkommen auf §6SwordGames.net§7!");
			player.sendMessage("                         §7Teamspeak §8» §aSwordGames.net");
			player.sendMessage("§8§m-----------------------------------------------------");
			
			BungeeCord.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {

				@Override
				public void run() {
					if(!(Bungee.getInstance().reports.isEmpty())) {
						
						TextComponent prefix = new TextComponent(Bungee.teamPrefix);
						TextComponent list = new TextComponent("§a§nAlle anzeigen");
						list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §7Alle §cReports §7anzeigen").create()));
						list.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/reports"));
						prefix.addExtra(list);
						
						player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cReport §8§m┃--------------------");
						boolean selected = false;
						String amount = "";
						
						if (Bungee.getInstance().reports.size() < 4) {
							amount = "§a" + Bungee.getInstance().reports.size();
							selected = true;
						}
						
						if (Bungee.getInstance().reports.size() < 7 && !selected) {
							amount = "§e" + Bungee.getInstance().reports.size();
							selected = true;
						}
						
						if (Bungee.getInstance().reports.size() < 10 && !selected) {
							amount = "§c" + Bungee.getInstance().reports.size();
							selected = true;
						}
						
						if (Bungee.getInstance().reports.size() < 13 && !selected) {
							amount = "§4" + Bungee.getInstance().reports.size();
							selected = true;
						}
						
						if (Bungee.getInstance().reports.size() >= 13 && !selected) {
							amount = "§4§l" + Bungee.getInstance().reports.size();
							selected = true;
						}
						
						player.sendMessage(Bungee.teamPrefix + "§7Unbearbeitete §cReports §8» " + amount);
						player.sendMessage(prefix);
						player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					}
				}
				
			}, 1500, TimeUnit.MILLISECONDS);
			
		}
		
	}

}
