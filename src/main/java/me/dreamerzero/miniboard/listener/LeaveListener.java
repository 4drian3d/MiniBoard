package me.dreamerzero.miniboard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dreamerzero.miniboard.MiniBoard;

public class LeaveListener implements Listener {
    private final MiniBoard plugin;
    public LeaveListener(MiniBoard plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onLeave(PlayerQuitEvent event) {
        plugin.scores().removeIf(score -> score.destroyIfPlayerEquals(event.getPlayer()));
    }
}
