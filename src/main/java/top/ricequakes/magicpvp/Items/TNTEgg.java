package top.ricequakes.magicpvp.Items;

import org.bukkit.*;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class TNTEgg implements Listener {
    public final MagicPVP plugin;

    public TNTEgg(MagicPVP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerThrowEgg(PlayerEggThrowEvent event) {
        Egg egg = event.getEgg();
        Player player = event.getPlayer();
        event.setHatching(false);
        Location loc = egg.getLocation();
        event.getPlayer().getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), Game.fireLevel, false, false);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Entity entity : egg.getNearbyEntities(5, 5, 5)) {
                    Player nearPlayer = (Player) entity;
                    if (nearPlayer.getGameMode() == GameMode.SPECTATOR) {
                        if (event.getPlayer() == nearPlayer) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " 自爆了");
                        } else {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " 炸死了 " + nearPlayer.getName());
                            Game.rewardKiller(player);
                        }
                    } else {
                        if (player != nearPlayer)
                            Game.killMap.put(nearPlayer, player);
                    }
                }
            }
        }.runTaskLater(plugin, 1);
    }
}
