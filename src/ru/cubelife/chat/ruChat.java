package ru.cubelife.chat;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ruChat extends JavaPlugin {
	
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
		this.saveCfg();
		
		this.log = Logger.getLogger("Minecraft");
		
		this.pl = getServer().getPluginManager();
		
		this.pl.registerEvents(new ChatListener(), this);
		
		this.log("Enabled!");
	}
	
	public void onDisable() {
		
	}
	
	private void log(String msg) {
		this.log.info(msg);
	}
	
	private void loadCfg() {
		
	}
	
	private void saveCfg() {
		
	}

}
