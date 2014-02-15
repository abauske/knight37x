package knight37x.lance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import static io.netty.buffer.Unpooled.buffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ItemLance extends ItemSword {

//	private int counter1 = 0;
	
	private EntityPlayer player;				//The player that has the lance in his inventory
	private World world;						//Current world
	
	private Entity pointedEntity;
	private EntityLivingBase pointedEntityLiving;
	private MovingObjectPosition objectMouseOver;
	public float knockTime = 0.0F;
	private boolean lastTickMouseButton0 = false;
	private float hit = 0.0F;
	private int knockCounter = 0;
	
	private int countedTicks = 0;
	private boolean lastTickMouseButton1 = false;
	
	private int leftClickCounter = 0;
	
	private ItemLance switchTo;
	
	//------------------Sended Data---------------------------
	
	protected float hitValue = 0;
	protected long hitTime = 0;
	
	protected long fwdTime = 0;
	
	//------------------------------------------------------
	
	public ItemLance() {
		super(ToolMaterial.IRON);
		setCreativeTab(null);
	}
	
	public String getMaterialString() {
		return "";
	}

	@SideOnly(Side.CLIENT)

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return false;
    }

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer par3EntityPlayer) {
		if(this.getSwitch() != null) {
			ItemStack newLance = new ItemStack(this.getSwitch(), 1);
			EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(itemstack), newLance);
			newLance.setItemDamage(itemstack.getItemDamage());
			return newLance;
		}
		return itemstack;
	}
	
	public Item getSwitch() {
		return null;
	}
	
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    @Override
	public void onUpdate(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5) {
//    	System.out.print(Item.field_150901_e.getObject("iron_lance_on"));
    	this.player = null;
    	this.world = world;
		if(entity != null && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			this.player = player;
			if(StaticMethods.isRunningOnClient()) {
				if(Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed()) {
					this.fwdTime = Minecraft.getSystemTime() + 200;
//					this.sendIsForwardKeyPressed(true, (EntityClientPlayerMP) player);
	    		}
				
				this.hit = 0.0F;
				boolean isButton0Down = Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed();
				if(isButton0Down && knockTime < 1) {
					this.knockTime += 0.03F;
				} else if(!isButton0Down && this.lastTickMouseButton0) {
					this.knockTime = -knockTime;
					this.hit = Math.abs(knockTime);
					this.knockCounter = 20;
				} else if (knockTime < 0 && this.knockCounter <= 0) {
					this.knockTime = 0.0F;
//					this.hit = true;
					this.knockCounter = 0;
				}
				if(this.knockCounter > 0) {
					this.knockCounter--;
				}
				if(this.knockTime < 0) {
					this.knockTime += 0.1F;
				}
				this.lastTickMouseButton0 = isButton0Down;
				
				
				if(hit != 0) {
					this.hitValue = hit;
					this.hitTime = Minecraft.getSystemTime() + 200;
//					this.sendHitValue(hit, (EntityClientPlayerMP) player);
				}
			}
			
			
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemLance) {
				if (StaticMethods.isRunningOnClient() && this.getMouseOver() != null) {
					Entity aim = this.getMouseOver();
					this.entity(aim.getEntityId(), player, world);
//					this.sendID(aim.func_145782_y(), (EntityClientPlayerMP) player);
				}
				if(player.getCurrentEquippedItem() != null) {
					if(((EntityPlayer) entity).getCurrentEquippedItem().getItemDamage() >= ((EntityPlayer) entity).getCurrentEquippedItem().getMaxDamage()) {
						this.destroy(player);
					}
				}
			}
		}
		
		if(this.leftClickCounter > 0) {
			this.leftClickCounter--;
		}
		
