package net.dreamerzero.miniboard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage("MiniBoard Enabled");
    }
}