package knight37x.lance;

import static io.netty.buffer.Unpooled.buffer;

import com.google.common.collect.Multimap;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemSpear extends Item {
	
	private float thrust = 0;
	private float thrustValue = 0;
	private boolean lastTickMouseButton0 = false;
	
	private int throwDelay = 0;
	
	public float thrustValue() {
//		System.out.println(this.thrust);
		return this.thrust;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.none;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
//        player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		return false;
	}
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 10000;
    }

//	@Override
//	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
//		return super.onItemRightClick(itemstack, world, player);
////		return itemstack;
//	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5) {
		if(entity != null && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			Item spear = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
			if(StaticMethods.isRunningOnClient()) {
				boolean isButton0Down = Minecraft.getMinecraft().gameSettings.keyBindUseItem.getIsKeyPressed();
				boolean flag = true;
				if(isButton0Down) {
					MovingObjectPosition mov = Minecraft.getMinecraft().objectMouseOver;
					if(mov.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
						Block block = world.getBlock(mov.blockX, mov.blockY, mov.blockZ);
						if(block.onBlockActivated(world, mov.blockX, mov.blockY, mov.blockZ, player, 0, 0, 0, 0)) {
							flag = false;
						}
					}
				}
				
				if(spear != null && spear == this && flag) {
					if(isButton0Down && this.thrust < 2.5F) {
						this.thrust += 0.1F;
					} else if(!isButton0Down && this.lastTickMouseButton0) {
						this.thrustValue = Math.abs(this.thrust);
						this.thrust = 0;
						int spearId = this.tryThrowSpear(player, world);
						if(spearId != 0) {
							this.send(thrustValue, (EntityClientPlayerMP) player, spearId);
						}
					}
					if(this.thrust > 2.5F) {
						this.thrust = 2.5F;
					}
					this.lastTickMouseButton0 = isButton0Down;
					
				} else {
					this.thrust = 0;
					this.lastTickMouseButton0 = false;
				}
			}
		}
		if(this.throwDelay > 0) {
			this.throwDelay--;
		}
	}
	
	public int throwSpear(EntityPlayer player, World world, float value) {
        if (!player.capabilities.isCreativeMode)
        {
            --player.getCurrentEquippedItem().stackSize;
            if(player.getCurrentEquippedItem().stackSize <= 0) {
            	player.setCurrentItemOrArmor(0, null);
            }
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        this.thrustValue = value;
        EntitySpear entity = new EntitySpear(world, player, this.thrustValue);
        if(player.capabilities.isCreativeMode) {
        	entity.canBePickedUp = 2;
        } else {
        	entity.canBePickedUp = 1;
        }
        world.spawnEntityInWorld(entity);
        return entity.getEntityId();
	}
	
	public void throwSpear(EntityPlayer player, World world, float value, int spearId) {
        if (!player.capabilities.isCreativeMode)
        {
            --player.getCurrentEquippedItem().stackSize;
            if(player.getCurrentEquippedItem().stackSize <= 0) {
            	player.setCurrentItemOrArmor(0, null);
            }
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        this.thrustValue = value;
        EntitySpear entity = new EntitySpear(world, player, this.thrustValue);
        if(player.capabilities.isCreativeMode) {
        	entity.canBePickedUp = 2;
        } else {
        	entity.canBePickedUp = 1;
        }
        entity.setEntityId(spearId);
        world.spawnEntityInWorld(entity);
	}
	
	public int tryThrowSpear(EntityPlayer player, World world) {
		
		if(this.throwDelay <= 0) {
			this.throwDelay = 20;
			return this.throwSpear(player, world, this.thrustValue);
		}
		return 0;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void send(float thrust, EntityClientPlayerMP player, int spearId)  {
		ByteBuf data = buffer(4);
		data.writeInt(0);
		data.writeInt(player.getEntityId());
		data.writeFloat(thrust);
		data.writeInt(spearId);
		C17PacketCustomPayload packet = new C17PacketCustomPayload("spear", data);
		player.sendQueue.addToSendQueue(packet);
	}
}
