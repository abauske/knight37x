package knight37x.magic;

import static io.netty.buffer.Unpooled.buffer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
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
import knight37x.magic.entity.EntityMagic;
import knight37x.magic.entity.MagicParticles;
import knight37x.magic.items.ItemWand;

public class NetworkMsg extends NetworkBase {

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClient(ByteBuf msg, int packetID) {
		Minecraft mc = Minecraft.getMinecraft();
		World world = mc.theWorld;
		
		if(packetID == Base.lightningPacketID) {
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			Entity e = new EntityLightning(world, player);
			world.spawnEntityInWorld(e);
			
			NBTTagCompound tag = player.getCurrentEquippedItem().stackTagCompound;
			if(tag.getInteger("storage") > 0 && !player.capabilities.isCreativeMode) {
				tag.setInteger("storage", tag.getInteger("storage") - 1);
			}
		} else if(packetID == Base.magicPacketID) {
			EntityLivingBase e = (EntityLivingBase) world.getEntityByID(msg.readInt());
			((ItemWand) Base.wand).victims.add(e);
			if(e == mc.thePlayer) {
				for(int i = 0; i < 91; i += 1) {
					for(float j = -1.0F; j < 1.0F; j += 0.1F) {
						if(j > 1.0F - 2 * 0.1F) {
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0)).victim = e;
						} else {
							MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
						}
					}
				}
			} else {
				for(int i = 0; i < 91; i += 1) {
					for(float j = 0.2F; j < e.getEyeHeight() + 0.5F; j += 0.1F) {
						if(j > e.getEyeHeight() + 0.5F - 2 * 0.1F) {
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0)).victim = e;
							((EntityDropParticle) MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0)).victim = e;
						} else {
							MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
							MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
						}
					}
				}
			}
		} else if(packetID == Base.magicSuccedPacketID) {
			EntityLivingBase e = (EntityLivingBase) world.getEntityByID(msg.readInt());
			e.attackEntityFrom(DamageSource.magic, e.getHealth() + 1);
		}
	}

	@Override
	public void handleServer(ByteBuf msg, int packetID) {
		MinecraftServer server = MinecraftServer.getServer();
		World world = server.getEntityWorld();
		
		if(packetID == Base.lightningPacketID) {
			EntityPlayer player = (EntityPlayer) world.getEntityByID(msg.readInt());
			
			if(msg.readBoolean()) {
				FMLProxyPacket packet = new FMLProxyPacket(msg, "lance");
				Lance.packetHandler.sendToAll(packet);
				
				Entity entity = new EntityLightning(world, player);
				world.spawnEntityInWorld(entity);
			} else {
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
			}
		} else if(packetID == Base.magicSuccedPacketID) {
			int id = msg.readInt();
			if(!world.getEntityByID(id).isDead) {
				FMLProxyPacket packet = new FMLProxyPacket(msg, "lance");
				Lance.packetHandler.sendToAll(packet);
				
				EntityLivingBase e = (EntityLivingBase) world.getEntityByID(id);
				e.attackEntityFrom(DamageSource.magic, e.getHealth() + 1);
			}
		}
		
	}

}
