package me.bartvv.antiafkfishing;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.bartvv.antiafkfishing.commands.Commandantiafkfishing;
import me.bartvv.antiafkfishing.listener.FishListener;
import me.bartvv.antiafkfishing.manager.FileManager;

@Getter
public class AntiAfkFishing extends JavaPlugin {

	private FileManager config;

	@Override
	public void onEnable() {
		this.config = new FileManager( this, "config.yml", -1, getDataFolder(), false );
		getServer().getPluginManager().registerEvents( new FishListener( this ), this );
		getCommand( "antiafkfishing" ).setExecutor( new Commandantiafkfishing( this ) );
	}
}
