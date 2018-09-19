/**
 * Die Klasse heißt: ReportsCommand.java
 * Die Klasse wurde am: 12.05.2017 | 22:31:32 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.Map.Entry;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Report;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportsCommand extends Command {

	public ReportsCommand(String name) {
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
				if(Bungee.getInstance().reports.size() == 0) {
					player.sendMessage(Bungee.teamPrefix + "§cEs liegen momentan keine unbeantworteten §eReports §cvor.");
					return;
				}
				player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cReport §8§m┃--------------------");
				int count = 0;
				for (Entry<ProxiedPlayer, Report> entry : Bungee.getInstance().reports.entrySet()) {
					count++;
					
					TextComponent tc = new TextComponent(Bungee.teamPrefix + "§e" + count + ". §8» " + entry.getKey().getDisplayName() + " §8┃ ");
					
					TextComponent list = new TextComponent("§aAnzeigen");
					list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cReport §7anzeigen").create()));
					list.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reports info " + entry.getKey().getName()));
					
					tc.addExtra(list);
					
					player.sendMessage(tc);
				}
				player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				return;
			} else if(args.length == 1) {
				player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
				return;
			} else if(args.length == 2) {
				
				if(args[0].equalsIgnoreCase("info")) {
					ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
					
					if (target == null) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + args[1] + " §cvor.");
						return;
					}
					
					if (!(Bungee.getInstance().reports.containsKey(target))) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + target.getName() + "§c vor.");
						return;
					}
					
					Report report = Bungee.getInstance().reports.get(target);
					
					report.sendDetailsToPlayer(player);
					return;
				}
				
				if(args[0].equalsIgnoreCase("accept")) {
					ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
					
					if (target == null) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + args[1] + " §cvor.");
						return;
					}
					
					if (!(Bungee.getInstance().reports.containsKey(target))) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + target.getName() + "§c vor.");
						return;
					}
					
					Report report = Bungee.getInstance().reports.get(target);
					
					report.accept(player);
					return;
				}
				
				if(args[0].equalsIgnoreCase("deny")) {
					ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
					
					if (target == null) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + args[1] + " §cvor.");
						return;
					}
					
					if (!(Bungee.getInstance().reports.containsKey(target))) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + target.getName() + "§c vor.");
						return;
					}
					
					Report report = Bungee.getInstance().reports.get(target);
					
					report.deny(player);
					return;
				}
				
				if(args[0].equalsIgnoreCase("ban")) {
					ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[1]);
					
					if (target == null) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + args[1] + " §cvor.");
						return;
					}
					
					if (!(Bungee.getInstance().reports.containsKey(target))) {
						player.sendMessage(Bungee.teamPrefix + "§cEs liegt kein §eReport §cbezüglich des Spielers §e" + target.getName() + "§c vor.");
						return;
					}
					
					Report report = Bungee.getInstance().reports.get(target);
					
					report.ban(player);
					return;
				}
				
			} else {
				player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
			}
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
