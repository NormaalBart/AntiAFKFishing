package me.bartvv.antiafkfishing.listener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.google.common.collect.Sets;

import lombok.RequiredArgsConstructor;
import me.bartvv.antiafkfishing.AntiAfkFishing;

@RequiredArgsConstructor
public class FishListener implements Listener {

	private final AntiAfkFishing antiAfkFishing;

	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void on( PlayerFishEvent e ) {
		Player player = e.getPlayer();
		if ( player.hasPermission( "antiafkfishing.bypass" ) )
			return;

		List< String > materials = getBlocks( player.getLocation().getBlock(),
				this.antiAfkFishing.getConfig().getInt( "Block-Radius" ) ).stream().map( Block::getType )
						.map( Material::toString ).collect( Collectors.toList() );
		e.setCancelled(
				this.antiAfkFishing.getConfig().getStringList( "Blacklisted-Items" ).stream().anyMatch( string -> {
					return materials.contains( string.toUpperCase() );
				} ) );
		if ( e.isCancelled() )
			this.antiAfkFishing.getConfig().getStringList( "Message" ).forEach( player::sendMessage );
	}

	private Set< Block > getBlocks( Block start, int radius ) {
		if ( radius < 0 )
			return Sets.newHashSetWithExpectedSize( 0 );

		int iterations = ( radius * 2 ) + 1;
		Set< Block > blocks = Sets.newHashSetWithExpectedSize( iterations * iterations * iterations );
		for ( int x = -radius; x <= radius; x++ ) {
			for ( int y = -radius; y <= radius; y++ ) {
				for ( int z = -radius; z <= radius; z++ ) {
					blocks.add( start.getRelative( x, y, z ) );
				}
			}
		}
		return blocks;
	}
}
