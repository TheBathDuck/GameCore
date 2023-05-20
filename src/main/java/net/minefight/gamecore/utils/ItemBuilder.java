package net.minefight.gamecore.utils;

import net.kyori.adventure.text.Component;
import net.minefight.gamecore.GameCore;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Easily create itemstacks, without messing your hands. <i>Note that if you do
 * use this in one of your projects, leave this notice.</i> <i>Please do credit
 * me if you do use this in one of your projects.</i>
 *
 * @author NonameSL
 */
public class ItemBuilder {

//    public static String getStringNBT(ItemStack item, String key) {
//        return NBTEditor.getString(item, key);
//    }

    public static Object getData(ItemStack item, PersistentDataType type, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GameCore.getInstance(), key);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(namespacedKey, type);
    }

    public static boolean hasData(ItemStack item, PersistentDataType type, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(GameCore.getInstance(), key);
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(namespacedKey, type);
    }

    private ItemStack is;

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m The material to create the ItemBuilder with.
     */
    public ItemBuilder(Material m) {
        this(m, 1);
    }

    /**
     * Create a new ItemBuilder over an existing itemstack.
     *
     * @param is The itemstack to create the ItemBuilder over.
     */
    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     */
    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    /**
     * Create a new ItemBuilder from scratch.
     *
     * @param m          The material of the item.
     * @param amount     The amount of the item.
     * @param durability The durability of the item.
     */
    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    /**
     * Clone the ItemBuilder into a new one.
     *
     * @return The cloned instance.
     */
    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

//    public ItemBuilder setNBT(String key, String value) {
//        is = NBTEditor.set(is, value, key);
//        return this;
//    }
//
//    public ItemBuilder setNBT(String key, int value) {
//        is = NBTEditor.set(is, value, key);
//        return this;
//    }
//
//    public ItemBuilder makeUnbreakable(boolean unbreakable) {
//        is = NBTEditor.set(is, unbreakable ? (byte) 1 : (byte) 0, "Unbreakable");
//        return this;
//    }

    public ItemBuilder setType(Material material) {
        is.setType(material);
        return this;
    }

    /**
     * Change the durability of the item.
     *
     * @param dur The durability to set it to.
     */
    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     */

    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.displayName(ChatUtils.color("<italic:false>" + name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setName(Component component) {
        ItemMeta im = is.getItemMeta();
        im.displayName(component);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the displayname of the item and changes colors
     *
     * @param name new name
     */
    @Deprecated
    public ItemBuilder setColoredName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatUtils.legacyColor(name));
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param ench  The enchantment to add.
     * @param level The level to put the enchant on.
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param ench The enchantment to remove
     */
    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param ench  The enchant to add
     * @param level The level
     */
    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to
     * Short.MAX_VALUE.
     */
    public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     */
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setComponentLore(List<Component> lore) {
        ItemMeta im = is.getItemMeta();
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line))
            return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     */
    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size())
            return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     */
//    public ItemBuilder addLoreLine(String line) {
//        ItemMeta im = is.getItemMeta();
//        List<String> lore = new ArrayList<>();
//        if (im.hasLore())
//            lore = new ArrayList<>(im.getLore());
//        lore.add(ChatUtils.color(line));
//        im.setLore(lore);
//        is.setItemMeta(im);
//        return this;
//    }
    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if (im.hasLore())
            lore = new ArrayList<>(im.lore());
        lore.add(ChatUtils.color("<italic:false>" + line));
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnformattedLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(Component component) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>();
        if (im.hasLore())
            lore = new ArrayList<>(im.lore());
        lore.add(component);
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = is.getItemMeta();
        meta.lore(null);
        is.setItemMeta(meta);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @param pos  The index of where to put it.
     */
    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<Component> lore = new ArrayList<>(im.lore());
        lore.set(pos, ChatUtils.color(line));
        im.lore(lore);
        is.setItemMeta(im);
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor
     * pieces.
     *
     * @param color The color to set it to.
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    /**
     * Set the flags of an item.
     *
     * @param itemFlag The itemFlag you want to add.
     */
    public ItemBuilder setItemFlag(ItemFlag itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setItemFlags() {
        ItemMeta im = is.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) {
            im.addItemFlags(flag);
        }
        is.setItemMeta(im);
        return this;
    }


    /**
     * Set the flags of an item.
     *
     * @param itemFlag The itemFlag you want to add.
     */
    public ItemBuilder setItemFlag(ItemFlag[] itemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(itemFlag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setSkull(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(player.getName());
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setData(String key, PersistentDataType type, Object value) {
        NamespacedKey namespacedKey = new NamespacedKey(GameCore.getInstance(), key);
        ItemMeta im = is.getItemMeta();
        im.getPersistentDataContainer().set(namespacedKey, type, value);
        is.setItemMeta(im);
        return this;
    }

    public ItemStack build() {
        return is;
    }
}