package me.dreamerzero.miniboard.configuration;

import java.util.List;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record WorldBundle(String title, List<String> lines){}
