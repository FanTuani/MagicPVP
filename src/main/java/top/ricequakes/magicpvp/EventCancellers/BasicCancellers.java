package top.ricequakes.magicpvp.EventCancellers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class BasicCancellers implements Listener {

    public BasicCancellers(MagicPVP plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerFallIntoVoid(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getY() < Game.voidHeight && !event.getPlayer().isFlying()) {
            Player player = event.getPlayer();
            Game.diePlayer(player);
            player.teleport(Game.spawnLocation);
            if (Game.killMap.containsKey(player)) {
                Game.rewardKiller(Game.killMap.get(player));
                Bukkit.broadcastMessage(ChatColor.YELLOW + Game.killMap.get(player).getName() + " 将 " + player.getName() + " 送进了虚空");
            } else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " 白给了");
            }
            Game.killMap.remove(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().getAllowFlight()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHungry(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMonstersSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPutBlock(BlockPlaceEvent event) {
        if (!event.getPlayer().getAllowFlight()) {
            event.setCancelled(true);
        }
    }
}
