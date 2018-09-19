/**
 * Die Klasse heißt: UnBanCommand.java
 * Die Klasse wurde am: 13.05.2017 | 02:36:01 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import de.bySwordGames.Bungee.MySQL.BanManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCommand extends Command {

	public UnBanCommand(String name) {
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
				player.sendMessage(Bungee.prefix + "§cGebe einen Spielernamen an, um diesen zu entbannen.");
				return;
			}
			
 			if(args.length == 1) {
 				player.sendMessage(Bungee.prefix + "§cGebe einen Grund an, warum der Spieler entbannt werden soll.");
 				return;
 			}
			
			UUID UUID = UUIDFetcher.getUUID(args[0]);
			String name = UUIDFetcher.getName(UUID);
			
			String grund = "";
			for(int i = 1; i < args.length; i++) {
				grund = grund  + args[i] + " ";
			}
			
			if(name != null) {
				
				if(!(BanManager.isPlayerBanned(UUID))) {
					player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist nicht gebannt.");
					return;
				}
				
				BanManager.unbanPlayer(UUID);
				player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnBan §8§m┃---------------------");
				player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
				player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
				player.sendMessage(Bungee.teamPrefix + "§7Entbannt von §8» " + player.getDisplayName());
				player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
						if(all != player) {
							if(!(Bungee.getInstance().notifyToggle.contains(all))) {
								all.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cUnBan §8§m┃---------------------");
								all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
								all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
								all.sendMessage(Bungee.teamPrefix + "§7Entbannt von §8» " + player.getDisplayName());
								all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
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