//		this.counter1++;
//		if(this.counter1 > 9000) {
//			this.counter1 = 20;
//		}
	}
    
    /*
     * second part of order
     * world and player can be null too!
     */
    public void entity(int aimid, EntityPlayer player, World world) {
    	if(player == null) {
    		player = this.player;
    	}
    	if(world == null) {
    		world = this.world;
    	}
    	
    	Entity aim = this.getRightEntity(world, aimid);
    	if (player != null && aim instanceof EntityLivingBase && player.getDistanceToEntity(aim) <= 12 && !aim.isDead) {
			this.CalcAttack((EntityLivingBase) aim, player);
		}
    }
    
    public void damageLance(Entity aim, EntityPlayer player) {
//    	if(counter1 < 20) {
//    		return ;
//    	}
//		this.counter1 = 0;
    	ItemStack stack = player.getCurrentEquippedItem();
    	if (stack != null) {
			this.setDamage(stack, stack.getItemDamage() + 1);
		}
		int armor = ((EntityLivingBase) aim).getTotalArmorValue();
		if (Lance.shouldTakeDamageFromArmour) {
			if(armor > 0) {
				this.setDamage(stack, stack.getItemDamage() + (int) ((100 / (11 - (armor / 2))) / 10) * Lance.armorBehaviour);
			}
		}
    }
	
	public Entity getMouseOver()
    {
		float par1 = 1.0F;
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityLivingBase entityRender = Minecraft.getMinecraft().renderViewEntity;
        if (entityRender != null)
        {
            if (Minecraft.getMinecraft().theWorld != null)
            {
                this.pointedEntityLiving = null;
                double d0 = (double)Minecraft.getMinecraft().playerController.getBlockReachDistance();
                this.objectMouseOver = entityRender.rayTrace(d0, par1);
                double d1 = d0;
                Vec3 vec3 = Minecraft.getMinecraft().renderViewEntity.getPosition(par1);
                
                boolean test = true;
                if (Minecraft.getMinecraft().playerController.extendedReach() || test)
                {
                    d0 = 7.0D;
                    d1 = 7.0D;
                }
                else
                {
                    if (d0 > 3.0D)
                    {
                        d1 = 3.0D;
                    }

                    d0 = d1;
                }

                if (this.objectMouseOver != null)
                {
                    d1 = this.objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = Minecraft.getMinecraft().renderViewEntity.getLook(par1);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                this.pointedEntity = null;
                float f1 = 20.0F;
                List list = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.getMinecraft().renderViewEntity, Minecraft.getMinecraft().renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
                double d2 = d1;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = (Entity)list.get(i);

                    if (entity.canBeCollidedWith())
                    {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                        
                        if (axisalignedbb.isVecInside(vec3))
                        {
                            if (0.0D < d2 || d2 == 0.0D)
                            {
                                this.pointedEntity = entity;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null)
                        {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D || d3 >= 20.0d)
                            {
                                if (entity == Minecraft.getMinecraft().renderViewEntity.ridingEntity)
                                {
                                    if (d2 == 0.0D)
                                    {
                                        this.pointedEntity = entity;
                                    }
                                }
                                else
                                {
                                    this.pointedEntity = entity;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (this.pointedEntity != null && (d2 < d1 || Minecraft.getMinecraft().objectMouseOver == null))
                {
                    this.objectMouseOver = new MovingObjectPosition(this.pointedEntity);

                    if (this.pointedEntity instanceof EntityLivingBase)
                    {
                        this.pointedEntityLiving = (EntityLivingBase) this.pointedEntity;
                    }
                }
            }
        }
        return this.pointedEntity;
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
	
	private void knockBack(EntityLivingBase entity, EntityPlayer player) {
		int speed;
		if(player.isSprinting()) {
			speed = 10;
		} else if(player.isRiding()) {
			Entity ridingEntity = player.ridingEntity;
			if(ridingEntity instanceof EntityPig) {
				speed = 5;
			} else {
				speed = 2;
			}
		} else if(player.isSneaking()) {
			speed = 0;
		}  else {
			speed = 2;
		}
		
		double d0 = player.posX - entity.posX;
        double d1;

        for (d1 = player.posZ - entity.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
        {
            d0 = (Math.random() - Math.random()) * 0.01D;
        }

        entity.attackedAtYaw = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - entity.rotationYaw;
        entity.knockBack(player, speed, d0, d1);
	}

	private void CalcAttack(EntityLivingBase entity, EntityPlayer player) {
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
			if(player.isRiding()) {
				Entity ridingEntity = player.ridingEntity;
				if(ridingEntity instanceof EntityPig) {
					hurt = 1F;
					hurt *= 1.1;
				} else if(ridingEntity != null) {
					double speed = this.getSpeed((EntityLivingBase) ridingEntity);
					hurt += speed;
				} else if(isForwardKeyPressed) {
					hurt += 1F;
				}
//				if(player.isSprinting()) {
//					hurt *= 1.3F;
//				}
			} else if(player.isSprinting()) {
				if(isForwardKeyPressed) {
					hurt += 3F;
				}
				hurt *= 1.2F;
			} else if(player.isSneaking()) {
				hurt *= 0.2F;
			} else if(isForwardKeyPressed) {
				hurt += 1F;
			}
			hurt += hitUse;
			hurt /= 10;
			hurt *= this.getStrengh();
			if(hurt != 0) {
//				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), hurt);
				this.send(entity.getEntityId(), hurt, (EntityClientPlayerMP) player);
			}
		}
	}
	
	/*
	 * Handles enchantments and returns extra damage caused by enchantments
	 */
	public static float handleEnchants(ItemStack itemstack, EntityLivingBase entity, EntityPlayer player)
    {
		float extraDamage = 0;
		entity.setFire(EnchantmentHelper.getFireAspectModifier(player) * 4);
		extraDamage += EnchantmentHelper.getEnchantmentModifierLiving(player, entity);
		
		int knockback = EnchantmentHelper.getKnockbackModifier(player, entity);
		if(knockback != 0) {
			double d0 = player.posX - entity.posX;
	        double d1;

	        for (d1 = player.posZ - entity.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
	        {
	            d0 = (Math.random() - Math.random()) * 0.01D;
	        }

	        entity.attackedAtYaw = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - entity.rotationYaw;
            float f1 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
            float f2 = 0.3F * knockback;
            entity.motionX /= 5.0D;
            entity.motionY /= 5.0D;
            entity.motionZ /= 5.0D;
            entity.motionX -= d0 / (double)f1 * (double)f2;
            entity.motionY += (double)f2;
            entity.motionZ -= d1 / (double)f1 * (double)f2;

            if (entity.motionY > 0.4000000059604645D)
            {
                entity.motionY = 0.4000000059604645D;
            }
		}
//		print(Lance.isAvailable("diamond"));
//		for(String current : OreDictionary.getOreNames())
//		print(current);
        return extraDamage;
    }
	
	public static void print(Object object) {
		System.out.println(object);
	}
	
	public boolean attack(EntityLivingBase entity, EntityPlayer player, float value) {
		return entity.attackEntityFrom(DamageSource.causePlayerDamage(player), value);
	}
	
	public int getStrengh() {
		return 0;
	}
	
 	private double getSpeed(EntityLivingBase entity) {
 		return entity.getDistance(entity.prevPosX, entity.prevPosY, entity.prevPosZ) * 35;
 	}

	
	private Entity getRightEntity(World world, Entity entity) {
		return this.getRightEntity(world, entity.getEntityId());
	}
	
	public static Entity getRightEntity(World world, int id) {
		List list = world.loadedEntityList;
		for(int i = 0; i < world.loadedEntityList.size(); i++) {
			Entity current = (Entity) list.toArray()[i];
			if(current != null) {
				if(current.getEntityId() == id) {
					return current;
				}
			}
		}
		return null;
	}
	
	private void destroy(EntityPlayer player) {
		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == this) {
			player.inventory.mainInventory[player.inventory.currentItem] = null;
			player.dropPlayerItemWithRandomChoice(new ItemStack(Items.stick, 2), true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void send(int entityID, float hurt, EntityClientPlayerMP player)  {
		ByteBuf data = buffer(4);
		data.writeInt(player.getEntityId());
		data.writeInt(entityID);
		data.writeFloat(hurt);
		C17PacketCustomPayload packet = new C17PacketCustomPayload("lance", data);
		player.sendQueue.addToSendQueue(packet);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		Item item1 = stack1.getItem();
		Item item2 = stack2.getItem();
		if(item1 == null && item2 == null) {
			return false;
		}
		return (item1 instanceof ItemLance || item1 instanceof ItemLanceUp) && (item2 instanceof ItemLance || item2 instanceof ItemLanceUp);
	}
}
