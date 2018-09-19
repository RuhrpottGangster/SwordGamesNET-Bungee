/**
 * Die Klasse heißt: ReplyCommand.java
 * Die Klasse wurde am: 12.05.2017 | 21:55:03 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReplyCommand extends Command {

	public ReplyCommand(String name) {
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
		
		if(args.length == 0) {
			player.sendMessage(Bungee.prefix + "§cDu musst eine Nachricht eingeben, um sie zu senden.");
			return;
		}
		
		if(args.length > 0) {
			if(Bungee.getInstance().privatMessage.containsKey(player) && Bungee.getInstance().privatMessage.get(player) != null) {
				ProxiedPlayer target = BungeeCord.getInstance().getPlayer(Bungee.getInstance().privatMessage.get(player).toString());
				
				if(target == null) {
					player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c ist nicht mehr online.");
					return;
				}
				
				if(Bungee.getInstance().msgToggle.contains(target)) {
					if(!(player.hasPermission("Server.Administrator"))) {
						player.sendMessage(Bungee.prefix + "§cDu darfst diesem Spieler momentan keine privaten Nachrichten senden.");
						return;
					}
				}
				
				String message = "";
				for(int i = 0; i < args.length; i++) {
					message = message + args[i] + " ";
				}
				
				if(Bungee.getInstance().onlineStaff.contains(player)) {
					message = ChatColor.translateAlternateColorCodes('&', message);
				}
				
				Bungee.getInstance().privatMessage.put(target, player);
				player.sendMessage("§aMSG §8► " + player.getDisplayName() + " §8➟ " + target.getDisplayName() + " §8» §7" + message);
				target.sendMessage("§aMSG §8► " + player.getDisplayName() + " §8➟ " + target.getDisplayName() + " §8» §7" + message);
				return;
				
			} else {
				player.sendMessage(Bungee.prefix + "§cDu hast keine zu beantwortenden Nachrichten.");
			}
		}

	}

}
