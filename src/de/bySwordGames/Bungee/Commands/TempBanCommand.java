/**
 * Die Klasse heißt: TempBanCommand.java
 * Die Klasse wurde am: 16.05.2017 | 00:05:44 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Spieler;
import de.bySwordGames.Bungee.APIs.ZeitAPI;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import de.bySwordGames.Bungee.MySQL.BanManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TempBanCommand extends Command {

	public TempBanCommand(String name) {
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
		
		if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator")) {
			
			if(args.length == 0) {
				player.sendMessage(Bungee.prefix + "§cGebe einen Spielernamen an, um diesen zu bannen.");
				return;
			}
			
			if(args.length == 1) {
				player.sendMessage(Bungee.prefix + "§cGebe eine Zahl an, um diesen Spieler zu bannen.");
				return;
			}
			
			if(args.length == 2) {
				player.sendMessage(Bungee.prefix + "§cDu musst ein Zeit-Format eingeben, um diesen Spieler zu bannen. (s, m, h, d)");
				return;
			}
			
			if(args.length == 3) {
				player.sendMessage(Bungee.prefix + "§cGebe einen Grund an, um diesen Spieler zu bannen.");
				return;
			}
			
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			UUID UUID = UUIDFetcher.getUUID(args[0]);
			String name = UUIDFetcher.getName(UUID);
			String banlenght = ZeitAPI.getTempBanString(args[1] + " " + args[2]);
			long zeit = 0;
			
			String grund = "";
			for(int i = 3; i < args.length; i++) {
				grund = grund  + args[i] + " ";
			}
			
			if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Administrator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Developer") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Developer") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Content") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Moderator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Moderator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Supporter") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Builder")) {
				player.sendMessage(Bungee.prefix + "§cDu darfst keine §eTeammitglieder §cbannen.");
				return;
			}
			
			if(target == player) {
				player.sendMessage(Bungee.prefix + "§cDu darfst dich nicht selbst bannen.");
				return;
			}
			
			if(args[2].equalsIgnoreCase("s")) {
				try {
					long time2 = Long.valueOf(args[1]);
					if(time2 > 0 && time2 <= 60) {
						zeit = System.currentTimeMillis() + time2 * 1000;
					} else {
						player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e60 §ceingeben.");
						return;
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e60 §ceingeben.");
					return;
				}
			}
			if(args[2].equalsIgnoreCase("m")) {
				try {
					long time2 = Long.valueOf(args[1]);
					if(time2 > 0 && time2 <= 60) {
						zeit = System.currentTimeMillis() + time2 * 1000 * 60;
					} else {
						player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e60 §ceingeben.");
						return;
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e60 §ceingeben.");
					return;
				}
			}
			if(args[2].equalsIgnoreCase("h")) {
				try {
					long time2 = Long.valueOf(args[1]);
					if(time2 > 0 && time2 <= 24) {
						zeit = System.currentTimeMillis() + time2 * 1000 * 60 * 60;
					} else {
						player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e24 §ceingeben.");
						return;
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e24 §ceingeben.");
					return;
				}
			}
			if(args[2].equalsIgnoreCase("d")) {
				try {
					long time2 = Long.valueOf(args[1]);
					if(time2 > 0 && time2 <= 364) {
						zeit = System.currentTimeMillis() + time2 * 1000 * 60 * 60 * 24;
					} else {
						player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e364 §ceingeben.");
						return;
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
					player.sendMessage(Bungee.prefix + "§cDu musst eine Zahl zwischen §e1 §cund §e364 §ceingeben.");
					return;
				}
			}
			if(!args[2].equalsIgnoreCase("s") && !args[2].equalsIgnoreCase("m") && !args[2].equalsIgnoreCase("h") && !args[2].equalsIgnoreCase("d")) {
				player.sendMessage(Bungee.prefix + "§cDu musst ein gültiges Zeit-Format eingeben, um diesen Spieler zu bannen. (s, m, h, d)");
				return;
			}
			
			if(target == null) {
				
				if(name != null) {
					if(BanManager.isPlayerBanned(UUID)) {
						if(BanManager.getTime(UUID) != -1 && System.currentTimeMillis() - BanManager.getTime(UUID) > 0) {
							BanManager.unbanPlayer(UUID);
						} else {
							player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist schon gebannt.");
							return;
						}
					}
					
					player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist momentan nicht online.");
					BanManager.banPlayer(UUID, grund, player.getName(), zeit);
					
					player.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cBan §8§m┃---------------------");
					player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
					player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
					player.sendMessage(Bungee.teamPrefix + "§7Gebannt von §8» " + player.getDisplayName());
					player.sendMessage(Bungee.teamPrefix + "§7Gebannte Zeit §8» §e" + banlenght);
					player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					
					for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
						if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
							if(all != player) {
								if(!(Bungee.getInstance().notifyToggle.contains(all))) {
									all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cBan §8§m┃---------------------");
									all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
									all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
									all.sendMessage(Bungee.teamPrefix + "§7Gebannt von §8» " + player.getDisplayName());
									all.sendMessage(Bungee.teamPrefix + "§7Gebannte Zeit §8» §e" + banlenght);
									all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
								}
							}
						}
					}
					return;
					
				} else {
					player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + " §cexistiert nicht.");
				}
				
			} else {
				
				if(BanManager.isPlayerBanned(UUID)) {
					if(BanManager.getTime(UUID) != -1 && System.currentTimeMillis() - BanManager.getTime(UUID) > 0) {
						BanManager.unbanPlayer(UUID);
					} else {
						player.sendMessage(Bungee.teamPrefix + "§cDer Spieler §e" + name + "§c ist schon gebannt.");
						return;
					}
				}
				
				BanManager.banPlayer(target.getUniqueId(), grund, player.getName(), zeit);
				
				player.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cBan §8§m┃---------------------");
				player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
				player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
				player.sendMessage(Bungee.teamPrefix + "§7Gebannt von §8» " + player.getDisplayName());
				player.sendMessage(Bungee.teamPrefix + "§7Gebannte Zeit §8» §e" + banlenght);
				player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
						if(all != player) {
							if(!(Bungee.getInstance().notifyToggle.contains(all))) {
								all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cBan §8§m┃---------------------");
								all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + target.getDisplayName());
								all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + grund);
								all.sendMessage(Bungee.teamPrefix + "§7Gebannt von §8» " + player.getDisplayName());
								all.sendMessage(Bungee.teamPrefix + "§7Gebannte Zeit §8» §e" + banlenght);
								all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
							}
						}
					}
				}
				
				target.disconnect("§6SwordGamesNET §8▼ §cFehler\n§cDu wurdest für §e" + banlenght + " §cvom Netzwerk gebannt.\n\n§7Grund §8» §e" + grund + "\n\n§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.\n");
				
				return;
				
			}
			
			
			
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

}
