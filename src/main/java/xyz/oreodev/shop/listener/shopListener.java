package xyz.oreodev.shop.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import xyz.oreodev.shop.command.shopCommand;
import xyz.oreodev.shop.util.shopUtil;

import java.util.UUID;


public class shopListener implements Listener {
    private shopUtil util;

    public shopListener() {
        this.util = new shopUtil();
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        if (shopCommand.editorList.contains((Player)e.getPlayer())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.getPlayer().sendMessage("opened");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getClickedInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            player.sendMessage("canceled1");
            player.getInventory().addItem(e.getCurrentItem());
        }
    }

    @EventHandler
    public void onMoveItem(InventoryDragEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("canceled2");
        }
    }

    @EventHandler
    public void onMoveItem2(InventoryPickupItemEvent e) {
        if (shopCommand.editorList.contains((Player)e.getHandlers())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onShiftClick(InventoryClickEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            player.sendMessage("canceled2");
            player.getInventory().addItem(e.getOldCursor());
        }
    }

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent e) {
        if (shopCommand.editorList.contains((Player)e.getHandlers())) return;
        if (e.getDestination().getTitle().contains("_상점")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().contains("_상점")) {
            e.getPlayer().sendMessage("closed");
            String[] storeName = e.getInventory().getTitle().split("_");
            for (String str : shopUtil.shopMap.values()) {
                if (str.equalsIgnoreCase(storeName[0])) {
                    for (UUID uuid : shopUtil.shopMap.keySet()) {
                        if (shopUtil.shopMap.get(uuid).equalsIgnoreCase(storeName[0])) {
                            for (int i = 0; i < e.getInventory().getSize(); i++) {
                                util.saveItemStack(uuid, i, e.getInventory().getItem(i));
                            }
                        }
                    }
                }
            }
        }
    }
}