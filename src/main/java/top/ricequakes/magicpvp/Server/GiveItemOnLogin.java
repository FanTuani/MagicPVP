package top.ricequakes.magicpvp.Server;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class GiveItemOnLogin implements Listener {

    public GiveItemOnLogin(MagicPVP plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Game.giveItems(event.getPlayer(), Material.EGG);
    }
}
