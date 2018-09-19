/**
 * Die Klasse heißt: ServerKickListener.java
 * Die Klasse wurde am: 11.05.2017 | 02:37:13 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerKickListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerKick(ServerKickEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if (p.getServer() != null) {
			if (ProxyServer.getInstance().getServerInfo(p.getServer().getInfo().getName()) != ProxyServer.getInstance().getServerInfo(Bungee.getInstance().lobbyServer)) {
				e.setCancelled(true);
				p.sendMessage(e.getKickReason());
			}
		}
	}

}
