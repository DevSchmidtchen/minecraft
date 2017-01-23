package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 23.01.17.
 */
@Getter
public class ItemBuilder {

    private String displayName = null;
    private List<String> lore = null;
    private Material material;
    private int amount = 1;
    private short data = 0;

    public ItemBuilder(Material material) {
        this.material = material;
        lore = new ArrayList<>();
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder setLore(String ... lore) {
        for (String line : lore) {
            this.lore.add(Color.GRAY + line);
        }
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> list = new ArrayList<>();
        for (String line : lore) {
            list.add(Color.GRAY + line);
        }
        this.lore = list;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        if (data != 0) itemStack.setDurability(data);

        return itemStack;
    }

}
