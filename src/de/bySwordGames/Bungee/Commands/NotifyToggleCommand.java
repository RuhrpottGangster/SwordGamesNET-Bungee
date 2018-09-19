/**
 * Die Klasse heißt: NotifyToggleCommand.java
 * Die Klasse wurde am: 12.05.2017 | 21:26:08 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NotifyToggleCommand extends Command {

	public NotifyToggleCommand(String name) {
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
			if(args.length == 0) {
				
				if(Bungee.getInstance().notifyToggle.contains(player)) {
					Bungee.getInstance().notifyToggle.remove(player);
					player.sendMessage(Bungee.prefix + "§aDu kannst nun wieder §eTeamChat-§a, §eReport-§a, §eKick-§a, §eMute-§a, §eBan-§a, §eUnMute-§a, und §eUnBan-Nachrichten §aempfangen.");
				} else {
					Bungee.getInstance().notifyToggle.add(player);
					player.sendMessage(Bungee.prefix + "§cDu empfängst nun keine §eTeamChat-§c, §eReport-§c, §eKick-§c, §eMute-§c, §eBan-§c, §eUnMute-§c, und §eUnBan-Nachrichten §cmehr.");
				}
				
			} else {
				player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
			}
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}
		
	}

}
