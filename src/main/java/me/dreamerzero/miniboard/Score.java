package me.dreamerzero.miniboard;

import org.bukkit.entity.Player;

import net.megavex.scoreboardlibrary.api.ScoreboardManager;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

public final class Score {
    private final Sidebar sidebar;
    private final Player player;

    public Score(Player player, ScoreboardManager manager){
        this.player = player;
        this.sidebar = manager.sidebar(Sidebar.MAX_LINES, player.locale());
        this.sidebar.addPlayer(player);
    }

    public void updateLines(){

    }

    public void destroy(){
        this.sidebar.close();
    }

    public Player player(){
        return this.player;
    }
}
