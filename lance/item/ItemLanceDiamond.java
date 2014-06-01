package knight37x.lance.item;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import knight37x.lance.Lance;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemLanceDiamond extends ItemLance {
	
	private final String material = "Diamond";
	private final int strengh = 10;
	
	@Override
	public String getMaterialString() {
		return this.material;
	}
	
	@Override
	public int getStrengh() {
		return this.strengh;
	}

	@Override
	public Item getSwitch() {
		return Lance.lanceUpDia;
	}
}
