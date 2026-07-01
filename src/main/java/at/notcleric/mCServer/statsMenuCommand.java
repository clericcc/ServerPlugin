package at.notcleric.mCServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class statsMenuCommand implements CommandExecutor {
    private final JavaPlugin plugin;
    private final ManaManager manaManager;

    public statsMenuCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        this.manaManager = new ManaManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        // 1. Create a 27-slot (3 rows) inventory with a custom title
        Inventory gui = Bukkit.createInventory(p, 27, ChatColor.DARK_GRAY + "Player Stats");

        // 2. Create Background Glass
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" "); // Blank name so it looks clean
        filler.setItemMeta(fillerMeta);

        // Fill all slots with the glass pane
        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, filler);
        }

        // 3. Create Mana Stat Icon (using your ManaManager!)
        ItemStack manaIcon = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta manaMeta = manaIcon.getItemMeta();
        manaMeta.setDisplayName(ChatColor.AQUA + "❈ Mana Pool");

        List<String> manaLore = new ArrayList<>();
        manaLore.add(ChatColor.GRAY + "Current Mana: " + ChatColor.WHITE + (int)manaManager.getMana(p));
        manaLore.add(ChatColor.GRAY + "Max Mana: " + ChatColor.WHITE + (int)manaManager.getMaxMana(p));
        manaMeta.setLore(manaLore);
        manaIcon.setItemMeta(manaMeta);

        // 4. Create Health Stat Icon
        ItemStack healthIcon = new ItemStack(Material.APPLE);
        ItemMeta healthMeta = healthIcon.getItemMeta();
        healthMeta.setDisplayName(ChatColor.RED + "❤ Health");

        List<String> healthLore = new ArrayList<>();
        healthLore.add(ChatColor.GRAY + "Max Health: " + ChatColor.WHITE + p.getMaxHealth());
        healthMeta.setLore(healthLore);
        healthIcon.setItemMeta(healthMeta);

        // 5. Place the icons in specific slots (Slot 11 is middle-left, 15 is middle-right)
        gui.setItem(11, healthIcon);
        gui.setItem(15, manaIcon);

        // 6. Open the GUI for the player
        p.openInventory(gui);

        return true;
    }
}
