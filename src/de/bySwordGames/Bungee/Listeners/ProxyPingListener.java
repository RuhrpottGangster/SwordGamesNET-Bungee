/**
 * Die Klasse heißt: ProxyPingListener.java
 * Die Klasse wurde am: 09.05.2017 | 17:03:17 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Files;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
	
//	public static String MOTD_1 = Files.cfg.getString("Administration.Motd.1");
//	public static String MOTD_2 = Files.cfg.getString("Administration.Motd.2");
//	public static String MOTD_Maintenance = Files.cfg.getString("Administration.Motd.Maintenance");
	
	public static String MOTD_1 = "§6SwordGames.net §8► §7Dein Minigames Netzwerk §8┃ §21.8";
	public static String MOTD_2 = "§8  ⋙ §aWir haben eröffnet §7+ §ePremium Verlosung";
	public static String MOTD_Maintenance = "§8  ⋙ §cWir befinden uns in Wartungsarbeiten!";
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProxyPingEvent(ProxyPingEvent e) {
		ServerPing ping = e.getResponse();
		ServerPing.Players players = ping.getPlayers();
		
		if(Bungee.getInstance().maintenance == true) {
			ping.setVersion(new ServerPing.Protocol("§4✘ §8┃ §cWartungsarbeiten", Short.MAX_VALUE));
			ping.setDescription(ChatColor.translateAlternateColorCodes('&', MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', MOTD_Maintenance));
		} else {
			players.setMax(Files.cfg.getInt("Administration.Slots"));
			ping.setDescription(ChatColor.translateAlternateColorCodes('&', MOTD_1) + "\n" + ChatColor.translateAlternateColorCodes('&', MOTD_2));
		}
		
    	try {
			ping.setFavicon(Favicon.create(ImageIO.read(new File(Bungee.getInstance().getDataFolder().getPath(), "server-icon.png"))));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	ping.setPlayers(players);
		
	}

}
