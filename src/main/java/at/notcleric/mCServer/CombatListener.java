package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatListener implements Listener {
    private final JavaPlugin plugin;
    private final MobStatsManager mobStatsManager;
    public CombatListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mobStatsManager = new MobStatsManager(plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            LivingEntity victim = (LivingEntity) event.getEntity();

            ItemStack weapon = player.getInventory().getItemInMainHand();
            if (weapon != null && weapon.hasItemMeta()) {
                ItemMeta meta = weapon.getItemMeta();
                NamespacedKey damageKey = new NamespacedKey(plugin, "weapon_damage");
                NamespacedKey strengthKey = new NamespacedKey(plugin, "weapon_strength");

                if (meta.getPersistentDataContainer().has(damageKey, PersistentDataType.DOUBLE)) {

                    double weaponDamage = meta.getPersistentDataContainer().get(damageKey, PersistentDataType.DOUBLE);
                    double strength = meta.getPersistentDataContainer().getOrDefault(strengthKey, PersistentDataType.DOUBLE, 0.0);

                    // Base math from your simulator
                    double finalDamage = (5.0 + weaponDamage) * (1.0 + (strength / 100.0));

                    // NEW: Read mob's defense from the Stats Engine!
                    double mobDefense = mobStatsManager.getStat(victim, "defense");
                    if (mobDefense > 0) {
                        // Formula: 100 / (100 + Defense)
                        finalDamage = finalDamage * (100.0 / (100.0 + mobDefense));
                    }

                    event.setDamage(finalDamage);
                    spawnDamageIndicator(finalDamage, event.getEntity().getLocation().add(0, 1.5, 0));
                }
            }
        }

        if (event.getDamager() instanceof LivingEntity && !(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
            LivingEntity mob = (LivingEntity) event.getDamager();

            // Ask the StatsManager if this mob has a custom attack power
            double customAttackPower = mobStatsManager.getStat(mob, "attack_power");

            // If it has custom attack power (greater than 0), apply it!
            if (customAttackPower > 0) {
                event.setDamage(customAttackPower);
            }
        }
    }

    private void spawnDamageIndicator(double damage, Location loc) {
        ArmorStand indicator = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        indicator.setVisible(false);
        indicator.setMarker(true);
        indicator.setGravity(false);

        indicator.setCustomNameVisible(true);
        String formattedDamage = String.format("%.0f", damage);
        indicator.setCustomName(ChatColor.GRAY + "✧ " + ChatColor.RED + formattedDamage);

        new BukkitRunnable() {
            @Override
            public void run() {
                indicator.remove();
            }
        }.runTaskLater(plugin, 20L);
    }
}
