package at.notcleric.mCServer;

import org.bukkit.entity.Player;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
public class ManaManager {
    private final NamespacedKey manaKey;
    private final NamespacedKey maxManaKey;

    public ManaManager(JavaPlugin plugin) {
        this.manaKey = new NamespacedKey(plugin, "mana");
        this.maxManaKey = new NamespacedKey(plugin, "max_mana");
    }

    public double getMaxMana(Player player) {
        return player.getPersistentDataContainer().getOrDefault(maxManaKey, PersistentDataType.DOUBLE, 100.0);
    }

    public void setMaxMana(Player player, double maxMana) {
        player.getPersistentDataContainer().set(maxManaKey, PersistentDataType.DOUBLE, maxMana);
    }

    public double getMana(Player player) {
        return player.getPersistentDataContainer().getOrDefault(manaKey, PersistentDataType.DOUBLE, getMaxMana(player));
    }

    public void setMana(Player player, double mana) {
        double max = getMaxMana(player);
        if (mana > max) mana = max; // Cap at max mana
        if (mana < 0) mana = 0;     // Prevent negative mana

        player.getPersistentDataContainer().set(manaKey, PersistentDataType.DOUBLE, mana);
    }

    // Call this from your AbilityListener when a player tries to use a spell!
    public boolean useMana(Player player, double amount) {
        double current = getMana(player);
        if (current >= amount) {
            setMana(player, current - amount);
            return true; // Spell cast successful!
        }
        return false; // Not enough mana!
    }

    public void addMana(Player player, double amount) {
        setMana(player, getMana(player) + amount);
    }
}
