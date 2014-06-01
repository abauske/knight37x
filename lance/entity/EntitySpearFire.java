package knight37x.lance.entity;

import java.util.Random;

import knight37x.lance.Lance;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySpearFire extends EntitySpear {

	public EntitySpearFire(World par1World) {
		super(par1World);
		this.setFire(1000000);
	}

    public EntitySpearFire(World world, double par2, double par4, double par6) {
        super(world, par2, par4, par6);
		this.setFire(1000000);
    }

    public EntitySpearFire(World world, EntityLivingBase entity, EntityLivingBase entity2, float par4, float par5) {
        super(world, entity, entity2, par4, par5);
		this.setFire(1000000);
    }

    public EntitySpearFire(World world, EntityLivingBase entity, float par3) {
        super(world, entity, par3);
		this.setFire(1000000);
    }
    
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0, 0, 0);
	}

	@Override
	protected void performActionOnHit(Entity entity) {
		super.performActionOnHit(entity);
		entity.setFire(1);
	}
	
	@Override
	protected void performActionInGround() {
		if(this.canBePickedUp == 1) {
			double rand = Math.random();
			if(rand > 0.8D) {
				this.entityDropItem(new ItemStack(Items.coal, 1, 1), 0F);
			} else {
				this.dropItem(Lance.spear, 1);
			}
		}
	
	
		int x = this.x;
		int y = this.y;
		int z = this.z;
		double difX = this.posX - this.x;
		double difY = this.posY - this.y;
		double difZ = this.posZ - this.z;
		if(difY > 1.0) {
			y++;
		} else if(difY < 0.0) {
			y--;
		} else if(difX > 1.0) {
			x++;
		} else if(difX < 0.0) {
			x--;
		} else if(difZ > 1.0) {
			z++;
		} else if(difZ < 0.0) {
			z--;
		}
		if(this.worldObj.isAirBlock(x, y, z)) {
			this.worldObj.setBlock(x, y, z, Blocks.fire);
		} else if(Blocks.fire.getFlammability(this.worldObj.getBlock(x, y, z)) > 0) {
			if(this.worldObj.isAirBlock(x, y + 1, z)) {
				y++;
			} else if(this.worldObj.isAirBlock(x + 1, y, z)) {
				x++;
			} else if(this.worldObj.isAirBlock(x - 1, y, z)) {
				x--;
			} else if(this.worldObj.isAirBlock(x, y, z + 1)) {
				z++;
			} else if(this.worldObj.isAirBlock(x, y, z - 1)) {
				z--;
			}
			if(this.worldObj.isAirBlock(x, y, z)) {
				this.worldObj.setBlock(x, y, z, Blocks.fire);
			}
		}
	}

}
