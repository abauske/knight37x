package knight37x.magic.items;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.magic.Base;
import knight37x.magic.VictimWithDrops;
import knight37x.magic.entity.EntityDropParticle;
import knight37x.magic.entity.EntityLightning;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.network.ForgeNetworkHandler;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ItemWand extends Item {
	
	public List<VictimWithDrops> victims = new ArrayList();
	public IIcon loading;
	
	public final static int COOLDOWN = 100;
	
	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		super.onCreated(itemstack, world, player);
		this.setNewNBT(itemstack);
	}
	
	public void setNewNBT(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			NBTTagCompound tag = stack.stackTagCompound;
			tag.setInteger("storage", 0);
			tag.setInteger("cooldown", 0);
		}
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.loading = reg.registerIcon("magic:wand_loading");
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		super.onUpdate(itemstack, world, entity, par4, par5);
		
		NBTTagCompound tag = itemstack.stackTagCompound;
		if(tag == null) {
			this.setNewNBT(itemstack);
		}
		tag = itemstack.stackTagCompound;
		
		if(tag.getInteger("cooldown") > 0) {
			tag.setInteger("cooldown", tag.getInteger("cooldown") - 1);
		}
		
//		Iterator<EntityLivingBase> it = victims.iterator();
//		while(it.hasNext()) {
//			EntityLivingBase e = it.next();
//			if(e == null || e.isDead) {
//				it.remove();
//			} else {
//				e.setPosition(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ);
//			}
//		}
//		ArrayList<Integer> rm = new ArrayList();
//		for(EntityLivingBase e : victims) {
//			if(e == null || e.isDead) {
//				rm.add(victims.indexOf(e));
//			} else {
//				e.setPosition(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ);
//			}
//		}
//		for(int i : rm) {
//			victims.remove(i);
//		}
		Iterator<VictimWithDrops> it = victims.iterator();
		while(it.hasNext()) {
			VictimWithDrops map = it.next();
			EntityLivingBase e = map.getVictim();
			if(e == null || e.isDead) {
				if(map.getDrops() != null) {
					for(EntityFX i : map.getDrops()) {
						if(i != null && i instanceof EntityDropParticle) {
							((EntityDropParticle) i).victim = null;
						}
						i.setDead();
					}
				}
				it.remove();
			} else {
				e.setPosition(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ);
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (itemstack.stackTagCompound != null) {
			list.add("Stored Mana: ");
			list.add(EnumChatFormatting.GREEN.toString() + itemstack.stackTagCompound.getInteger("storage"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(StaticMethods.isRunningOnClient() && stack.stackTagCompound != null) {
			NBTTagCompound tag = stack.stackTagCompound;
			if((tag.getInteger("cooldown") <= 0 && tag.getInteger("storage") > 0) || player.capabilities.isCreativeMode) {
				this.sendSpawnLightning((EntityClientPlayerMP) player, false);
				if(!player.capabilities.isCreativeMode) {
					tag.setInteger("cooldown", COOLDOWN);
					tag.setInteger("storage", tag.getInteger("storage") - 1);
				}
			}
		}
		return stack;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    public void sendSpawnLightning(EntityClientPlayerMP player, boolean forced) {
		ByteBuf data = buffer(4);
		data.writeInt(Base.lightningPacketID);
		data.writeInt(player.getEntityId());
		data.writeBoolean(forced);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		return tag != null && tag.hasKey("cooldown") ? tag.getInteger("cooldown") <= 0 ? this.itemIcon : this.loading : this.itemIcon;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return this.getIconIndex(stack);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return this.getIconIndex(stack);
	}
}
