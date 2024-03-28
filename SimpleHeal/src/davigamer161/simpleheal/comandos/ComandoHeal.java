package davigamer161.simpleheal.comandos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	private Map<UUID, Long> cooldowns = new HashMap<>();

    private SimpleHeal plugin;
    public ComandoHeal(SimpleHeal plugin){
      this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
		if(sender instanceof Player){
			// Archivos de configuracion y mensajes
			FileConfiguration config = plugin.getConfig();
			FileConfiguration messages = plugin.getMessages();
			// Declarar jugador que usa comando
			Player jugador = (Player) sender;
			// Comando con 1 argumento
			if(args.length == 0){
				// Permiso necesario
				if(jugador.hasPermission("simpleheal.heal")){
					// Localizacion del path
					String poth = "Config.pay-to-heal";
					// Path activado / desactivado
					if(config.getString(poth).equals("true")){
						// Economia de Vault
						Economy econ = plugin.getEconomy();
						double dinero = econ.getBalance(jugador);
						int precio = Integer.valueOf(config.getString("Config.pay-to-heal-price"));
						// Permiso necesario
						if(jugador.hasPermission("simpleheal.econ.exempt")){

							//-------------------------desde aqui---------------------------------------//
							// Localizacion del path
							String path = "Config.wait-to-heal";
							// Permiso necesario 
							// Path activado / desactivado
							if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(path).equals("false")){
								// Curar al jugador
								jugador.setHealth(jugador.getMaxHealth());
								jugador.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(jugador);
							}else{
								// Tiempo para curar al jugador
								timeToHeal(jugador);
							}
							//-------------------------hasta aqui---------------------------------------//

						}else if(dinero >=precio){
							// Quitar dinero a jugador
							econ.withdrawPlayer(jugador, precio);

							//-------------------------desde aqui---------------------------------------//
							// Localizacion del path
							String path = "Config.wait-to-heal";
							// Permiso necesario 
							// Path activado / desactivado
							if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(path).equals("false")){
								// Curar al jugador
								jugador.setHealth(jugador.getMaxHealth());
								jugador.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(jugador);
							}else{
								// Tiempo para curar al jugador
								timeToHeal(jugador);
							}
							//-------------------------hasta aqui---------------------------------------//

						}else{
							// Localizacion del path
							String path = "Config.heal-message";
							// Path activado / desactivado
							if(config.getString(path).equals("true")){
								// Localizacion del mensaje + mensaje
								String mensaje = messages.getString("Messages.heal.not-enought-money");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre)));
							}
						}
					}else{

						//-------------------------desde aqui---------------------------------------//
						// Localizacion del path
						String path = "Config.wait-to-heal";
						// Permiso necesario 
						// Path activado / desactivado
						if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(path).equals("false")){
							// Curar al jugador
							jugador.setHealth(jugador.getMaxHealth());
							jugador.setFoodLevel(20);
							// Mensaje de curacion
							healMethod(jugador);
						}else{
							// Tiempo para curar al jugador
							timeToHeal(jugador);
						}
						//-------------------------hasta aqui---------------------------------------//

					}
				}else{
					noPermMethod(jugador);
				}
			}
			// Comando con 2 argumentos
			else if(args.length == 1){
				// Declarar jugador que recibe comando
				Player target = Bukkit.getPlayer(args[0]);
				// UUID de jugador
				UUID targetID = target.getUniqueId();
				// Permiso necesario
				if(jugador.hasPermission("simpleheal.heal.others")){
					// Localizacion del path
					String poth = "Config.pay-to-heal";
					if(config.getString(poth).equals("true")){
						// Economia de Vault
						Economy econ = plugin.getEconomy();
						double dinero = econ.getBalance(jugador);
						int precio = Integer.valueOf(config.getString("Config.pay-to-heal-price"));
						// Permiso necesario
						if(jugador.hasPermission("simpleheal.econ.exempt.others")){

							//-------------------------desde aqui---------------------------------------//
							// Localizacion del path
							String pith = "Config.wait-to-heal";
							// Permiso necesario 
							// Path activado / desactivado
							if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(pith).equals("false")){
								// Curar al jugador
								target.setHealth(target.getMaxHealth());
								target.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(target);
							}else{
								// Verificar si el jugador está en enfriamiento
								if (cooldowns.containsKey(targetID)) {
									// Localizacion del path
									int puth = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
									// Calculo de segundos
									long secondsLeft = ((cooldowns.get(targetID) / 1000) + puth) - (System.currentTimeMillis() / 1000);
									if (secondsLeft > 0) {
										// Localizacion del mensaje + mensaje
										String mensaje = messages.getString("Messages.heal.wait-time-heal-others");
										jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName()).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
										return true;
									}
								}
								// Curar al jugador
								target.setHealth(target.getMaxHealth());
								target.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(target);
								// Establecer el tiempo de espera
								cooldowns.put(targetID, System.currentTimeMillis());
							}
							//-------------------------hasta aqui---------------------------------------//

							// Localizacion del path
							String path = "Config.heal-message";
							// Path activado / desactivado
							if(config.getString(path).equals("true")){
								// Localizacion del mensaje + mensaje
								String mensaje = messages.getString("Messages.heal.heal-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName())));
							}
						}else if(dinero >=precio){
							// Quitar dinero a jugador
							econ.withdrawPlayer(jugador, precio);
							// Tiempo para curar al jugador
							// Verificar si el jugador está en enfriamiento
							if (cooldowns.containsKey(targetID)) {
								// Localizacion del path
								int path = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
								// Calculo de segundos
								long secondsLeft = ((cooldowns.get(targetID) / 1000) + path) - (System.currentTimeMillis() / 1000);
								if (secondsLeft > 0) {
									// Localizacion del mensaje + mensaje
									String mensaje = messages.getString("Messages.heal.wait-time-heal-others");
									jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName()).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
									return true;
								}
							}

							//-------------------------desde aqui---------------------------------------//
							// Localizacion del path
							String pith = "Config.wait-to-heal";
							// Permiso necesario 
							// Path activado / desactivado
							if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(pith).equals("false")){
								// Curar al jugador
								target.setHealth(target.getMaxHealth());
								target.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(target);
							}else{
								// Verificar si el jugador está en enfriamiento
								if (cooldowns.containsKey(targetID)) {
									// Localizacion del path
									int path = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
									// Calculo de segundos
									long secondsLeft = ((cooldowns.get(targetID) / 1000) + path) - (System.currentTimeMillis() / 1000);
									if (secondsLeft > 0) {
										// Localizacion del mensaje + mensaje
										String mensaje = messages.getString("Messages.heal.wait-time-heal-others");
										jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName()).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
										return true;
									}
								}
								// Curar al jugador
								target.setHealth(target.getMaxHealth());
								target.setFoodLevel(20);
								// Mensaje de curacion
								healMethod(target);
								// Establecer el tiempo de espera
								cooldowns.put(targetID, System.currentTimeMillis());
							}
							//-------------------------hasta aqui---------------------------------------//

							// Localizacion del path
							String path = "Config.heal-message";
							// Path activado / desactivado
							if(config.getString(path).equals("true")){
								// Localizacion del mensaje + mensaje
								String mensaje = messages.getString("Messages.heal.heal-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName())));
							}
						}else{
							// Localizacion del path
							String path = "Config.heal-message";
							// Path activado / desactivado
							if(config.getString(path).equals("true")){
								// Localizacion del mensaje + mensaje
								String mensaje = messages.getString("Messages.heal.not-enought-money-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName())));
							}
						}
					}else{
						// Tiempo para curar al jugador
						// Verificar si el jugador está en enfriamiento
						if (cooldowns.containsKey(targetID)) {
							// Localizacion del path
							int path = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
							// Calculo de segundos
							long secondsLeft = ((cooldowns.get(targetID) / 1000) + path) - (System.currentTimeMillis() / 1000);
							if (secondsLeft > 0) {
								// Localizacion del mensaje + mensaje
								String mensaje = messages.getString("Messages.heal.wait-time-heal-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName()).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
								return true;
							}
						}

						//-------------------------desde aqui---------------------------------------//
						// Localizacion del path
						String pith = "Config.wait-to-heal";
						// Permiso necesario 
						// Path activado / desactivado
						if(jugador.hasPermission("simpleheal.wait-to-heal.exempt") || config.getString(pith).equals("false")){
							// Curar al jugador
							target.setHealth(target.getMaxHealth());
							target.setFoodLevel(20);
							// Mensaje de curacion
							healMethod(target);
						}else{
							// Verificar si el jugador está en enfriamiento
							if (cooldowns.containsKey(targetID)) {
								// Localizacion del path
								int path = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
								// Calculo de segundos
								long secondsLeft = ((cooldowns.get(targetID) / 1000) + path) - (System.currentTimeMillis() / 1000);
								if (secondsLeft > 0) {
									// Localizacion del mensaje + mensaje
									String mensaje = messages.getString("Messages.heal.wait-time-heal-others");
									jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName()).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
									return true;
								}
							}
							// Curar al jugador
							target.setHealth(target.getMaxHealth());
							target.setFoodLevel(20);
							// Mensaje de curacion
							healMethod(target);
							// Establecer el tiempo de espera
							cooldowns.put(targetID, System.currentTimeMillis());
						}
						//-------------------------hasta aqui---------------------------------------//

						// Localizacion del path
						String path = "Config.heal-message";
						// Path activado / desactivado
						if(config.getString(path).equals("true")){
							// Localizacion del mensaje + mensaje
							String mensaje = messages.getString("Messages.heal.heal-others");
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%target%", target.getName())));
						}
					}
				}else{
					noPermMethod(jugador);
				}
			}
		}
		return false;
    }
	private void healMethod(Player jugador){
		// Archivos de configuracion y mensajes
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		// Localizacion del path
		String path = "Config.heal-message";
		// Path activado / desactivado
    	if(config.getString(path).equals("true")){
				// Localizacion del mensaje + mensaje
        String mensaje = messages.getString("Messages.heal.heal");
        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre)));
      }
	}
	private void noPermMethod(Player jugador){
		// Archivos de configuracion y mensajes
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		// Localizacion del path
		String path = "Config.no-perm-message";
		// Path activado / desactivado
		if(config.getString(path).equals("true")){
			// Localizacion del mensaje + mensaje
			String mensaje = messages.getString("Messages.no-perm");
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre)));
		}
	}
	private void timeToHeal(Player jugador){
		// Archivos de configuracion y mensajes
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		// UUID de jugador
		UUID jugadorID = jugador.getUniqueId();
		// Verificar si el jugador está en enfriamiento
		if (cooldowns.containsKey(jugadorID)) {
			// Localizacion del path
			int poth = Integer.valueOf(config.getString("Config.wait-to-heal-time"));
			// Calculo de segundos
			long secondsLeft = ((cooldowns.get(jugadorID) / 1000) + poth) - (System.currentTimeMillis() / 1000);
			if (secondsLeft > 0) {
					// Localizacion del mensaje + mensaje
					String mensaje = messages.getString("Messages.heal.wait-time-heal");
					jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%plugin%", plugin.nombre).replaceAll("%seconds-left%", String.valueOf(secondsLeft))));
					return;
			}
		}
		// Curar al jugador
		jugador.setHealth(jugador.getMaxHealth());
		jugador.setFoodLevel(20);
		// Mensaje de curacion
		healMethod(jugador);
		// Establecer el tiempo de espera
		cooldowns.put(jugadorID, System.currentTimeMillis());
	}
}