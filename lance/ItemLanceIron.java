package knight37x.lance;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
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
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
		ItemStack newLance = new ItemStack(Lance.lanceUpIron, 1);
		newLance.setItemDamage(itemstack.getItemDamage());
		return newLance;
	}
	
	@Override
	public int getStrengh() {
		return this.strengh;
	}

}
