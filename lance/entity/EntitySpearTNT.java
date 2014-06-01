package knight37x.lance.entity;

import java.util.List;

import knight37x.lance.Lance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySpearTNT extends EntitySpear {
	
	public EntitySpearTNT(World world)
    {
        super(world);
    }

    public EntitySpearTNT(World world, double par2, double par4, double par6)
    {
        super(world, par2, par4, par6);
    }

    public EntitySpearTNT(World world, EntityLivingBase entity, EntityLivingBase entity2, float par4, float par5)
    {
        super(world, entity, entity2, par4, par5);
    }

    public EntitySpearTNT(World world, EntityLivingBase entity, float par3)
    {
        super(world, entity, par3);
    }
    
	@Override
	protected void performActionOnHit(Entity entity) {
		entity.hurtResistantTime = 0;
		this.performActionInGround();
	}
	
	@Override
	protected void performActionInGround() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true);
	}
    
}
