package knight37x.lance.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import knight37x.lance.entity.EntitySpear;
import knight37x.lance.entity.EntitySpearTNT;

public class ItemSpearTNT extends ItemSpear {

	@Override
	protected EntitySpear getSpear(World world, EntityPlayer player) {
		return new EntitySpearTNT(world, player, (float) (this.thrustValue * 0.8));
	}
}
