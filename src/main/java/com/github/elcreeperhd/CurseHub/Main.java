package com.github.elcreeperhd.CurseHub;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public Logger log = Bukkit.getLogger();
	public EventListener event = new EventListener(this);

	public void onEnable(){	
		getServer().getPluginManager().registerEvents(event,this);
		getCommand("gm").setExecutor(new Commands());
		log.info("CurseHub is enabled");
		}
	   
	
	public void onDisable(){	
		log.info("CurseHub is disabled");
	}
	
	
	
}
