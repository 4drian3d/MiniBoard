package net.dreamerzero.miniboard;

import fr.mrmicky.fastboard.FastBoard;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

	private final Map<UUID, FastBoard> boards = new HashMap<>();
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		getLogger().info("<-------------->");
		getLogger().info("MiniBoard enabling");
		getLogger().info("<-------------->");
		
		getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                board.updateLines(
					ChatColor.GRAY + "------------------",
					ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "Perfil" + ChatColor.DARK_GRAY + "]",
					ChatColor.GRAY + "------------------",
				);
            }
        }, 0, 20);
	}
	
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		FastBoard board = new FastBoard(player);
		
		board.updateTitle(ChatColor.RED + "Peru" + ChatColor.WHITE + "vian" + ChatColor.RED + "kkit");

        this.boards.put(player.getUniqueId(), board);
	}
	
	@EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());
		
		if (board != null) {
            board.delete();
        }
	}
}