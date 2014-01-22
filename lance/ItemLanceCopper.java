package knight37x.lance;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLanceCopper extends ItemLance {
	
	private final String material = "Copper";
	private final int strengh = 4;
	
	@Override
	public String getMaterialString() {
		return this.material;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
		ItemStack newLance = new ItemStack(Lance.lanceUpCopper, 1);
		newLance.setItemDamage(itemstack.getItemDamage());
		return newLance;
	}
	
	@Override
	public int getStrengh() {
		return this.strengh;
	}
	
}
