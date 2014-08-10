package knight37x.magic.entity;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;

import java.util.List;

import scala.collection.mutable.HashMap;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import knight37x.magic.VictimWithDrops;
import knight37x.magic.items.ItemWand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
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
    
    public static HashMap<Block, Block> BLOCK_TRANSFORMATION = new HashMap<Block, Block>();
    static {
    	BLOCK_TRANSFORMATION.put(Blocks.stone, Blocks.stonebrick);
    	BLOCK_TRANSFORMATION.put(Blocks.grass, Blocks.mossy_cobblestone);
    	BLOCK_TRANSFORMATION.put(Blocks.dirt, Blocks.grass);
    	BLOCK_TRANSFORMATION.put(Blocks.cobblestone, Blocks.stone);
    	BLOCK_TRANSFORMATION.put(Blocks.planks, Blocks.log);
    	BLOCK_TRANSFORMATION.put(Blocks.sand, Blocks.sandstone);
    	BLOCK_TRANSFORMATION.put(Blocks.gravel, Blocks.sand);
    	BLOCK_TRANSFORMATION.put(Blocks.gold_ore, Blocks.emerald_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.iron_ore, Blocks.gold_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.coal_ore, Blocks.lapis_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.sponge, Blocks.diamond_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.glass, Blocks.glowstone);
    	BLOCK_TRANSFORMATION.put(Blocks.lapis_ore, Blocks.iron_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.lapis_block, Blocks.iron_block);
    	BLOCK_TRANSFORMATION.put(Blocks.sandstone, Blocks.stone);
    	BLOCK_TRANSFORMATION.put(Blocks.noteblock, Blocks.jukebox);
    	BLOCK_TRANSFORMATION.put(Blocks.detector_rail, Blocks.golden_rail);
    	BLOCK_TRANSFORMATION.put(Blocks.web, Blocks.wool);
    	BLOCK_TRANSFORMATION.put(Blocks.deadbush, Blocks.sapling);
    	BLOCK_TRANSFORMATION.put(Blocks.yellow_flower, Blocks.red_flower);
    	BLOCK_TRANSFORMATION.put(Blocks.red_flower, Blocks.yellow_flower);
    	BLOCK_TRANSFORMATION.put(Blocks.brown_mushroom, Blocks.red_mushroom);
    	BLOCK_TRANSFORMATION.put(Blocks.red_mushroom, Blocks.brown_mushroom);
    	BLOCK_TRANSFORMATION.put(Blocks.gold_block, Blocks.emerald_block);
    	BLOCK_TRANSFORMATION.put(Blocks.iron_block, Blocks.gold_block);
    	BLOCK_TRANSFORMATION.put(Blocks.double_stone_slab, Blocks.stonebrick);
    	BLOCK_TRANSFORMATION.put(Blocks.brick_block, Blocks.hardened_clay);
    	BLOCK_TRANSFORMATION.put(Blocks.mossy_cobblestone, Blocks.stone);
    	BLOCK_TRANSFORMATION.put(Blocks.obsidian, Blocks.iron_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.farmland, Blocks.dirt);
    	BLOCK_TRANSFORMATION.put(Blocks.rail, Blocks.detector_rail);
    	BLOCK_TRANSFORMATION.put(Blocks.snow_layer, Blocks.snow);
    	BLOCK_TRANSFORMATION.put(Blocks.ice, Blocks.packed_ice);
    	BLOCK_TRANSFORMATION.put(Blocks.snow, Blocks.ice);
    	BLOCK_TRANSFORMATION.put(Blocks.clay, Blocks.brick_block);
    	BLOCK_TRANSFORMATION.put(Blocks.fence, Blocks.nether_brick_fence);
    	BLOCK_TRANSFORMATION.put(Blocks.pumpkin, Blocks.lit_pumpkin);
    	BLOCK_TRANSFORMATION.put(Blocks.netherrack, Blocks.nether_brick);
    	BLOCK_TRANSFORMATION.put(Blocks.soul_sand, Blocks.netherrack);
    	BLOCK_TRANSFORMATION.put(Blocks.glowstone, Blocks.redstone_lamp);
    	BLOCK_TRANSFORMATION.put(Blocks.lit_pumpkin, Blocks.melon_block);
    	BLOCK_TRANSFORMATION.put(Blocks.stained_glass, Blocks.glowstone);
    	BLOCK_TRANSFORMATION.put(Blocks.monster_egg, Blocks.stonebrick);
    	BLOCK_TRANSFORMATION.put(Blocks.stonebrick, Blocks.brick_block);
    	BLOCK_TRANSFORMATION.put(Blocks.brown_mushroom_block, Blocks.red_mushroom_block);
    	BLOCK_TRANSFORMATION.put(Blocks.red_mushroom_block, Blocks.brown_mushroom_block);
    	BLOCK_TRANSFORMATION.put(Blocks.mycelium, Blocks.grass);
    	BLOCK_TRANSFORMATION.put(Blocks.end_stone, Blocks.dragon_egg);
    	BLOCK_TRANSFORMATION.put(Blocks.double_wooden_slab, Blocks.log);
    	BLOCK_TRANSFORMATION.put(Blocks.emerald_ore, Blocks.diamond_ore);
    	BLOCK_TRANSFORMATION.put(Blocks.emerald_block, Blocks.diamond_block);
    	BLOCK_TRANSFORMATION.put(Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate);
    	BLOCK_TRANSFORMATION.put(Blocks.heavy_weighted_pressure_plate, Blocks.light_weighted_pressure_plate);
    	BLOCK_TRANSFORMATION.put(Blocks.quartz_ore, Blocks.quartz_block);
    	BLOCK_TRANSFORMATION.put(Blocks.activator_rail, Blocks.detector_rail);
    	BLOCK_TRANSFORMATION.put(Blocks.carpet, Blocks.wool);
    	BLOCK_TRANSFORMATION.put(Blocks.hardened_clay, Blocks.stained_hardened_clay);
    	BLOCK_TRANSFORMATION.put(Blocks.coal_block, Blocks.lapis_block);
    }
	
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
			if(pos.typeOfHit == MovingObjectType.ENTITY && pos.entityHit instanceof EntityLivingBase) {
				((ItemWand) Base.wand).victims.add(new VictimWithDrops((EntityLivingBase) pos.entityHit, null));
				ByteBuf data = buffer(4);
				data.writeInt(Base.magicPacketID);
				data.writeInt(pos.entityHit.getEntityId());
				FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
				Lance.packetHandler.sendToAll(packet);
			} else if(pos.typeOfHit == MovingObjectType.BLOCK) {
				Block b = this.worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
				if(b != null && BLOCK_TRANSFORMATION.contains(b)) {
					Block newBlock = BLOCK_TRANSFORMATION.apply(b);
					TileEntity e = this.worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
					this.worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, newBlock, this.worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ), 2);
					this.worldObj.setTileEntity(pos.blockX, pos.blockY, pos.blockZ, e);
					this.worldObj.notifyBlockChange(pos.blockX, pos.blockY, pos.blockZ, newBlock);
				}
			}
		}
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
