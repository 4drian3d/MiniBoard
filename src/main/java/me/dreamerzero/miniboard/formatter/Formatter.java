package me.dreamerzero.miniboard.formatter;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public interface Formatter {
    Component format(Player player, String string);

    Component format(Player player, String string, TagResolver additionalResolver);
}
