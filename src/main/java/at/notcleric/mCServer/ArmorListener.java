package at.notcleric.mCServer;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import javax.naming.Name;

public class ArmorListener implements Listener {
    private final JavaPlugin plugin;
    public ArmorListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        ItemStack boots = player.getInventory().getBoots();
        if (boots == null || !boots.hasItemMeta()) return;

        ItemMeta meta = boots.getItemMeta();
        NamespacedKey idKey = new NamespacedKey(plugin, "custom_armor");

        if (meta.getPersistentDataContainer().has(idKey, PersistentDataType.STRING)) {
            String armorType = meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
            if ("cloud_boots".equals(armorType)) {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_WOOL_STEP, 1.0f, 1.0f);
            }
        }
    }
}
