package me.dreamerzero.miniboard;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import me.dreamerzero.miniboard.commands.MiniBoardCommand;
import me.dreamerzero.miniboard.configuration.Configuration;
import me.dreamerzero.miniboard.enums.FormatType;
import me.dreamerzero.miniboard.listener.JoinListener;
import me.dreamerzero.miniboard.listener.LeaveListener;
import me.dreamerzero.miniboard.listener.WorldChangeListener;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.megavex.scoreboardlibrary.ScoreboardLibraryImplementation;
import net.megavex.scoreboardlibrary.api.ScoreboardManager;
import net.megavex.scoreboardlibrary.exception.ScoreboardLibraryLoadException;

public final class MiniBoard extends JavaPlugin  {
    private ScoreboardManager manager;
    private final Set<PlayerScore> scoreboards = new HashSet<>();
    private Configuration.Config configuration;
    private FormatType formatType;

    @Override
    public void onEnable() {
        this.reloadConfig();
        try {
            ScoreboardLibraryImplementation.init();
        } catch (ScoreboardLibraryLoadException e) {
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.formatType = this.getServer().getPluginManager().isPluginEnabled("miniplaceholders-paper")
            ? FormatType.MINIPLACEHOLDERS
            : FormatType.REGULAR;
        this.manager = ScoreboardManager.scoreboardManager(this);

        this.getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        this.getServer().getPluginManager().registerEvents(new LeaveListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldChangeListener(this), this);

        PluginCommand miniBoardCommand = this.getServer().getPluginCommand("miniboard");
        miniBoardCommand.permissionMessage(MiniMessage.miniMessage()
            .deserialize("You cannot execute this command"));
        miniBoardCommand.setExecutor(new MiniBoardCommand(this));
        miniBoardCommand.setPermission("miniboard.command");
        miniBoardCommand.setDescription("MiniBoard main command");

        // This plugin only supports 1.18.2+ versions... brigadier already exists since 1.13
        Commodore commodore = CommodoreProvider.getCommodore(this);
        this.setCompletions(commodore, miniBoardCommand);
    }

    private void setCompletions(Commodore commodore, PluginCommand command) {
        commodore.register(command,
            LiteralArgumentBuilder.literal("miniboard")
                .then(LiteralArgumentBuilder.literal("reload").build())
                .then(LiteralArgumentBuilder.literal("toggle").build())
            .build()
        );
    }

    public Path getDataPath(){
        return this.getDataFolder().toPath();
    }

    @Override
    public void onDisable() {
        this.scoreboards.forEach(PlayerScore::destroy);
        this.scoreboards.clear();
        this.manager.close();
    }

    public ScoreboardManager manager() {
        return this.manager;
    }

    public Configuration.Config config() {
        return this.configuration;
    }

    public Set<PlayerScore> scores(){
        return this.scoreboards;
    }

    public FormatType formatter() {
        return this.formatType;
    }

    public void reloadConfig(){
        this.configuration = Configuration.loadMainConfig(
            this.getDataPath(),
            this.getSLF4JLogger()
        );
    }
}