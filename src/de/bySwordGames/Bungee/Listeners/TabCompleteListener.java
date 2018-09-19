/**
 * Die Klasse heißt: TabCompleteListener.java
 * Die Klasse wurde am: 11.05.2017 | 00:12:05 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabCompleteListener implements Listener {
	
	@EventHandler
	public void onTabComplete(TabCompleteEvent e) {
        String[] args = e.getCursor().split(" ");
        String checked = (args.length > 0 ? args[args.length - 1] : e.getCursor()).toLowerCase();
        
		if (!e.getSuggestions().isEmpty() | !e.getCursor().startsWith("/")) {
			return;
		}
		
        for (ProxiedPlayer players : BungeeCord.getInstance().getPlayers()) {
			if (players.getName().toLowerCase().startsWith(checked)) {
				e.getSuggestions().add(players.getName());
			}
        }
    }

}
