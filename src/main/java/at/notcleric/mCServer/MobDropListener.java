package at.notcleric.mCServer;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class MobDropListener implements Listener {
    private final JavaPlugin plugin;
    private final Random random = new Random();

    public MobDropListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        // For now, we are attaching our loot table to all Zombies
        if (event.getEntity().getType() == EntityType.ZOMBIE) {

            // Optional: If you want to remove vanilla drops like Rotten Flesh, uncomment this!
            // event.getDrops().clear();

            // --- 1. Wind Blade Drop (5% Chance) ---
            if (rollChance(5.0)) {
                event.getDrops().add(ItemManager.createWindBlade(plugin));
            }

            // --- 2. Cloud Boots Drop (3% Chance) ---
            if (rollChance(3.0)) {
                event.getDrops().add(ItemManager.createCloudBoots(plugin));
            }

            // Easily add more items here later!
            // if (rollChance(1.5)) { event.getDrops().add(ItemManager.createEpicSword(plugin)); }
        }
    }

    /**
     * Helper method to easily calculate drop chances.
     * @param percentage The % chance for the item to drop (e.g. 5.0 for 5%)
     * @return true if the item should drop, false if not.
     */
    private boolean rollChance(double percentage) {
        // random.nextDouble() generates a number between 0.00 and 99.99
        double roll = random.nextDouble() * 100.0;
        return roll <= percentage;
    }


}

