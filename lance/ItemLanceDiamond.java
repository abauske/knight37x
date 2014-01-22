package knight37x.lance;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLanceDiamond extends ItemLance {
	
	private final String material = "Diamond";
	private final int strengh = 10;
	
	@Override
	public String getMaterialString() {
		return this.material;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
		ItemStack newLance = new ItemStack(Lance.lanceUpDia, 1);
		newLance.setItemDamage(itemstack.getItemDamage());
		return newLance;
	}
	
	@Override
	public int getStrengh() {
		return this.strengh;
	}

}
