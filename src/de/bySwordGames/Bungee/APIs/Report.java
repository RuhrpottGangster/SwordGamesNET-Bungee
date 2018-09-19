/**
 * Die Klasse heißt: Report.java
 * Die Klasse wurde am: 11.05.2017 | 20:59:48 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.APIs;

import java.util.UUID;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.Fetcher.UUIDFetcher;
import de.bySwordGames.Bungee.MySQL.MuteManager;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Report {
	
	private ProxiedPlayer reported;
	private ProxiedPlayer reporter;
	private String grund;
	
	public Report(ProxiedPlayer reported, String grund, ProxiedPlayer reporter) {
		this.reported = reported;
		this.reporter = reporter;
		this.grund = grund;
		Bungee.getInstance().reports.put(reported, this);
	}
	
	public ProxiedPlayer getReportedPlayer() {
		return this.reported;
	}
	
	public ProxiedPlayer getReporter() {
		return this.reporter;
	}
	
	public String getGrund() {
		return this.grund;
	}
	
	
	@SuppressWarnings("deprecation")
	public void announce() {
		TextComponent prefix = new TextComponent(Bungee.teamPrefix);
		TextComponent list = new TextComponent("§a§nAlle anzeigen");
		
		list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §7Alle §cReports §7anzeigen").create()));
		list.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/reports"));
		
		prefix.addExtra(list);
		
		for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
				if(!(Bungee.getInstance().notifyToggle.contains(all))) {
					all.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cReport §8§m┃--------------------");
					all.sendMessage(Bungee.teamPrefix + "§7Ein neuer §cReport §7ist eingegangen.");

					boolean selected = false;
					String amount = "";
					
					if (Bungee.getInstance().reports.size() < 4) {
						amount = "§a" + Bungee.getInstance().reports.size();
						selected = true;
					}
					
					if (Bungee.getInstance().reports.size() < 7 && !selected) {
						amount = "§e" + Bungee.getInstance().reports.size();
						selected = true;
					}
					
					if (Bungee.getInstance().reports.size() < 10 && !selected) {
						amount = "§c" + Bungee.getInstance().reports.size();
						selected = true;
					}
					
					if (Bungee.getInstance().reports.size() < 13 && !selected) {
						amount = "§4" + Bungee.getInstance().reports.size();
						selected = true;
					}
					
					if (Bungee.getInstance().reports.size() >= 13 && !selected) {
						amount = "§4§l" + Bungee.getInstance().reports.size();
						selected = true;
					}
					
					all.sendMessage(Bungee.teamPrefix + "§7Unbearbeitete §cReports §8» " + amount);
					all.sendMessage(prefix);
					all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendDetailsToPlayer(ProxiedPlayer player) {
		TextComponent tc = new TextComponent(Bungee.teamPrefix + "§7Server §8» ");
		TextComponent server = new TextComponent("§e" + this.reported.getServer().getInfo().getName());
		server.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aZu ihm springen").create()));
		server.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + this.reported.getServer().getInfo().getName()));
		tc.addExtra(server);
		
		TextComponent action = new TextComponent(Bungee.teamPrefix);
		
		TextComponent accept = new TextComponent("§aAnnehmen");
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §aAnnehmen").create()));
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reports accept " + this.reported.getName()));
		 
		TextComponent spacer = new TextComponent(" §8┃ ");
		
		TextComponent deny = new TextComponent("§cAblehnen");
		deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §cAblehnen").create()));
		deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reports deny " + this.reported.getName()));
		
		TextComponent ban = new TextComponent("§eUnnötiger Report");
		ban.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §eUnnötiger Report").create()));
		ban.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reports ban " + this.reported.getName()));
		
		action.addExtra(accept);
		action.addExtra(spacer);
		action.addExtra(deny);
		action.addExtra(spacer);
		action.addExtra(ban);
		
		player.sendMessage(Bungee.teamPrefix + "§8§m--------------------§r§8┃ §cReport §8§m┃--------------------");
		player.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + this.reported.getDisplayName());
		player.sendMessage(Bungee.teamPrefix + "§7Grund §8» §e" + this.grund);
		player.sendMessage(Bungee.teamPrefix + "§7Reportet von §8» " + this.reporter.getDisplayName());
		player.sendMessage(tc);
		player.sendMessage(Bungee.teamPrefix);
		player.sendMessage(action);
		player.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
	}
	
	@SuppressWarnings("deprecation")
	public void accept(ProxiedPlayer dealing) {
		
		Bungee.getInstance().reports.remove(this.reported);
		
		dealing.sendMessage(Bungee.teamPrefix + "§7Du hast den §cReport §7bezüglich " + this.reported.getDisplayName() + " §aangenommen§7.");
		
		if(dealing.getServer().getInfo() != this.reported.getServer().getInfo()) {
			dealing.connect(this.reported.getServer().getInfo());
		}
		
		for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
				if(all != dealing) {
					if(!(Bungee.getInstance().notifyToggle.contains(all))) {
						all.sendMessage(Bungee.teamPrefix + "§7Der §cReport §7bezüglich " + this.reported.getDisplayName() + " §7wurde von " + dealing.getDisplayName() + " §aangenommen§7.");	
					}
				}
			}
		}
		
		ProxiedPlayer reporter = BungeeCord.getInstance().getPlayer(this.reporter.getUniqueId());

		if(reporter != null) {
			reporter.sendMessage(Bungee.prefix + "§7Dein §cReport §7bezüglich " + this.reported.getDisplayName() + " §7wurde von " + dealing.getDisplayName() + " §aangenommen§7.");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void deny(ProxiedPlayer dealing) {

		Bungee.getInstance().reports.remove(this.reported);

		dealing.sendMessage(Bungee.teamPrefix + "§7Du hast den §cReport §7bezüglich " + this.reported.getDisplayName() + " §cabgelehnt§7.");

		for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
				if(all != dealing) {
					if(!(Bungee.getInstance().notifyToggle.contains(all))) {
						all.sendMessage(Bungee.teamPrefix + "§7Der §cReport §7bezüglich " + this.reported.getDisplayName() + " §7wurde von " + dealing.getDisplayName() + " §cabgelehnt§7.");	
					}
				}
			}
		}
		
		ProxiedPlayer reporter = BungeeCord.getInstance().getPlayer(this.reporter.getUniqueId());
		
		if(reporter != null) {
			reporter.sendMessage(Bungee.prefix + "§7Dein §cReport §7bezüglich " + this.reported.getDisplayName() + " §7wurde von " + dealing.getDisplayName() + " §cabgelehnt§7.");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void ban(ProxiedPlayer dealing) {
		
		Bungee.getInstance().reports.remove(this.reported);

		dealing.sendMessage(Bungee.teamPrefix + "§7Du hast den §cReport §7bezüglich " + this.reported.getDisplayName() + " §cabgelehnt§7.");
		
		for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
			if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
				if(all != dealing) {
					if(!(Bungee.getInstance().notifyToggle.contains(all))) {
						all.sendMessage(Bungee.teamPrefix + "§7Der §cReport §7bezüglich " + this.reported.getDisplayName() + " §7wurde von " + dealing.getDisplayName() + " §cabgelehnt§7.");	
					}
				}
			}
		}
		
		ProxiedPlayer reporter = BungeeCord.getInstance().getPlayer(this.reporter.getUniqueId());
		UUID UUID = UUIDFetcher.getUUID(this.reporter.getName());
		String name = UUIDFetcher.getName(UUID);
		
		if(Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Administrator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Developer") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Developer") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Content") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Head-Moderator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Moderator") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Supporter") | Spieler.getPlayerGroup(UUID).equalsIgnoreCase("Builder")) {
			dealing.sendMessage(Bungee.prefix + "§cDu darfst keine §eTeammitglieder §cmuten.");
			return;
		}
		
		if(reporter == null) {
			
			if(MuteManager.isPlayerMuted(UUID)) {
				if(MuteManager.getTime(UUID) != -1 && System.currentTimeMillis() - MuteManager.getTime(UUID) > 0) {
					MuteManager.unmutePlayer(UUID);
				} else {
					dealing.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist schon gemutet.");
					return;
				}
			}
			dealing.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist momentan nicht online.");
			
			MuteManager.mutePlayer(UUID, " Unnötiger Report", dealing.getName(), System.currentTimeMillis() + Long.valueOf(3) * 1000 * 60 * 60 * 24);
			
			dealing.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
			dealing.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
			dealing.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eUnnötiger Report");
			dealing.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» " + dealing.getDisplayName());
			dealing.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e3 Tage");
			dealing.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
			
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
					if(all != dealing) {
						if(!(Bungee.getInstance().notifyToggle.contains(all))) {
							all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
							all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» §e" + name);
							all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eUnnötiger Report");
							all.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» " + dealing.getDisplayName());
							all.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e3 Tage");
							all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
						}
					}
				}
			}
			return;
			
		} else {
			
			if(MuteManager.isPlayerMuted(UUID)) {
				if(MuteManager.getTime(UUID) != -1 && System.currentTimeMillis() - MuteManager.getTime(UUID) > 0) {
					MuteManager.unmutePlayer(UUID);
				} else {
					dealing.sendMessage(Bungee.prefix + "§cDer Spieler §e" + name + "§c ist schon gemutet.");
					return;
				}
			}
			
			MuteManager.mutePlayer(reporter.getUniqueId(), " Unnötiger Report", dealing.getName(), System.currentTimeMillis() + Long.valueOf(3) * 1000 * 60 * 60 * 24);
			
			reporter.sendMessage(" ");
			reporter.sendMessage(Bungee.prefix + "§cDu wurdest §e3 Tage §cvom Netzwerk gemutet.");
			reporter.sendMessage(Bungee.prefix + "§7Grund §8» §eUnnötiger Report");
			reporter.sendMessage(Bungee.prefix + "§7Du kannst auf unserem §eTeamspeak §7einen §aEntbannungsantrag §7stellen.");
			reporter.sendMessage(" ");
			
			dealing.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
			dealing.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + reporter.getDisplayName());
			dealing.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eUnnötiger Report");
			dealing.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» " + dealing.getDisplayName());
			dealing.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e3 Tage");
			dealing.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
			
			for(ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
				if(all.hasPermission("Server.Administrator") | all.hasPermission("Server.Head-Developer") | all.hasPermission("Server.Developer") | all.hasPermission("Server.Content") | all.hasPermission("Server.Head-Moderator") | all.hasPermission("Server.Moderator") | all.hasPermission("Server.Supporter")) {
					if(all != dealing) {
						if(!(Bungee.getInstance().notifyToggle.contains(all))) {
							all.sendMessage(Bungee.teamPrefix + "§8§m----------------------§r§8┃ §cMute §8§m┃---------------------");
							all.sendMessage(Bungee.teamPrefix + "§7Spieler §8» " + reporter.getDisplayName());
							all.sendMessage(Bungee.teamPrefix + "§7Grund §8» §eUnnötiger Report");
							all.sendMessage(Bungee.teamPrefix + "§7Gemutet von §8» " + dealing.getDisplayName());
							all.sendMessage(Bungee.teamPrefix + "§7Gemutete Zeit §8» §e3 Tage");
							all.sendMessage(Bungee.teamPrefix + "§8§m------------------------------------------------");
						}
					}
				}
			}
			return;
			
		}
		
	}
	
}
