package me.dreamerzero.miniboard.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class RegularFormatter extends Formatter {

    public RegularFormatter() {
        super(TagResolver.empty());
    }

    @Override
    public Component format(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    @Override
    public Component format(String string, TagResolver additionalResolver) {
        return MiniMessage.miniMessage().deserialize(string, additionalResolver);
    }

}
