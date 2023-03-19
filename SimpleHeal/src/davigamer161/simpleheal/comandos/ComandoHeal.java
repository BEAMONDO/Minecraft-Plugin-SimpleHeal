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
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        Player jugador = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        if(sender instanceof Player && jugador.hasPermission("simpleheal.heal")){
            jugador.setHealth(20);
            jugador.setFoodLevel(20);
            String path = "Config.heal-mensaje";
            if(config.getString(path).equals("true")){
                    List<String> mensaje2 = config.getStringList("Config.heal-permiso-texto");
                        for(int i=0;i<mensaje2.size();i++){
                            String texto = mensaje2.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                        }
                }
        }
        else if(sender instanceof Player && !(jugador.hasPermission("simpleheal.heal"))){
            List<String> mensaje = config.getStringList("Config.heal-no-permiso-texto");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                }
        }else{
                List<String> mensaje = config.getStringList("Config.consola-texto-error");
                    for(int i=0;i<mensaje.size();i++){
                        String texto = mensaje.get(i);
                        Bukkit.getConsoleSender().sendMessage(texto);
                    }
        }
        return false;
    }
}