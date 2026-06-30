package at.notcleric.mCServer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnItemCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public SpawnItemCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /mmoitem <windblade|cloudboots>");
            return true;
        }

        String requestedItem = args[0].toLowerCase();

        switch (requestedItem) {
            case "windblade":
                p.getInventory().addItem(ItemManager.createWindBlade(plugin));
                p.sendMessage(ChatColor.GREEN + "You received the Wind Blade!");
                break;

            case "cloudboots":
                p.getInventory().addItem(ItemManager.createCloudBoots(plugin));
                p.sendMessage(ChatColor.DARK_PURPLE + "You received the Cloudstriders!");
                break;

            default:
                p.sendMessage(ChatColor.RED + "Unknown item! Available: windblade, cloudboots");
                break;
        }

        return true;
    }
}
