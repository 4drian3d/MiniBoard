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
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

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
        @Comment("""
            Default Scoreboard
            This scoreboard will be displayed if no scoreboard has been assigned
            to the world in which the player is located""")
        @Setting(value = "default-scoreboard")
        private ScoreBundle defaultScoreboard = new ScoreBundle(
            "<rainbow>Miniboard",
            List.of(
                "<green>This server is using MiniBoard",
                "<gradient:#FF0000:white:red>||||||||||||||||",
                "<gradient:aqua:white>play.yourserver.com"
            ),
            100
        );
        @Comment("World Scoreboards")
        @Setting(value = "world-scoreboards")
        private Map<String, ScoreBundle> worldScores = Map.of(
            "world_nether",
            new ScoreBundle(
                "<rainbow>Miniboard</rainbow>",
                List.of(
                    "<green>This server is using MiniBoard",
                    "<gradient:red:yellow>OMG, WORLD SCOREBOARDS!!!!!!!!!!!!",
                    "<gradient:#FF0000:white:red>||||||||||||||||</gradient>",
                    "<gradient:aqua:white>play.yourserver.com"
                ),
                100
            )
        );

        public ScoreBundle defaultScoreBoard() {
            return this.defaultScoreboard;
        }

        public Map<String, ScoreBundle> worldScores() {
            return this.worldScores;
        }
    }
}
