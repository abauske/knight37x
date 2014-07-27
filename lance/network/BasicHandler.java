package knight37x.lance.network;

import java.util.List;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import knight37x.lance.Lance;
import knight37x.lance.block.ContainerBowConfig;
import knight37x.lance.item.ItemLance;
import knight37x.lance.item.ItemMayorBow;
import knight37x.lance.item.ItemSks;
import knight37x.lance.item.ItemSpear;
import knight37x.lance.render.RenderLance;
import knight37x.lance.render.RenderSpear;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;

public class BasicHandler extends NetworkBase {
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleClient(ByteBuf msg, int packetID) {
		if (packetID == 1) {
			Minecraft client = Minecraft.getMinecraft();
			World world = client.theWorld;
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if (stack != null) {
				item = stack.getItem();
			}
			if (item instanceof ItemSpear && player != null) {
				ItemSpear spear = (ItemSpear) item;
				RenderSpear.data.put(player.getEntityId(), false);
				spear.throwSpearOnOtherClients(player, world, msg.readFloat());
			}
		} else if(packetID == 2) {
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if (stack != null) {
				item = stack.getItem();
			}
			if (item instanceof ItemSks && player != null) {
				ItemSks sks = (ItemSks) item;
				sks.knockBack(player, msg.readDouble(), msg.readDouble(), msg.readDouble());
				if(msg.readBoolean()) {
					stack.damageItem(1, player);
					if(stack.getItemDamage() >= stack.getMaxDamage()) {
						player.setCurrentItemOrArmor(0, null);
					}
				}
			}
		} else if(packetID == 10) {
			RenderLance.data.put(msg.readInt(), msg.readBoolean());
		} else if(packetID == 11) {
			RenderSpear.data.put(msg.readInt(), msg.readBoolean());
		} else if(packetID == 12) {
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null) {
				NBTTagCompound tag = stack.stackTagCompound;
				if (tag != null) {
					tag.setBoolean("active", msg.readBoolean());
				}
			} else {
				boolean active = msg.readBoolean();
				for(int i = 0; i < player.inventory.mainInventory.length; i++) {
					stack = player.inventory.mainInventory[i];
					if(stack != null && stack.getItem() instanceof ItemMayorBow) {
						stack.stackTagCompound.setBoolean("active", active);
					}
				}
			}
		}
	}

	@Override
	public void handleServer(ByteBuf msg, int packetID) {
		MinecraftServer server = MinecraftServer.getServer();
		
		if(packetID == 0) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
			
			EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if(stack != null) {
				item = stack.getItem();
			}

			Entity entity = ItemLance.getRightEntity(server.getEntityWorld(), msg.readInt());
			if(item instanceof ItemLance && player != null && entity != null) {
				ItemLance lance = (ItemLance) item;
				
				if(lance.attack((EntityLivingBase) entity, player, msg.readFloat() + lance.handleEnchants(stack, (EntityLivingBase) entity, player)) && !player.capabilities.isCreativeMode && Lance.shouldLanceBreak) {
					if(Math.random() < 1.0f / (EnchantmentHelper.getEnchantmentLevel(34, stack) + 1)) {
						lance.damageLance(entity, player);
					}
				}
				
			}
		} else if(packetID == 1) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
			
			EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if (stack != null) {
				item = stack.getItem();
			}
			if (item instanceof ItemSpear && player != null) {
				ItemSpear spear = (ItemSpear) item;
				spear.throwSpear(player, server.getEntityWorld(), msg.readFloat());
			}
		} else if(packetID == 2) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
			
			EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if (stack != null) {
				item = stack.getItem();
			}
			if (item instanceof ItemSks && player != null) {
				ItemSks sks = (ItemSks) item;
				sks.knockBack(player, msg.readDouble(), msg.readDouble(), msg.readDouble());
				if(msg.readBoolean()) {
					stack.damageItem(1, player);
					if(stack.getItemDamage() >= stack.getMaxDamage()) {
						player.setCurrentItemOrArmor(0, null);
					}
				}
			}
		} else if(packetID == 3) {
			int id = msg.readInt();
			/**
			 * id:
			 * 1 -> spread
			 * 2 -> direction
			 */
			if(id == 0) {
				EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(msg.readInt());
				Container container = player.openContainer;
				if(container != null && container instanceof ContainerBowConfig) {
		        	NBTTagCompound tag = ((ContainerBowConfig) container).getCurrentStack().stackTagCompound;
					int spread = msg.readInt();
					tag.setInteger("spread", spread);
				}
			} else if(id ==1) {
				EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(msg.readInt());
				Container container = player.openContainer;
				if(container != null && container instanceof ContainerBowConfig) {
					NBTTagCompound tag = ((ContainerBowConfig) container).getCurrentStack().stackTagCompound;
					tag.setBoolean("horizontal", msg.readBoolean());
				}
			}
		} else if(packetID >= 10) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			Lance.packetHandler.sendToAll(clientmsg);
		}
	}

}
