package at.notcleric.mCServer;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MobSpawnListener implements Listener {
    private final MobStatsManager statsManager;
    public MobSpawnListener(JavaPlugin plugin) {
        this.statsManager = new MobStatsManager(plugin);
    }
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getType() == EntityType.ZOMBIE) {
            LivingEntity zombie = event.getEntity();

            // Set custom stats for this specific zombie
            statsManager.setStat(zombie, "max_health", 100.0);
            zombie.setMaxHealth(100.0);
            zombie.setHealth(100.0);

            statsManager.setStat(zombie, "attack_power", 8.0);
        }
    }
}
