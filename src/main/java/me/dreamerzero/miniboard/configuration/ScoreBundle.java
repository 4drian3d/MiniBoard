package me.dreamerzero.miniboard.configuration;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record ScoreBundle(String title, List<String> lines, long updateInterval){}
