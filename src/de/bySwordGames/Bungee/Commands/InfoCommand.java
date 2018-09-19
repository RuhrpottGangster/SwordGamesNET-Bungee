/**
 * Die Klasse heißt: InfoCommand.java
 * Die Klasse wurde am: 15.05.2017 | 14:19:39 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.google.common.collect.Iterables;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Spieler;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import de.bySwordGames.Bungee.MySQL.BanManager;
import de.bySwordGames.Bungee.MySQL.MuteManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class InfoCommand extends Command implements TabExecutor {

	public InfoCommand(String name) {
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
				player.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um Informationen über ihn zu bekommen.");
				return;
			}
			ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
			UUID UUID = UUIDFetcher.getUUID(args[0]);
			String name = UUIDFetcher.getName(UUID);
			
			if(name != null) {
				
				if(BungeeCord.getInstance().getPlayer(name) != null) {
					
					player.sendMessage(Bungee.teamPrefix + "§8§m---------------------§r§8┃ §cInfo §8§m┃----------------------");
					player.sendMessage(Bungee.teamPrefix + "§7Name §8» §e" + target.getDisplayName());
					TextComponent server = new TextComponent(ProxyServer.getInstance().getPlayer(name).getServer().getInfo().getName());
				    server.setColor(ChatColor.AQUA);
				    server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
				    server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + ProxyServer.getInstance().getPlayer(name).getServer().getInfo().getName()));
				    player.sendMessage(new BaseComponent[] { new TextComponent(Bungee.teamPrefix + "§7Status §8» §aOnline §8» "), server });
					player.sendMessage(Bungee.teamPrefix);
					
					String rang = "";
					String group = "";
					String util = Spieler.getPlayerGroupUntil(UUID, group);
					if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Administrator")) {
						rang = "§4Administrator";
						group = "Administrator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Developer")) {
						rang = "§bHead-Developer";
						group = "Head-Developer";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Developer")) {
						rang = "§bDeveloper";
						group = "Developer";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Content")) {
						rang = "§bContent";
						group = "Content";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Moderator")) {
						rang = "§cHead-Moderator";
						group = "Head-Moderator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Moderator")) {
						rang = "§cModerator";
						group = "Moderator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Supporter")) {
						rang = "§eSupporter";
						group = "Supporter";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Builder")) {
						rang = "§2Builder";
						group = "Builder";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Youtuber")) {
						rang = "§5Youtuber";
						group = "Youtuber";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("PremiumPlus")) {
						rang = "§6PremiumPlus";
						group = "PremiumPlus";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Premium")) {
						rang = "§6Premium";
						group = "Premium";
					}
					else {
						rang = "§9Spieler";
						group = "Spieler";
					}
					
					Calendar cal1 = Calendar.getInstance();
					cal1.setTimeInMillis(Long.valueOf(Spieler.getPlayerGroupUntil(UUID, group)) * (long) 1000);
					Date date1 = cal1.getTime();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					String dateString1 = sdf1.format(date1);
					
					if(util == "-1") {
						dateString1 = "Permanant";
					}
					
					player.sendMessage(Bungee.teamPrefix + "§7Rang §8» " + rang);
					player.sendMessage(Bungee.teamPrefix + "§7Ablaufdatum §8» §e" + dateString1);
					player.sendMessage(Bungee.teamPrefix);
					if(Spieler.isSystemPlayerExisting(UUID)) {
						player.sendMessage(Bungee.teamPrefix + "§7Coins §8» §e" + Spieler.getCoins(UUID));
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Coins §8» " + "§cNicht registriert");
					}
					player.sendMessage(Bungee.teamPrefix);
					if(Bungee.getInstance().allowedMaintenacePlayers.contains(UUID)) {
						player.sendMessage(Bungee.teamPrefix + "§7Global-Whitelistet §8» §aJa");
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Global-Whitelistet §8» §cNein");
					}
					if(Bungee.getInstance().blacklistetPlayers.contains(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Geblacklistet §8» §aJa");
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Geblacklistet §8» §cNein");
					}
					if(BanManager.isPlayerBanned(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Gebannt §8» §aJa");
						player.sendMessage(Bungee.teamPrefix + "§7  Von §8» §e" + BanManager.getWhoBanned(UUID));
						player.sendMessage(Bungee.teamPrefix + "§7  Grund §8» §e" + BanManager.getGrund(UUID));
						if(BanManager.getTime(UUID) == -1) {
							player.sendMessage(Bungee.teamPrefix + "§7  Gebannt bis §8» §ePermanant");
						} else {
							Calendar cal = Calendar.getInstance();
			    			cal.setTimeInMillis(BanManager.getTime(UUID));
			    			Date date = cal.getTime();
			                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			                String dateString = sdf.format(date);
							player.sendMessage(Bungee.teamPrefix + "§7  Gebannt bis §8» §e" + dateString);
						}
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Gebannt §8» §cNein");
					}
					if(MuteManager.isPlayerMuted(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Gemutet §8» §aJa");
						player.sendMessage(Bungee.teamPrefix + "§7  Von §8» §e" + MuteManager.getWhoMuted(UUID));
						player.sendMessage(Bungee.teamPrefix + "§7  Grund §8» §e" + MuteManager.getGrund(UUID));
						if(MuteManager.getTime(UUID) == -1) {
							player.sendMessage(Bungee.teamPrefix + "§7  Gemutet bis §8» §ePermanant");
						} else {
							Calendar cal = Calendar.getInstance();
			    			cal.setTimeInMillis(MuteManager.getTime(UUID));
			    			Date date = cal.getTime();
			                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			                String dateString = sdf.format(date);
							player.sendMessage(Bungee.teamPrefix + "§7  Gemutet bis §8» §e" + dateString);
						}
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Gemutet §8» §cNein");
					}
					player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
					
				} else {
						
					player.sendMessage(Bungee.teamPrefix + "§8§m---------------------§r§8┃ §cInfo §8§m┃----------------------");
					player.sendMessage(Bungee.teamPrefix + "§7Name §8» §e" + name);
					player.sendMessage(Bungee.teamPrefix + "§7Status §8» §cOffline");
					player.sendMessage(Bungee.teamPrefix);
					
					String rang = "";
					String group = "";
					String util = Spieler.getPlayerGroupUntil(UUID, group);
					if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Administrator")) {
						rang = "§4Administrator";
						group = "Administrator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Developer")) {
						rang = "§bHead-Developer";
						group = "Head-Developer";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Developer")) {
						rang = "§bDeveloper";
						group = "Developer";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Content")) {
						rang = "§bContent";
						group = "Content";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Moderator")) {
						rang = "§cHead-Moderator";
						group = "Head-Moderator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Moderator")) {
						rang = "§cModerator";
						group = "Moderator";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Supporter")) {
						rang = "§eSupporter";
						group = "Supporter";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Builder")) {
						rang = "§2Builder";
						group = "Builder";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Youtuber")) {
						rang = "§5Youtuber";
						group = "Youtuber";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("PremiumPlus")) {
						rang = "§6PremiumPlus";
						group = "PremiumPlus";
					}
					else if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Premium")) {
						rang = "§6Premium";
						group = "Premium";
					}
					else {
						rang = "§9Spieler";
						group = "Spieler";
					}
					
					Calendar cal1 = Calendar.getInstance();
					cal1.setTimeInMillis(Long.valueOf(Spieler.getPlayerGroupUntil(UUID, group)) * (long) 1000);
					Date date1 = cal1.getTime();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
					String dateString1 = sdf1.format(date1);
					
					if(util == "-1") {
						dateString1 = "Permanant";
					}
					
					player.sendMessage(Bungee.teamPrefix + "§7Rang §8» " + rang);
					player.sendMessage(Bungee.teamPrefix + "§7Ablaufdatum §8» §e" + dateString1);
					player.sendMessage(Bungee.teamPrefix);
					if(Spieler.isSystemPlayerExisting(UUID)) {
						player.sendMessage(Bungee.teamPrefix + "§7Coins §8» §e" + Spieler.getCoins(UUID));
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Coins §8» " + "§cNicht registriert");
					}
					player.sendMessage(Bungee.teamPrefix);
					if(Bungee.getInstance().allowedMaintenacePlayers.contains(UUID)) {
						player.sendMessage(Bungee.teamPrefix + "§7Global-Whitelistet §8» §aJa");
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Global-Whitelistet §8» §cNein");
					}
					if(Bungee.getInstance().blacklistetPlayers.contains(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Geblacklistet §8» §aJa");
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Geblacklistet §8» §cNein");
					}
					if(BanManager.isPlayerBanned(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Gebannt §8» §aJa");
						player.sendMessage(Bungee.teamPrefix + "§7  Von §8» §e" + BanManager.getWhoBanned(UUID));
						player.sendMessage(Bungee.teamPrefix + "§7  Grund §8» §e" + BanManager.getGrund(UUID));
						if(BanManager.getTime(UUID) == -1) {
							player.sendMessage(Bungee.teamPrefix + "§7  Gebannt bis §8» §ePermanant");
						} else {
							Calendar cal = Calendar.getInstance();
			    			cal.setTimeInMillis(BanManager.getTime(UUID));
			    			Date date = cal.getTime();
			                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			                String dateString = sdf.format(date);
							player.sendMessage(Bungee.teamPrefix + "§7  Gebannt bis §8» §e" + dateString);
						}
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Gebannt §8» §cNein");
					}
					if(MuteManager.isPlayerMuted(UUID)) {
						player.sendMessage(Bungee.teamPrefix);
						player.sendMessage(Bungee.teamPrefix + "§7Gemutet §8» §aJa");
						player.sendMessage(Bungee.teamPrefix + "§7  Von §8» §e" + MuteManager.getWhoMuted(UUID));
						player.sendMessage(Bungee.teamPrefix + "§7  Grund §8» §e" + MuteManager.getGrund(UUID));
						if(MuteManager.getTime(UUID) == -1) {
							player.sendMessage(Bungee.teamPrefix + "§7  Gemutet bis §8» §ePermanant");
						} else {
							Calendar cal = Calendar.getInstance();
			    			cal.setTimeInMillis(MuteManager.getTime(UUID));
			    			Date date = cal.getTime();
			                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			                String dateString = sdf.format(date);
							player.sendMessage(Bungee.teamPrefix + "§7  Gemutet bis §8» §e" + dateString);
						}
					} else {
						player.sendMessage(Bungee.teamPrefix + "§7Gemutet §8» §cNein");
					}
					player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				
				}
				
			} else {
				player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c existsiert nicht.");
			}
			
		} else {
			player.sendMessage(Bungee.prefix + Bungee.noPermissions);
		}

	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		ArrayList<String> output = new ArrayList<>();
		
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Supporter")) {
				for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
					output.add(all.getName());
				}
			}
			
		}
		
		return Iterables.filter(output, String.class);
	}

}
