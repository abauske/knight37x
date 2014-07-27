package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;

import java.util.List;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import knight37x.magic.items.ItemWand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityLightning extends EntityThrowable {

	private final double downMotion;
	
	private int field_145788_c = -1;
    private int field_145786_d = -1;
    private int field_145787_e = -1;
    private Block field_145785_f;
    public int throwableShake;
    /** The entity that threw this throwable item. */
    private EntityLivingBase thrower;
    private String throwerName;
    private int ticksInGround;
    private int ticksInAir;
    
    public EntityLivingBase victim;
    private int deathCounter = 0;
    private boolean drop = true;
	
	public EntityLightning(World par1World) {
		super(par1World);
//		this.setSize(0.1F, 0.7F);
		this.downMotion = this.motionY;;
	}
	
	public EntityLightning(World par1World, double x, double y, double z) {
		super(par1World, x, y, z);
//		this.setSize(0.1F, 0.7F);
		this.downMotion = this.motionY;;
	}
	
	public EntityLightning(World par1World, EntityLivingBase par2EntityLivingBase) {
		super(par1World, par2EntityLivingBase);
//		this.setSize(0.1F, 0.7F);
		this.downMotion = this.motionY;
	}
	
	@Override
	public void onUpdate()
    {
//		this.setDead();
//		this.motionX = 0;
//		this.motionY = -0.02F;
//		this.motionZ = 0;
		
		
		Entity e = new EntityLightningParticle(this.worldObj);
		e.copyLocationAndAnglesFrom(this);
		this.worldObj.spawnEntityInWorld(e);
		
		super.onUpdate();
    }

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(StaticMethods.isRunningOnServer()) {
			StaticMethods.out("server");
			if(pos.typeOfHit == MovingObjectType.ENTITY && pos.entityHit instanceof EntityLivingBase) {
				((ItemWand) Base.wand).victims.add((EntityLivingBase) pos.entityHit);
				ByteBuf data = buffer(4);
				data.writeInt(Base.magicPacketID);
				data.writeInt(pos.entityHit.getEntityId());
				FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
				Lance.packetHandler.sendToAll(packet);
			}
		} else {
			StaticMethods.out("client");
		}
		StaticMethods.out(this.getEntityId());
		this.setDead();
	}
	
	public void setVictim(EntityLivingBase vic) {
		this.victim = vic;
	}

	@Override
	protected float func_70182_d() {
		return 2;
	}

	@Override
	protected float func_70183_g() {
		return super.func_70183_g();
	}
}
