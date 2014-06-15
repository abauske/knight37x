package knight37x.lance.item;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderCannon extends Item {
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		boolean flag = true;
		if (!player.capabilities.isCreativeMode) {
			flag = player.inventory.consumeInventoryItem(Items.ender_pearl);
		}
		if(flag) {
			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityEnderPearl(world, player));
			}
		}
		return itemstack;
	}
}
