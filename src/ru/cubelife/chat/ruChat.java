package ru.cubelife.chat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ruChat extends JavaPlugin {
	
	/** ������������ �� PEX */
	public static boolean usePex;
	
	/** ������ ���� ������� */
	public static HashMap<Player, ChatMode> modes;
	
	/** ���������������� ���� */
	public static FileConfiguration cfg;
	
	/** ���� ������� */
	private File f;
	
	/** ������ */
	private Logger log;
	
	/** ������-�������� */
	private PluginManager pl;
	
	public void onEnable() {
		this.f = new File(getDataFolder(), "config.yml");
		ruChat.cfg = YamlConfiguration.loadConfiguration(f);
		this.loadCfg();
		
		ruChat.modes = new HashMap<Player, ChatMode>();
		
		this.log = Logger.getLogger("Minecraft");
		
		this.pl = getServer().getPluginManager();
		
		ruChat.usePex = false;
		if(pl.getPlugin("PermissionsEx") != null) {
			ruChat.usePex = true;
		}
		
		this.pl.registerEvents(new ChatListener(), this);
		
		List<PluginCommand> cmds = new ArrayList<PluginCommand>();
		
		cmds.add(getCommand("global")); cmds.add(getCommand("g"));
		cmds.add(getCommand("private")); cmds.add(getCommand("p"));
		cmds.add(getCommand("sale")); cmds.add(getCommand("s"));
		cmds.add(getCommand("help")); cmds.add(getCommand("h"));
		cmds.add(getCommand("chat"));
		
		CommandsExecutor ex = new CommandsExecutor();
		
		for(PluginCommand cmd : cmds) {
			cmd.setExecutor(ex);
		}
		
		this.log("Enabled!");
	}
	
	public void onDisable() {
		this.log("Disabled!");
	}
	
	private void log(String msg) {
		this.log.info(msg);
	}
	
	private void loadCfg() {
		ruChat.cfg.set("private-chat-format", ruChat.cfg.getString("private-chat-format", "$prefix$player$suffix: $msg"));
		ruChat.cfg.set("private-chat-range" ,ruChat.cfg.getInt("private-chat-range", 150));
		ruChat.cfg.set("global-chat-format", ruChat.cfg.getString("global-chat-format", "&a[G] &f$prefix$player$suffix: &b$msg"));
		ruChat.cfg.set("sale-chat-format", ruChat.cfg.getString("sale-chat-format", "&e[&5SALE&e] &f$prefix$player$suffix: &6$msg"));
		ruChat.cfg.set("help-chat-format", ruChat.cfg.getString("help-chat-format", "&7[HELP] &f$prefix$player$suffix: &o$msg"));
		try {
			ruChat.cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
