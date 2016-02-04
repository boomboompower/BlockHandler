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
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

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
	public void bucketFillEvent(PlayerBucketFillEvent e) {
		if (getConfig().getBoolean("other.disablebuckets")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void bucketEmptyEvent(PlayerBucketEmptyEvent e) {
		if (getConfig().getBoolean("other.disablebuckets")) {
			e.setCancelled(true);
		}
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
	   
	public void chatHandler() {
		if(Bukkit.getPluginManager().isPluginEnabled("ChatHandler")) {
			broadcast("&b[BlockHandler] &3ChatHandler what are we going to do today?");
			broadcast("&b[ChatHandler] &3We are going to take over the world!");
		}
	}
	   
	public void blockTest(Player player, String permission, Event event, Boolean cancelled) {
		if (player == null) return;
		if (event == null) return;
		if (event instanceof BlockBreakEvent) {
			for (Short id : getConfig().getShortList("placing.blacklistedblocks")) {
				for (String message : getConfig().getStringList("placing.denymessage.message")) {
					if(!(player.hasPermission(permission))) {
						if (((BlockBreakEvent) event).getBlock().getType() == Material.getMaterial(id)) {
							((BlockBreakEvent) event).setCancelled(cancelled);	
							if(getConfig().getBoolean("placing.denymessage.enabled")) player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
						}
					}
				}
			}	
		} else if (event instanceof BlockPlaceEvent) {
			for (Short id : getConfig().getShortList("breaking.blacklistedblocks")) {
				if(!(player.hasPermission(permission + Material.getMaterial(id)))) {
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
	
	public void saveREADME() {
		File customConfigFile = null;
		if (customConfigFile == null) customConfigFile = new File(getDataFolder(), "README.yml");   
		if (!customConfigFile.exists()) this.saveResource("README.yml", false); 
	}
	
	private static void broadcast(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}
