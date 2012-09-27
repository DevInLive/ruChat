package ru.cubelife.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Utils {
	
	public static void privateMessage(Player p, String msg) {
		Location pLoc = p.getLocation();
		
		String m = ruChat.cfg.getString("private-chat-format");
		m = replaceSpecials(m, msg, p);
		
		double range = Math.pow(ruChat.cfg.getInt("private-chat-range"), 2);
		
		int i = 0;
		for(Player r : Bukkit.getOnlinePlayers()) {
			if(pLoc.distanceSquared(r.getLocation()) < range) {
				r.sendMessage(m);
				i++;
			}
		}
		
		p.sendMessage(ChatColor.BLUE + "(" + i + ") " + ChatColor.WHITE + m);
	}
	
	public static void globalMessage(Player p, String msg) {
		String m = ruChat.cfg.getString("global-chat-format");
		m = replaceSpecials(m, msg, p);
		for(Player r : Bukkit.getOnlinePlayers()) {
			r.sendMessage(m);
		}
	}
	
	public static void saleMessage(Player p, String msg) {
		String m = ruChat.cfg.getString("sale-chat-format");
		m = replaceSpecials(m, msg, p);
		for(Player r : Bukkit.getOnlinePlayers()) {
			r.sendMessage(m);
		}
	}
	
	public static void helpMessage(Player p, String msg) {
		String m = ruChat.cfg.getString("help-chat-format");
		m = replaceSpecials(m, msg, p);
		for(Player r : Bukkit.getOnlinePlayers()) {
			if(ruChat.modes.get(r) == ChatMode.HELP || r.hasPermission("ruchat.help")) {
				r.sendMessage(m);
			}
		}
	}
	
	public static void changeMode(Player p, ChatMode cm) {
		if(ruChat.modes.containsKey(p)) {
			ruChat.modes.remove(p);
		}
		ruChat.modes.put(p, cm);
		p.sendMessage(ChatColor.AQUA + "Режим чата успешно изменен!");
	}
	
	public static String replaceSpecials(String s, String msg, Player p) {
        String worldName = p.getWorld().getName();
        if(ruChat.usePex) {
        	PermissionUser user = PermissionsEx.getPermissionManager().getUser(p);
        	s = s.replaceAll("$player", p.getDisplayName()).replaceAll("$world", worldName).replaceAll("$msg", msg).replaceAll("$prefix", user.getPrefix(worldName).replaceAll("$suffix", user.getSuffix(worldName)));
        } else {
        	s = s.replaceAll("$player", p.getDisplayName()).replaceAll("$world", worldName).replaceAll("$msg", msg).replaceAll("$prefix", "").replaceAll("$suffix", "");
        }
		return s;
	}
	
	public static String sMsg(String[] m) {
		String msg = "";
		for(String w : m) {
			msg += w;
		}
		return msg;
	}

}
