package knight37x.lance.item;

import knight37x.lance.entity.EntitySpear;
import knight37x.lance.entity.EntitySpearPoison;
import knight37x.lance.entity.EntitySpearTNT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSpearPoison extends ItemSpear {

	@Override
	protected EntitySpear getSpear(World world, EntityPlayer player) {
		return new EntitySpearPoison(world, player, (float) (this.thrustValue * 0.8));
	}
	
}
