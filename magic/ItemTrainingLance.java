package knight37x.magic;

import static io.netty.buffer.Unpooled.buffer;

import java.util.ArrayList;
import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import knight37x.lance.Lance;
import knight37x.lance.item.ItemLance;
import knight37x.magic.items.ItemTrainingArmor;

public class ItemTrainingLance extends ItemLance {

	private final String material = "Wool";
	private final int strengh = 2;
	public IIcon[] icons = new IIcon[16];
	
	public ArrayList<Object[]> turnament = new ArrayList();
	
	@Override
	public String getMaterialString() {
		return this.material;
	}

	@Override
	public Item getSwitch() {
		return Base.training_lance_up;
	}

	@Override
	public int getStrengh() {
		return this.strengh;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5) {
		super.onUpdate(par1ItemStack, world, entity, par4, par5);
		
		Iterator<Object[]> it = this.turnament.iterator();
		while(it.hasNext()) {
			Object[] o = it.next();
			if(o.length != 3 || !(o[0] instanceof EntityPlayer) || !(o[1] instanceof EntityPlayer) || !(o[2] instanceof Integer)) {
				it.remove();
			} else {
				o[2] = ((Integer) o[2]) - 1;
				if(((Integer) o[2]) <= 0) {
					it.remove();
				}
			}
		}
	}

	@Override
	public void calcAttack(EntityLivingBase entity, EntityPlayer player) {
		boolean isForwardKeyPressed = false;
		if(this.fwdTime >= Minecraft.getSystemTime()) {
			isForwardKeyPressed = true;
		}
		float hitUse = 0;
		
		if(this.hitTime >= Minecraft.getSystemTime()) {
			hitUse = Math.abs(this.hitValue) * 4;
		}
		if(hitUse != 0 || (player.getDistanceToEntity(entity) <= 6 && isForwardKeyPressed)) {
			float hurt = 0;
			if(isForwardKeyPressed) {
				hurt = player.capabilities.getWalkSpeed() * 10;
			}
			if(player.isRiding()) {
				Entity ridingEntity = player.ridingEntity;
				if(ridingEntity instanceof EntityPig) {
					hurt = 1F;
					hurt *= 1.1;
				} else if(ridingEntity != null) {
					double speed = this.getSpeed((EntityLivingBase) ridingEntity);
					hurt += speed - 1;
				}
//				if(player.isSprinting()) {
//					hurt *= 1.3F;
//				}
			} else if(player.isSprinting()) {
				if(isForwardKeyPressed) {
					hurt += 2F;
				}
				hurt *= 1.2F;
			} else if(player.isSneaking()) {
				hurt *= 0.2F;
			}
			hurt += hitUse;
			hurt /= 10;
			hurt *= this.getStrengh();
			if(hurt != 0) {
				int armorColor = -10;
				ItemStack armor = null;
				for(int i = 1; i <= 4; i++) {
					ItemStack current = entity.getEquipmentInSlot(i);
					if(current != null && current.getItem() instanceof ItemTrainingArmor) {
						armor = current;
						if(armorColor == -10) {
							armorColor = ((ItemTrainingArmor) current.getItem()).getColor(current);
						} else if(armorColor != ((ItemTrainingArmor) current.getItem()).getColor(current)) {
							armorColor = -1;
							break;
						}
					}
				}
				
				int armorColor2 = -10;
				ItemStack armor2 = null;
				for(int i = 1; i <= 4; i++) {
					ItemStack current = player.getEquipmentInSlot(i);
					if(current != null && current.getItem() instanceof ItemTrainingArmor) {
						armor2 = current;
						if(armorColor2 == -10) {
							armorColor2 = ((ItemTrainingArmor) current.getItem()).getColor(current);
						} else if(armorColor2 != ((ItemTrainingArmor) current.getItem()).getColor(current)) {
							armorColor2 = -1;
							break;
						}
					}
				}
				if(armorColor >= 0 && armor != null && armorColor2 >= 0 && armor2 != null) {
					hurt = 0;
					this.sendTurnament(entity.getEntityId(), hurt, (EntityClientPlayerMP) player);
				} else {
					this.send(entity.getEntityId(), hurt, (EntityClientPlayerMP) player);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void sendTurnament(int entityID, float hurt, EntityClientPlayerMP player) {
		ByteBuf data = buffer(4);
		data.writeInt(Base.trainingLancePacketID);
		data.writeInt(player.getEntityId());
		data.writeInt(entityID);
		data.writeFloat(hurt);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendToServer(packet);
	}

	public NBTTagCompound setNewNBT(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			NBTTagCompound tag = stack.stackTagCompound;
			tag.setInteger("colorDamage", 0);
		}
		return stack.stackTagCompound;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer par3EntityPlayer) {
		if(this.getSwitch() != null) {
			ItemStack newLance = new ItemStack(this.getSwitch(), 1);
			newLance.stackTagCompound = itemstack.stackTagCompound;
			EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), newLance);
			newLance.setItemDamage(itemstack.getItemDamage());
			return newLance;
		}
		return itemstack;
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.icons = ((ItemTrainingLanceUp) Base.training_lance_up).getIcons(reg);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if(tag != null && tag.hasKey("colorDamage")) {
			return this.icons[tag.getInteger("colorDamage")];
		}
		return this.icons[0];
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
