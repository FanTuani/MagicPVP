package top.ricequakes.magicpvp.Items;

import net.minecraft.server.v1_8_R3.EntityLargeFireball;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLargeFireball;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

import java.util.HashSet;

public class MagicItems implements Listener {
    public final MagicPVP plugin;
    private final double[][] cl = {{0, 0}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    public MagicItems(MagicPVP plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean isRightClick(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK;
    }

    @EventHandler
    public void TNTEgg(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isRightClick(event)) {
            if (event.getMaterial() == Material.EGG) {
                new BukkitRunnable() {
                    final double cd = plugin.getConfig().getDouble("cdtime") * 20;
                    double x = 0;

                    public void run() {
                        player.setExp((float) (x / cd));
                        if (player.getInventory().contains(Material.EGG))
                            cancel();
                        if (x >= cd) {
                            if (!player.getInventory().contains(Material.EGG))
                                Game.giveItems(player, Material.EGG);
                            cancel();
                        }
                        x++;
                    }
                }.runTaskTimer(plugin, 0, 1);
            }
        }
    }

    @EventHandler
    public void lightingStick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        if (isRightClick(event)) {
            if (event.getMaterial() == Material.STICK) {
                int stickIndex = inventory.first(Material.STICK);
                if (inventory.getItem(stickIndex).getAmount() == 1) {
                    inventory.clear(stickIndex);
                } else {
                    inventory.getItem(stickIndex).setAmount(inventory.getItem(stickIndex).getAmount() - 1);
                }
                Location lightLocation = player.getTargetBlock((HashSet<Byte>) null, 100).getLocation();
                player.getWorld().strikeLightning(lightLocation);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getWorld().strikeLightning(lightLocation);
                    }
                }.runTaskLater(plugin, 20);
            }
        }
    }

    @EventHandler
    public void scaffoldSlimeBall(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        if (isRightClick(event)) {
            if (event.getMaterial() == Material.SLIME_BALL) {
                int slimeIndex = inventory.first(Material.SLIME_BALL);
                if (inventory.getItem(slimeIndex).getAmount() == 1) {
                    inventory.clear(slimeIndex);
                } else {
                    inventory.getItem(slimeIndex).setAmount(inventory.getItem(slimeIndex).getAmount() - 1);
                }
                new BukkitRunnable() {
                    int x = 3;

                    @SuppressWarnings("deprecation")
                    public void run() { // Title message
                        player.sendTitle("", String.valueOf(x));
                        if (x == 0) {
                            player.sendTitle("", "");
                            cancel();
                        }
                        x--;
                    }
                }.runTaskTimer(plugin, 0, 20);
                new BukkitRunnable() {
                    int times = 0;

                    public void run() {
                        if (!player.isFlying())
                            scaffold(player);
                        if (times++ == 12) cancel();
                    }
                }.runTaskTimer(plugin, 0, 4);

            }
        }
    }

    @EventHandler
    public void fireBallLauncher(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        if (isRightClick(event)) {
            if (event.getMaterial() == Material.BLAZE_ROD) {
                int rodIndex = inventory.first(Material.BLAZE_ROD);
                if (inventory.getItem(rodIndex).getAmount() == 1) {
                    inventory.clear(rodIndex);
                } else {
                    inventory.getItem(rodIndex).setAmount(inventory.getItem(rodIndex).getAmount() - 1);
                }
                Location loc = player.getLocation();
                Vector vec = loc.getDirection().multiply(0.08);
                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setCustomName("§0§l申必球");
                fireball.setCustomNameVisible(true);
                EntityLargeFireball cFireball = ((CraftLargeFireball) fireball).getHandle();
                cFireball.dirX = vec.getX();
                cFireball.dirY = vec.getY();
                cFireball.dirZ = vec.getZ();
            }
        }
    }

    @EventHandler
    public void blockWallBrick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        if (isRightClick(event)) {
            if (event.getMaterial() == Material.BRICK) {
                int brickIndex = inventory.first(Material.BRICK);
                if (inventory.getItem(brickIndex).getAmount() == 1) {
                    inventory.clear(brickIndex);
                } else {
                    inventory.getItem(brickIndex).setAmount(inventory.getItem(brickIndex).getAmount() - 1);
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 70, 9));
                new BukkitRunnable() {
                    int times = 0;
                    final int wid = 3;

                    @Override
                    public void run() {
                        if (times == 0 || times == 20 || times == 40) {
                            player.sendTitle("", String.valueOf((60 - times) / 20));
                        }
                        if (!player.isFlying()) {
                            for (int height = 0; height < 3; height++) {
                                boolean needBlock;
                                for (int i = -wid; i <= wid; i++) {
                                    needBlock = i == -wid || i == wid;
                                    for (int j = -wid; j <= wid; j++) {
                                        if (needBlock || j == -wid || j == wid) {
                                            Location loc = player.getLocation();
                                            loc.setY(loc.getY() + height);
                                            loc.setX(loc.getX() + i);
                                            loc.setZ(loc.getZ() + j);
                                            Block block = loc.getBlock();
                                            if (block.getType() == Material.AIR) {
                                                block.setType(Material.GLASS);
                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        block.setType(Material.AIR);
                                                    }
                                                }.runTaskLater(plugin, 5);
                                            }
                                        }
                                    }
                                }

                            }
                            if (times++ == 60) {
                                player.sendTitle("", "");
                                cancel();
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0, 1);
            }
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        Inventory inv = player.getInventory();
        ItemStack bow = event.getBow();
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 11451);
        int idx = inv.first(bow);
        inv.clear(idx);
    }

    @EventHandler
    public void onArrowIntoPlayer(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            Player player = (Player) event.getEntity();
            Location loc = player.getLocation();
            player.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0, false, false);
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow) { // Bow
            Player player = (Player) event.getEntity();
            new BukkitRunnable() {
                @Override
                public void run() {
                    Arrow arrow = (Arrow) event.getDamager();
                    Player shooter = (Player) arrow.getShooter();
                    if (player.getGameMode() == GameMode.SPECTATOR) {
                        if (player == shooter) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + shooter.getName() + " 竟然把自己射爆了");
                        } else {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + shooter.getName() + " 射爆了 " + event.getEntity().getName());
                        }
                        Game.rewardKiller(shooter);
                    }
                }
            }.runTaskLater(plugin, 2);
        } else if (event.getFinalDamage() >= 20) { // Sword
            Player killerPlayer = (Player) event.getDamager();
            Bukkit.broadcastMessage(ChatColor.YELLOW + killerPlayer.getName() + " 秒杀了 " + event.getEntity().getName());
            Game.rewardKiller(killerPlayer);
            Location loc = event.getEntity().getLocation();
            killerPlayer.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0, false, false);
            Inventory inv = killerPlayer.getInventory();
            int idx = inv.first(Material.GOLD_SWORD);
            inv.clear(idx);
        } else {
            if (!(event.getDamager() instanceof Egg))
                Game.killMap.put((Player) event.getEntity(), (Player) event.getDamager());
        }
    }

    @EventHandler
    public void enderPearl(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Inventory inv = player.getInventory();
        if (player.getInventory().getItemInHand() != null && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                int pearlIndex = inv.first(Material.ENDER_PEARL);
                if (inv.getItem(pearlIndex).getAmount() == 1) {
                    inv.clear(pearlIndex);
                } else {
                    inv.getItem(pearlIndex).setAmount(inv.getItem(pearlIndex).getAmount() - 1);
                }
                EnderPearl projectilePearl = player.launchProjectile(EnderPearl.class);
                Vector vec = player.getLocation().getDirection();
                vec.setX(vec.getX() * 1.7);
                vec.setY(vec.getY() * 1.7);
                vec.setZ(vec.getZ() * 1.7);
                projectilePearl.setVelocity(vec);
                projectilePearl.setPassenger(player);
            }
        }
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.FIREBALL) {
            Fireball fireball = (Fireball) event.getEntity();
            if (!(fireball.getShooter() instanceof Player)) return;
            event.blockList().clear();
            event.setYield(2F);
        }
    }

    private void scaffold(Player player) {
        for (int i = 0; i < 9; i++) {
            Location location = player.getLocation();
            location.setY(location.getY() - 1);
            location.setX(location.getX() + cl[i][0]);
            location.setZ(location.getZ() + cl[i][1]);
            Block block = location.getBlock();
            if (block.getType() == Material.AIR) {
                block.setType(Material.STONE);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.AIR);
                    }
                }.runTaskLater(plugin, 100); // stay 5s
            }
        }
    }

}
