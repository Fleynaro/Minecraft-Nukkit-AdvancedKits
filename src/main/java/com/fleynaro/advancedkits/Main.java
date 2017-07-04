package com.fleynaro.advancedkits;


import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import com.fleynaro.advancedkits.economy.EconomyManager;
import com.fleynaro.advancedkits.lang.LangManager;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main extends PluginBase implements Listener {

    public Map<String, Kit> kits = new HashMap<>();
    public EconomyManager economy;
    public boolean permManager = false;
    public LangManager langManager;

    @Override
    public void onEnable() {
        this.getDataFolder().mkdirs();
        this.saveDefaultConfig();
        this.saveResource("lang/" + this.getServer().getLanguage().getLang().toUpperCase() + ".properties", "/lang.properties", false);
        
        this.loadKits();
        this.economy = new EconomyManager(this);
        this.langManager = new LangManager(this);
        if( this.getConfig().getBoolean("permissions") ){
            this.permManager = true;
        }
        this.getServer().getScheduler().scheduleRepeatingTask(this, new Runnable() {
            private int min = 0;
            private int everyMin = getConfig().getInt("autosave");
            public void run() {
                for( Kit kit : kits.values() ) {
                    kit.processCoolDown();
                }
                if ( ++ this.min % everyMin == 0 ) {
                    for( Kit kit : kits.values() ) {
                        kit.save();
                    }
                }
            }
        }, 1200);
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        
        this.getLogger().info(TextFormat.WHITE +"The plugin "+ TextFormat.RED +"AdvancedKits"+ TextFormat.WHITE +" has been loaded.");
    }

    @Override
    public void onDisable() {
        for( Kit kit : kits.values() ) {
            kit.save();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        switch(command.getName().toLowerCase()){
            case "kit":
            {
                if( !(sender instanceof Player) ){
                    sender.sendMessage(this.langManager.getTranslation("in-game"));
                    return true;
                }
                if( args.length == 0 ){
                    sender.sendMessage(this.langManager.getTranslation("av-kits", new String[]{this.getKitList()}));
                    return true;
                }
                Kit kit = this.getKit(args[0]);
                if( kit == null ){
                    sender.sendMessage(this.langManager.getTranslation("no-kit", new String[]{args[0]}));
                    return true;
                }
                kit.handleRequest((Player)sender);
                return true;
            }
            case "akreload":
            {
                for( Map.Entry<String, Kit> entry : kits.entrySet() ) {
                    entry.getValue().save();
                }
                this.kits = null;
                this.loadKits();
                sender.sendMessage(this.langManager.getTranslation("reload"));
                return true;
            }
        }
        return true;
    }

    private void loadKits(){
        this.saveResource("kits.yml");
        Config cfgKits = new Config(this.getDataFolder() + "/kits.yml", Config.YAML);
        for( Map.Entry<String, Object> entry : cfgKits.getAll().entrySet() ) {
            this.kits.put(entry.getKey(), new Kit(this, (ConfigSection) entry.getValue(), entry.getKey()));
        }
    }

    /*private void fixConfig(&config){
        foreach(config as name => kit){
            if(isset(kit["users"])){
                users = array_map("strtolower", kit["users"]);
                config[name]["users"] = users;
            }
            if(isset(kit["worlds"])){
                worlds = array_map("strtolower", kit["worlds"]);
                config[name]["worlds"] = worlds;
            }
        }
    }*/

    public String getKitList() {
        String allKits = "";
        for( String kitName : kits.keySet() ) {
            allKits += kitName + "|";
        }
        return allKits.substring(0, allKits.length() - 2);
    }

    public Kit getKit(String kit) {
        Map<String, Kit> lowerKeys = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        lowerKeys.putAll(kits);
        if( lowerKeys.containsKey(kit.toLowerCase()) ){
            return lowerKeys.get(kit.toLowerCase());
        }
        return null;
    }
}