package me.dreamerzero.miniboard;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.dreamerzero.miniboard.configuration.ScoreBundle;
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
    private ScoreBundle scoreBundle;

    public PlayerScore(@NotNull final Player player, @NotNull MiniBoard miniBoard){
        this.plugin = Objects.requireNonNull(miniBoard, () -> "the plugin cannot be null");
        this.player = Objects.requireNonNull(player, () -> "the player cannot be null");
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

    public void updateLines(){
        int lines = Objects.checkIndex(this.scoreBundle.lines().size(), 15);
        for(int i = 0; i < lines; i++) {
            sidebar.line(i, formatter.format(this.scoreBundle.lines().get(i)));
        }
        sidebar.title(formatter.format(this.scoreBundle.title()));
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
        final ScoreBundle playerScoreBundle = plugin.config().worldScores().get(player.getLocation().getWorld().getName());
        this.scoreBundle = playerScoreBundle == null
            ? plugin.config().defaultScoreBoard()
            : playerScoreBundle;
        this.formatter = plugin.formatter() == FormatType.MINIPLACEHOLDERS
            ? new MiniPlaceholdersFormatter(player)
            : new RegularFormatter();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(
            plugin,
            this::updateLines,
            0L,
            this.scoreBundle.updateInterval()
        );
        this.updateLines();
    }

    @Override
    public boolean equals(final @Nullable Object o){
        if(o == this) return true;
        if(!(o instanceof PlayerScore)) return false;
        return Objects.equals(this.player, ((PlayerScore)o).player());
    }
}
