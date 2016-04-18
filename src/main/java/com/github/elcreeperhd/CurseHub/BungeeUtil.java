package com.github.elcreeperhd.CurseHub;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeUtil {
	private Main plugin;
	public BungeeUtil(Main plugin){	
		this.plugin = plugin;
	}
	public void sendToServer(Player player, String server){	
		player.sendMessage(ChatColor.GREEN + "Sending to " + server);

	plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
	  ByteArrayDataOutput out = ByteStreams.newDataOutput();
	  out.writeUTF("ConnectOther");
	  out.writeUTF(player.getName());
	  out.writeUTF(server);
	  player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
}
