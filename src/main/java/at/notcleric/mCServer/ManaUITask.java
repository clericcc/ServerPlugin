package at.notcleric.mCServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaUITask extends BukkitRunnable {
    @Override
    public void run() {
        ManaManager.regenerateMana();

        for (Player p : Bukkit.getOnlinePlayers()) {
            int currentMana = ManaManager.getMana(p);
            String actionBarText = ChatColor.AQUA + "✎ Mana: " + currentMana + "/" + ManaManager.MAX_MANA;
            p.sendActionBar(actionBarText);
        }
    }
}
