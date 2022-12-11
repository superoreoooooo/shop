package xyz.oreodev.shop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oreodev.shop.command.shopCommand;
import xyz.oreodev.shop.listener.shopListener;
import xyz.oreodev.shop.manager.ymlManager;
import xyz.oreodev.shop.util.m;
import xyz.oreodev.shop.util.shopUtil;

public final class Shop extends JavaPlugin {
    public ymlManager ymlManager;
    public shopUtil util;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new shopListener(), this);
        getCommand("shop").setExecutor(new shopCommand());
        this.ymlManager = new ymlManager(this);
        new m("hello world!");
    }

    @Override
    public void onDisable() {
        new m("shut down..");
    }
}