package me.bartvv.antiafkfishing.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import lombok.RequiredArgsConstructor;
import me.bartvv.antiafkfishing.AntiAfkFishing;

@RequiredArgsConstructor
public class Commandantiafkfishing implements CommandExecutor {

	private final AntiAfkFishing antiAfkFishing;

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
		if ( !sender.hasPermission( "antiafkfishing.reload" ) ) {
			this.antiAfkFishing.getConfig().getStringList( "No-Permission" ).forEach( sender::sendMessage );
			return true;
		}

		this.antiAfkFishing.getConfig().reload();
		this.antiAfkFishing.getConfig().getStringList( "Reload" ).forEach( sender::sendMessage );
		return true;
	}
}