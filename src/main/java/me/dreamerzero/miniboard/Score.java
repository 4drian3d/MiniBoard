package me.dreamerzero.miniboard;

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

public final class Score {
    private final MiniBoard miniBoard;
    private final Sidebar sidebar;
    private final Player player;
    private final BukkitTask task;

    public Score(@NotNull final Player player, @NotNull MiniBoard miniBoard){
        this.miniBoard = miniBoard;
        this.player = player;
        this.sidebar = miniBoard.manager().sidebar(Sidebar.MAX_LINES, player.locale());
        this.updateLines();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(
            miniBoard,
            this::updateLines,
            0L,
            miniBoard.config().updateInterval()
        );
        this.sidebar.addPlayer(player);
    }

    public void updateLines(){
        List<String> lines = miniBoard.config().lines();
        Objects.checkIndex(lines.size(), 15);
        for(int i = 0; i < lines.size(); i++) {
            sidebar.line(i, MiniMessage.miniMessage().deserialize(lines.get(i)));
        }
        sidebar.title(MiniMessage.miniMessage().deserialize(lines.get(1)));
    }

    public void destroy(){
        this.task.cancel();
        if(!this.sidebar.closed())
            this.sidebar.close();
    }

    public boolean destroyIfPlayerEquals(Player player) {
        if(player.equals(this.player)){
            this.destroy();
            return true;
        }
        return false;
    }

    public Player player(){
        return this.player;
    }
}
