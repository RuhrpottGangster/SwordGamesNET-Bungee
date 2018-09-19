/**
 * Die Klasse heißt: ChatListener.java
 * Die Klasse wurde am: 14.05.2017 | 18:41:06 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Listeners;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.ChatAPI;
import de.bySwordGames.Bungee.MySQL.MuteManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChatEvent(ChatEvent e) {
		final ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		final String message = e.getMessage();
		
		if(!(e.isCommand())) {
			e.setCancelled(true);
			
			BungeeCord.getInstance().getScheduler().runAsync(Bungee.getInstance(), new Runnable() {

				@Override
				public void run() {
					
					if(MuteManager.isPlayerMuted(player.getUniqueId())) {
						if(MuteManager.getTime(player.getUniqueId()) == -1) {
							player.sendMessage(" ");
							player.sendMessage(Bungee.prefix + "§cDu wurdest §ePERMANANT §cvom Netzwerk gemutet.");
							player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
							player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
							player.sendMessage(" ");
							return;
						} else {
							if(System.currentTimeMillis() - MuteManager.getTime(player.getUniqueId()) < 0) {
								Calendar cal = Calendar.getInstance();
				    			cal.setTimeInMillis(MuteManager.getTime(player.getUniqueId()));
				    			Date date = cal.getTime();
				                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				                String dateString = sdf.format(date);
								
								player.sendMessage(" ");
								player.sendMessage(Bungee.prefix + "§cDu wurdest §eTEMPORÄR §cvom Netzwerk gemutet.");
								player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
								player.sendMessage(Bungee.prefix + "§7Gemutet bis §8» §e" + dateString);
								player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
								player.sendMessage(" ");
								return;	
							} else {
								MuteManager.unmutePlayer(player.getUniqueId());
							}
						}
					}
					
					if(!(player.hasPermission("Server.Administrator"))) {
						
						if(!(Bungee.getInstance().spamCount.containsKey(player.getUniqueId()))) {
							Bungee.getInstance().spamCount.put(player.getUniqueId(), 0);
						}
						
						if(!(Bungee.getInstance().chatCooldown.contains(player.getUniqueId()))) {
							Bungee.getInstance().chatCooldown.add(player.getUniqueId());
							BungeeCord.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {

								@Override
								public void run() {
									Bungee.getInstance().chatCooldown.remove(player.getUniqueId());
								}
								
							}, 1, TimeUnit.SECONDS);
							
						} else {
							Bungee.getInstance().spamCount.put(player.getUniqueId(), Bungee.getInstance().spamCount.get(player.getUniqueId()) + 1);
							BungeeCord.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {

								@Override
								public void run() {
									Bungee.getInstance().spamCount.remove(player.getUniqueId());
								}
								
							}, 5, TimeUnit.SECONDS);
							
							player.sendMessage(Bungee.prefix + "§cBitte schreibe etwas langsamer.");
							
							if(Bungee.getInstance().spamCount.get(player.getUniqueId()) == 3) {
								Bungee.getInstance().spamCount.remove(player.getUniqueId());
								
								if(!(Bungee.getInstance().messageCount.containsKey(player.getUniqueId()))) {
									Bungee.getInstance().messageCount.put(player.getUniqueId(), 1);
								} else {
									Bungee.getInstance().messageCount.put(player.getUniqueId(), Bungee.getInstance().messageCount.get(player.getUniqueId()) + 1);
								}
								
								if(Bungee.getInstance().messageCount.get(player.getUniqueId()) == 1) {
									
									player.disconnect("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest vom Netzwerk gekickt.\n\n§7Grund §8» §e" + "Spam" + "\n");
									
									for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
										if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
											if(!(Bungee.getInstance().notifyToggle.contains(all))) {
												all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cKick §8§m┃---------------------");
												all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + player.getDisplayName());
												all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eSpam");
												all.sendMessage(Bungee.teamPrefix + "§7Gekickt von §8» §eAutomatisch §8| §aAntiSpam");
												all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
											}
										}
									}
									
								} else {
									Bungee.getInstance().messageCount.remove(player.getUniqueId());
									
									MuteManager.mutePlayer(player.getUniqueId(), "Spam", "§eAutomatisch §8| §aAntiSpam", System.currentTimeMillis() + Long.valueOf(12) * 1000 * 60 * 60);
									
									player.sendMessage(" ");
									player.sendMessage(Bungee.prefix + "§cDu wurdest für §e12 Stunden §cvom Netzwerk gemutet.");
									player.sendMessage(Bungee.prefix + "§7Grund §8» §eSpam");
									player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
									player.sendMessage(" ");
									
									for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
										if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
											if(!(Bungee.getInstance().notifyToggle.contains(all))) {
												all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
												all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + player.getDisplayName());
												all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eSpam");
												all.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» §eAutomatisch §8| §aAntiSpam");
												all.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e12 Stunden");
												all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
											}
										}
									}
									
								}
								
							}
							
							return;
						}
						
						if(ChatAPI.messageContainsBlackListedWords(message) == true) {
							player.sendMessage(Bungee.prefix + "§cBitte achte auf deine Wortwahl.");
							return;
						}
						
						if(ChatAPI.messageContainsOnlineMinecraftServerIP(message) == true) {
							if(!(Bungee.getInstance().werbungCount.containsKey(player.getUniqueId()))) {
								Bungee.getInstance().werbungCount.put(player.getUniqueId(), 1);
							} else {
								Bungee.getInstance().werbungCount.put(player.getUniqueId(), Bungee.getInstance().werbungCount.get(player.getUniqueId()) + 1);
							}
							player.sendMessage(Bungee.prefix + "§cDu darfst keine Werbung für Server §cmachen. §8[§e" + Bungee.getInstance().werbungCount.get(player.getUniqueId()) + "§8/§e3§8]");
							
							if(Bungee.getInstance().werbungCount.get(player.getUniqueId()) == 3) {
								Bungee.getInstance().werbungCount.remove(player.getUniqueId());
								MuteManager.mutePlayer(player.getUniqueId(), "Werbung", "§eAutomatisch §8| §aAntiWerbung", System.currentTimeMillis() + Long.valueOf(60) * 1000 * 60 * 60 * 24);
								
								player.sendMessage(" ");
								player.sendMessage(Bungee.prefix + "§cDu wurdest für §e60 Tage §cvom Netzwerk gemutet.");
								player.sendMessage(Bungee.prefix + "§7Grund §8» §eWerbung");
								player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
								player.sendMessage(" ");
								
								for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
									if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
										if(!(Bungee.getInstance().notifyToggle.contains(all))) {
											all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
											all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + player.getDisplayName());
											all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eWerbung");
											all.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» §eAutomatisch §8| §aAntiWerbung");
											all.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e60 Tage");
											all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
										}
									}
								}
								
							}
							return;
						}
						
					}
					
					player.chat(message);
					
				}
				
			});
				
		} else {
			
			if(message.startsWith("/msg ") | message.startsWith("/tell ") | message.startsWith("/whisper ") | message.startsWith("/w ") | message.startsWith("/reply ") | message.startsWith("/r ")) {
				e.setCancelled(true);
				
				BungeeCord.getInstance().getScheduler().runAsync(Bungee.getInstance(), new Runnable() {

					@Override
					public void run() {
						
						if(MuteManager.isPlayerMuted(player.getUniqueId())) {
							if(MuteManager.getTime(player.getUniqueId()) == -1) {
								player.sendMessage(" ");
								player.sendMessage(Bungee.prefix + "§cDu wurdest §ePERMANANT §cvom Netzwerk gemutet.");
								player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
								player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
								player.sendMessage(" ");
								return;
							} else {
								if(System.currentTimeMillis() - MuteManager.getTime(player.getUniqueId()) < 0) {
									Calendar cal = Calendar.getInstance();
					    			cal.setTimeInMillis(MuteManager.getTime(player.getUniqueId()));
					    			Date date = cal.getTime();
					                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
					                String dateString = sdf.format(date);
									
									player.sendMessage(" ");
									player.sendMessage(Bungee.prefix + "§cDu wurdest §eTEMPORÄR §cvom Netzwerk gemutet.");
									player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
									player.sendMessage(Bungee.prefix + "§7Gemutet bis §8» §e" + dateString);
									player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
									player.sendMessage(" ");
									return;	
								} else {
									MuteManager.unmutePlayer(player.getUniqueId());
								}
							}
						}
						
					}
					
				});
				
			}
			
			if(message.startsWith("/report ")) {
				e.setCancelled(true);
				BungeeCord.getInstance().getScheduler().runAsync(Bungee.getInstance(), new Runnable() {

					@Override
					public void run() {
						
						if(MuteManager.isPlayerMuted(player.getUniqueId())) {
							if(MuteManager.getTime(player.getUniqueId()) == -1) {
								if(MuteManager.getGrund(player.getUniqueId()).equalsIgnoreCase(" Unnötiger Report")) {
									player.sendMessage(" ");
									player.sendMessage(Bungee.prefix + "§cDu wurdest §ePERMANANT §cvom Netzwerk gemutet.");
									player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
									player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
									player.sendMessage(" ");
									return;	
								}
							} else {
								if(System.currentTimeMillis() - MuteManager.getTime(player.getUniqueId()) < 0) {
									if(MuteManager.getGrund(player.getUniqueId()).equalsIgnoreCase(" Unnötiger Report")) {
										Calendar cal = Calendar.getInstance();
						    			cal.setTimeInMillis(MuteManager.getTime(player.getUniqueId()));
						    			Date date = cal.getTime();
						                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
						                String dateString = sdf.format(date);
										
										player.sendMessage(" ");
										player.sendMessage(Bungee.prefix + "§cDu wurdest §eTEMPORÄR §cvom Netzwerk gemutet.");
										player.sendMessage(Bungee.prefix + "§7Grund §8» §e" + MuteManager.getGrund(player.getUniqueId()));
										player.sendMessage(Bungee.prefix + "§7Gemutet bis §8» §e" + dateString);
										player.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
										player.sendMessage(" ");
										return;		
									}
								} else {
									MuteManager.unmutePlayer(player.getUniqueId());
								}
							}
						}
						
						String message2 = message;
						message2 = message2.replaceFirst("/", "");
						BungeeCord.getInstance().getPluginManager().dispatchCommand(player, message2);
						
					}
					
				});
				
			}
			
		}
		
	}

}
