/**
 * Die Klasse heißt: Files.java
 * Die Klasse wurde am: 09.05.2017 | 13:09:40 erstellt.
 * Der Author der Klasse ist: bySwordGames
 */
package de.bySwordGames.Bungee;

import java.io.File;
import java.util.UUID;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Files {
	
	public static File file;
	public static Configuration cfg;
	
	public static void createConfig() {
        if(!(Bungee.getInstance().getDataFolder().exists())) {
        	Bungee.getInstance().getDataFolder().mkdir();
		}
		
        file = new File(Bungee.getInstance().getDataFolder().getPath(), "config.yml");

		if (!(file.exists())) {
            try {
				java.nio.file.Files.copy(Bungee.getInstance().getResourceAsStream("config.yml"), file.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void getInformationsFromConfig() {
		try {
			
			Bungee.getInstance().lobbyServer = cfg.getString("Administration.Lobby-Server");
			Bungee.getInstance().bauServer = cfg.getString("Administration.Bau-Server");
			Bungee.getInstance().maintenance = cfg.getBoolean("Administration.Maintenance.Status");
			
			for(String blacklistetUUIDs : cfg.getStringList("Administration.Blacklistet-UUIDs")) {
				Bungee.getInstance().blacklistetPlayers.add(UUID.fromString(blacklistetUUIDs));
			}
			
			for(String maintenanceAllowed : Files.cfg.getStringList("Administration.Maintenance-Allowed")) {
				Bungee.getInstance().allowedMaintenacePlayers.add(UUID.fromString(maintenanceAllowed));
			}
			
			for(String blacklistetWords : Files.cfg.getStringList("Administration.Chat.BlacklistetWords")) {
				Bungee.getInstance().blacklistetWords.add(blacklistetWords);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveConfig() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
