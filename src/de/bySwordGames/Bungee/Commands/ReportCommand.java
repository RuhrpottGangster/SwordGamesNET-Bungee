/**
 * Die Klasse heißt: ReportCommand.java
 * Die Klasse wurde am: 12.05.2017 | 22:07:37 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee.Commands;

import java.util.concurrent.TimeUnit;

import de.bySwordGames.Bungee.Bungee;
import de.bySwordGames.Bungee.APIs.Report;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {

	public ReportCommand(String name) {
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
		
		if(args.length == 0) {
			player.sendMessage(Bungee.prefix + "§cDu musst einen Spielernamen angeben, um ihn zu melden.");
			return;
		}
		
		if(args.length == 1) {
			player.sendMessage(Bungee.prefix + "§cDu musst einen Grund angeben, um diesen Spieler zu melden.");
			return;
		}
		ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
		
		if(target == null) {
			player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c ist nicht online.");
			return;
		}
		
		if(target == player) {
			player.sendMessage(Bungee.prefix + "§cDu darfst dich nicht selbst melden.");
			return;
		}
		
		if(Bungee.getInstance().onlineStaff.contains(target)) {
			player.sendMessage(Bungee.prefix + "§cDu darfst keine §eTeammitglieder §cmelden.");
			return;
		}
		
		if(Bungee.getInstance().reports.containsKey(target)) {
			player.sendMessage(Bungee.prefix + "§cDer Spieler §e" + args[0] + "§c wurde bereits reportet.");
			return;
		}
		
		if(Bungee.getInstance().reportCooldown.contains(player)) {
			player.sendMessage(Bungee.prefix + "§cDu darfst nur alle §e5 Sekunden §ceinen Spieler melden.");
			return;
		}
		
		String grund = "";
		for(int i = 1; i < args.length; i++) {
			grund = grund + args[i] + " ";
		}
		
		Report report = new Report(target, grund, player);
		report.announce();
		
		player.sendMessage(Bungee.prefix + "§7Dein Report über " + target.getDisplayName() + "§7 wurde erstellt!");
		player.sendMessage(Bungee.prefix + "§cMissbrauch wird mit einem Mute bestraft.");
		
		Bungee.getInstance().reportCooldown.add(player);
		BungeeCord.getInstance().getScheduler().schedule(Bungee.getInstance(), new Runnable() {

			@Override
			public void run() {
				Bungee.getInstance().reportCooldown.remove(player);
			}
			
		}, 5, TimeUnit.SECONDS);
		
	}

}
