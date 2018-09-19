/**
 * Die Klasse heißt: WhitelistCommand.java
 * Die Klasse wurde am: 11.05.2017 | 15:03:27 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Iterables;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Files;
import de.bySwordGames.Bungee.APIs.Maintenance;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class WhitelistCommand extends Command implements TabExecutor {

	public WhitelistCommand(String name) {
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
					sender.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um ihn zu whitelisten.");
					return;
				}
				if(args.length == 2) {
					
					UUID UUID = UUIDFetcher.getUUID(args[1]);
					String name = UUIDFetcher.getName(UUID);
					
					if(name != null) {
						if(!(Bungee.getInstance().allowedMaintenacePlayers.contains(UUID))) {
							
							Bungee.getInstance().allowedMaintenacePlayers.add(UUID);
							List<String> update = Files.cfg.getStringList("Administration.Maintenance-Allowed");
							update.add(UUID.toString());
							Files.cfg.set("Administration.Maintenance-Allowed", update);
							Files.saveConfig();
							
							for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if(all.hasPermission("Server.Administrator") && !Bungee.getInstance().notifyToggle.contains(all)) {
									all.sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde gewhitelistet.");
								}
							}
							BungeeCord.getInstance().getConsole().sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde gewhitelistet.");
							return;
							
						} else {
							sender.sendMessage(Bungee.prefix + "§cDer Spieler ist schon gewhitelistet.");
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
					sender.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um ihn zu entwhitelisten.");
					return;
				}
				if(args.length == 2) {
					
					UUID UUID = UUIDFetcher.getUUID(args[1]);
					String name = UUIDFetcher.getName(UUID);
					
					if(name != null) {
						if(Bungee.getInstance().allowedMaintenacePlayers.contains(UUID)) {
							
							Bungee.getInstance().allowedMaintenacePlayers.remove(UUID);
							List<String> update = Files.cfg.getStringList("Administration.Maintenance-Allowed");
							update.remove(UUID.toString());
							Files.cfg.set("Administration.Maintenance-Allowed", update);
							Files.saveConfig();
							
							for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
								if(all.hasPermission("Server.Administrator") && !Bungee.getInstance().notifyToggle.contains(all)) {
									all.sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde von der Whitelist entfernt.");
								}
							}
							BungeeCord.getInstance().getConsole().sendMessage(Bungee.teamPrefix + "§7Der Spieler §e" + name + "§7 wurde von der Whitelist entfernt.");
							
							if(Bungee.getInstance().maintenance == true) {
								if(BungeeCord.getInstance().getPlayer(UUID) != null) {
									BungeeCord.getInstance().getPlayer(UUID).disconnect(Maintenance.getCancelledScreen());
								}
							}
							
						} else {
							sender.sendMessage(Bungee.prefix + "§cDer Spieler ist nicht gewhitelistet.");
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
					
					sender.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §eWhitelist §8§m┃--------------------"); 
					for (UUID players : Bungee.getInstance().allowedMaintenacePlayers) {
						sender.sendMessage("§8» §e" + UUIDFetcher.getName(players));
					}
					if (Bungee.getInstance().allowedMaintenacePlayers.isEmpty()) {
						sender.sendMessage("§8» §cEs sind momentan keine Spieler auf der §eWhitelist§c.");
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
				for(UUID UUID : Bungee.getInstance().allowedMaintenacePlayers) {
					output.add(UUIDFetcher.getName(UUID));
				}
			}
			
		}
		
		return Iterables.filter(output, String.class);
	}

}
