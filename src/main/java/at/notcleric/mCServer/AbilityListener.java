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
    private final ManaManager manaManager;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long COOLDOWN_TIME = 1000;

    public AbilityListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.manaManager = new ManaManager(plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getItem() == null || !event.getItem().hasItemMeta()) return;

        NamespacedKey key = new NamespacedKey(plugin, "custom_weapon");

        if (!event.getItem().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;

        String weaponType = event.getItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        Player p = event.getPlayer();
        UUID playerId = p.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // IMPROVEMENT 3: Use a Switch statement. This makes adding new items incredibly easy!
        switch (weaponType) {
            case "wind_blade":

                // --- 1. Cooldown Check ---
                if (cooldowns.containsKey(playerId)) {
                    long timePassed = currentTime - cooldowns.get(playerId);
                    if (timePassed < COOLDOWN_TIME) {
                        // IMPROVEMENT 2: Use floating-point math (/ 1000.0) to show decimals (e.g. 0.5s)
                        double timeLeft = (COOLDOWN_TIME - timePassed) / 1000.0;
                        p.sendMessage(ChatColor.RED + "Wind Dash is on cooldown for " + String.format("%.1f", timeLeft) + "s!");
                        return;
                    }
                }

                // --- 2. Mana Check ---
                int manaCost = 25;
                if (!manaManager.useMana(p, manaCost)) {
                    p.sendMessage(ChatColor.RED + "Not enough mana!");
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }

                // --- 3. Execute Ability ---
                p.setVelocity(p.getLocation().getDirection().multiply(2.0));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 1.0f);

                // Put them on cooldown after a successful cast
                cooldowns.put(playerId, currentTime);
                break;

            // IMPROVEMENT 4: Ready for the future!
            // case "fire_staff":
            //     // Add fire staff logic here!
            //     break;
        }
    }
}

