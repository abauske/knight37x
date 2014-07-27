package knight37x.magic.entity;

import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityTroll extends EntityCreature {
	
	protected int experienceValue = 100;
	private int homeCheckTimer;
	Village villageObj;
	public InventoryTroll inv = new InventoryTroll(this);
	
	public EntityTroll(World world) {
		super(world);
		this.setSize(1.4F, 1.9F);
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    }
    
    @Override
    protected void updateAITick() {
//    	this.setDead();
//    	StaticMethods.out("living");
        if (--this.homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null) {
                this.detachHome();
            } else {
                ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
                this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITick();
    }
    
    @Override
    protected int decreaseAirSupply(int par1) {
        return par1;
    }
    
    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        this.worldObj.setEntityState(this, (byte)4);
        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

        if (flag)
        {
            par1Entity.motionY += 0.4000000059604645D;
        }

//        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }
    
    public Village getVillage() {
        return this.villageObj;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(3);

            if (par2 > 0)
            {
                j += this.rand.nextInt(par2 + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
        
        StaticMethods.out("drop");
        
        int j = this.rand.nextInt(5);
        switch(j) {
        case 0: this.dropItem(Items.carrot, 1);
        	break;
        case 1: this.dropItem(Items.baked_potato, 1);
        	break;
        case 2: this.dropItem(Items.bread, 1);
        	break;
        case 3: this.dropItem(Items.pumpkin_pie, 1);
        	break;
        }
        
        j = this.rand.nextInt(3);
        if(j == 0) {
        	this.dropItem(Base.mana, this.rand.nextInt(2) + 1);
        }
	}

	@Override
	public int getMaxSpawnedInChunk() {
		// TODO Auto-generated method stub
		return super.getMaxSpawnedInChunk();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setTag("inv", this.inv.writeToNBT(new NBTTagList()));
		super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
        this.inv.readFromNBT(tag.getTagList("Inventory", 10));
		super.readFromNBT(tag);
	}
	
	@Override
	protected boolean isAIEnabled() {
		return true;
	}
	
	@Override
    public boolean getCanSpawnHere() {
		if(worldObj.villageCollectionObj.getVillageList().iterator().hasNext() && worldObj.villageCollectionObj.findNearestVillage((int)this.posX, (int)this.posY, (int)this.posZ, 10) == null) {
			return false;
        } else if(!this.worldObj.canBlockSeeTheSky((int) this.posX, (int) this.posY, (int) this.posZ)) {
        	return false;
        }
		return true;
    }

	@Override
    protected boolean canDespawn() {
    	return true;
    }
}
