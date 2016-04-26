package com.github.elcreeperhd.CurseHub;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{

	public boolean onCommand(CommandSender cmds, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gm")){
			if(cmds instanceof Player){	
				Player p = (Player) cmds;
				if(args.length == 1 && ( args[0].equals("0") || args[0].equals("1") || args[0].equals("2") || args[0].equals("3") || args[0].equalsIgnoreCase("SURVIVAL") || args[0].equalsIgnoreCase("CREATIVE") || args[0].equalsIgnoreCase("ADVENTURE") || args[0].equalsIgnoreCase("SPECTATOR") )){
					String gm = args[0];
					gm.replaceAll("0", "SURVIVAL");
					gm.replaceAll("1", "CREATIVE");
					gm.replaceAll("2", "ADVENTURE");
					gm.replaceAll("3", "SPECTATOR");
					gm.toUpperCase();
					cmds.sendMessage(ChatColor.GREEN + "Your gamemode was changed!");
					p.setGameMode(GameMode.valueOf(gm));
				}else{	
                   cmds.sendMessage(ChatColor.RED + "The gamemode is not valid or you didn't enter parameters!");
				}//fin else
			}else{	
				cmds.sendMessage(ChatColor.RED + "This command cannot be executed by console");
			}
		}
		return false;
	}

}
