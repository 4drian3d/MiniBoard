package net.dreamerzero.miniboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();
	
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		
		getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
		
		Bukkit.getConsoleSender().sendMessage("<-------------->");
		Bukkit.getConsoleSender().sendMessage(" ______  _       _ ______                       _ ");
		Bukkit.getConsoleSender().sendMessage("|  ___ \(_)     (_|____  \                     | |");
		Bukkit.getConsoleSender().sendMessage("| | _ | |_ ____  _ ____)  ) ___   ____  ____ _ | |");
		Bukkit.getConsoleSender().sendMessage("| || || | |  _ \| |  __  ( / _ \ / _  |/ ___) || |");
		Bukkit.getConsoleSender().sendMessage("| || || | | | | | | |__)  ) |_| ( ( | | |  ( (_| |");
		Bukkit.getConsoleSender().sendMessage("|_||_||_|_|_| |_|_|______/ \___/ \_||_|_|   \____| By 4drian3d");
		Bukkit.getConsoleSender().sendMessage("                                                  ");
		Bukkit.getConsoleSender().sendMessage("<-------------->");
    }
	
	@EventHandler
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
	
	private void updateBoard(FastBoard board) {
        board.updateLines(
                "------------------",
                "[Perfil]",
                "4drian3d",
                "Ping ",
                "------------------",
				"Modalidad",
				"Towny: ",
				"SkyBlock",
				"Somos 20 Online",
				"------------------",
				"Yo me quedo en PVK",
				"play.peruviankkit.net"
        );
    }
}