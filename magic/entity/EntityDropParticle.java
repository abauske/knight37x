package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityHeartFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticle extends EntityDropParticleFX {

//	public static Map<EntityLivingBase, Integer> victims = new HashMap();
	
	private Material materialType;
    /** The height of the current bob */
    private int bobTimer;
    public static EntityLivingBase lastTickVictim = null;
    public static EntityLivingBase victim = null;

    public EntityDropParticle(World par1World, double par2, double par4, double par6, Material material)
    {
        super(par1World, par2, par4, par6, material);
        this.motionX = this.motionY = this.motionZ = 0.0D;

        if (material == Material.water)
        {
            this.particleRed = 0.0F;
            this.particleGreen = 0.0F;
            this.particleBlue = 1.0F;
        }
        else
        {
            this.particleRed = 1.0F;
            this.particleGreen = 0.0F;
            this.particleBlue = 0.0F;
        }

        this.setParticleTextureIndex(113);
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.materialType = material;
        this.bobTimer = 40;
        this.particleMaxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
        this.motionX = this.motionY = this.motionZ = 0.0D;
    }

    public EntityDropParticle(World world, double d, double e, double f, Material water, EntityLivingBase victim) {
		this(world, d, e, f, water);
		this.victim = victim;
	}

	public int getBrightnessForRender(float par1)
    {
        return this.materialType == Material.water ? super.getBrightnessForRender(par1) : 257;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return this.materialType == Material.water ? super.getBrightness(par1) : 1.0F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
//    	if(this.victim != null) {
//        	StaticMethods.out(this.victim.getEntityId());
//    	}
    	if(victim != lastTickVictim && lastTickVictim != null && !lastTickVictim.isDead) {
    		this.sendMagic(lastTickVictim);
    	}
    	if(victim != null) {
        	lastTickVictim = victim;
    	}
    	
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.materialType == Material.water)
        {
            this.particleRed = 0.2F;
            this.particleGreen = 0.3F;
            this.particleBlue = 1.0F;
        }
        else
        {
            this.particleRed = 1.0F;
            this.particleGreen = 16.0F / (float)(40 - this.bobTimer + 16);
            this.particleBlue = 4.0F / (float)(40 - this.bobTimer + 8);
        }

        this.motionY -= (double)this.particleGravity;

        if (this.bobTimer-- > 0)
        {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
            this.setParticleTextureIndex(113);
        }
        else
        {
            this.setParticleTextureIndex(112);
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.particleMaxAge-- <= 0)
        {
            this.setDead();
        }

        if (this.onGround)
        {
            if (this.materialType == Material.water)
            {
                this.setDead();
                if(this.victim != null) {
                	this.sendMagic(victim);
                }
                this.worldObj.spawnParticle("splash", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
            else
            {
                this.setParticleTextureIndex(114);
            }

            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }

        Material material = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial();

        if (material.isLiquid() || material.isSolid())
        {
            double d0 = (double)((float)(MathHelper.floor_double(this.posY) + 1) - BlockLiquid.getLiquidHeightPercent(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))));

            if (this.posY < d0)
            {
                this.setDead();
            }
        }
    }
    
    private void sendMagic(EntityLivingBase toKill) {
    	if(!toKill.isDead) {
        	ByteBuf data = buffer(4);
    		data.writeInt(Base.magicSuccedPacketID);
    		data.writeInt(toKill.getEntityId());
    		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
    		Lance.packetHandler.sendToServer(packet);
    	}
    }
}
