/**
 * Die Klasse heißt: MaintenanceCommand.java
 * Die Klasse wurde am: 09.05.2017 | 13:44:33 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Maintenance;
import de.bySwordGames.Bungee.Listeners.ProxyPingListener;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MaintenanceCommand extends Command {

	public MaintenanceCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("Server.Administrator")) {
			
			if(Bungee.getInstance().maintenance == true) {
				if(args.length == 0) {
					Bungee.getInstance().maintenance = false;
					Maintenance.setConfigMaintenanceBoolean(false);
					
					for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
						all.sendMessage(Bungee.prefix + "§eDie Wartungsarbeiten wurden §cdeaktiviert§e.");
						all.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_2) + "\n\n"), new TextComponent("\n§7Server §8» §e" + all.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
					}
					BungeeCord.getInstance().getConsole().sendMessage(Bungee.prefix + "§eDie Wartungsarbeiten wurden §cdeaktiviert§e.");
					
				} else {
					sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
				}
			} else {
				if(args.length == 0) {
					sender.sendMessage(Bungee.prefix + "§cDu musst einen Grund angeben, um die Wartungsarbeiten zu aktivieren.");
					return;
				}
				String grund = "";
				for(int i = 0; i < args.length; i++) {
					grund = grund  + args[i] + " ";
				}
				
				Bungee.getInstance().maintenance = true;
				Maintenance.setConfigMaintenanceBoolean(true);
				Maintenance.setConfigMaintenanceReason(grund);
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(!(Bungee.getInstance().allowedMaintenacePlayers.contains(all.getUniqueId()) | all.hasPermission("Server.Administrator"))) {
						all.disconnect(Maintenance.getCancelledScreen());
					}
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					all.sendMessage(Bungee.prefix + "§eDie Wartungsarbeiten wurden §aaktiviert§e.");
					all.sendMessage(Bungee.prefix + "§8 ➟ §e" + grund.replaceAll("&", "§"));
					all.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_Maintenance) + "\n\n"), new TextComponent("\n§7Server §8» §e" + all.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
				}
				BungeeCord.getInstance().getConsole().sendMessage(Bungee.prefix + "§eDie Wartungsarbeiten wurden §aaktiviert§e.");
				BungeeCord.getInstance().getConsole().sendMessage(Bungee.prefix + "§8 ➟ §e" + grund.replaceAll("&", "§"));
				
			}
			
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
