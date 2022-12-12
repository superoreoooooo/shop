package xyz.oreodev.shop.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.oreodev.shop.Shop;
import xyz.oreodev.shop.command.shopCommand;

import java.util.Collections;
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

    public void listShop(Player player) {
        player.sendMessage("total shop count : " + shopUtil.shopMap.size());
        for (UUID uuid : shopUtil.shopMap.keySet()) {
            player.sendMessage("UUID : " + uuid.toString() + " | Title : " + getSavedTitle(uuid) + " | Size : " + getSavedInventorySize(uuid));
        }
    }

    public void giveItem(Player player) {
            player.sendMessage("confirmed!");
            ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "밀치기 막대");
            itemMeta.setLore(Collections.singletonList(ChatColor.GOLD + "밀치기 막대(테스트)"));
            itemMeta.addEnchant(Enchantment.KNOCKBACK, 31321, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
    }

    public void openShop(Player player, String name) {
        if (!shopMap.containsValue(name)) {
            return;
        }
        UUID key = getIDFromName(name);
        shopInventory shopInventory = new shopInventory(key, getSavedTitle(key), getSavedInventorySize(key));
        player.openInventory(shopInventory.getInventory());
    }

    public void editShop(Player player, String name) {
        if (!shopMap.containsValue(name)) {
            player.sendMessage("/shop edit (name)");
            return;
        }
        UUID key = getIDFromName(name);
        shopInventory shopInventory = new shopInventory(key, getSavedTitle(key), getSavedInventorySize(key));
        player.sendMessage("UUID  > " + key);
        player.sendMessage("Title > " + getSavedTitle(key));
        player.sendMessage("Size  > " + getSavedInventorySize(key));
        for (int i = 0; i < getSavedInventorySize(key); i++) {
            player.sendMessage("Item" + i + "  > " + getSavedItemStack(key, i));
        }
        player.openInventory(shopInventory.getInventory());
        shopCommand.editorList.add(player);
    }

    public void removeShop(Player player, String name) {
        if (!shopMap.containsValue(name)) {
            player.sendMessage("/shop remove (name)");
            return;
        }
        UUID key = getIDFromName(name);
        player.sendMessage("UUID  > " + key.toString());
        player.sendMessage("Title > " + getSavedTitle(key));
        player.sendMessage("Size  > " + getSavedInventorySize(key));
        for (int i = 0; i < getSavedInventorySize(key); i++) {
            player.sendMessage("Item" + i + "  > " + getSavedItemStack(key, i));
        }
        removeInventory(key);
        shopUtil.shopMap.remove(key);
    }

    public void createShop(Player player, String name, String size) {
        if (shopMap.containsValue(name)) {
            player.sendMessage("overlapping names are impossible");
            return;
        }
        if (Integer.parseInt(size) % 9 != 0) {
            player.sendMessage("chest size must be multiples of 9");
            return;
        }
        shopInventory shopInventory = new shopInventory(UUID.randomUUID(), name, Integer.parseInt(size));
        player.sendMessage("new Shop Created");
        player.sendMessage("UUID  > " + shopInventory.getInventoryID());
        player.sendMessage("Title > " + shopInventory.getTitle());
        player.sendMessage("Size  > " + shopInventory.getSize());
        player.openInventory(shopInventory.getInventory());
        shopMap.put(shopInventory.getInventoryID(), shopInventory.getTitle());
        shopCommand.editorList.add(player);
    }
}
