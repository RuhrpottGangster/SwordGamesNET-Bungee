/**
 * Die Klasse heißt: WhereIsCommand.java
 * Die Klasse wurde am: 12.05.2017 | 23:24:21 erstellt.
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

public class WhereIsCommand extends Command {

	public WhereIsCommand(String name) {
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
				 player.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um seinen Server zu sehen.");
				 return;
			}
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			
			if(target == null) {
				player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c ist nicht online.");
				return;
			}
			
			TextComponent server = new TextComponent(target.getServer().getInfo().getName());
			server.setColor(ChatColor.YELLOW);
			server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
			server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + target.getServer().getInfo().getName()));
			
			player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.prefix + target.getDisplayName() + " §7befindet §7sich §7momentan §7auf §7dem §7Server §8» "), server });
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
