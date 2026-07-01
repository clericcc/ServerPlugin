package at.notcleric.mCServer;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class MobStatsManager {
    private final JavaPlugin plugin;
    public MobStatsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public double getStat(LivingEntity entity, String statKey) {
        NamespacedKey key = new NamespacedKey(plugin, statKey);
        if (entity.getPersistentDataContainer().has(key, PersistentDataType.DOUBLE)) {
            return entity.getPersistentDataContainer().get(key, PersistentDataType.DOUBLE);
        }
        return 0.0; // Default if not set
    }

    public void setStat(LivingEntity entity, String statKey, double value) {
        NamespacedKey key = new NamespacedKey(plugin, statKey);
        entity.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, value);
    }
}
