package iTzPower.explosionControl;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ExplosionTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        List<String> list = new ArrayList<>();

        if (!cmd.getName().equalsIgnoreCase("explosioncontrol"))
            return list;

        if (args.length == 1) {
            list.add("help");
            list.add("add");
            list.add("remove");
            list.add("reload");
            list.add("mode");
            return filter(list, args[0]);
        }

        if (args[0].equalsIgnoreCase("mode") && args.length == 2) {
            list.add("whitelist");
            list.add("blacklist");
            return filter(list, args[1]);
        }

        if (args[0].equalsIgnoreCase("add") && args.length == 2) {
            for (Material m : Material.values()) {
                if (m.isBlock()) list.add(m.name());
            }
            return filter(list, args[1]);
        }

        if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
            List<String> blocks = ExplosionControl.getInstance()
                    .getConfig().getStringList("explosioncontrol.blocks");
            return filter(blocks, args[1]);
        }

        return list;
    }

    private List<String> filter(List<String> list, String prefix) {
        List<String> result = new ArrayList<>();
        String p = prefix.toUpperCase();

        for (String s : list) {
            if (s.toUpperCase().startsWith(p)) result.add(s);
        }
        return result;
    }
}
