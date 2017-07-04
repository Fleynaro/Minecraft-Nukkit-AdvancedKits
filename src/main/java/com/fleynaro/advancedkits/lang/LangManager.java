package com.fleynaro.advancedkits.lang;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import com.fleynaro.advancedkits.Main;
import java.util.LinkedHashMap;

public class LangManager {

    public int LANG_VERSION = 0;

    private Main ak;
    private LinkedHashMap<String, Object> defaults = new LinkedHashMap<>();
    private Config data;

    public LangManager(Main ak){
        this.ak = ak;
        this.defaults.put("lang-version", 0);
        this.defaults.put("in-game", "Please run this command in game");
        this.defaults.put("av-kits", "Available kits: {%0}");
        this.defaults.put("no-kit", "Kit &e{%0} &fdoes not exist");
        this.defaults.put("reload", "Reloaded kits settings");
        this.defaults.put("sel-kit", "&eSelected kit: &b{%0}");
        this.defaults.put("cant-afford", "You cannot afford kit: {%0}");
        this.defaults.put("cooldown", "&dYou will be able to get it in &c{%0}");
        this.defaults.put("no-perm", "&cYou haven't the permission to use kit &4{%0}");
        this.defaults.put("cooldown-format1", "{%0} minutes");
        this.defaults.put("cooldown-format2", "{%0} hours and {%1} minutes");
        this.defaults.put("cooldown-format3", "{%0} hours");
        this.defaults.put("no-sign-on-kit", "&cOn this sign, the kit is not specified");
        this.defaults.put("no-perm-sign", "&cYou don't have permission to create a sign kit");
        
        this.data = new Config(this.ak.getDataFolder() + "/lang.properties", Config.PROPERTIES, new ConfigSection(this.defaults));
        
        /*if( this.data.getInt("lang-version") != LANG_VERSION ) {
            this.ak.getLogger().alert("Translation file is outdated. The old file has been renamed and a new one has been created");
            this.data = new Config(this.ak.getDataFolder() + "lang.properties", Config.PROPERTIES, new ConfigSection(this.defaults));
        }*/
    }
    
    public String getTranslation(String dataKey) {
        return this.getTranslation(dataKey, null);
    }

    public String getTranslation(String dataKey, String[] args) {
        if( !this.defaults.containsKey(dataKey) ){
            this.ak.getLogger().error("Invalid dataKey "+ dataKey +" passed to method LangManager::getTranslation()");
            return "";
        }
        
        String str = this.data.getString(dataKey, (String) this.defaults.get(dataKey));
        if ( args != null ) {
            for ( int i = 0; i < args.length; i ++ ) {
                str = str.replace("{%"+ i +"}", args[i]);
            }
        }
        return TextFormat.colorize(str);
    }

}