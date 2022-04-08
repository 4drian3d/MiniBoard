package me.dreamerzero.miniboard;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import me.dreamerzero.miniboard.configuration.Configuration;
import net.megavex.scoreboardlibrary.ScoreboardLibraryImplementation;
import net.megavex.scoreboardlibrary.api.ScoreboardManager;
import net.megavex.scoreboardlibrary.exception.ScoreboardLibraryLoadException;

public final class MiniBoard extends JavaPlugin  {
	private ScoreboardManager manager;
	private final Set<Score> scoreboards = new HashSet<>();
	private Configuration.Config configuration;

	@Override
	public void onEnable() {
		this.configuration = Configuration.loadMainConfig(this.getDataFolder().toPath(), this.getSLF4JLogger());
		try {
			ScoreboardLibraryImplementation.init();
		} catch (ScoreboardLibraryLoadException e) {
			e.printStackTrace();
			return;
		}
		this.manager = ScoreboardManager.scoreboardManager(this);
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
	
}