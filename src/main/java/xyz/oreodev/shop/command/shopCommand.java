package xyz.oreodev.shop.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.oreodev.shop.util.shopInventory;
import xyz.oreodev.shop.util.shopUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class shopCommand implements CommandExecutor {
    private static shopInventory shopInventory;
    private shopUtil util;

    public static List<Player> editorList = new ArrayList<>();

    public final String bar = ChatColor.GREEN + "====================================================";

    public shopCommand() {
        this.util = new shopUtil();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            util.initialize();
            if (args.length == 0) {
                player.sendMessage(bar);
                player.sendMessage("/shop list | open (name) | remove (name) | edit (name) | create (name) (size)");
                player.sendMessage(bar);
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
                    player.sendMessage(bar);
                    player.sendMessage("total shop count : " + shopUtil.shopMap.size());
                    for (UUID uuid : shopUtil.shopMap.keySet()) {
                        player.sendMessage("UUID : " + uuid.toString() + " | Title : " + util.getSavedTitle(uuid) + " | Size : " + util.getSavedInventorySize(uuid));
                    }
                    player.sendMessage(bar);
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("o")) {
                    for (String name : shopUtil.shopMap.values()) {
                        if (args[1].equalsIgnoreCase(name)) {
                            for (UUID key : shopUtil.shopMap.keySet()) {
                                if (shopUtil.shopMap.get(key).equalsIgnoreCase(name)) {
                                    shopInventory shopInventory = new shopInventory(key, new shopUtil().getSavedTitle(key), new shopUtil().getSavedInventorySize(key));
                                    player.openInventory(shopInventory.getInventory());
                                }
                            }
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) {
                    for (String name : shopUtil.shopMap.values()) {
                        if (args[1].equalsIgnoreCase(name)) {
                            for (UUID key : shopUtil.shopMap.keySet()) {
                                if (shopUtil.shopMap.get(key).equalsIgnoreCase(name)) {
                                    player.sendMessage(bar);
                                    player.sendMessage("UUID  > " + key.toString());
                                    player.sendMessage("Title > " + util.getSavedTitle(key));
                                    player.sendMessage("Size  > " + util.getSavedInventorySize(key));
                                    for (int i = 0; i < util.getSavedInventorySize(key); i++) {
                                        player.sendMessage("Item" + i + "  > " + util.getSavedItemStack(key, i));
                                    }
                                    player.sendMessage(bar);
                                    player.openInventory(new shopInventory(key, new shopUtil().getSavedTitle(key), new shopUtil().getSavedInventorySize(key)).getInventory());
                                    editorList.add(player);
                                }
                            }
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r")) {
                    int cnt = 0;
                    for (String name : shopUtil.shopMap.values()) {
                        if (args[1].equalsIgnoreCase(name)) {
                            for (UUID key : shopUtil.shopMap.keySet()) {
                                if (shopUtil.shopMap.get(key).equalsIgnoreCase(name)) {
                                    player.sendMessage(bar);
                                    player.sendMessage("UUID  > " + key.toString());
                                    player.sendMessage("Title > " + util.getSavedTitle(key));
                                    player.sendMessage("Size  > " + util.getSavedInventorySize(key));
                                    for (int i = 0; i < util.getSavedInventorySize(key); i++) {
                                        player.sendMessage("Item" + i + "  > " + util.getSavedItemStack(key, i));
                                    }
                                    util.removeInventory(key);
                                    shopUtil.shopMap.remove(key);
                                    cnt++;
                                    player.sendMessage(bar);
                                }
                            }
                        }
                    }
                    if (cnt == 0) {
                        player.sendMessage(bar);
                        player.sendMessage("nothing removed..");
                        player.sendMessage(bar);
                    }
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
                    for (String name : shopUtil.shopMap.values()) {
                        if (args[1].equalsIgnoreCase(name)) {
                            player.sendMessage(bar);
                            player.sendMessage("overlapping names are impossible");
                            player.sendMessage(bar);
                            return false;
                        }
                    }
                    if (Integer.parseInt(args[2]) % 9 != 0) {
                        player.sendMessage(bar);
                        player.sendMessage("chest size must be multiples of 9");
                        player.sendMessage(bar);
                        return false;
                    }
                    shopInventory = new shopInventory(UUID.randomUUID(), args[1], Integer.parseInt(args[2]));
                    player.sendMessage(bar);
                    player.sendMessage("new Shop Created");
                    player.sendMessage("UUID  > " + shopInventory.getInventoryID());
                    player.sendMessage("Title > " + shopInventory.getTitle());
                    player.sendMessage("Size  > " + shopInventory.getSize());
                    player.sendMessage(bar);
                    player.openInventory(shopInventory.getInventory());
                    shopUtil.shopMap.put(shopInventory.getInventoryID(), shopInventory.getTitle());
                }
            }
        }
        return false;
    }
}
