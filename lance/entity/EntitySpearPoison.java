package knight37x.lance.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySpearPoison extends EntitySpear {

	public EntitySpearPoison(World par1World) {
		super(par1World);
	}

    public EntitySpearPoison(World world, double par2, double par4, double par6)
    {
        super(world, par2, par4, par6);
    }

    public EntitySpearPoison(World world, EntityLivingBase entity, EntityLivingBase entity2, float par4, float par5)
    {
        super(world, entity, entity2, par4, par5);
    }

    public EntitySpearPoison(World world, EntityLivingBase entity, float par3)
    {
        super(world, entity, par3);
    }
    
	@Override
	protected void performActionOnHit(Entity entity) {
		super.performActionOnHit(entity);
		if(entity instanceof EntityLivingBase && !(entity instanceof EntityEnderman)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(19, 400, 1, true));
		}
	}
	
	@Override
	protected void performActionInGround() {
		super.performActionInGround();
        if (!this.worldObj.isRemote)
        {
            List list = Items.potionitem.getEffects(4);

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = this.boundingBox.expand(4.0D, 2.0D, 4.0D);
                List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.getPotionID();

                                if (Potion.potionTypes[i].isInstant())
                                {
                                	if(this.shootingEntity instanceof EntityLivingBase) {
                                        Potion.potionTypes[i].affectEntity((EntityLivingBase) this.shootingEntity, entitylivingbase, potioneffect.getAmplifier(), d1);
                                	}
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entitylivingbase.addPotionEffect(new PotionEffect(19, 400, 0, true));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 4);
        }
//		this.worldObj.spawnEntityInWorld(new EntityPotion(this.worldObj, this.posX, this.posY, this.posZ, 4));
	}

}
