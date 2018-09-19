/**
 * Die Klasse heißt: RundrufCommand.java
 * Die Klasse wurde am: 12.05.2017 | 16:48:46 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RundrufCommand extends Command {

	public RundrufCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("Server.Administrator")) {
			
			if(args.length == 0) {
				sender.sendMessage(Bungee.prefix + "§cDu musst eine Nachricht eingeben, um sie zu senden.");
				return;
			}
			
			String message  = "";
			for(int i = 0; i < args.length; i++) {
				message = message + args[i] + " ";
			}
			
			message = ChatColor.translateAlternateColorCodes('&', message);
			
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				all.sendMessage(" ");
				all.sendMessage("§cRundruf §8► §7" + message);
				all.sendMessage(" ");
			}
			BungeeCord.getInstance().getConsole().sendMessage(" ");
			BungeeCord.getInstance().getConsole().sendMessage("§cRundruf §8► §7" + message);
			BungeeCord.getInstance().getConsole().sendMessage(" ");
			
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
