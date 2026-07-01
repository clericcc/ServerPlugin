package at.notcleric.mCServer;

import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;

public final class MCServer extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new MobDropListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(this), this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new MobBehaviorListener(), this);

        SpawnItemCommand mmoItemCmd = new SpawnItemCommand(this);

        getCommand("mmoitem").setExecutor(mmoItemCmd);
        getCommand("mmoitem").setTabCompleter(mmoItemCmd);
        getCommand("stats").setExecutor(new statsMenuCommand(this));

        new ManaUITask(this).runTaskTimer(this, 0L, 20L);

        getLogger().info("MMORPG Plugin aktiviert!");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        getLogger().info("MMORPG Plugin deaktiviert!");
        // Plugin shutdown logic
    }
}
