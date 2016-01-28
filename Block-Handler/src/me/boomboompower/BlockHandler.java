package me.boomboompower;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
	    Block b = e.getBlock();
	    blockTest(p, "break.stone", b, Material.STONE, e, true);
	    blockTest(p, "break.dirt", b, Material.DIRT, e, true);
	    blockTest(p, "break.grass", b, Material.GRASS, e, true);
	    blockTest(p, "break.bedrock", b, Material.BEDROCK, e, true);  
	}
	   
	@EventHandler 
	public void blockPlace(BlockPlaceEvent e) {
	      Player p = e.getPlayer();
	      Block b = e.getBlock();
	      blockTest(p, "place.stone", b, Material.STONE, e, true);
	      blockTest(p, "place.dirt", b, Material.DIRT, e, true);
	      blockTest(p, "place.grass", b, Material.GRASS, e, true);
	      blockTest(p, "place.bedrock", b, Material.BEDROCK, e, true);  
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
	   
	public static void blockTest(Player player, String permission, Block block, Material material, Event event, Boolean cancelled) {
		if(!(player.hasPermission(permission)) && block.getType() == material) event.setCancelled(cancelled);
	}
}
