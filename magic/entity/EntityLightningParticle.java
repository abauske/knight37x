package knight37x.magic.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityLightningParticle extends Entity {

	public EntityLightningParticle(World par1World) {
		super(par1World);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		if(this.ticksExisted > 40) {
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

}
