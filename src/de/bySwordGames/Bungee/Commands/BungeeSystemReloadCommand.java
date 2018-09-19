/**
 * Die Klasse heißt: BungeeSystemReloadCommand.java
 * Die Klasse wurde am: 09.05.2017 | 17:55:16 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Files;
import de.bySwordGames.Bungee.Listeners.ProxyPingListener;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BungeeSystemReloadCommand extends Command {

	public BungeeSystemReloadCommand(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("Server.Administrator")) {
			if(args.length == 0) {
				
				try {
					Files.cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Files.file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Bungee.getInstance().allowedMaintenacePlayers.clear();
				Bungee.getInstance().blacklistetPlayers.clear();
				Bungee.getInstance().blacklistetWords.clear();
				
				Bungee.getInstance().lobbyServer = Files.cfg.getString("Administration.Lobby-Server");
				Bungee.getInstance().maintenance = Files.cfg.getBoolean("Administration.Maintenance.Status");
				
				for(String blacklistetUUIDs : Files.cfg.getStringList("Administration.Blacklistet-UUIDs")) {
					Bungee.getInstance().blacklistetPlayers.add(UUID.fromString(blacklistetUUIDs));
				}
				
				for(String maintenanceAllowed : Files.cfg.getStringList("Administration.Maintenance-Allowed")) {
					Bungee.getInstance().allowedMaintenacePlayers.add(UUID.fromString(maintenanceAllowed));
				}
				
				for(String blacklistetWords : Files.cfg.getStringList("Administration.Chat.BlacklistetWords")) {
					Bungee.getInstance().blacklistetWords.add(blacklistetWords);
				}
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(Bungee.getInstance().maintenance == true) {
						all.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_Maintenance) + "\n\n"), new TextComponent("\n§7Server §8» §e" + all.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
					} else {
						all.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_2) + "\n\n"), new TextComponent("\n§7Server §8» §e" + all.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
					}
				}
				
				sender.sendMessage(Bungee.prefix + "§aDu hast das BungeeSystem neu geladen.");
			} else {
				sender.sendMessage(Bungee.prefix + Bungee.unknownCommand);
			}
		} else {
			sender.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
