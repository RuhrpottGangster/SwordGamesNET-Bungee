/**
 * Die Klasse heißt: LobbyCommand.java
 * Die Klasse wurde am: 12.05.2017 | 16:55:48 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCommand extends Command {

	public LobbyCommand(String name) {
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
			if(!(player.getServer().getInfo().getName().equalsIgnoreCase(Bungee.getInstance().lobbyServer))) {
				
				player.connect(BungeeCord.getInstance().getServerInfo(Bungee.getInstance().lobbyServer));
				player.sendMessage(Bungee.prefix + "§eDu wirst mit der Lobby verbunden.");
				
			} else {
				player.sendMessage(Bungee.prefix + "§cDu befindest dich schon in der Lobby.");
			}
		} else {
			player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
		}

	}

}
