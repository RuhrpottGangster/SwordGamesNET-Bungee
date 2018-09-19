/**
 * Die Klasse heißt: LoginListener.java
 * Die Klasse wurde am: 09.05.2017 | 13:27:05 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.MySQL.BanManager;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
	
	@EventHandler
	public void onLoginEvent(final LoginEvent e) {
		UUID UUID = e.getConnection().getUniqueId();
		
		if(Bungee.getInstance().blacklistetPlayers.contains(UUID)) {
			e.setCancelled(true);
			e.setCancelReason("§6SwordGamesNET §8▼ §cFehler\n§cDu darfst das Netzwerk nicht betreten.\n\n§7Grund §8» §eDu wurdest geblacklistet\n");
			return;
		}
		
		if(BanManager.isPlayerBanned(UUID)) {
			if(BanManager.getTime(UUID) == -1) {
				e.setCancelled(true);
				e.setCancelReason("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest §ePERMANANT §cvom Netzwerk gebannt.\n\n§7Grund §8» §e" + BanManager.getGrund(UUID) + "\n\n§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.\n");
			} else {
				if(System.currentTimeMillis() - BanManager.getTime(UUID) < 0) {
					Calendar cal = Calendar.getInstance();
	    			cal.setTimeInMillis(BanManager.getTime(UUID));
	    			Date date = cal.getTime();
	                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	                String dateString = sdf.format(date);
	                
	                e.setCancelled(true);
					e.setCancelReason("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest §eTEMPORÄR §cvom Netzwerk gebannt.\n\n§7Grund §8» §e" + BanManager.getGrund(UUID) + "\n§7Gebannt bis §8» §b" + dateString + "\n\n§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.\n");
	                
				} else {
					BanManager.unbanPlayer(UUID);
				}
			}
		}
		
	}

}
