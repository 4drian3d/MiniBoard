package me.dreamerzero.miniboard.configuration;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

public final class Configuration {
    private Configuration(){}
    public static Config loadMainConfig(@NotNull final Path path, @NotNull final Logger logger){
        Path configPath = path.resolve("config.conf");
        final HoconConfigurationLoader loader = HoconConfigurationLoader.builder()
            .defaultOptions(opts -> opts
                .shouldCopyDefaults(true)
                .header("""
                    MiniBoard | by 4drian3d
                    """)
            )
            .path(configPath)
            .build();
        Config config = null;
        try {
            final CommentedConfigurationNode node = loader.load();
            config = node.get(Config.class);
            node.set(Config.class, config);
            loader.save(node);
        } catch (ConfigurateException exception){
            logger.error("Could not load config.conf file, error: {}", exception.getMessage());
        }
        return config;
    }

    @ConfigSerializable
    public static class Config {
        private String title = "<rainbow>Miniboard</rainbow>";
        private List<String> lines = List.of(
            "<green>This server is using MiniBoard",
            "<gradient:#FF0000:white:red>||||||||||||||||</gradient>",
            "<gradient:aqua:white>play.yourserver.com"
        );
        private Map<String, WorldBundle> worldScores = Map.of(
            "world-the-nether",
            new WorldBundle(
                "<rainbow>Miniboard</rainbow>",
                List.of(
                    "<green>This server is using MiniBoard",
                    "OMG, WORLD SCOREBOARDS!!!!!!!!!!!!",
                    "<gradient:#FF0000:white:red>||||||||||||||||</gradient>",
                    "<gradient:aqua:white>play.yourserver.com"
                )
            )
        );
        private long updateInterval = 100l;

        public String title() {
            return this.title;
        }

        public List<String> lines(){
            return this.lines;
        }

        public long updateInterval(){
            return this.updateInterval;
        }

        public Map<String, WorldBundle> worldScores() {
            return this.worldScores;
        }
    }
}
