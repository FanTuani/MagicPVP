package top.ricequakes.magicpvp.EventCancellers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class NoPlatformPvP implements Listener {
    public final MagicPVP plugin;

    public NoPlatformPvP(MagicPVP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean isOnPlatform(Player player) {
        return player.getLocation().getY() > Game.spawnLocation.getY() - 7;
    }


    @EventHandler
    public void noItemUse(PlayerInteractEvent event) {
        if (isOnPlatform(event.getPlayer()) && !event.getPlayer().getAllowFlight()
                && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            event.setCancelled(true);
            Material material = event.getItem().getType();
            if (material == Material.EGG || material == Material.ENDER_PEARL)
                event.getPlayer().sendMessage(ChatColor.YELLOW + "物品没了？试着对地板右键一下！");
        }
    }

    @EventHandler
    public void noBowShoot(EntityShootBowEvent event) {
        if (isOnPlatform((Player) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void noDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (player.getLocation().getY() > Game.spawnLocation.getY() - 7)
            event.setCancelled(true);
    }
}
