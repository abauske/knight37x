package knight37x.lance;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Spear extends ItemSnowball {

	
	@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --itemstack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

//        if(!world.isRemote) {
        	EntitySpear entity = new EntitySpear(world, player, 10);
        	if(player.capabilities.isCreativeMode) {
        		entity.canBePickedUp = 2;
        	}
//        	EntitySpear entity = new EntitySpear(world, player);
        	world.spawnEntityInWorld(entity);
//        }

        return itemstack;
    }
}
