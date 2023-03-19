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


public class ComandoPrincipal implements CommandExecutor{

    private SimpleHeal plugin;

    public ComandoPrincipal(SimpleHeal plugin){
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        if(!(sender instanceof Player)){
            FileConfiguration messages = plugin.getConfig();
            List<String> mensaje = messages.getStringList("Config.consola-texto-error");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    Bukkit.getConsoleSender().sendMessage(texto);
                }
        }
        else{
            Player jugador = (Player) sender;
            FileConfiguration messages = plugin.getConfig();
            FileConfiguration config = plugin.getConfig();

            
//-------------------------------------Comando version----------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
if(args.length > 0){
    if(args[0].equalsIgnoreCase("version")){
        if(sender instanceof Player && (jugador.hasPermission("simpleheal.version"))){
            String path = "Config.version-mensaje";
            if(config.getString(path).equals("true")){
                jugador.sendMessage(ChatColor.GREEN+"Version: "+ChatColor.YELLOW+plugin.version);
            }
        }
        if(sender instanceof Player && !(jugador.hasPermission("simpleheal.version"))){
            String path = "Config.version-mensaje";
            if(config.getString(path).equals("true")){
                List<String> mensaje = config.getStringList("Config.version-no-texto");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                }
            }
        }
    }
    else if(args[0].equalsIgnoreCase("ver")){
        if(sender instanceof Player && (jugador.hasPermission("simpleheal.version"))){
            String path = "Config.version-mensaje";
            if(config.getString(path).equals("true")){
                jugador.sendMessage(ChatColor.GREEN+"Version: "+ChatColor.YELLOW+plugin.version);
            }
        }
        if(sender instanceof Player && !(jugador.hasPermission("simpleheal.version"))){
            String path = "Config.version-mensaje";
            if(config.getString(path).equals("true")){
                List<String> mensaje = config.getStringList("Config.version-no-texto");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                }
            }
        }
        
    }
//----------------------------------------Hasta aqui---------------------------------------//

                

//---------------------------------------Comando help--------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("help")){
                    if(sender instanceof Player && (jugador.hasPermission("simpleheal.help"))){
                        String path = "Config.help-mensaje";
                        if(config.getString(path).equals("true")){
                            List<String> mensaje = messages.getStringList("Config.help-texto");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                            }
                        }
                    }
                }
 //----------------------------------------Hasta aqui---------------------------------------//

                

 //--------------------------------------Comando reload---------------------------------------------------------//
 //----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("reload")){                    
                    if(sender instanceof Player && (jugador.hasPermission("simpleheal.reload"))){
                        String path = "Config.reload-mensaje";
                        plugin.reloadConfig();
                        if(config.getString(path).equals("true")){
                            List<String> mensaje = messages.getStringList("Config.reload-permiso-texto");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                            }
                        }
                    }else if(sender instanceof Player && !(jugador.hasPermission("simpleheal.reload"))){
                        List<String> mensaje = messages.getStringList("Config.reload-no-permiso-texto");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                            }
                        }                    
 //----------------------------------------Hasta aqui---------------------------------------//


                    else{
                        List<String> mensaje = messages.getStringList("Config.comando-no-existe");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                            }
                        }
            }else if(args.length > 0){
                String path = "Config.help-mensaje";
                if(config.getString(path).equals("false")){                    
                }else{
                    List<String> mensaje = messages.getStringList("Config.comando-no-argumento");
                    for(int i=0;i<mensaje.size();i++){
                        String texto = mensaje.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName())));
                    }
                }
                }else if(args.length == 0){
                    String path = "Config.help-mensaje";
                    if(config.getString(path).equals("false")){                        
                    }
                }
            }
            return false;
        }
        return false;
    }
}