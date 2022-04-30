package top.ricequakes.magicpvp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class SetFire implements CommandExecutor {
    private final MagicPVP plugin;

    public SetFire(MagicPVP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setfire") && sender instanceof Player) {
            Player player = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            config.set("fire", Double.valueOf(args[0]));
            plugin.saveConfig();
            Game.initGame(plugin);
            player.sendMessage(ChatColor.YELLOW + "Fire set: " + args[0]);
            return true;
        }
        return false;
    }
}
