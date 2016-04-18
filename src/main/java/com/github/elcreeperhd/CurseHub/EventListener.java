package com.github.elcreeperhd.CurseHub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
		String invname = ChatColor.GREEN + "" + ChatColor.GREEN + "Server selector";
		if(!ev.getWhoClicked().hasPermission("curse.admin")){			

			ev.setCancelled(true);
		}//end perm

		if(ev.getInventory().getTitle().equalsIgnoreCase(invname)){
			ev.setCancelled(true);
			if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)){	
				bungee.sendToServer((Player) ev.getWhoClicked(), "factions");
			}	
			else if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.IRON_FENCE)){	
				bungee.sendToServer((Player) ev.getWhoClicked(), "prison");
			}
			else if(!ev.getCurrentItem().equals(null) && ev.getCurrentItem().getType().equals(Material.NETHER_STAR)){	
				bungee.sendToServer((Player) ev.getWhoClicked(), "lobby");
			}
		}
	}
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent ev){
		Location hub = new Location(Bukkit.getWorld("world"), 248, 55, 1362);
		ev.getPlayer().teleport(hub);
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemStack topple = new ItemStack(Material.STICK);
		ItemMeta topplem = topple.getItemMeta();
		topplem.addEnchant(Enchantment.KNOCKBACK, 10, true);
		topple.setItemMeta(topplem);
		/*	    ItemMeta compassm = compass.getItemMeta();
	    List<String> lorec = new ArrayList<String>();
	    lorec.add("");
	    compassm.setLore(lorec);
	    compass.setItemMeta(compassm);*/

		ev.getPlayer().getInventory().setItem(0, compass);
		ev.getPlayer().getInventory().setItem(1, topple);

	}
	@EventHandler
	public void DragEvent(InventoryDragEvent ev){
		if(!ev.getWhoClicked().hasPermission("curse.admin")){	
			ev.setCancelled(true);
		}	
	}
	@EventHandler
	public void Interact(PlayerInteractEvent ev){	

		ItemStack factions = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack lobby = new ItemStack(Material.NETHER_STAR);
		ItemStack jail = new ItemStack(Material.IRON_FENCE);
		ItemMeta factionsi = factions.getItemMeta();
		ItemMeta lobbyi = lobby.getItemMeta();
		ItemMeta jaili = jail.getItemMeta();
		factionsi.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Factions");
		jaili.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Prison");
		lobbyi.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Lobby");
		factions.setItemMeta(factionsi);
		jail.setItemMeta(jaili);
		lobby.setItemMeta(lobbyi);
		String invname = ChatColor.GREEN + "" + ChatColor.GREEN + "Server selector";
		Inventory menu = Bukkit.createInventory(null,9,invname);
		menu.setItem(0,factions);
		menu.setItem(1,lobby);
		menu.setItem(2,jail);
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
			//ev.getPlayer().updateInventory();
		}	
	}

}
