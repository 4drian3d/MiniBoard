package me.dreamerzero.miniboard;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import me.dreamerzero.miniboard.configuration.Configuration;
import me.dreamerzero.miniboard.formatter.Formatter;
import me.dreamerzero.miniboard.formatter.MiniPlaceholdersFormatter;
import me.dreamerzero.miniboard.formatter.RegularFormatter;
import net.megavex.scoreboardlibrary.ScoreboardLibraryImplementation;
import net.megavex.scoreboardlibrary.api.ScoreboardManager;
import net.megavex.scoreboardlibrary.exception.ScoreboardLibraryLoadException;

public final class MiniBoard extends JavaPlugin  {
	private ScoreboardManager manager;
	private final Set<Score> scoreboards = new HashSet<>();
	private Configuration.Config configuration;
	private Formatter formatter;

	@Override
	public void onEnable() {
		this.configuration = Configuration.loadMainConfig(this.getDataFolder().toPath(), this.getSLF4JLogger());
		try {
			ScoreboardLibraryImplementation.init();
		} catch (ScoreboardLibraryLoadException e) {
			e.printStackTrace();
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.formatter = getServer().getPluginManager().isPluginEnabled("MiniPlaceholders")
			? new MiniPlaceholdersFormatter()
			: new RegularFormatter();
		this.manager = ScoreboardManager.scoreboardManager(this);
	}

	@Override
	public void onDisable() {
		for(Score score : this.scoreboards) {
			score.destroy();
		}
		this.scoreboards.clear();
		this.manager.close();
	}

	public ScoreboardManager manager() {
		return this.manager;
	}

	public Configuration.Config config() {
		return this.configuration;
	}

	public Set<Score> scores(){
		return this.scoreboards;
	}

	public Formatter formatter() {
		return this.formatter;
	}
}