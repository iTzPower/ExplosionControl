package iTzPower.explosionControl;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;
import java.util.List;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {

        String mode = ExplosionControl.getInstance().getConfig().getString("explosioncontrol.type");
        List<String> blocks = ExplosionControl.getInstance().getConfig().getStringList("explosioncontrol.blocks");

        Iterator<Material> it;

        e.blockList().removeIf(block -> {

            Material type = block.getType();

            if (mode.equalsIgnoreCase("whitelist")) {
                return !blocks.contains(type.name());
            } else {
                return blocks.contains(type.name());
            }
        });
    }
}