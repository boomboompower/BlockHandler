package me.boomboompower;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class BlockHandler extends JavaPlugin implements Listener {
	
	@Override  
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	    saveREADME();
	    saveDefaultConfig();
	    chatHandler();  
	}
	   
	@EventHandler  
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
	    blockTest(p, "break.", e, true); 
	}
	   
	@EventHandler 
	public void blockPlace(BlockPlaceEvent e) {
	      Player p = e.getPlayer();
	      blockTest(p, "place.", e, true); 
	}
	  
	public void saveREADME() {
		File customConfigFile = null;
		if (customConfigFile == null) customConfigFile = new File(getDataFolder(), "README.yml");   
		if (!customConfigFile.exists()) this.saveResource("README.yml", false); 
	}
	   
	public void chatHandler() {
		if(Bukkit.getPluginManager().isPluginEnabled("ChatHandler")) {
			getLogger().info("ChatHandler what are we going to do today?");
			Bukkit.getConsoleSender().sendMessage("[ChatHandler] We are going to take over the world!");
			getConfig().set("ChatHandler.enabled", true);
		} else {
			getConfig().set("ChatHandler.enabled", false); 
		}  
	}
	   
	public void blockTest(Player player, String permission, Event event, Boolean cancelled) {	
		if (event instanceof BlockPlaceEvent) {
			for (Short id : getConfig().getShortList("breaking.blacklistedblocks")) {
				if(!(player.hasPermission(permission + Material.getMaterial(id)))) {
					if (((BlockBreakEvent) event).getBlock().getType() == Material.getMaterial(id)) {
						((BlockBreakEvent) event).setCancelled(cancelled);
					}
				}
			}	
		} else if (event instanceof BlockBreakEvent) {
			for (Short id : getConfig().getShortList("placing.blacklistedblocks")) {
				if(!(player.hasPermission(permission))) {
					if (((BlockPlaceEvent) event).getBlock().getType() == Material.getMaterial(id)) {
						((BlockPlaceEvent) event).setCancelled(cancelled);
					}
				}
			}	
		} else {	
			Plugin p = Bukkit.getPluginManager().getPlugin("BlockHandler");	
			Bukkit.getPluginManager().disablePlugin(p);
		}
	}
}
