package com.github.elcreeperhd.CurseHub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EventListener implements Listener{

	private Main plugin;

	public EventListener(Main plugin)
	{
		this.plugin = plugin;
	}


	@EventHandler
	public void ChatEvent(AsyncPlayerChatEvent ev){	
		String beforename = " " + ChatColor.GREEN + "" + ChatColor.BOLD + ">" + " ";
		PermissionUser user = PermissionsEx.getUser(ev.getPlayer());
		String prefix = user.getPrefix(); 
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		if(ev.getPlayer().hasPermission("curse.admin")){	
			String message = ev.getMessage();
			message = ChatColor.translateAlternateColorCodes('&', message);
			ev.setFormat(prefix + ev.getPlayer().getName() + beforename + ChatColor.RESET + message);						
		}else{	
			ev.setFormat(prefix + ev.getPlayer().getName() + beforename + ChatColor.RESET + ev.getMessage());			
		}	
	}
	@EventHandler(ignoreCancelled=true)
	public void CraftEvent(CraftItemEvent ev){	
		if(!ev.getWhoClicked().hasPermission("curse.admin")){	
			ev.setCancelled(true);	
		}
	}	

	@EventHandler(ignoreCancelled=true)
	public void InventoryClick(InventoryClickEvent ev){
		BungeeUtil bungee = new BungeeUtil(plugin);
		String invname = ChatColor.GREEN + "" + ChatColor.BOLD + "Server selector";
		if(!ev.getWhoClicked().hasPermission("curse.admin")){			

			ev.setCancelled(true);
		}//end perm

		if(ev.getInventory().getTitle().equalsIgnoreCase(invname)){
			ev.setCancelled(true);
			if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)){	
				if(ev.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Factions")){	
				bungee.sendToServer((Player) ev.getWhoClicked(), "factions");
				}else{	
				bungee.sendToServer((Player) ev.getWhoClicked(), "kitpvp");	
				}
			}	
			else if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.GRASS)){	
			    bungee.sendToServer((Player) ev.getWhoClicked(), "creative");
			}
			else if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.DIRT)){	
				bungee.sendToServer((Player) ev.getWhoClicked(), "skyblock");
			}
		}
	}
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent ev){
		OfflinePlayer op = Bukkit.getOfflinePlayer(ev.getPlayer().getUniqueId());
		if(!op.hasPlayedBefore()){	
			ev.getPlayer().sendMessage(ChatColor.GREEN  + "" + ChatColor.BOLD + "Welcome to CurseNetwork " + ChatColor.YELLOW  + "" + ev.getPlayer().getName() + ChatColor.GREEN + "" + "!");
		}else{	
			ev.getPlayer().sendMessage(ChatColor.GREEN + "Welcome back, " + ChatColor.YELLOW  + "" + ev.getPlayer().getName() + ChatColor.GREEN + "!"); 
		}
		ev.setJoinMessage("");
		//Start of item related stuff

		Location hub = new Location(Bukkit.getWorld("world"), 248, 55, 1362);
		ev.getPlayer().teleport(hub);
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassm = compass.getItemMeta();
		ItemStack topple = new ItemStack(Material.STICK);
		ItemMeta topplem = topple.getItemMeta();
		topplem.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Da Stick");
		compassm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Server Selector");
		topplem.addEnchant(Enchantment.KNOCKBACK, 10, true);
		topple.setItemMeta(topplem);
		compass.setItemMeta(compassm);
		ev.getPlayer().getInventory().setItem(0, compass);
		ev.getPlayer().getInventory().setItem(1, topple);

	}
	@EventHandler
	public void DragEvent(InventoryDragEvent ev){
		if(!ev.getWhoClicked().hasPermission("curse.admin")|| ev.getInventory().getName().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Server selector")){	
			ev.setCancelled(true);
		}	
	}
	@EventHandler
	public void Interact(PlayerInteractEvent ev){	

		ItemStack factions = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack creative = new ItemStack(Material.GRASS);
		ItemStack skyblock = new ItemStack(Material.DIRT);
		ItemStack kitPvP = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta factionsi = factions.getItemMeta();
		ItemMeta creativei = creative.getItemMeta();
		ItemMeta skyblocki = skyblock.getItemMeta();
		ItemMeta kitPvPi = kitPvP.getItemMeta();
		kitPvPi.addEnchant(Enchantment.DAMAGE_ALL, 10, false);
		factionsi.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Factions");
		creativei.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Creative");
		skyblocki.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "SkyBlock");	
		kitPvPi.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "KitPvP");	
		factions.setItemMeta(factionsi);
		creative.setItemMeta(creativei);
		skyblock.setItemMeta(skyblocki);
		kitPvP.setItemMeta(kitPvPi);
		String invname = ChatColor.GREEN + "" + ChatColor.BOLD + "Server selector";
		Inventory menu = Bukkit.createInventory(null,9,invname);
		menu.setItem(0,factions);
		menu.setItem(1,creative);
		menu.setItem(2,skyblock);
		menu.setItem(3,kitPvP);
		if(!ev.getPlayer().getItemInHand().equals(null) && ev.getPlayer().getItemInHand().getType().equals(Material.COMPASS)){
			ev.getPlayer().openInventory(menu);
			ev.setCancelled(true);


		}
		if(!ev.getPlayer().hasPermission("curse.admin")){	
			ev.setCancelled(true);
		}	
	}
	@EventHandler
	public void DropEvent(PlayerDropItemEvent ev){
		if(!ev.getPlayer().hasPermission("curse.admin")){	
			ev.setCancelled(true);

		}	
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent ev){	
		ev.setQuitMessage("");
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent ev){	
		if(ev.getDamager() instanceof Player && ev.getEntity() instanceof Player){
			Player damager = (Player) ev.getDamager();
			Player damaged = (Player) ev.getEntity();
			if(damager.getItemInHand().getType().equals(Material.STICK)){	
				ev.setDamage(0);
			}else{	
				ev.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent ev){	
		ev.setCancelled(true);
	}


}
