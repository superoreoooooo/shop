package xyz.oreodev.shop.listener;

import org.bukkit.entity.HumanEntity;
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
            e.getPlayer().sendMessage("opened shop : " + e.getInventory().getTitle().split("_")[0]);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getClickedInventory() == null || !e.getClick().equals(ClickType.SHIFT_LEFT) || !e.getClick().equals(ClickType.SHIFT_RIGHT) || !e.getClick().equals(ClickType.DOUBLE_CLICK)) {
            return;
        }
        if (e.getClickedInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            player.getInventory().addItem(e.getCurrentItem());
        }
    }

    @EventHandler
    public void onMoveItem(InventoryDragEvent e) {
        if (shopCommand.editorList.contains((Player)e.getWhoClicked())) return;
        if (e.getInventory().getTitle().contains("_상점")) {
            e.setCancelled(true);
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
            String[] storeName = e.getInventory().getTitle().split("_");
            e.getPlayer().sendMessage(shopCommand.bar);
            e.getPlayer().sendMessage("closed shop : " + storeName[0]);
            if (shopCommand.editorList.contains((Player)e.getPlayer())) {
                e.getPlayer().sendMessage("Saved items");
            }
            for (String str : shopUtil.shopMap.values()) {
                if (str.equalsIgnoreCase(storeName[0])) {
                    for (UUID uuid : shopUtil.shopMap.keySet()) {
                        if (shopUtil.shopMap.get(uuid).equalsIgnoreCase(storeName[0])) {
                            for (int i = 0; i < e.getInventory().getSize(); i++) {
                                util.saveItemStack(uuid, i, e.getInventory().getItem(i));
                                if (shopCommand.editorList.contains((Player)e.getPlayer())) {
                                    if (e.getInventory().getItem(i) != null) {
                                        e.getPlayer().sendMessage(e.getInventory().getItem(i).getType().name());
                                    }
                                }
                            }
                            e.getPlayer().sendMessage(shopCommand.bar);
                        }
                    }
                }
            }
        }
        shopCommand.editorList.remove((Player)e.getPlayer());
    }
}