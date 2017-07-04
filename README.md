# Minecraft-Nukkit-AdvancedKits

# Minecraft-Nukkit-AdvancedKits
Minecraft PE plugin AdvancedKits for Nukkit


Портированный плагин AdvancedKits для серверов Minecraft PE на серверной платформе Nukkit позволит вам создать на сервере функциональную систему выдачи наборов командой /kit.
Поддерживается плагин EconomyAPI, все функции AdvancedKits портированы без изменений и потерь.
Подробнее на русском и с картинками:
https://forum.24serv.pro/topic/21-advancedkits-plagin-dobavlyayuschiy-kity-na-vash-server/


The original text:
PocketMine-MP plugin that adds kits to your PocketMine server.

This is a simple yet useful PocketMine-MP kit plugin. For who doesn't know what kits are, they are groups of items that you can get simply by typing a command or touching a sign.

Features:

Highly configurable
Custom permission support: give a player permission advancedkits.kitname to let him use the kit named "kitname"
Built in perms system for non-PurePerms users (read the documentation)
Economy support: pay to get a kit. Support for EconomyS, PocketMoney and MassiveEconomy
Sign support: write a sign to let users get a kit
Unlimited kits with unlimited items, and armor support
Time limit (cooldown) for kits
Option for one kit per life (see config.yml)
Execute commands with kits
Easy translation system
Commands: The main command: /kit Alias for /kit: /ak, /advancedkits .

/kit
/akreload - reload kits.yml (when edited while the server is running)
Signs: To let users get a kit through a sign, you can create one like this: (capitals don't matter)

Line 1: [AdvancedKits]
Line 2: kitname
Line 3 & 4: Whatever you like

The default kit is: testkit. You can add kits editing kits.yml (see "Kit settings").

Kit Settings:

In order to add kit you will need to edit the config kits.yml . If you open that file with bloc notes, you will be not able to edit because it will be all in one line, so open it with WordPad, Notepad ++, ... You can add lots of kits, but remember to keep this file format:
