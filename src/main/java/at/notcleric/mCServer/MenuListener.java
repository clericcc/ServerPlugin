package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        // 1. Check if the inventory title matches our custom GUI exactly
        if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Player Stats")) {

            // 2. Stop the player from moving or taking items!
            event.setCancelled(true);

            // Note: If you want to make clickable "Upgrade" buttons later,
            // you would check `event.getRawSlot()` here to see what they clicked!
        }
    }

}
