package at.notcleric.mCServer;

import org.bukkit.plugin.java.JavaPlugin;

public final class MCServer extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
        getServer().getPluginManager().registerEvents(new MobDropListener(this), this);

        getCommand("windblade").setExecutor(new WindBladeCommand(this));

        new ManaUITask().runTaskTimer(this, 0L, 20L);

        getServer().getPluginManager().registerEvents(new CombatListener(this), this);

        getLogger().info("MMORPG Plugin aktiviert!");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        getLogger().info("MMORPG Plugin deaktiviert!");
        // Plugin shutdown logic
    }
}
