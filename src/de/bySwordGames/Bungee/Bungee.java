/**
 * Die Klasse heißt: Bungee.java
 * Die Klasse wurde am: 09.05.2017 | 13:06:16 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.bySwordGames.Bungee.APIs.Report;
import de.bySwordGames.Bungee.Commands.*;
import de.bySwordGames.Bungee.Listeners.*;
import de.bySwordGames.Bungee.Listeners.PlayerDisconnectListener;
import de.bySwordGames.Bungee.MySQL.MySQL;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Bungee extends Plugin {
	
	private static Bungee plugin;
	private static MySQL sql;
	
	public static String prefix = "§8§l┃ §6SwordGames §8► ";
	public static String teamPrefix = "§8§l┃§c ✚ §8┃ ";
	public static String noPermissions = "§cDu hast keinen Zugriff auf diesen Befehl.";
	public static String unknownCommand = "§cDieser Befehl existiert nicht.";
	
	public ArrayList<UUID> allowedMaintenacePlayers = new ArrayList<>();
	public ArrayList<UUID> blacklistetPlayers = new ArrayList<>();
	public ArrayList<ProxiedPlayer> messageCooldown = new ArrayList<>();
	public ArrayList<ProxiedPlayer> reportCooldown = new ArrayList<>();
	public ArrayList<String> blacklistetWords = new ArrayList<>();
	public ArrayList<UUID> chatCooldown = new ArrayList<>();
	
	public ArrayList<ProxiedPlayer> onlineStaff = new ArrayList<>();
	public ArrayList<ProxiedPlayer> notifyToggle = new ArrayList<>();
	public ArrayList<ProxiedPlayer> msgToggle = new ArrayList<>();
	
	public HashMap<ProxiedPlayer, String> joinMe = new HashMap<>();
	public HashMap<ProxiedPlayer, Report> reports = new HashMap<>();
	public HashMap<ProxiedPlayer, ProxiedPlayer> privatMessage = new HashMap<>();
	public HashMap<UUID, Integer> spamCount = new HashMap<>();
	public HashMap<UUID, Integer> messageCount = new HashMap<>();
	public HashMap<UUID, Integer> werbungCount = new HashMap<>();
	
	public String lobbyServer;
	public String bauServer;
	
	public boolean maintenance;
	
	@Override
	public void onEnable() {
		plugin = this;
		Files.createConfig();
		Files.getInformationsFromConfig();
		sql = new MySQL("localhost", 3306, "server", "root", "SportaKuss007");
		register();
	}
	
	@Override
	public void onDisable() {
		sql.disconnect();
	}

	private void register() {
		PluginManager pm = BungeeCord.getInstance().getPluginManager();
		
		pm.registerListener(plugin, new LoginListener());
		pm.registerListener(plugin, new ProxyPingListener());
		pm.registerListener(plugin, new TabCompleteListener());
		pm.registerListener(plugin, new ServerConnectedListener());
		pm.registerListener(plugin, new ServerConnectListener());
		pm.registerListener(plugin, new PlayerDisconnectListener());
		pm.registerListener(plugin, new ServerKickListener());
		pm.registerListener(plugin, new ChatListener());
		
		pm.registerCommand(plugin, new MaintenanceCommand("maintenance"));
		pm.registerCommand(plugin, new BauserverCommand("bauserver"));
		pm.registerCommand(plugin, new BauserverCommand("build"));
		pm.registerCommand(plugin, new BungeeSystemReloadCommand("bsreload"));
		pm.registerCommand(plugin, new BungeeSystemReloadCommand("bsrl"));
		pm.registerCommand(plugin, new BlacklistCommand("blacklist"));
		pm.registerCommand(plugin, new BlacklistCommand("bl"));
		pm.registerCommand(plugin, new WhitelistCommand("gwhitelist"));
		pm.registerCommand(plugin, new WhitelistCommand("gwl"));
		pm.registerCommand(plugin, new WhitelistCommand("wl"));
		pm.registerCommand(plugin, new ChatclearCommand("chatclear"));
		pm.registerCommand(plugin, new ChatclearCommand("cc"));
		pm.registerCommand(plugin, new RundrufCommand("global"));
		pm.registerCommand(plugin, new RundrufCommand("g"));
		pm.registerCommand(plugin, new JoinmeCommand("joinme"));
		pm.registerCommand(plugin, new LobbyCommand("lobby"));
		pm.registerCommand(plugin, new LobbyCommand("hub"));
		pm.registerCommand(plugin, new LobbyCommand("leave"));
		pm.registerCommand(plugin, new LobbyCommand("l"));
		pm.registerCommand(plugin, new MSGCommand("msg"));
		pm.registerCommand(plugin, new MSGCommand("tell"));
		pm.registerCommand(plugin, new MSGCommand("whisper"));
		pm.registerCommand(plugin, new MSGCommand("w"));
		pm.registerCommand(plugin, new MSGToggleCommand("msgtoggle"));
		pm.registerCommand(plugin, new NotifyToggleCommand("notifytoggle"));
		pm.registerCommand(plugin, new NotifyToggleCommand("ntoggle"));
		pm.registerCommand(plugin, new OnlineCommand("online"));
		pm.registerCommand(plugin, new OnlineCommand("o"));
		pm.registerCommand(plugin, new PingCommand("ping"));
		pm.registerCommand(plugin, new ReplyCommand("reply"));
		pm.registerCommand(plugin, new ReplyCommand("r"));
		pm.registerCommand(plugin, new ReportCommand("report"));
		pm.registerCommand(plugin, new ReportsCommand("reports"));
		pm.registerCommand(plugin, new StaffCommand("staff"));
		pm.registerCommand(plugin, new StaffCommand("staffchat"));
		pm.registerCommand(plugin, new StaffCommand("s"));
		pm.registerCommand(plugin, new StaffOnlineCommand("staffonline"));
		pm.registerCommand(plugin, new StaffOnlineCommand("sonline"));
		pm.registerCommand(plugin, new StaffOnlineCommand("so"));
		pm.registerCommand(plugin, new WhereIsCommand("whereis"));
		
		pm.registerCommand(plugin, new InfoCommand("info"));
		pm.registerCommand(plugin, new KickCommand("kick"));
		pm.registerCommand(plugin, new BanCommand("ban"));
		pm.registerCommand(plugin, new TempBanCommand("tempban"));
		pm.registerCommand(plugin, new TempBanCommand("tban"));
		pm.registerCommand(plugin, new UnBanCommand("unban"));
		pm.registerCommand(plugin, new MuteCommand("mute"));
		pm.registerCommand(plugin, new TempMuteCommand("tempmute"));
		pm.registerCommand(plugin, new TempMuteCommand("tmute"));
		pm.registerCommand(plugin, new UnMuteCommand("unmute"));
		
	}
	
	public static Bungee getInstance() {
		return plugin;
	}
	
}
