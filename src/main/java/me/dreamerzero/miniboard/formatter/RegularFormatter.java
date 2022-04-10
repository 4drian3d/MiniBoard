package me.dreamerzero.miniboard.formatter;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class RegularFormatter implements Formatter{

    @Override
    public Component format(Player player, String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    @Override
    public Component format(Player player, String string, TagResolver additionalResolver) {
        return MiniMessage.miniMessage().deserialize(string, additionalResolver);
    }

}
