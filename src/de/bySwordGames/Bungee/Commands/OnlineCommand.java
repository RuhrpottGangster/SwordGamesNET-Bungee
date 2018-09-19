/**
 * Die Klasse heißt: OnlineCommand.java
 * Die Klasse wurde am: 12.05.2017 | 21:42:11 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class OnlineCommand extends Command {

	public OnlineCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(args.length == 0) {
			
			int online = BungeeCord.getInstance().getPlayers().size();
			
			if(online == 1) {
				sender.sendMessage(Bungee.prefix + "§7Es ist momentan §e" + online + "§7 Spieler online.");
			} else {
				sender.sendMessage(Bungee.prefix + "§7Es sind momentan §e" + online + "§7 Spieler online.");
			}
			
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
		}

	}

}
