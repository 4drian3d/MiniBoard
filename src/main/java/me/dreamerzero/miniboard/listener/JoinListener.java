package me.dreamerzero.miniboard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.dreamerzero.miniboard.MiniBoard;
import me.dreamerzero.miniboard.PlayerScore;

public final class JoinListener implements Listener {
    private final MiniBoard plugin;
    public JoinListener(MiniBoard plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onjoin(final PlayerJoinEvent event) {
        plugin.scores().add(new PlayerScore(event.getPlayer(), plugin));
    }
}
