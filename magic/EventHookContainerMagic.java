package knight37x.magic;

import knight37x.magic.entity.EntityLightning;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class EventHookContainerMagic {

	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent event) {
		if(event.crafting.getItem() == Base.wand) {
			boolean flag = true;
			for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				if(event.craftMatrix.getStackInSlot(i) != null) {
					Item current = event.craftMatrix.getStackInSlot(i).getItem();
					if(current != Base.mana && current != Base.wand) {
						flag = false;
					}
				}
			}
			if(flag) {
				for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
					event.craftMatrix.setInventorySlotContents(i, null);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void gameTick(TickEvent event) {
//		World world = null;
//		if(event.type == TickEvent.Type.CLIENT) {
//			world = Minecraft.getMinecraft().theWorld;
//		} else if(event.type == TickEvent.Type.SERVER) {
//			world = MinecraftServer.getServer().getEntityWorld();
//		}
//		if(world != null) {
//			for(Object o : world.loadedEntityList) {
//				if(o instanceof EntityLivingBase) {
//					EntityLivingBase e = (EntityLivingBase) o;
//					NBTTagCompound tag = e.getEntityData();
//					if(tag.hasKey("magic")) {
//						int death = tag.getInteger("magic");
//						if(death == EntityLightning.getTimeToDeath()) {
//							tag.setInteger("magic", death - 1);
//							for(int i = 0; i < 91; i += 1) {
//								for(float j = 0.2F; j < 2.3F; j += 0.1F) {
//									world.spawnParticle("dripWater", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
//									world.spawnParticle("dripWater", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
//									world.spawnParticle("dripWater", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
//									world.spawnParticle("dripWater", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
//									
//									
////									MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
////									MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ + Math.sin(i), 0, 0, 0);
////									MagicParticles.spawnParticle("Drop", e.posX - Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
////									MagicParticles.spawnParticle("Drop", e.posX + Math.cos(i), e.posY + j, e.posZ - Math.sin(i), 0, 0, 0);
//								}
//							}
//						} else if(death <= 0) {
//							e.attackEntityFrom(DamageSource.magic, e.getHealth());
//							tag.removeTag("magic");
//						} else {
//							tag.setInteger("magic", death - 1);
//						}
//					}
//				}
//			}
//		}
	}
}
