package me.dreamerzero.miniboard.formatter;

import org.bukkit.entity.Player;

import me.dreamerzero.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MiniPlaceholdersFormatter extends Formatter {


    public MiniPlaceholdersFormatter(Player player) {
        super(TagResolver.resolver(
            MiniPlaceholders.getGlobalPlaceholders(),
            MiniPlaceholders.getAudiencePlaceholders(player)
        ));
    }

    @Override
    public Component format(String string) {
        return MiniMessage.miniMessage().deserialize(
            string,
            this.playerResolver
        );
    }

    @Override
    public Component format(String string, TagResolver additionalResolver) {
        return MiniMessage.miniMessage().deserialize(
            string,
            this.playerResolver,
            additionalResolver
        );
    }

}
