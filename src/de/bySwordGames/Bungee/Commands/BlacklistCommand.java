/**
 * Die Klasse heißt: BlacklistCommand.java
 * Die Klasse wurde am: 09.05.2017 | 18:14:38 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Iterables;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Files;
import de.bySwordGames.Bungee.APIs.Spieler;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BlacklistCommand extends Command implements TabExecutor {

	public BlacklistCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("Server.Administrator")) {
			
			if(args.length == 0) {
				sender.sendMessage(Bungee.prefix + "§cDu musst eine Aktion, die ausgeführt werden soll, angeben. (add, list, remove)");
				return;
			}
			
			if(args[0].equalsIgnoreCase("add")) {
				if(args.length == 1) {
					sender.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um ihn zu blacklisten.");
					return;
				}
				if(args.length == 2) {
					
					UUID UUID = UUIDFetcher.getUUID(args[1]);
					String name = UUIDFetcher.getName(UUID);
					
					if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Administrator")) {
						sender.sendMessage(Bungee.prefix + "§cDu darfst keine §4Administratoren §cblacklisten.");
						return;
					}
					
					if(name != null) {
						if(!(Bungee.getInstance().blacklistetPlayers.contains(UUID))) {
							
							Bungee.getInstance().blacklistetPlayers.add(UUID);
							List<String> update = Files.cfg.getStringList("Administration.Blacklistet-UUIDs");
							update.add(UUID.toString());
							Files.cfg.set("Administration.Blacklistet-UUIDs", update);
							Files.saveConfig();
							
							for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if(all.hasPermission("Server.Administrator") && !Bungee.getInstance().notifyToggle.contains(all)) {
									all.sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde geblacklistet.");
								}
							}
							BungeeCord.getInstance().getConsole().sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde geblacklistet.");
							
							if (ProxyServer.getInstance().getPlayer(UUID) != null) {
			                	ProxyServer.getInstance().getPlayer(UUID).disconnect("§6SwordGamesNET §8▼ §cFehler\n§cDu darfst das Netzwerk nicht betreten.\n\n§7Grund §8» §eDu wurdest geblacklistet\n");
			                }
							return;
							
						} else {
							sender.sendMessage(Bungee.prefix + "§cDer Spieler ist schon geblacklistet.");
							return;
						}
					} else {
						sender.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[1] + "§c existsiert nicht.");
						return;
					}
					
				} else {
					sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length == 1) {
					sender.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um ihn zu entblacklisten.");
					return;
				}
				if(args.length == 2) {
					
					UUID UUID = UUIDFetcher.getUUID(args[1]);
					String name = UUIDFetcher.getName(UUID);
					
					if(name != null) {
						if(Bungee.getInstance().blacklistetPlayers.contains(UUID)) {
							
							Bungee.getInstance().blacklistetPlayers.remove(UUID);
							List<String> update = Files.cfg.getStringList("Administration.Blacklistet-UUIDs");
							update.remove(UUID.toString());
							Files.cfg.set("Administration.Blacklistet-UUIDs", update);
							Files.saveConfig();
							
							for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if(all.hasPermission("Server.Administrator") && !Bungee.getInstance().notifyToggle.contains(all)) {
									all.sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde von der Blacklist entfernt.");
								}
							}
							BungeeCord.getInstance().getConsole().sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde von der Blacklist entfernt.");
							
						} else {
							sender.sendMessage(Bungee.prefix + "§cDer Spieler ist nicht geblacklistet.");
							return;
						}
					} else {
						sender.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[1] + "§c existsiert nicht.");
						return;
					}
					
				} else {
					sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(args.length == 1) {
					
					sender.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §eBlacklist §8§m┃--------------------"); 
					for (UUID players : Bungee.getInstance().blacklistetPlayers) {
						sender.sendMessage("§8» §e" + UUIDFetcher.getName(players));
					}
					if (Bungee.getInstance().blacklistetPlayers.isEmpty()) {
						sender.sendMessage("§8» §cEs sind momentan keine Spieler auf der §eBlacklist§c.");
					}
					sender.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					return;
					
				} else {
					sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
				}
			} else {
				sender.sendMessage(Bungee.prefix + "§cDu musst eine Aktion, die ausgeführt werden soll, angeben. (add, list, remove)");
			}
			
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		ArrayList<String> output = new ArrayList<>();
		
		if(sender.hasPermission("Server.Administrator")) {
			
			if(args[0].equalsIgnoreCase("add")) {
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					output.add(all.getName());
				}
			}
			
			if(args[0].equalsIgnoreCase("remove")) {
				for(UUID UUID : Bungee.getInstance().blacklistetPlayers) {
					output.add(UUIDFetcher.getName(UUID));
				}
			}
			
		}
		
		return Iterables.filter(output, String.class);
	}

}
