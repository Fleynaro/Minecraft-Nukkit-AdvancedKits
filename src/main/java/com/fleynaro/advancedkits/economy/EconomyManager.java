package com.fleynaro.advancedkits.economy;

import cn.nukkit.Player;
import com.fleynaro.advancedkits.Main;
import me.onebone.economyapi.EconomyAPI;

public class EconomyManager {

    private EconomyAPI api;

    public EconomyManager(Main plugin){
        if ( plugin.getServer().getPluginManager().getPlugin("EconomyAPI") != null ) {
            api = EconomyAPI.getInstance();
        }
    }

    public boolean grantKit(Player player, int money) {
        if( this.api != null && this.api.reduceMoney(player, money) == 1 ) {
            return true;
        }
        return false;
    }

}