/**
 * Die Klasse heißt: ChatclearCommand.java
 * Die Klasse wurde am: 12.05.2017 | 16:34:41 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatclearCommand extends Command {

	public ChatclearCommand(String name) {
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
		
		if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter")) {
			if(args.length == 0) {
				
				for(ProxiedPlayer all : player.getServer().getInfo().getPlayers()) {
					if(!(Bungee.getInstance().onlineStaff.contains(all))) {
						for(int i = 0; i < 100; i++) {
							all.sendMessage(" ");	
						}
					}
					all.sendMessage(" ");
					all.sendMessage(Bungee.prefix + "§7Der Chat wurde von §r" + player.getDisplayName() + "§7 geleert.");
					all.sendMessage(" ");
				}
				
			} else {
				player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
			}
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
