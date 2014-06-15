package knight37x.lance.item;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class ItemMayorBow extends ItemBow {

    @SideOnly(Side.CLIENT)
    private IIcon iconPulled;
    private boolean active = false;
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World par2World, EntityPlayer player, int par4) {
		this.active = false;
		if(StaticMethods.isRunningOnServer()) {
			this.sendActive(player, false);
		}
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

            EntityArrow[] arrow = new EntityArrow[3];


//        	StaticMethods.out(Math.abs(player.rotationYawHead));
//            double a = Math.abs(player.rotationYawHead);
//            double b = 0.0001;
            double changeX = 0.0D;
            double changeY = 0.0D;
            double changeZ = 0.0D;
//            
//        	if(a > 90 && a < 270) {
//        		changeZ = b * a * (a - 180);
//        	} else if(a < 90 || a > 270) {
//        		changeZ = b * (a - 180) * (a - 360);
//        	}
//        	if(a > 0 && a < 180) {
//        		changeX = b * (a - 90) * (a - 270);
//        	} else if(a > 180 || a < 360) {
//        		changeX = -b * (a - 90) * (a - 270);
//        	}
//        	
            Vec3 vec = player.getLookVec();
            vec.rotateAroundX(10.0F);
            int q = j;
            if(q > 25) {
            	q = 25;
            }
            changeX = vec.zCoord * q / 25;
//            changeY = vec.yCoord * par4 / 72000;
            changeZ = vec.xCoord * q / 25;

        	StaticMethods.out(par4);
//        	StaticMethods.out(vec.zCoord);
        	
        	
            for(int i = 0; i < arrow.length; i++) {
//            	StaticMethods.out(entityarrow.length);
            	arrow[i] = new EntityArrow(par2World, player, f * 2.0F);

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

                if (flag)
                {
                    arrow[i].canBePickedUp = 2;
                }
                else
                {
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		this.active = true;
		if(StaticMethods.isRunningOnServer()) {
			this.sendActive(player, true);
		}
		return super.onItemRightClick(par1ItemStack, par2World, player);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        this.itemIcon = reg.registerIcon("lance:mayorBow_standby");
        this.iconPulled = reg.registerIcon("lance:mayorBow_pulled");
    }

	@Override
	public IIcon getIconFromDamage(int par1) {
		return this.active ? this.iconPulled : this.itemIcon;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
//	@SideOnly(Side.SERVER)
	@SubscribeEvent(priority = EventPriority.NORMAL)
    private void sendActive(EntityPlayer player, boolean active) {
		ByteBuf data = buffer(4);
		data.writeInt(12);
		data.writeInt(player.getEntityId());
		data.writeBoolean(active);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToAll(packet);
	}
}
