package at.notcleric.mCServer;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ManaManager {
    // Stores UUID -> Current Mana
    private static final HashMap<UUID, Integer> playerMana = new HashMap<>();
    public static final int MAX_MANA = 100;

    // Get a player's mana (defaults to 100 if they aren't in the map yet)
    public static int getMana(Player player) {
        return playerMana.getOrDefault(player.getUniqueId(), MAX_MANA);
    }

    // Set a player's mana directly
    public static void setMana(Player player, int amount) {
        // Math.min ensures they never go over their Max Mana
        playerMana.put(player.getUniqueId(), Math.min(amount, MAX_MANA));
    }

    // Attempt to consume mana. Returns true if successful, false if they don't have enough.
    public static boolean consumeMana(Player player, int cost) {
        int currentMana = getMana(player);
        if (currentMana >= cost) {
            setMana(player, currentMana - cost);
            return true;
        }
        return false;
    }

    // Loop through everyone in the map and give them 5 mana back
    public static void regenerateMana() {
        for (UUID uuid : playerMana.keySet()) {
            int current = playerMana.get(uuid);
            if (current < MAX_MANA) {
                playerMana.put(uuid, Math.min(current + 5, MAX_MANA));
            }
        }
    }
}
