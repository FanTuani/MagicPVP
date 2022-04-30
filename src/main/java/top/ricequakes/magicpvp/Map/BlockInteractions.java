package top.ricequakes.magicpvp.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.ricequakes.magicpvp.MagicPVP;

public class BlockInteractions implements Listener {
    public final MagicPVP plugin;

    public BlockInteractions(MagicPVP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGoldPressurePlate(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getBlock().getType() == Material.GOLD_PLATE) {
            Player player = event.getPlayer();
            Vector vec = player.getLocation().getDirection();
            vec.setY(0.8);
            vec.setX(vec.getX() * 1.82);
            vec.setZ(vec.getZ() * 1.82);
            player.setVelocity(vec);
        }
    }

//    @EventHandler
//    public void onCenterEmerald(PlayerMoveEvent event) {
//        Location loc = event.getPlayer().getLocation();
//        loc.setY(loc.getY() - 1);
//        if (loc.getBlock().getType() == Material.EMERALD_BLOCK) {
//            Player player = event.getPlayer();
//            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 0));
//            if (player.getLevel() == 0) {
//                new BukkitRunnable() {
//                    int time = 10;
//
//                    @Override
//                    public void run() {
//                        player.setLevel(time);
//                        if (time == 0 || loc.getBlock().getType() != Material.EMERALD) {
//                            cancel();
//                        }
//                        time--;
//                    }
//                }.runTaskTimer(plugin, 0, 20);
//            }
//        } else {
//            event.getPlayer().setLevel(0);
//        }
//    }
}
