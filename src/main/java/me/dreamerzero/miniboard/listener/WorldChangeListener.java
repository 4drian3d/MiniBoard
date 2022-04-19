package me.dreamerzero.miniboard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.dreamerzero.miniboard.MiniBoard;
import me.dreamerzero.miniboard.PlayerScore;

public final class WorldChangeListener implements Listener {
    private final MiniBoard plugin;
    public WorldChangeListener(final MiniBoard plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent event){
        final PlayerScore score = plugin.scoreByplayer(event.getPlayer());
        if(score != null) score.reload();
    }
}
