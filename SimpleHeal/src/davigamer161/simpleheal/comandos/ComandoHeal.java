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
import net.milkbowl.vault.economy.Economy;

public class ComandoHeal implements CommandExecutor{

    private SimpleHeal plugin;

    public ComandoHeal(SimpleHeal plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
        if(!(sender instanceof Player)){
            FileConfiguration config = plugin.getConfig();
            List<String> mensaje = config.getStringList("Config.console-error-text");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    Bukkit.getConsoleSender().sendMessage(texto);
                }
        }else{
        Player jugador = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        Economy econ = plugin.getEconomy();
        double dinero = econ.getBalance(jugador);
        int precio = Integer.valueOf(config.getString("Config.heal-price"));
        if(sender instanceof Player && jugador.hasPermission("simpleheal.heal")){
                if(dinero >=precio){
                econ.withdrawPlayer(jugador, precio);
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
                }
                else{
                 String path = "Config.heal-message";
                 if(config.getString(path).equals("true")){
                List<String> mensaje = config.getStringList("Config.no-enought-money-heal");
                        for(int i=0;i<mensaje.size();i++){
                            String texto = mensaje.get(i);
                            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                        }
                    }
                }
                if(jugador.hasPermission("simpleheal.heal.free")){
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
            }
        }
        else if(sender instanceof Player && !(jugador.hasPermission("simpleheal.heal"))){
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
        return false;
    }
}