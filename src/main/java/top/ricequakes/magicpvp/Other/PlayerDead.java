package top.ricequakes.magicpvp.Other;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;


public class PlayerDead implements Listener {
    public final MagicPVP plugin;

    public PlayerDead(MagicPVP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDamageToDead(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getFinalDamage() >= ((Player) event.getEntity()).getHealth()
                && event.getCause() != EntityDamageEvent.DamageCause.FALL) {

            Game.diePlayer((Player) event.getEntity());
            event.setCancelled(true);
        }
    }
}
