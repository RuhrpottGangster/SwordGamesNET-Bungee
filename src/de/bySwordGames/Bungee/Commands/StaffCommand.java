/**
 * Die Klasse heißt: StaffCommand.java
 * Die Klasse wurde am: 12.05.2017 | 14:02:25 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffCommand extends Command {

	public StaffCommand(String name) {
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
		
		if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter") | player.hasPermission("Server.Builder")) {
			
			if(Bungee.getInstance().notifyToggle.contains(player)) {
				player.sendMessage(Bungee.prefix + "§cWeil du alle Team-Mitteilungen ausgeschalten hast, darfst du nicht den TeamChat nutzen.");
				return;
			}
			
			if(args.length == 0) {
				player.sendMessage(Bungee.prefix + "§cGebe eine Nachricht ein, um sie in den TeamChat zu senden.");
				return;
			}
			
			String rang = "";
			if(player.hasPermission("Server.Administrator")) {
				rang = "§4Administrator";
			} else if(player.hasPermission("Server.Head-Developer")) {
				rang = "§bHead-Developer";
			} else if(player.hasPermission("Server.Developer")) {
				rang = "§bDeveloper";
			} else if(player.hasPermission("Server.Content")) {
				rang = "§bContent";
			} else if(player.hasPermission("Server.Head-Moderator")) {
				rang = "§cHead-Moderator";
			} else if(player.hasPermission("Server.Moderator")) {
				rang = "§cModerator";
			} else if(player.hasPermission("Server.Supporter")) {
				rang = "§eSupporter";
			} else if(player.hasPermission("Server.Builder")) {
				rang = "§2Builder";
			}
			
			String message = "";
			for(int i = 0; i < args.length; i++) {
				message = message + args[i] + " ";
			}
			
			message = ChatColor.translateAlternateColorCodes('&', message);
			
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if(Bungee.getInstance().onlineStaff.contains(all)) {
					if(!(Bungee.getInstance().notifyToggle.contains(all))) {
						all.sendMessage(Bungee.teamPrefix + rang + " §8► " + player.getDisplayName() + "§8 » §7" + message);
					}
				}
			}
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
