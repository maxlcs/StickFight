package me.skaliert.stickfight.utils;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

public class ItemBuilder {

	private ItemStack item;
	private ItemMeta itemMeta;

	public ItemBuilder(Material material, short subId) {
		item = new ItemStack(material, 1, subId);
		itemMeta = item.getItemMeta();
	}

	public ItemBuilder(Material material) {
		this(material, (short) 0);
	}

	public ItemBuilder addEnchantment(Enchantment enchantment, int value, boolean exceeded) {
		itemMeta.addEnchant(enchantment, value, exceeded);
		return this;
	}

	public ItemBuilder setDurability(short durability) {
		item.setDurability(durability);
		return this;
	}

	public ItemBuilder setName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setPotionType(PotionEffectType potionEffectType) {
		PotionMeta potionMeta = (PotionMeta) itemMeta;
		potionMeta.setMainEffect(potionEffectType);
		return this;
	}

	public ItemBuilder setPotionName(String name) {
		PotionMeta potionMeta = (PotionMeta) itemMeta;
		potionMeta.setDisplayName(name);
		return this;
	}

	public ItemBuilder setPotionColor(Color color) {
		PotionMeta potionMeta = (PotionMeta) itemMeta;
		potionMeta.setColor(color);
		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		item.getItemMeta().setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder addItemFlag(ItemFlag flag) {
		itemMeta.addItemFlags(flag);
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public ItemBuilder setAuthor(String author) {
		BookMeta meta = (BookMeta) itemMeta;
		meta.setAuthor(author);
		return this;
	}

	public ItemBuilder setText(String... pages) {
		BookMeta meta = (BookMeta) itemMeta;
		meta.setPages(pages);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setOwner(String owner) {
		SkullMeta meta = (SkullMeta) itemMeta;
		meta.setOwner(owner);
		return this;
	}

	public ItemBuilder setOwner(OfflinePlayer offlinePlayer) {
		SkullMeta meta = (SkullMeta) itemMeta;
		meta.setOwningPlayer(offlinePlayer);
		return this;
	}

	public ItemStack build() {
		item.setItemMeta(itemMeta);
		return item;
	}
}