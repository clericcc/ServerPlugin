package at.notcleric.mCServer;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public class MobBehaviorListener implements Listener {

    @EventHandler
    public void onMobCombust(EntityCombustEvent event) {
        // 1. Check if the entity catching fire is a Zombie
        if (event.getEntity().getType() == EntityType.ZOMBIE) {

            // 2. We only want to cancel sunlight burning.
            // If the fire is NOT from a block (like lava) and NOT from an entity (like a Fire Aspect sword)...
            if (!(event instanceof EntityCombustByEntityEvent) && !(event instanceof EntityCombustByBlockEvent)) {

                // 3. ...then it must be the sun! Cancel the fire.
                event.setCancelled(true);
            }
        }
    }
}
