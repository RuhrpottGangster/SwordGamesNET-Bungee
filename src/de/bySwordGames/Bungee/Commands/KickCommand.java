/**
 * Die Klasse heißt: KickCommand.java
 * Die Klasse wurde am: 13.05.2017 | 00:18:51 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("Server.Administrator") | sender.hasPermission("Server.Head-Developer") | sender.hasPermission("Server.Developer") | sender.hasPermission("Server.Content") | sender.hasPermission("Server.Head-Moderator") | sender.hasPermission("Server.Moderator") | sender.hasPermission("Server.Supporter")) {
			
			if(args.length == 0) {
				sender.sendMessage(Bungee.prefix + "§cGebe einen Spielernamen an, um diesen zu kicken.");
				return;
			}
			
			if(args.length == 1) {
				sender.sendMessage(Bungee.prefix + "§cGeben sie einen Grund an, wieso diesen ihn kicken.");
				return;
			}
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			
			if(target == null) {
				sender.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c ist nicht online.");
				return;
			}
			
			if(Bungee.getInstance().onlineStaff.contains(target)) {
				sender.sendMessage(Bungee.prefix + "§cDu darfst keine §eTeammitglieder §ckicken.");
				return;
			}
			
			String grund = "";
			for(int i = 1; i < args.length; i++) {
				grund = grund  + args[i] + " ";
			}
			
			grund = ChatColor.translateAlternateColorCodes('&', grund);
			
			if(sender instanceof ProxiedPlayer) {
				ProxiedPlayer player = (ProxiedPlayer) sender;
				
				if(target == player) {
					player.sendMessage(Bungee.prefix + "§cDu darfst dich nicht selbst kicken.");
					return;
				}
				
				player.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cKick §8§m┃---------------------");
				player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
				player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
				player.sendMessage(Bungee.teamPrefix + "§7Gekickt von §8» " + player.getDisplayName());
				player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
						if(all != player) {
							if(!(Bungee.getInstance().notifyToggle.contains(all))) {
								all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cKick §8§m┃---------------------");
								all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
								all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
								all.sendMessage(Bungee.teamPrefix + "§7Gekickt von §8» " + player.getDisplayName());
								all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
							}
						}
					}
				}
				
				target.disconnect("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest vom Netzwerk gekickt.\n\n§7Grund §8» §e" + grund + "\n");
				
			} else {
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
						if(!(Bungee.getInstance().notifyToggle.contains(all))) {
							all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cKick §8§m┃---------------------");
							all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
							all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
							all.sendMessage(Bungee.teamPrefix + "§7Gekickt von §8» §4" + "Konsole");
							all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
						}
					}
				}
				
				target.disconnect("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest vom Netzwerk gekickt.\n\n§7Grund §8» §e" + grund + "\n");
				
			}
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
