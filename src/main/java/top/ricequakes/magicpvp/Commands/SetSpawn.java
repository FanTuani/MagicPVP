package top.ricequakes.magicpvp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class SetSpawn implements CommandExecutor {
    private final MagicPVP plugin;

    public SetSpawn(MagicPVP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("setSpawn")) {
                Player player = (Player) sender;
                Location location = player.getLocation();
                Game.spawnLocation = location;
                FileConfiguration config = plugin.getConfig();
                config.set("spx", location.getX());
                config.set("spy", location.getY());
                config.set("spz", location.getZ());
                config.set("spyaw", location.getYaw());
                config.set("sppit", location.getPitch());
                player.sendMessage(ChatColor.YELLOW + "Spawn set!");
                plugin.saveConfig();
                return true;
            }
        }
        return false;
    }
}