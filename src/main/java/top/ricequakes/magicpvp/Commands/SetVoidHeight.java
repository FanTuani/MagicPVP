package top.ricequakes.magicpvp.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.ricequakes.magicpvp.Game;
import top.ricequakes.magicpvp.MagicPVP;

public class SetVoidHeight implements CommandExecutor {
    private final MagicPVP plugin;

    public SetVoidHeight(MagicPVP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && ((Player) sender).getAllowFlight() && cmd.getName().equalsIgnoreCase("setvoidheight")) {
            Game.voidHeight = (int) ((Player) sender).getLocation().getY();
            plugin.getConfig().set("voidheight", Game.voidHeight);
            plugin.saveConfig();
            sender.sendMessage(ChatColor.YELLOW + "Void Height set: " + Game.voidHeight);
            return true;
        }
        return false;
    }
}
