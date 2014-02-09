package knight37x.lance;

import static io.netty.buffer.Unpooled.buffer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.world.World;

public class ItemSpear extends ItemSword {
	
	private float thrust = 0;
	private float thrustValue = 0;
	private boolean lastTickMouseButton0 = false;
	
	private int throwDelay = 0;
	
	public ItemSpear() {
		super(ToolMaterial.IRON);
	}
	
	public float thrustValue() {
//		System.out.println(this.thrust);
		return this.thrust;
	}
	
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.none;
    }

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		return false;
	}

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
			if(StaticMethods.isRunningOnClient() && spear != null && spear == this) {
				boolean isButton0Down = Minecraft.getMinecraft().gameSettings.keyBindUseItem.func_151470_d();
				if(isButton0Down && this.thrust < 2.5F) {
					this.thrust += 0.1F;
				} else if(!isButton0Down && this.lastTickMouseButton0) {
					this.thrustValue = Math.abs(this.thrust);
					this.thrust = 0;
					if(this.tryThrowSpear(player, world)) {
						this.send(thrustValue, (EntityClientPlayerMP) player);
					}
				}
				if(this.thrust > 2.5F) {
					this.thrust = 2.5F;
				}
				this.lastTickMouseButton0 = isButton0Down;
			}
		}
		if(this.throwDelay > 0) {
			this.throwDelay--;
		}
	}
	
	public void throwSpear(EntityPlayer player, World world) {
        if (!player.capabilities.isCreativeMode)
        {
            --player.getCurrentEquippedItem().stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        EntitySpear entity = new EntitySpear(world, player, this.thrustValue);
        if(player.capabilities.isCreativeMode) {
        	entity.canBePickedUp = 2;
        }
        world.spawnEntityInWorld(entity);
	}
	
	public boolean tryThrowSpear(EntityPlayer player, World world) {
		
		if(this.throwDelay <= 0) {
			this.throwDelay = 20;
			this.throwSpear(player, world);
	        return true;
		}
		return false;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void send(float thrust, EntityClientPlayerMP player)  {
		ByteBuf data = buffer(4);
		data.writeInt(player.func_145782_y());
		data.writeFloat(thrust);
		C17PacketCustomPayload packet = new C17PacketCustomPayload("spear", data);
		player.sendQueue.func_147297_a(packet);
	}
}
