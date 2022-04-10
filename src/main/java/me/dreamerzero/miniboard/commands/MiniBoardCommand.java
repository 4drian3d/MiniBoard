package me.dreamerzero.miniboard.commands;

import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import me.dreamerzero.miniboard.MiniBoard;
import me.dreamerzero.miniboard.PlayerScore;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class MiniBoardCommand implements CommandExecutor {
    private final MiniBoard plugin;
    public MiniBoardCommand(final MiniBoard plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        return switch(args.length){
            case 0 -> {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(
                    "<gradient:red:aqua>MiniBoard</gradient> | <aqua>by <rainbow:!>4drian3d"
                ));
                yield true;
            }
            case 1 -> {
                switch (args[0].toLowerCase(Locale.ROOT)){
                    case "reload" -> {
                        if(sender.hasPermission("miniboard.command.reload")) {
                            plugin.reloadConfig();
                            plugin.scores().forEach(PlayerScore::reload);
                            yield true;
                        }
                        yield false;
                    }
                    case "toggle" -> {
                        if(sender.hasPermission("miniboard.command.toggle")) {
                            for(PlayerScore score : plugin.scores()) {
                                if(score.player().equals(sender)) {
                                    score.toggleState();
                                    yield true;
                                }
                            }
                        }
                        yield false;
                    }
                    default -> {
                        yield false;
                    }
                }
            }
            default -> {
                yield true;
            }
        };
    }
}
