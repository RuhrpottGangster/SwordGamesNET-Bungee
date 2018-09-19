/**
 * Die Klasse heißt: PingCommand.java
 * Die Klasse wurde am: 12.05.2017 | 21:45:56 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {

	public PingCommand(String name) {
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
			player.sendMessage(Bungee.prefix + "§7Dein Ping beträgt §e" + player.getPing() + "ms§7.");
		} else if(args.length == 1) {
			
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c ist nicht online.");
				return;
			}
			
			player.sendMessage(Bungee.prefix + "§7Der Ping von " + target.getDisplayName() + "§7 beträgt §e" + target.getPing() + "ms§7.");
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
		}

	}

}
