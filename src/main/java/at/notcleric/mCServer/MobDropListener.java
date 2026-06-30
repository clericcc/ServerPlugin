package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class MobDropListener implements Listener {
    private final JavaPlugin plugin;
    private final Random random = new Random();
    private final double DROP_CHANCE = 0.02; // 2% chance

    public MobDropListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        if (!(event.getEntity() instanceof Monster)) return;

        if (random.nextDouble() <= DROP_CHANCE) {
            event.getDrops().add(ItemManager.createWindBlade(plugin));
            killer.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "RNG DROP! " + ChatColor.AQUA + "You found a Wind Blade!");
            killer.playSound(killer.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);
        }
    }
}

