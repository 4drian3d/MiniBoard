package me.dreamerzero.miniboard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.dreamerzero.miniboard.MiniBoard;
import me.dreamerzero.miniboard.PlayerScore;
import me.dreamerzero.miniboard.configuration.WorldBundle;

public final class WorldChangeListener implements Listener {
    private final MiniBoard plugin;
    public WorldChangeListener(MiniBoard plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        final WorldBundle bundle = plugin.config().worldScores().get(event.getPlayer().getLocation().getWorld().getName());
        if(bundle == null) return;
        for(PlayerScore score : plugin.scores()) {
            if(score.player().equals(event.getPlayer())) {
                score.changeLines(bundle);
                return;
            }
        }

    }
}
