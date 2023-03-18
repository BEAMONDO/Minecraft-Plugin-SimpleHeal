package davigamer161.simpleheal.comandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import davigamer161.simpleheal.SimpleHeal;


public class ComandoPrincipal implements CommandExecutor{

    private SimpleHeal plugin;

    public ComandoPrincipal(SimpleHeal plugin){
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        if(!(sender instanceof Player)){
            Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.WHITE+" No puedes ejecutar este comando desde la consola");
            return false;
        }
        else{
            Player jugador = (Player) sender;
//-------------------------------------Comando version----------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
           if(args.length > 0){
                if(args[0].equalsIgnoreCase("version")){
                    jugador.sendMessage(plugin.nombre+ChatColor.WHITE+"Version: "+ChatColor.YELLOW+plugin.version);
                    return true;
                }
                else if(args[0].equalsIgnoreCase("ver")){
                    jugador.sendMessage(plugin.nombre+ChatColor.WHITE+"Version: "+ChatColor.YELLOW+plugin.version);
                    return true;
                }
//----------------------------------------Hasta aqui---------------------------------------//

                

//---------------------------------------Comando help--------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("help")){
                    jugador.sendMessage(ChatColor.BLUE+"<-----------------"+ChatColor.GREEN+"COMANDOS"+ChatColor.BLUE+"----------------->");
                    jugador.sendMessage(ChatColor.YELLOW+" /sh version "+ChatColor.WHITE+ChatColor.WHITE+"Version plugin");
                    jugador.sendMessage(ChatColor.YELLOW+" /sh reload "+ChatColor.WHITE+"Recargar plugin");
                    jugador.sendMessage(ChatColor.YELLOW+" /heal "+ChatColor.WHITE+"Curarte");
                    return true;
                }
 //----------------------------------------Hasta aqui---------------------------------------//

                

 //--------------------------------------Comando reload---------------------------------------------------------//
 //----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("reload")){
                    plugin.reloadConfig();
                    jugador.sendMessage(plugin.nombre+ChatColor.WHITE+"El plugin ha sido recargado correctamente");
                    return true;
                }
 //----------------------------------------Hasta aqui---------------------------------------//
                else{
                    jugador.sendMessage(plugin.nombre+ChatColor.RED+"Ese comando no existe");
                    return true;
                }
           }else{
                jugador.sendMessage(plugin.nombre+ChatColor.WHITE+" Usa /kmc help para ver la lista de comandos");
                return true;
           }
        }
    }
}
