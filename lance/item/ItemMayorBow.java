package knight37x.lance.item;

import static io.netty.buffer.Unpooled.buffer;

import java.util.List;

import org.lwjgl.input.Mouse;

import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import knight37x.lance.entity.EntityMDArrow;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ItemMayorBow extends ItemBow {

	public final String[] spreads = new String[] {"no Spread", "small", "medium", "wide", "very wide"};
    @SideOnly(Side.CLIENT)
    private IIcon iconPulled;
	
    @Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		super.onCreated(itemstack, world, player);
		itemstack.stackTagCompound = new NBTTagCompound();
		NBTTagCompound tag = itemstack.stackTagCompound;
		tag.setBoolean("horizontal", true);
		tag.setInteger("spread", 1);
		tag.setBoolean("active", false);
	}
    
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World par2World, EntityPlayer player, int par4) {
		NBTTagCompound tag = itemstack.stackTagCompound;
		tag.setBoolean("active", false);
		if(StaticMethods.isRunningOnServer()) {
			this.sendActive(player, false);
		}
        int j = this.getMaxItemUseDuration(itemstack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            return;
        }
        j = event.charge;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityMDArrow[] arrow = new EntityMDArrow[3];
            
			double changeX = 0.0D;
			double changeY = 0.0D;
			double changeZ = 0.0D;

			if (tag.getInteger("spread") == 0) {
				arrow = new EntityMDArrow[2];
			} else {
				Vec3 vec = player.getLookVec();
				vec.rotateAroundX(10.0F);
				int q = j;
				if (q > 25) {
					q = 25;
				}

				if (tag.getBoolean("horizontal")) {
					changeX = vec.zCoord * q / 25
							* (Math.sqrt(tag.getInteger("spread")) * 2 - 1.2);
					changeZ = vec.xCoord * q / 25
							* (Math.sqrt(tag.getInteger("spread")) * 2 - 1.2);
				} else {
					changeY = tag.getInteger("spread") * 0.3F;
				}
			}
        	
        	
            for(int i = 0; i < arrow.length; i++) {
//            	StaticMethods.out(entityarrow.length);
            	arrow[i] = new EntityMDArrow(par2World, player, f * 2.0F);

                if (f == 1.0F)
                {
                    arrow[i].setIsCritical(true);
                }

                int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

                if (k > 0)
                {
                    arrow[i].setDamage(arrow[i].getDamage() + (double)k * 0.5D + 0.5D);
                }

                int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

                if (l > 0)
                {
                    arrow[i].setKnockbackStrength(l);
                }

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
                {
                    arrow[i].setFire(100);
                }

                itemstack.damageItem(1, player);
                par2World.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (flag) {
                    arrow[i].canBePickedUp = 2;
                } else {
                    player.inventory.consumeInventoryItem(Items.arrow);
                }

                if (!par2World.isRemote)
                {
					if (i == 1) {
						arrow[i].motionX += changeX;
						arrow[i].motionY += changeY;
						arrow[i].motionZ += changeZ;
					} else if (i == 2) {
						arrow[i].motionX -= changeX;
						arrow[i].motionY -= changeY;
						arrow[i].motionZ -= changeZ;
					}
                    
                    
                    par2World.spawnEntityInWorld(arrow[i]);
//                    par2World.spawnEntityInWorld(arrow2);
//                    par2World.spawnEntityInWorld(arrow3);
                }
            }
            
        }
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		super.onUpdate(itemstack, world, entity, par4, par5);
		
		
		NBTTagCompound tag = itemstack.stackTagCompound;
		if(tag == null) {
			tag = itemstack.stackTagCompound = new NBTTagCompound();
		}
		if(!tag.hasKey("spread")) {
			tag.setInteger("spread", 1);
		}
		if(!tag.hasKey("horizontal")) {
			tag.setBoolean("horizontal", true);
		}
		if(!tag.hasKey("active")) {
			tag.setBoolean("active", false);
		}
		
		if(tag.getBoolean("active") && StaticMethods.isRunningOnClient() && !Mouse.isButtonDown(1) && entity instanceof EntityPlayer) {
			this.sendActive((EntityPlayer) entity, false);
		}
	}
	
	private void shot(ItemStack itemstack, EntityPlayer player, World world, int par4) {
		int j = this.getMaxItemUseDuration(itemstack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float)j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double)f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityMDArrow(world, player, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
            {
                entityarrow.setFire(100);
            }

            itemstack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer player) {
		itemstack.stackTagCompound.setBoolean("active", true);
		if(StaticMethods.isRunningOnServer()) {
			this.sendActive(player, true);
		}
		return super.onItemRightClick(itemstack, par2World, player);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = reg.registerIcon("lance:mayorBow_standby");
        this.iconPulled = reg.registerIcon("lance:mayorBow_pulled");
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		if (itemstack.stackTagCompound != null) {
			list.add("Spread: " + this.spreads[itemstack.stackTagCompound.getInteger("spread")]);
			if(itemstack.stackTagCompound.getBoolean("horizontal")) {
				list.add("Direction: horizontal");
			} else {
				list.add("Direction: vertical");
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
    private void sendActive(EntityPlayer player, boolean active) {
		ByteBuf data = buffer(4);
		data.writeInt(12);
		data.writeInt(player.getEntityId());
		data.writeBoolean(active);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToAll(packet);
	}
	
	@Override
	public IIcon getIconFromDamage(int par1) {
		return this.itemIcon;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
//		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
//		return super.getIcon(stack, pass);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		if(stack.stackTagCompound != null) {
			return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
		} else {
			return this.itemIcon;
		}
//		return super.getIconIndex(stack);
	}
}






//package knight37x.lance.item;
//
//import static io.netty.buffer.Unpooled.buffer;
//import java.util.List;
//
//import org.lwjgl.input.Mouse;
//
//import io.netty.buffer.ByteBuf;
//import knight37x.lance.Lance;
//import knight37x.lance.StaticMethods;
//import knight37x.lance.block.ContainerBowConfig;
//import knight37x.lance.entity.EntityMDArrow;
//import io.netty.buffer.ByteBuf;
//import knight37x.lance.Lance;
//import knight37x.lance.StaticMethods;
//import cpw.mods.fml.common.eventhandler.EventPriority;
//import cpw.mods.fml.common.eventhandler.SubscribeEvent;
//import cpw.mods.fml.common.network.internal.FMLProxyPacket;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import net.minecraft.client.entity.EntityClientPlayerMP;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.enchantment.Enchantment;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.init.Items;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemBow;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumChatFormatting;
//import net.minecraft.util.IIcon;
//import net.minecraft.util.Vec3;
//import net.minecraft.world.World;
//import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.entity.player.ArrowLooseEvent;
//
//public class ItemMayorBow extends ItemBow {
//	
//	public final String[] spreads = new String[] {"no Spread", "small", "medium", "wide", "very wide"};
//	
//    @SideOnly(Side.CLIENT)
//    private IIcon iconPulled;
//	
//	@Override
//	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
//		super.onCreated(itemstack, world, player);
//		itemstack.stackTagCompound = new NBTTagCompound();
//		NBTTagCompound tag = itemstack.stackTagCompound;
//		tag.setBoolean("horizontal", true);
//		tag.setInteger("spread", 1);
//		tag.setBoolean("active", false);
//	}
//	
//	@Override
//	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int par4) {
//		NBTTagCompound tag = itemstack.stackTagCompound;
//		tag.setBoolean("active", false);
//		
//		if(StaticMethods.isRunningOnServer()) {
//			this.sendActive(player, false);
//		}
//        int j = this.getMaxItemUseDuration(itemstack) - par4;
//
//        ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, j);
//        MinecraftForge.EVENT_BUS.post(event);
//        if (event.isCanceled()) {
//            return;
//        }
//        j = event.charge;
//
//        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;
//
//        if (flag || player.inventory.hasItem(Items.arrow))
//        {
//            float f = (float)j / 20.0F;
//            f = (f * f + f * 2.0F) / 3.0F;
//
//            if ((double)f < 0.1D)
//            {
//                return;
//            }
//
//            if (f > 1.0F)
//            {
//                f = 1.0F;
//            }
//            
//            EntityMDArrow[] arrow = new EntityMDArrow[3];
//            
//            double changeX = 0.0D;
//            double changeY = 0.0D;
//            double changeZ = 0.0D;
//            
//            if(tag.getInteger("spread") == 0) {
//            	arrow = new EntityMDArrow[2];
//            } else {
//                Vec3 vec = player.getLookVec();
//                vec.rotateAroundX(10.0F);
//                int q = j;
//                if(q > 25) {
//                	q = 25;
//                }
//                
//                if(tag.getBoolean("horizontal")) {
//                    changeX = vec.zCoord * q / 25 * (Math.sqrt(tag.getInteger("spread")) * 2 - 1.2);
//                    changeZ = vec.xCoord * q / 25 * (Math.sqrt(tag.getInteger("spread")) * 2 - 1.2);
//                } else {
//                	changeY = tag.getInteger("spread") * 0.3F;
//                }
//            }
//        	
//        	
//            for(int i = 0; i < arrow.length; i++) {
////            	StaticMethods.out(entityarrow.length);
//            	arrow[i] = new EntityMDArrow(world, player, f * 2.0F);
//
//                if (f == 1.0F)
//                {
//                    arrow[i].setIsCritical(true);
//                }
//
//                int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
//
//                if (k > 0)
//                {
//                    arrow[i].setDamage(arrow[i].getDamage() + (double)k * 0.5D + 0.5D);
//                }
//
//                int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
//
//                if (l > 0)
//                {
//                    arrow[i].setKnockbackStrength(l);
//                }
//
//                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
//                {
//                    arrow[i].setFire(100);
//                }
//
//                itemstack.damageItem(1, player);
//                world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//                
//                boolean hasArrow = true;
//                world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//
//                if (flag)
//                {
//                    arrow[i].canBePickedUp = 2;
//                }
//                else
//                {
//                    hasArrow = player.inventory.consumeInventoryItem(Items.arrow);
//                }
//
//                if (!world.isRemote && hasArrow)
//                    player.inventory.consumeInventoryItem(Items.arrow);
//                }
//
//                if (!world.isRemote)
//                {
//					if (i == 1) {
//						arrow[i].motionX += changeX;
//						arrow[i].motionY += changeY;
//						arrow[i].motionZ += changeZ;
//					} else if (i == 2) {
//						arrow[i].motionX -= changeX;
//						arrow[i].motionY -= changeY;
//						arrow[i].motionZ -= changeZ;
//					}
//                    
//                    
//                    world.spawnEntityInWorld(arrow[i]);
////                    par2World.spawnEntityInWorld(arrow2);
////                    par2World.spawnEntityInWorld(arrow3);
//                }
//            }
//            
//        }
//	}
//	
//	@Override
//	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
//		super.onUpdate(itemstack, world, entity, par4, par5);
//		
//		
//		NBTTagCompound tag = itemstack.stackTagCompound;
//		if(tag == null) {
//			tag = itemstack.stackTagCompound = new NBTTagCompound();
//		}
//		if(!tag.hasKey("spread")) {
//			tag.setInteger("spread", 1);
//		}
//		if(!tag.hasKey("horizontal")) {
//			tag.setBoolean("horizontal", true);
//		}
//		if(!tag.hasKey("active")) {
//			tag.setBoolean("active", false);
//		}
//		
//		if(tag.getBoolean("active") && StaticMethods.isRunningOnClient() && !Mouse.isButtonDown(1)) {
//			this.sendActive(entity, false);
//		}
//	}
//	
//	private void shot(ItemStack itemstack, EntityPlayer player, World world, int par4) {
//		int j = this.getMaxItemUseDuration(itemstack) - par4;
//
//        ArrowLooseEvent event = new ArrowLooseEvent(player, itemstack, j);
//        MinecraftForge.EVENT_BUS.post(event);
//        if (event.isCanceled())
//        {
//            return;
//        }
//        j = event.charge;
//
//        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;
//
//        if (flag || player.inventory.hasItem(Items.arrow))
//        {
//            float f = (float)j / 20.0F;
//            f = (f * f + f * 2.0F) / 3.0F;
//
//            if ((double)f < 0.1D)
//            {
//                return;
//            }
//
//            if (f > 1.0F)
//            {
//                f = 1.0F;
//            }
//
//            EntityArrow entityarrow = new EntityMDArrow(world, player, f * 2.0F);
//
//            if (f == 1.0F)
//            {
//                entityarrow.setIsCritical(true);
//            }
//
//            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
//
//            if (k > 0)
//            {
//                entityarrow.setDamage(entityarrow.getDamage() + (double)k * 0.5D + 0.5D);
//            }
//
//            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
//
//            if (l > 0)
//            {
//                entityarrow.setKnockbackStrength(l);
//            }
//
//            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
//            {
//                entityarrow.setFire(100);
//            }
//
//            itemstack.damageItem(1, player);
//            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//
//            if (flag)
//            {
//                entityarrow.canBePickedUp = 2;
//            }
//            else
//            {
//                player.inventory.consumeInventoryItem(Items.arrow);
//            }
//
//            if (!world.isRemote)
//            {
//                world.spawnEntityInWorld(entityarrow);
//            }
//        }
//	}
//
//	@Override
//	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer player) {
//		itemstack.stackTagCompound.setBoolean("active", true);
//		if(StaticMethods.isRunningOnServer()) {
//			this.sendActive(player, true);
//		}
//		return super.onItemRightClick(itemstack, par2World, player);
//	}
//	
//	@Override
//	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
//		if (itemstack.stackTagCompound != null) {
//			list.add("Spread: " + this.spreads[itemstack.stackTagCompound.getInteger("spread")]);
//			if(itemstack.stackTagCompound.getBoolean("horizontal")) {
//				list.add("Direction: horizontal");
//			} else {
//				list.add("Direction: vertical");
//			}
//		}
//		
//	
//	@Override
//	@SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister reg)
//    {
//        this.itemIcon = reg.registerIcon("lance:mayorBow_standby");
//        this.iconPulled = reg.registerIcon("lance:mayorBow_pulled");
//    }
//
//	@Override
//	public IIcon getIconFromDamage(int par1) {
//		return this.itemIcon;
//	}
//	
//	@Override
//	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
//		return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
////		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
//	}
//
//	@Override
//	public IIcon getIcon(ItemStack stack, int pass) {
//		return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
////		return super.getIcon(stack, pass);
//	}
//
//	@Override
//	public IIcon getIconIndex(ItemStack stack) {
//		if(stack.stackTagCompound != null) {
//			return stack.stackTagCompound.getBoolean("active") ? this.iconPulled : this.itemIcon;
//		} else {
//			return this.itemIcon;
//		}
////		return super.getIconIndex(stack);
//	}
//
//	@Override
//	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
////		StaticMethods.out(par1);
////		StaticMethods.out(par2);
//		return super.getIconFromDamageForRenderPass(par1, par2);
//	}
//	
//	public int getDefaultArrowDelay() {
//		return 8;
//	}
//	
//	@SubscribeEvent(priority = EventPriority.NORMAL)
//    private void sendActive(Entity player, boolean active) {
//		return this.active ? this.iconPulled : this.itemIcon;
//	}
//	
////	@SideOnly(Side.SERVER)
//	@SubscribeEvent(priority = EventPriority.NORMAL)
//    private void sendActive(EntityPlayer player, boolean active) {
//		ByteBuf data = buffer(4);
//		data.writeInt(12);
//		data.writeInt(player.getEntityId());
//		data.writeBoolean(active);
//		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
//		Lance.packetHandler.sendToAll(packet);
//	}
//}
