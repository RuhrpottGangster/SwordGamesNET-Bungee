package de.bySwordGames.Bungee.Commands;

import de.bySwordGames.Bungee.Bungee;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Diese Klasse ( BauserverCommand ) wurde von ByteGames erstellt!
 * Am folgenden Datum: 24.06.2017 - 09:12
 */
public class BauserverCommand extends Command {

    public BauserverCommand(String name) {
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

        if(player.hasPermission("Server.Administrator") | player.hasPermission("Server.Head-Developer") | player.hasPermission("Server.Developer") | player.hasPermission("Server.Content") | player.hasPermission("Server.Head-Moderator") | player.hasPermission("Server.Moderator") | player.hasPermission("Server.Builder")) {
            if(args.length == 0) {

                player.connect(BungeeCord.getInstance().getServerInfo(Bungee.getInstance().bauServer));
                player.sendMessage(Bungee.prefix + "§eDu wirst mit dem Bauserver verbunden.");

            } else {
                player.sendMessage(Bungee.prefix + Bungee.unknownCommand);
            }
        } else {
            player.sendMessage(Bungee.prefix + Bungee.noPermissions);
        }

    }

}
