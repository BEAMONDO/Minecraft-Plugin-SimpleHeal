package davigamer161.simpleheal.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import davigamer161.simpleheal.SimpleHeal;

public class UpdateChecker implements Listener{
	private SimpleHeal plugin;
	public UpdateChecker(SimpleHeal plugin){
		this.plugin = plugin;
	}
    @EventHandler
    public void CheckUpdate(PlayerJoinEvent event){
    Player jugador = event.getPlayer();
    FileConfiguration config = plugin.getConfig();
    FileConfiguration messages = plugin.getMessages();
    String path = "Config.update-message";
        if(jugador.isOp() && !(plugin.getVersion().equals(plugin.getLatestVersion())) && config.getString(path).equals("true")){
            String mensaje = messages.getString("Messages.update-checker");
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%latestversion%", plugin.getLatestVersion())));
        }
        if(!(jugador.isOp()) && jugador.hasPermission("simpleheal.updatechecker") && !(plugin.getVersion().equals(plugin.getLatestVersion())) && config.getString(path).equals("true")){
            String mensaje = messages.getString("Messages.update-checker");
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%latestversion%", plugin.getLatestVersion())));
        }
    }
}