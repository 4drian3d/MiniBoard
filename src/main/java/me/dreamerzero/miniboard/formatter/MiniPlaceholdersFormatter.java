package me.dreamerzero.miniboard.formatter;

import org.bukkit.entity.Player;

import me.dreamerzero.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MiniPlaceholdersFormatter implements Formatter {

    @Override
    public Component format(Player player, String string) {
        return MiniMessage.miniMessage().deserialize(
            string,
            MiniPlaceholders.getAudiencePlaceholders(player)
        );
    }

    @Override
    public Component format(Player player, String string, TagResolver additionalResolver) {
        return MiniMessage.miniMessage().deserialize(
            string,
            MiniPlaceholders.getAudiencePlaceholders(player),
            additionalResolver
        );
    }

}
