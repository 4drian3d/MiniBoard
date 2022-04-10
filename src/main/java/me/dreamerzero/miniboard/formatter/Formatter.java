package me.dreamerzero.miniboard.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public abstract class Formatter {
    protected final TagResolver playerResolver;

    protected Formatter(TagResolver resolver){
        playerResolver = resolver;
    }

    public abstract Component format(String string);

    public abstract Component format(String string, TagResolver additionalResolver);
}
