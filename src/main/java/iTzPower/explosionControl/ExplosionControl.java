package iTzPower.explosionControl;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ExplosionControl extends JavaPlugin {

    private static ExplosionControl instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        MessageManager.load(this);

        getServer().getPluginManager().registerEvents(new ExplosionListener(), this);

        getCommand("explosioncontrol").setExecutor(this);
        getCommand("explosioncontrol").setTabCompleter(new ExplosionTabComplete());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            MessageManager.load(this);
            sender.sendMessage(MessageManager.get("config-reloaded"));
            return true;
        }

        if (args[0].equalsIgnoreCase("mode") && args.length == 2) {

            String mode = args[1].toLowerCase();

            if (!mode.equals("whitelist") && !mode.equals("blacklist")) {
                sender.sendMessage(MessageManager.get("invalid-mode"));
                return true;
            }

            getConfig().set("explosioncontrol.type", mode);
            saveConfig();
            sender.sendMessage(MessageManager.get("mode-set").replace("{mode}", mode));
            return true;
        }

        if (args[0].equalsIgnoreCase("add") && args.length == 2) {

            String block = args[1].toUpperCase();
            Material mat = Material.getMaterial(block);

            if (mat == null) {
                sender.sendMessage(MessageManager.get("invalid-block").replace("{block}", block));
                return true;
            }

            List<String> list = getConfig().getStringList("explosioncontrol.blocks");

            if (list.contains(block)) {
                sender.sendMessage(MessageManager.get("already-added"));
                return true;
            }

            list.add(block);
            getConfig().set("explosioncontrol.blocks", list);
            saveConfig();
            sender.sendMessage(MessageManager.get("block-added").replace("{block}", block));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove") && args.length == 2) {

            String block = args[1].toUpperCase();
            List<String> list = getConfig().getStringList("explosioncontrol.blocks");

            if (!list.contains(block)) {
                sender.sendMessage(MessageManager.get("not-in-list"));
                return true;
            }

            list.remove(block);
            getConfig().set("explosioncontrol.blocks", list);
            saveConfig();
            sender.sendMessage(MessageManager.get("block-removed").replace("{block}", block));
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {

        sender.sendMessage(MessageManager.get("help-header"));
        sender.sendMessage(MessageManager.get("help-1"));
        sender.sendMessage(MessageManager.get("help-2"));
        sender.sendMessage(MessageManager.get("help-3"));
        sender.sendMessage(MessageManager.get("help-4"));
        sender.sendMessage(MessageManager.get("help-5"));
    }

    public static ExplosionControl getInstance() {
        return instance;
    }
}
