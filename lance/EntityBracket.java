package knight37x.lance;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBracket extends EntityItemFrame {

	public EntityBracket(World par1World) {
        super(par1World);
    }

    public EntityBracket(World world, int par2, int par3, int par4, int par5) {
        super(world, par2, par3, par4, par5);
    }
    
    @Override
	public void onUpdate() {
		super.onUpdate();
		System.out.println("living");
	}
	
//	//clicked on coords:
//	private boolean hasBeenSet = false;
//	private int x;
//	private int y;
//	private int z;
//	
//	public EntityBracket(World par1World) {
//		super(par1World);
//	}
//	
//	/*
//	 * x,y,z where player clicked on
//	 */
//	public EntityBracket(World world, int par2, int par3, int par4, int par5) {
//		super(world, par2, par3, par4, par5);
//    }
//	
//	public boolean setClickedOn(int x, int y, int z) {
//		if(!this.hasBeenSet) {
//			this.x = x;
//			this.y = y;
//			this.z = z;
//			this.hasBeenSet = true;
//			return true;
//		}
//		return false;
//	}
//	
//	@Override
//	public void onUpdate() {
//		super.onUpdate();
//		System.out.println("living");
//	}
//
//	@Override
//	public int getWidthPixels() {
//		return 7;
//	}
//
//	@Override
//	public int getHeightPixels() {
//		return 5;
//	}
//
//	@Override
//	public void onBroken(Entity var1) {
//		
//	}
//	
//	@Override
//	public boolean onValidSurface()
//    {
//        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty())
//        {
//            return false;
//        } else {
//            int i = Math.max(1, this.getWidthPixels() / 16);
//            int j = Math.max(1, this.getHeightPixels() / 16);
//            int k = this.field_146063_b;
//            int l = this.field_146064_c;
//            int i1 = this.field_146062_d;
//
//            if (this.hangingDirection == 2)
//            {
//                k = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
//            }
//
//            if (this.hangingDirection == 1)
//            {
//                i1 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
//            }
//
//            if (this.hangingDirection == 0)
//            {
//                k = MathHelper.floor_double(this.posX - (double)((float)this.getWidthPixels() / 32.0F));
//            }
//
//            if (this.hangingDirection == 3)
//            {
//                i1 = MathHelper.floor_double(this.posZ - (double)((float)this.getWidthPixels() / 32.0F));
//            }
//
//            l = MathHelper.floor_double(this.posY - (double)((float)this.getHeightPixels() / 32.0F));
//
//            if(this.hasBeenSet) {
//                if(!this.worldObj.func_147439_a(this.x, this.y, this.z).func_149688_o().isSolid()) {
//            		return false;
//            	}
//            } else {
//                for (int j1 = 0; j1 < i; ++j1)
//                {
//                    for (int k1 = 0; k1 < j; ++k1)
//                    {
//                        Material material;
//
//                        if (this.hangingDirection != 2 && this.hangingDirection != 0)
//                        {
//                            material = this.worldObj.func_147439_a(this.field_146063_b, l + k1, i1 + j1).func_149688_o();
//                        }
//                        else
//                        {
//                            material = this.worldObj.func_147439_a(k + j1, l + k1, this.field_146062_d).func_149688_o();
//                        }
//
//                        if (!material.isSolid())
//                        {
//                            return false;
//                        }
//                    }
//                }
//            }
//
//            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox);
//            Iterator iterator = list.iterator();
//            Entity entity;
//
//            do
//            {
//                if (!iterator.hasNext())
//                {
//                    return true;
//                }
//
//                entity = (Entity)iterator.next();
//            }
//            while (!(entity instanceof EntityHanging));
//
//            return false;
//        }
//    }

}
