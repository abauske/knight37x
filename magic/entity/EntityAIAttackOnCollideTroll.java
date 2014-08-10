package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIAttackOnCollideTroll extends EntityAIBase {
	
	World worldObj;
	EntityCreature attacker;
	/**
	 * An amount of decrementing ticks that allows the entity to attack once the
	 * tick reaches 0.
	 */
	int attackTick;
	/** The speed with which the mob will approach the target */
	double speedTowardsTarget;
	/**
	 * When true, the mob will continue chasing its target, even if it can't
	 * find a path to them right now.
	 */
	boolean longMemory;
	/** The PathEntity of our entity. */
	PathEntity entityPathEntity;
	Class classTarget;
	private int field_75445_i;
	private double field_151497_i;
	private double field_151495_j;
	private double field_151496_k;
	private static final String __OBFID = "CL_00001595";

	private int failedPathFindingPenalty;

	public EntityAIAttackOnCollideTroll(EntityCreature par1EntityCreature, Class par2Class, double par3, boolean par5) {
		this(par1EntityCreature, par3, par5);
		this.classTarget = par2Class;
	}

	public EntityAIAttackOnCollideTroll(EntityCreature par1EntityCreature, double par2, boolean par4) {
		this.attacker = par1EntityCreature;
		this.worldObj = par1EntityCreature.worldObj;
		this.speedTowardsTarget = par2;
		this.longMemory = par4;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else if (!entitylivingbase.isEntityAlive()) {
			return false;
		} else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
			return false;
		} else {
			if (--this.field_75445_i <= 0) {
				this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
				this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
				return this.entityPathEntity != null;
			} else {
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
		this.field_75445_i = 0;
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {
		this.attacker.getNavigator().clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		EntityLivingBase entity = this.attacker.getAttackTarget();
		this.attacker.getLookHelper().setLookPositionWithEntity(entity, 30.0F, 30.0F);
		double d0 = this.attacker.getDistanceSq(entity.posX, entity.boundingBox.minY, entity.posZ);
		double d1 = (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + entity.width);
		--this.field_75445_i;

		if ((this.longMemory || this.attacker.getEntitySenses().canSee(entity)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || entity.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
			this.field_151497_i = entity.posX;
			this.field_151495_j = entity.boundingBox.minY;
			this.field_151496_k = entity.posZ;
			this.field_75445_i = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

			if (this.attacker.getNavigator().getPath() != null) {
				PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
				if (finalPathPoint != null && entity.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
					failedPathFindingPenalty = 0;
				} else {
					failedPathFindingPenalty += 10;
				}
			} else {
				failedPathFindingPenalty += 10;
			}

			if (d0 > 1024.0D) {
				this.field_75445_i += 10;
			} else if (d0 > 256.0D) {
				this.field_75445_i += 5;
			}

			if (!this.attacker.getNavigator().tryMoveToEntityLiving(entity, this.speedTowardsTarget)) {
				this.field_75445_i += 15;
			}
		}

//		this.attackTick = Math.max(this.attackTick - 1, 0);
		
		if(d0 <= d1) {
			if(this.attackTick == 20) {
				this.attackTick = -10;
			} else if(this.attackTick == -10) {
				this.attackTick = 0;
				this.attacker.attackEntityAsMob(entity);
				entity.setPositionAndUpdate(entity.posX, entity.posY - 0.1F, entity.posZ);
				if(entity instanceof EntityCreature) {
					((EntityCreature) entity).setRevengeTarget(this.attacker);
				}
			}
		}
//		StaticMethods.out(this.attackTick);
		if(this.attackTick == -10) {
			this.attacker.getEntityData().setFloat("armState", -0.75F);
			this.sendArmState(this.attacker, -0.75F);
		} else {
			this.attacker.getEntityData().setFloat("armState", (float) (0F - this.attackTick * 0.075));
			this.sendArmState(this.attacker, (float) (0F - this.attackTick * 0.075));
		}
		if(this.attackTick < 20 && this.attackTick >= 0) {
			this.attackTick++;
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void sendArmState(Entity entity, float armState) {
		ByteBuf data = buffer(4);
		data.writeInt(Base.trollArmStatePacketID);
		data.writeInt(entity.getEntityId());
		data.writeFloat(armState);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToAll(packet);
	}
}