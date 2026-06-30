package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.UUID;

public class AbilityListener implements Listener {
    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 1000;

    public AbilityListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null || !event.getItem().hasItemMeta()) return;

        NamespacedKey key = new NamespacedKey(plugin, "custom_weapon");
        String weaponType = null;
        if (event.getItem().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {

            weaponType = event.getItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

            if ("wind_blade".equals(weaponType)) {
                Player p = event.getPlayer();
                UUID playerId = p.getUniqueId();
                long currentTime = System.currentTimeMillis();

                if (cooldowns.containsKey(playerId)) {
                    long timePassed = currentTime - cooldowns.get(playerId);
                    if (timePassed < COOLDOWN_TIME) {
                        long timeLeft = (COOLDOWN_TIME - timePassed) / 1000;
                        p.sendMessage(ChatColor.RED + "Wind Dash is on cooldown for " + timeLeft + "s!");
                        return;
                    }
                }

                // 2. Check Mana Second (Let's make it cost 25 Mana)
                int manaCost = 25;
                if (!ManaManager.consumeMana(p, manaCost)) {
                    p.sendMessage(ChatColor.RED + "Not enough mana!");
                    p.playSound(p.getLocation(), org.bukkit.Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return; // Stop here
                }

                p.setVelocity(p.getLocation().getDirection().multiply(2.0));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 1.0f);
                cooldowns.put(playerId, currentTime);
            }
        }
    }
}

