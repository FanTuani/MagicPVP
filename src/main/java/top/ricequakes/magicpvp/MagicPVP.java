package top.ricequakes.magicpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import top.ricequakes.magicpvp.Commands.SetEggCD;
import top.ricequakes.magicpvp.Commands.SetFire;
import top.ricequakes.magicpvp.Commands.SetSpawn;
import top.ricequakes.magicpvp.Commands.SetVoidHeight;
import top.ricequakes.magicpvp.EventCancellers.*;
import top.ricequakes.magicpvp.Items.MagicItems;
import top.ricequakes.magicpvp.Items.TNTEgg;
import top.ricequakes.magicpvp.Map.BlockInteractions;
import top.ricequakes.magicpvp.Other.PlayerDead;
import top.ricequakes.magicpvp.Server.GiveItemOnLogin;

public final class MagicPVP extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Game.initGame(this);
        saveDefaultConfig();
        registerListeners();
        registerCommands();
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "MagicPVP plugin loaded!");
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Developed by: FanTuani & LSDog");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "插件重载完成");
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage(ChatColor.YELLOW + "即将重载插件");
    }

    private void registerListeners() {
        new TNTEgg(this);
        new MagicItems(this);
        new PlayerDead(this);
        new BasicCancellers(this);
        new GiveItemOnLogin(this);
        new NoPlatformPvP(this);
        new BlockInteractions(this);
    }

    private void registerCommands() {
        getCommand("setfire").setExecutor(new SetFire(this));
        getCommand("setspawn").setExecutor(new SetSpawn(this));
        getCommand("setvoidheight").setExecutor(new SetVoidHeight(this));
        getCommand("seteggcd").setExecutor(new SetEggCD(this));
    }
}
