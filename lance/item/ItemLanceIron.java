package knight37x.lance.item;

import java.util.Map;

import knight37x.lance.Lance;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLanceIron extends ItemLance {

	private final String material = "Iron";
	private final int strengh = 7;
	
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
		return Lance.lanceUpIron;
	}
	
}
