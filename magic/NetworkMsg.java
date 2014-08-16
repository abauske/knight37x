package knight37x.magic;

import static io.netty.buffer.Unpooled.buffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.lance.item.ItemLance;
import knight37x.lance.network.NetworkBase;
import knight37x.magic.entity.EntityDropParticle;
import knight37x.magic.entity.EntityLightning;
import knight37x.magic.entity.EntityDrop;
import knight37x.magic.entity.MagicParticles;
import knight37x.magic.items.ItemTrainingLance;
import knight37x.magic.items.ItemWand;

public class NetworkMsg extends NetworkBase {

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClient(ByteBuf msg, int packetID) {
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.theWorld;
		
		if(packetID == Base.spawnLightningPacketID) {
			// Spawn Lightning for example when player uses Wand
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			Entity e = new EntityLightning(world, player);
			world.spawnEntityInWorld(e);
			
			NBTTagCompound tag = player.getCurrentEquippedItem().stackTagCompound;
			if(tag.getInteger("storage") > 0 && !player.capabilities.isCreativeMode) {
				tag.setInteger("storage", tag.getInteger("storage") - 1);
			}
		} else if(packetID == Base.spawnDropsPacketID) {
			// Spawn Drop Entity when lightning hits an entity
			EntityLivingBase e = (EntityLivingBase) world.getEntityByID(msg.readInt());
			world.spawnEntityInWorld(new EntityDrop(world, e));
		} else if(packetID == Base.mirrorUsePacketID) {
			// Removes Drop Entity when player uses Mirror
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			for(Object o : world.loadedEntityList) {
				if(o instanceof EntityDrop) {
					EntityDrop e = (EntityDrop) o;
					if(e.victim == player) {
						e.victim = null;
						e.setDead();
						
						Entity entity = new EntityLightning(world, player);
						world.spawnEntityInWorld(entity);
						break;
					}
				}
			}
		} else if(packetID == Base.trainingLancePacketID) {
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			Entity entity = ItemLance.getRightEntity(world, msg.readInt());
			entity.ridingEntity = null;
		} else if(packetID == Base.trollArmStatePacketID) {
			// Makes Troll use the hammer naturally
			Entity entity = world.getEntityByID(msg.readInt());
			float armState = msg.readFloat();
			if(entity != null) {
				entity.getEntityData().setFloat("armState", armState);
			}
		}
	}

	@Override
	public void handleServer(ByteBuf msg, int packetID) {
		MinecraftServer server = MinecraftServer.getServer();
		World world = server.getEntityWorld();
		
		if(packetID == Base.spawnLightningPacketID) {
			// Spawn Lightning for example when player uses Wand
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null && stack.getItem() == Base.wand && stack.stackTagCompound != null) {
				NBTTagCompound tag = stack.stackTagCompound;
				int st = tag.getInteger("storage");
				if(st > 0 || player.capabilities.isCreativeMode) {
					FMLProxyPacket packet = new FMLProxyPacket(msg, "lance");
					Lance.packetHandler.sendToAll(packet);
					
					Entity entity = new EntityLightning(world, player);
					world.spawnEntityInWorld(entity);
					if(!player.capabilities.isCreativeMode) {
						tag.setInteger("storage", st - 1);
					}
				}
			}
		} else if(packetID == Base.mirrorUsePacketID) {
			// Removes Drop Entity when player uses Mirror
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
			
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			for(Object o : world.loadedEntityList) {
				if(o instanceof EntityDrop) {
					EntityDrop e = (EntityDrop) o;
					if(e.victim == player) {
						e.victim = null;
						e.setDead();
						
						Entity entity = new EntityLightning(world, player);
						world.spawnEntityInWorld(entity);
						break;
					}
				}
			}
		} else if(packetID == Base.trainingLancePacketID) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
			
			int playerID = msg.readInt();
			EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(playerID);
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if(stack != null) {
				item = stack.getItem();
			}
			
			int entityID = msg.readInt();
			Entity entity = ItemLance.getRightEntity(server.getEntityWorld(), entityID);
			if(item instanceof ItemLance && player != null && entity != null) {
				ItemLance lance = (ItemLance) item;
				
				boolean secondHit = false;
				for(Object[] o : ((ItemTrainingLance) Base.training_lance).turnament) {
					if((o[0] == player || o[1] == player) && (o[0] == entity || o[1] == entity)) {
						secondHit = true;
						break;
					}
				}
				if(!secondHit && lance.attack((EntityLivingBase) entity, player, 0.1F)) {
					entity.ridingEntity = null;
					((ItemTrainingLance) Base.training_lance).turnament.add(new Object[] {player, (EntityPlayer) entity, 100});
					if(!player.capabilities.isCreativeMode && Lance.shouldLanceBreak) {
						if(Math.random() < 1.0f / (EnchantmentHelper.getEnchantmentLevel(34, stack) + 1)) {
							lance.damageLance(entity, player);
						}
					}
				}
			}
		}
		
	}

}
