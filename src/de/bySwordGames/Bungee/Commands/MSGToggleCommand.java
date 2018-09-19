/**
 * Die Klasse heißt: MSGToggleCommand.java
 * Die Klasse wurde am: 12.05.2017 | 21:18:43 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MSGToggleCommand extends Command {

	public MSGToggleCommand(String name) {
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
			
			if(Bungee.getInstance().msgToggle.contains(player)) {
				Bungee.getInstance().msgToggle.remove(player);
				player.sendMessage(Bungee.prefix + "§aDu kannst nun wieder private Nachrichten empfangen.");
			} else {
				Bungee.getInstance().msgToggle.add(player);
				player.sendMessage(Bungee.prefix + "§cDu empfängst nun keine privaten Nachrichten mehr.");
			}
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
		}

	}

}
