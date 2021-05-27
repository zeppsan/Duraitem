package com.qvarnstrom.tech.duraitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public final class Duraitem extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        // Check if the user is holding a tool
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        if(isTool(itemInHand.getType())){
            if(!itemInHand.getItemMeta().hasDisplayName()){
                short durability = itemInHand.getDurability();
                short maxdurability = itemInHand.getData().getItemType().getMaxDurability();

                ItemMeta meta = itemInHand.getItemMeta();
                List<String> lore = new LinkedList<String>();
                lore.add(getDurabilityString(maxdurability, durability));
                meta.setLore(lore);
                itemInHand.setItemMeta(meta);
                player.getInventory().setItemInMainHand(itemInHand);
            }
        }
    }

    private String getDurabilityString(short maxDurability, short durability){
        String color;
        if((float)(maxDurability - durability) / (float)maxDurability * 10 > 6.66)
            color = "&7";
        else if ((float)(maxDurability - durability) / (float)maxDurability * 10 > 3.33)
            color = "&6";
        else
            color = "&c";

        String result ="&7Durability: " +maxDurability + " / " + color + (maxDurability - durability);
        return ChatColor.translateAlternateColorCodes('&', result);
    }

    private boolean isTool(Material item){
        String toolName = item.toString();
        if(toolName.endsWith("PICKAXE"))
            return true;
        if(toolName.endsWith("SHOVEL"))
            return true;
        if(toolName.endsWith("HOE"))
            return true;
        if(toolName.endsWith("AXE"))
            return true;
        if(toolName.endsWith("SHEARS"))
            return true;
        return false;
    }
}