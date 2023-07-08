package davigamer161.simpleheal.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import davigamer161.simpleheal.SimpleHeal;

public class ComandoHeal implements CommandExecutor{

    private SimpleHeal plugin;

    public ComandoHeal(SimpleHeal plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
    if(sender instanceof Player){
	    FileConfiguration config = plugin.getConfig();
	    Player jugador = (Player) sender;
		if(args.length == 0){
		    if(jugador.hasPermission("simpleheal.heal")){
                jugador.setHealth(20);
                jugador.setFoodLevel(20);
			    String path = "Config.heal-message";
			    if(config.getString(path).equals("true")){
			        List<String> mensaje = config.getStringList("Config.heal-text");
				    for(int i=0;i<mensaje.size();i++){
					    String texto = mensaje.get(i);
					    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
				    }
                }
                return true;
            }else{
                String path = "Config.heal-message";
                if(config.getString(path).equals("true")){
                    List<String> mensaje = config.getStringList("Config.no-perm-text");
                    for(int i=0;i<mensaje.size();i++){
                        String texto = mensaje.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                    }
                }
            }
        }else if(args.length == 1){
			if(jugador.hasPermission("simpleheal.heal.others")){
			    Player target = Bukkit.getPlayer(args[0]);
		        if(target.hasPermission("simpleheal.heal")){
                    target.setHealth(20);
                    target.setFoodLevel(20);
			        String path = "Config.heal-message";
			        if(config.getString(path).equals("true")){
			            List<String> mensaje = config.getStringList("Config.heal-text");
				        for(int i=0;i<mensaje.size();i++){
					        String texto = mensaje.get(i);
					        target.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
				        }
                    }
                    return true;
                }else{
                    String path = "Config.heal-message";
                    if(config.getString(path).equals("true")){
                        List<String> mensaje = config.getStringList("Config.no-perm-heal-others-text");
                        for(int i=0;i<mensaje.size();i++){
                            String texto = mensaje.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
                        }
                    }
                }
			}else{
				String path = "Config.no-perm";
				if(config.getString(path).equals("true")){
					List<String> mensaje = config.getStringList("Config.no-perm-text");
					for(int i=0;i<mensaje.size();i++){
						String texto = mensaje.get(i);
						jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
					}
				}
			}
		}
	}
	return false;
    }
}
