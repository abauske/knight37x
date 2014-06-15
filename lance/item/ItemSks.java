package knight37x.lance.item;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;

import javax.swing.Icon;

import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSks extends ItemSword {

	private IIcon icon2;
	private String icon2String;
	private int swingCount = 0;
	
	private boolean isBoosted = false;
	
	public ItemSks(ToolMaterial toolMaterial, String iconString2) {
		super(toolMaterial);
		this.icon2String = iconString2;
		this.setCreativeTab(Lance.tabLance);
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
		super.onUpdate(itemstack, world, entity, par4, par5);
		if(entity.isCollidedVertically) {
			this.isBoosted = false;
		}
	}

	public void knockBack(EntityLivingBase player, double x, double y, double z) {
		if((int) player.posX == (int) x && (int) player.posZ == (int) z) {
            player.motionY += 0.2D;
		} else {
			double d0 = player.posX - x;
			double d1;
			for (d1 = player.posZ - z; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
				d0 = (Math.random() - Math.random()) * 0.01D;
			}
			player.knockBack(player, 0.0F, -d0, -d1);
		}
		this.isBoosted = true;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase entity, EntityLivingBase p) {
		return super.hitEntity(par1ItemStack, entity, p);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack itemstack) {
		if(StaticMethods.isRunningOnClient() && Mouse.isButtonDown(0)) {
			this.swingCount++;
			if(this.swingCount > 4) {
				this.swingCount = 0;
			}
			MovingObjectPosition o = Minecraft.getMinecraft().objectMouseOver;
			switch(o.typeOfHit) {
				case BLOCK: {
					if(Lance.sksOnBlocks) {
						this.send((EntityClientPlayerMP) entityLiving, o.blockX, o.blockY, o.blockZ, this.swingCount == 4);
					}
					break;
				}
				case ENTITY: {
					this.send((EntityClientPlayerMP) entityLiving, o.entityHit.posX, o.entityHit.posY, o.entityHit.posZ, false);
				}
				default: {
					break;
				}
			}
		}
		return super.onEntitySwing(entityLiving, itemstack);
	}

	public IIcon getIcon2() {
		return this.icon2;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.icon2 = reg.registerIcon(this.icon2String);
		super.registerIcons(reg);
	}

	@Override
	public IIcon getIconFromDamageForRenderPass(int par1, int pass) {
		return pass == 0 ? this.getIconFromDamage(par1) : this.getIcon2();
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void send(EntityClientPlayerMP player, double x, double y, double z, boolean damage)  {
		ByteBuf data = buffer(4);
		data.writeInt(2);
		data.writeInt(player.getEntityId());
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeBoolean(damage);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
	}
	
	public boolean isBoosted() {
		return isBoosted;
	}
}
