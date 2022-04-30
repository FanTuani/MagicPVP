package top.ricequakes.magicpvp;


import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;

public class Game {
    public static Location spawnLocation;
    public static float fireLevel;
    public static int voidHeight;
    public static MagicPVP plg;
    public static HashMap<Player, Player> killMap = new HashMap<>();

    public static void initGame(MagicPVP plugin) {
//        for (Player p : plugin.getServer().getOnlinePlayers()) {
//            p.setGameMode(GameMode.SURVIVAL);
//        }
        plg = plugin;
        voidHeight = plugin.getConfig().getInt("voidheight");
        spawnLocation = new Location(plugin.getServer().getWorlds().get(0), 0, 0, 0);
        spawnLocation.setX(plugin.getConfig().getDouble("spx"));
        spawnLocation.setZ(plugin.getConfig().getDouble("spz"));
        spawnLocation.setY(plugin.getConfig().getDouble("spy"));
        spawnLocation.setYaw((float) plugin.getConfig().getDouble("spyaw"));
        spawnLocation.setPitch((float) plugin.getConfig().getDouble("sppit"));
        fireLevel = (float) plugin.getConfig().getDouble("fire");
        giveItems();
    }

    public static void diePlayer(Player player) {
        player.sendTitle(ChatColor.RED + "You Died!", "");
        player.setGameMode(GameMode.SPECTATOR);
        Vector vector = player.getVelocity();
        vector.setY(vector.getY() + 2);
        player.setVelocity(vector);
        Game.giveAllRandomMagicItem(player);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
        new BukkitRunnable() {
            public void run() {
                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.HEAL);
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20);
                Location safeSpawn = player.getWorld().getHighestBlockAt(spawnLocation).getLocation();
                safeSpawn.setY(safeSpawn.getY() + 1);
                player.teleport(safeSpawn);
                player.getInventory().clear();
                giveItems(player, Material.EGG);
                player.setFoodLevel(20);
                player.setLevel(0);
                player.setExp(0.01F);
            }
        }.runTaskLater(plg, 10);
    }

    public static void giveItems() {
        for (Player player : plg.getServer().getOnlinePlayers()) {
            Inventory inv = player.getInventory();
            ItemStack items = new ItemStack(Material.EGG);
            if (!inv.contains(items))
                inv.addItem(items);
        }
    }

    public static void giveItems(Player player, Material material) {
        Inventory inv = player.getInventory();
        ItemStack items = new ItemStack(material);
        inv.addItem(items);
    }

    public static void rewardKiller(Player player) {
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 0));
    }

    public static void giveAllRandomMagicItem(Player deadPlayer) {
        Random rand = new Random();
        for (Player player : plg.getServer().getOnlinePlayers()) {
            if (player != deadPlayer) {
                int rd = rand.nextInt(7);
                Inventory inv = player.getInventory();
                switch (rd) {
                    case 0: {
                        ItemStack slimeBall = new ItemStack(Material.SLIME_BALL);
                        slimeBall.getItemMeta().setDisplayName("Scaffold Slime Ball");
                        inv.addItem(slimeBall);
                        player.sendMessage(ChatColor.GREEN + "获得：自动搭路球！");
                        break;
                    }
                    case 1: {
                        inv.addItem(new ItemStack(Material.STICK));
                        player.sendMessage(ChatColor.GREEN + "获得：闪电棍！");
                        break;
                    }
                    case 2: {
                        inv.addItem(new ItemStack(Material.ENDER_PEARL));
                        player.sendMessage(ChatColor.GREEN + "获得：末影珍珠！");
                        break;
                    }
                    case 3: {
                        ItemStack sword = new ItemStack(Material.GOLD_SWORD);
                        sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 11451);
                        inv.addItem(sword);
                        player.sendMessage(ChatColor.GREEN + "获得：秒人剑！");
                        break;
                    }
                    case 4: {
                        ItemStack bow = new ItemStack(Material.BOW);
                        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 11451);
                        inv.addItem(bow);
                        inv.addItem(new ItemStack(Material.ARROW));
                        player.sendMessage(ChatColor.GREEN + "获得：秒人弓！");
                        break;
                    }
                    case 5: {
                        ItemStack brick = new ItemStack(Material.BRICK);
                        inv.addItem(brick);
                        player.sendMessage(ChatColor.GREEN + "获得：禁军之墙！");
                        break;
                    }
                    case 6: {
                        ItemStack brick = new ItemStack(Material.BLAZE_ROD);
                        inv.addItem(brick);
                        player.sendMessage(ChatColor.GREEN + "获得：申必球发射器！");
                        break;
                    }
                }
            }
        }
    }

}
