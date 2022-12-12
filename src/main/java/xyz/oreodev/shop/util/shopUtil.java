package xyz.oreodev.shop.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oreodev.shop.Shop;

import java.util.HashMap;
import java.util.UUID;

public class shopUtil {
    private Shop plugin;
    public static HashMap<UUID, String> shopMap = new HashMap<>(); //ID, Name

    public shopUtil() {
        this.plugin = JavaPlugin.getPlugin(Shop.class);
    }

    public void initialize() {
        for (String uuid : plugin.ymlManager.getConfig().getConfigurationSection("shop.").getKeys(false)) {
            shopMap.put(UUID.fromString(uuid), getSavedTitle(UUID.fromString(uuid)));
        }
        for (UUID uuid : shopMap.keySet()) {
            Bukkit.getConsoleSender().sendMessage("uuid : " + uuid + " name : " + shopMap.get(uuid));
        }
    }

    public ItemStack getSavedItemStack(UUID InventoryID, int placeholder) {
        return plugin.ymlManager.getConfig().getItemStack("shop." + InventoryID.toString() + ".inventory." + placeholder);
    }

    public void saveItemStack(UUID InventoryID, int placeholder, ItemStack itemStack) {
        plugin.ymlManager.getConfig().set("shop." + InventoryID.toString() + ".inventory." + placeholder, itemStack);
        plugin.ymlManager.saveConfig();
    }

    public int getSavedInventorySize(UUID InventoryID) {
        return plugin.ymlManager.getConfig().getInt("shop." + InventoryID.toString() + ".size");
    }

    public void saveInventorySize(UUID InventoryID, int size) {
        plugin.ymlManager.getConfig().set("shop." + InventoryID.toString() + ".size", size);
        plugin.ymlManager.saveConfig();
    }

    public String getSavedTitle(UUID InventoryID) {
        return plugin.ymlManager.getConfig().getString("shop." + InventoryID.toString() + ".title");
    }

    public void saveTitle(UUID InventoryID, String title) {
        plugin.ymlManager.getConfig().set("shop." + InventoryID.toString() + ".title", title);
        plugin.ymlManager.saveConfig();
    }

    public void removeInventory(UUID InventoryID) {
        plugin.ymlManager.getConfig().set("shop." + InventoryID.toString(), null);
        plugin.ymlManager.saveConfig();
    }

    public UUID getIDFromName(String name) {
        for (UUID key : shopUtil.shopMap.keySet()) {
            if (shopUtil.shopMap.get(key).equalsIgnoreCase(name)) {
                return key;
            }
        }
        return null;
    }
}
