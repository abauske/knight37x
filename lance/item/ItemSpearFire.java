package knight37x.lance.item;

import knight37x.lance.entity.EntitySpear;
import knight37x.lance.entity.EntitySpearFire;
import knight37x.lance.entity.EntitySpearPoison;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSpearFire extends ItemSpear {

	@Override
	protected EntitySpear getSpear(World world, EntityPlayer player) {
		return new EntitySpearFire(world, player, (float) (this.thrustValue * 0.8));
	}
	
	@Override
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		System.out.println(par1ItemStack);
		System.out.println(par2World);
		System.out.println(par3EntityPlayer);
	}
	
}
