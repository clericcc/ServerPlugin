package at.notcleric.mCServer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaUITask extends BukkitRunnable {
    private final ManaManager manaManager;

    public ManaUITask(JavaPlugin plugin) {
        this.manaManager = new ManaManager(plugin);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            // 1. Passive Regeneration: Give 2 mana every second (since this task runs every 20 ticks)
            manaManager.addMana(player, 2.0);

            // 2. Fetch current stats from the NBT container
            double currentMana = manaManager.getMana(player);
            double maxMana = manaManager.getMaxMana(player);

            // 3. Format and display on the Action Bar
            String actionBarText = ChatColor.AQUA + "❈ Mana: " + (int)currentMana + " / " + (int)maxMana;
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarText));
        }
    }
}
