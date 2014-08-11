package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityDrop extends Entity {

	public final static int TICKS_UNTIL_DROP = 60;
	private int remainingTicks;
	private float falling = 0;
	public EntityLivingBase victim;
	
	public EntityDrop(World par1World, EntityLivingBase victim) {
		super(par1World);
		this.victim = victim;
		this.setSize(0.0F, victim.getEyeHeight());
		this.remainingTicks = TICKS_UNTIL_DROP;
        this.motionX = this.motionY = this.motionZ = 0.0D;
//        if(StaticMethods.isRunningOnClient() && this.victim == Minecraft.getMinecraft().thePlayer) {
        	this.setPosition(victim.posX, victim.posY, victim.posZ);
//        } else {
//            this.setPosition(victim.posX, victim.posY + victim.getEyeHeight(), victim.posZ);
//        }
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
//		this.setDead();
		if(!victim.isDead) {
			this.victim.motionX = this.victim.motionY = this.victim.motionZ = 0.0D;
			this.victim.setPosition(this.victim.lastTickPosX, this.victim.lastTickPosY, this.victim.lastTickPosZ);
			if(this.posY < this.victim.posY - this.victim.getEyeHeight()) {
				if(StaticMethods.isRunningOnClient()) {
					this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ));
				}
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
				this.victim.attackEntityFrom(DamageSource.magic, this.victim.getHealth() + 1);
				this.setDead();
			}
			if (this.remainingTicks-- > 0) {
				this.motionX = this.motionY = this.motionZ = 0.0D;
	        } else {
	        	this.falling++;
	        	this.setPosition(this.posX, this.posY - this.falling / 10, this.posZ);
	        }
//			this.motionY -= (double)0.06F;
//
//	        if (this.remainingTicks-- > 0) {
//	            this.motionX *= 0.02D;
//	            this.motionY *= 0.02D;
//	            this.motionZ *= 0.02D;
//	        } else {
//	        	this.height -= 0.1F;
//				this.falling += 0.1F;
//				if(this.height <= 0) {
//					this.height = 0.0F;
//					this.sendMagic(victim);
//					this.setDead();
//				}
//	        }
//
//	        this.moveEntity(this.motionX, this.motionY, this.motionZ);
//	        this.motionX *= 0.9800000190734863D;
//	        this.motionY *= 0.9800000190734863D;
//	        this.motionZ *= 0.9800000190734863D;
//			
////			if(this.falling > 0) {
////				this.posX = this.victim.posX;
////				this.motionY -= 0.1F;
////				this.posZ = this.victim.posZ;
////			} else {
////				this.setPosition(this.victim.posX, this.victim.posY, this.victim.posZ);
////			}
////			
////			this.remainingTicks--;
////			if(this.remainingTicks <= 0) {
////				this.height -= 0.1F;
////				this.falling += 0.1F;
////				if(this.height <= 0) {
////					this.height = 0.0F;
////					this.sendMagic(victim);
////					this.setDead();
////				}
////			}
		} else {
			this.setDead();
		}
	}
	
	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {}
}
