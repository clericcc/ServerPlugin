package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemFlag;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;


public class ItemManager {

    public static ItemStack createWindBlade(JavaPlugin plugin) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Wind Blade");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+50");
        lore.add(ChatColor.GRAY + "Strength: " + ChatColor.RED + "+20");
        lore.add("");
        lore.add(ChatColor.GOLD + "Item Ability: Wind Dash " + ChatColor.YELLOW + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Dash forward in the direction");
        lore.add(ChatColor.GRAY + "you are looking.");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "25");
        lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "1s");
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY SWORD");

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        NamespacedKey idKey = new NamespacedKey(plugin, "custom_weapon");
        NamespacedKey damageKey = new NamespacedKey(plugin, "weapon_damage");
        NamespacedKey strengthKey = new NamespacedKey(plugin, "weapon_strength");

        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, "wind_blade");
        meta.getPersistentDataContainer().set(damageKey, PersistentDataType.DOUBLE, 50.0);
        meta.getPersistentDataContainer().set(strengthKey, PersistentDataType.DOUBLE, 20.0);

        sword.setItemMeta(meta);
        return sword;
    }

    public static ItemStack createCloudBoots(JavaPlugin plugin) {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Cloud Striders");
        meta.setColor(Color.WHITE);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Defense: " + ChatColor.GREEN + "+15");
        lore.add(ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+25");
        lore.add("");
        lore.add(ChatColor.GOLD + "Passive Ability: Featherweight");
        lore.add(ChatColor.GRAY + "Completely negates all fall damage");
        lore.add(ChatColor.GRAY + "while worn.");
        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "EPIC BOOTS");

        meta.setLore(lore);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DYE);

        NamespacedKey idKey = new NamespacedKey(plugin, "custom_armor");
        meta.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, "cloud_boots");

        boots.setItemMeta(meta);
        return boots;
    }
}
