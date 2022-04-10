package me.dreamerzero.miniboard;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import me.dreamerzero.miniboard.configuration.WorldBundle;
import me.dreamerzero.miniboard.enums.FormatType;
import me.dreamerzero.miniboard.formatter.Formatter;
import me.dreamerzero.miniboard.formatter.MiniPlaceholdersFormatter;
import me.dreamerzero.miniboard.formatter.RegularFormatter;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;

public final class PlayerScore {
    private final MiniBoard plugin;
    private final Sidebar sidebar;
    private final Player player;
    private BukkitTask task;
    private Formatter formatter;
    private WorldBundle actualLines;

    public PlayerScore(@NotNull final Player player, @NotNull MiniBoard miniBoard){
        this.plugin = miniBoard;
        this.player = player;
        this.sidebar = miniBoard.manager().sidebar(Sidebar.MAX_LINES, player.locale());
        this.sidebar.addPlayer(player);
        sidebar.visible(true);
        this.reload();
    }

    public void toggleState(){
        sidebar.visible(!sidebar.visible());
    }

    public void state(boolean state){
        sidebar.visible(state);
    }

    public void changeLines(WorldBundle bundle){
        updateLines(this.actualLines = bundle);
    }

    public void updateLines(){
        updateLines(actualLines);
    }

    public void updateLines(WorldBundle bundle){
        int lines = Objects.checkIndex(bundle.lines().size(), 15);
        for(int i = 0; i < lines; i++) {
            sidebar.line(i, formatter.format(bundle.lines().get(i)));
        }
        sidebar.title(formatter.format(bundle.title()));
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

    public void reload() {
        final WorldBundle playerWorldBundle = plugin.config().worldScores().get(player.getLocation().getWorld().getName());
        this.actualLines = playerWorldBundle != null
            ? new WorldBundle(plugin.config().title(), plugin.config().lines())
            : playerWorldBundle;
        this.formatter = plugin.formatter() == FormatType.MINIPLACEHOLDERS
            ? new MiniPlaceholdersFormatter(player)
            : new RegularFormatter();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(
            plugin,
            () -> updateLines(),
            0L,
            plugin.config().updateInterval()
        );
        this.updateLines();
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof PlayerScore)) return false;
        return this.player.equals(((PlayerScore)o).player());
    }
}
