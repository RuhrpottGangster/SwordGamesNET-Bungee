/**
 * Die Klasse heißt: ServerConnectedListener.java
 * Die Klasse wurde am: 10.05.2017 | 22:42:21 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectedListener implements Listener {
	
	@EventHandler
	public void onServerConnectedEvent(ServerConnectedEvent e) {
		ProxiedPlayer player = e.getPlayer();
		
		Bungee.getInstance().joinMe.remove(player);
		
		if(Bungee.getInstance().maintenance == true) {
			player.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_Maintenance) + "\n\n"), new TextComponent("\n§7Server §8» §e" + e.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
		} else {
			player.setTabHeader(new TextComponent(ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', ProxyPingListener.MOTD_2) + "\n\n"), new TextComponent("\n§7Server §8» §e" + e.getServer().getInfo().getName() + "\n§7TeamSpeak §8» §aSwordGames.net\n§7Twitter §8» §b@SwordGamesNET"));
		}
		
	}

}
