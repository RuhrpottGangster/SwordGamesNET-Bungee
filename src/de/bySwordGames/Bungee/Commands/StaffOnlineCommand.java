/**
 * Die Klasse heißt: StaffOnlineCommand.java
 * Die Klasse wurde am: 12.05.2017 | 23:00:12 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffOnlineCommand extends Command {

	public StaffOnlineCommand(String name) {
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
		
		if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter") | player.hasPermission("Server.Builder")) {
			if(args.length == 0) {
				
				player.sendMessage(Bungee.teamPrefix + "§8§m------------------§r§8┃ §cOnlineTeam §8§m┃--------------------");
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Administrator")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Head-Developer")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Developer")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Content")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Head-Moderator")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Moderator")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Supporter")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Builder")) {
						TextComponent server = new TextComponent(all.getServer().getInfo().getName());
					    server.setColor(ChatColor.YELLOW);
					    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
					    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + all.getServer().getInfo().getName()));
					    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§8» " + all.getDisplayName() + " §8» §e"), server });
					}
				}
				
				player.sendMessage(Bungee.teamPrefix + "§8§m-------------------------------------------------");
				
			} else {
				player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
			}
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}
		
	}

}
