package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.magic.Base;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMagic extends Entity {

	public final static int TICKS_UNTIL_DROP = 60;
	private int remainingTicks;
	private float falling = 0;
	public EntityLivingBase victim;
	
	public EntityMagic(World par1World, EntityLivingBase victim) {
		super(par1World);
		this.victim = victim;
		this.setSize(0.0F, victim.getEyeHeight());
		this.remainingTicks = TICKS_UNTIL_DROP;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.setPosition(victim.posX, victim.posY, victim.posZ);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!victim.isDead) {
			this.motionY -= (double)0.06F;

	        if (this.remainingTicks-- > 0) {
	            this.motionX *= 0.02D;
	            this.motionY *= 0.02D;
	            this.motionZ *= 0.02D;
	        } else {
	        	this.height -= 0.1F;
				this.falling += 0.1F;
				if(this.height <= 0) {
					this.height = 0.0F;
					this.sendMagic(victim);
					this.setDead();
				}
	        }

	        this.moveEntity(this.motionX, this.motionY, this.motionZ);
	        this.motionX *= 0.9800000190734863D;
	        this.motionY *= 0.9800000190734863D;
	        this.motionZ *= 0.9800000190734863D;
			
//			if(this.falling > 0) {
//				this.posX = this.victim.posX;
//				this.motionY -= 0.1F;
//				this.posZ = this.victim.posZ;
//			} else {
//				this.setPosition(this.victim.posX, this.victim.posY, this.victim.posZ);
//			}
//			
//			this.remainingTicks--;
//			if(this.remainingTicks <= 0) {
//				this.height -= 0.1F;
//				this.falling += 0.1F;
//				if(this.height <= 0) {
//					this.height = 0.0F;
//					this.sendMagic(victim);
//					this.setDead();
//				}
//			}
		} else {
			this.setDead();
		}
	}
    
    private void sendMagic(EntityLivingBase toKill) {
    	ByteBuf data = buffer(4);
		data.writeInt(Base.magicSuccedPacketID);
		data.writeInt(toKill.getEntityId());
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
    }

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {}
}
