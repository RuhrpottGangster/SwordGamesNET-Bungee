/**
 * Die Klasse heißt: JoinmeCommand.java
 * Die Klasse wurde am: 11.05.2017 | 21:02:35 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Image.ImageChar;
import de.bySwordGames.Bungee.Image.ImageMessage;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class JoinmeCommand extends Command {

	public JoinmeCommand(String name) {
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
				if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter") | player.hasPermission("Server.Builder") | player.hasPermission("Server.Youtuber") | player.hasPermission("Server.PremiumPlus")) {
					if(!(player.getServer().getInfo().getName().equalsIgnoreCase(Bungee.getInstance().lobbyServer))) {
						if(!(Bungee.getInstance().joinMe.containsKey(player))) {
							
							for (ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								URL url = null;
								try {
									url = new URL("https://minotar.net/avatar/" + player.getName() + "/8.png");
								} catch (MalformedURLException ex) {
									ex.printStackTrace();
								}
								ImageMessage imageMessage = null;
								try {
									imageMessage = new ImageMessage(ImageIO.read(url), 8, ImageChar.BLOCK.getChar());
								} catch (IOException ex) {
									ex.printStackTrace();
								}
								int i = 1;
								for (String lines : imageMessage.getLines()) {
									if (i != 4 && i != 5) {
										all.sendMessage(lines);
									}
									if (i == 4) {
										all.sendMessage(lines + " " + player.getDisplayName() + " §7spielt §6" + (player.getServer().getInfo().getName().contains("-") ? player.getServer().getInfo().getName().split("-")[0] : player.getServer().getInfo().getName()) + "§7.");
									}
									if (i == 5) {
										TextComponent linesString = new TextComponent(lines + " ");
										TextComponent joinString = new TextComponent("§a§nServer betreten");
	
										joinString.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §e" + player.getServer().getInfo().getName()).create()));
										joinString.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/joinme " + player.getServer().getInfo().getName()));
										
										linesString.addExtra(joinString);
										
										all.sendMessage(linesString);
									}
									i++;
								}
							}
							
							Bungee.getInstance().joinMe.put(player, player.getServer().getInfo().getName());
						} else {
							player.sendMessage(Bungee.prefix + "§cDu hast bereits eine §eJoinMe-Nachricht §cfür diesen Server gesendet.");
						}
					} else {
						player.sendMessage(Bungee.prefix + "§cDu darfst diese Funktion nicht in der Lobby verwenden.");
					}
				} else {
					player.sendMessage(Bungee.prefix + Bungee.noPermissions);
				}
				
			} else if(args.length == 1) {
				boolean isValid = false;
				for (String servers : BungeeCord.getInstance().getServers().keySet()) {
					if (args[0].equalsIgnoreCase(servers) && !args[0].equalsIgnoreCase(Bungee.getInstance().lobbyServer)) {
						isValid = true;
					}
				}
				if (!isValid) {
					player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
					return;
				}
				isValid = false;
				for (String servers : Bungee.getInstance().joinMe.values()) {
					if (args[0].equalsIgnoreCase(servers)) {
						player.connect(BungeeCord.getInstance().getServerInfo(servers));
						isValid = true;
						break;
					}
				}
				if (!isValid) {
					player.sendMessage(Bungee.prefix + "§cDer Spieler dieses JoinMe's hat diesen Server bereits verlassen.");
				}
				return;
			}
		
	}

}
