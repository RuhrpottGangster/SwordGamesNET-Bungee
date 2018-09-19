/**
 * Die Klasse heißt: UnMuteCommand.java
 * Die Klasse wurde am: 14.05.2017 | 21:05:14 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import de.bySwordGames.Bungee.MySQL.MuteManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnMuteCommand extends Command {

	public UnMuteCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(Bungee.prefix + "§cDu musst ein Spieler sein, um diesen Befehl nutzen zu können.");
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) sender;
		
		if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator")) {
			
			if(args.length == 0) {
				player.sendMessage(Bungee.prefix + "§cGebe einen Spielernamen an, um diesen zu entmuten.");
				return;
			}
			
 			if(args.length == 1) {
 				player.sendMessage(Bungee.prefix + "§cGebe einen Grund an, warum der Spieler entmutet werden soll.");
 				return;
 			}
			
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			UUID UUID = UUIDFetcher.getUUID(args[0]);
			String name = UUIDFetcher.getName(UUID);
			
			String grund = "";
			for(int i = 1; i < args.length; i++) {
				grund = grund  + args[i] + " ";
			}
			
			if(name != null) {
				
				if(!(MuteManager.isPlayerMuted(UUID))) {
					player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist nicht gemutet.");
					return;
				}
				
				if(target == null) {
					
					MuteManager.unmutePlayer(UUID);
					player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnMute §8§m┃---------------------");
					player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
					player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
					player.sendMessage(Bungee.teamPrefix + "§7Entmutet von §8» " + player.getDisplayName());
					player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					
					for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
						if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
							if(all != player) {
								if(!(Bungee.getInstance().notifyToggle.contains(all))) {
									all.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnMute §8§m┃---------------------");
									all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
									all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
									all.sendMessage(Bungee.teamPrefix + "§7Entmutet von §8» " + player.getDisplayName());
									all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
								}
							}
						}
					}
					
				} else {
					
					MuteManager.unmutePlayer(UUID);
					player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnMute §8§m┃---------------------");
					player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
					player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
					player.sendMessage(Bungee.teamPrefix + "§7Entmutet von §8» " + player.getDisplayName());
					player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					
					for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
						if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
							if(all != player) {
								if(!(Bungee.getInstance().notifyToggle.contains(all))) {
									all.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnMute §8§m┃---------------------");
									all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
									all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
									all.sendMessage(Bungee.teamPrefix + "§7Entmutet von §8» " + player.getDisplayName());
									all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
								}
							}
						}
					}
					
				}
				
			} else {
				player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + " §cexistiert nicht.");
			}
			
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}
		
	}

}
