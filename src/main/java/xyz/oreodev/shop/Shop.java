package xyz.oreodev.shop;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oreodev.shop.command.shopCommand;
import xyz.oreodev.shop.command.tabComplete.shopCompleter;
import xyz.oreodev.shop.listener.shopListener;
import xyz.oreodev.shop.manager.ymlManager;
import xyz.oreodev.shop.util.m;

public final class Shop extends JavaPlugin {
    public ymlManager ymlManager;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new shopListener(), this);
        getCommand("shop").setExecutor(new shopCommand());
        getCommand("shop").setTabCompleter(new shopCompleter());
        this.ymlManager = new ymlManager(this);
        new m("hello world!");
    }

    @Override
    public void onDisable() {
        new m("shut down..");
    }
}
