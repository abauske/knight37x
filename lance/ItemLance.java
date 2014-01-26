package knight37x.lance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import static io.netty.buffer.Unpooled.buffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
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

public class ItemLance extends ItemSword {

	private int counter1 = 0;
	private int counter2 = 0;
	private int counter3 = 0;
	
	private EntityPlayer player;				//The player that has the lance in his inventory
	private World world;						//Current world
	
	private Entity pointedEntity;
	private EntityLiving pointedEntityLiving;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void func_150895_a(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
	}
	
	@Override
	public int getItemEnchantability() {
		return 14;
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
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer par3EntityPlayer) {
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
			if(this.isRunningOnClient()) {
				if(Minecraft.getMinecraft().gameSettings.keyBindForward.func_151470_d()) {
					this.sendIsForwardKeyPressed(true, (EntityClientPlayerMP) player);
	    		}
				
				this.hit = 0.0F;
				boolean isButton0Down = Minecraft.getMinecraft().gameSettings.keyBindAttack.func_151470_d();
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
					this.sendHitValue(hit, (EntityClientPlayerMP) player);
				}
			}
			
			
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemLance) {
				if (this.isRunningOnClient() && this.getMouseOver() != null) {
					Entity aim = this.getMouseOver();
					this.sendID(aim.func_145782_y(), (EntityClientPlayerMP) player);
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
		
		this.counter1++;
		if(this.counter1 > 9000) {
			this.counter1 = 20;
		}
		
		this.counter2++;
		if(this.counter2 > 9000) {
			this.counter2 = 20;
		}
		
		this.counter3++;
		if(this.counter3 > 9000) {
			this.counter3 = 20;
		}
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
    	if (player != null && aim instanceof EntityLiving && player.getDistanceToEntity(aim) <= 12 && !aim.isDead) {
			boolean attacked = this.attack((EntityLiving) aim, player);
			if (attacked && player.getCurrentEquippedItem() != null) {
				this.setDamage(player.getCurrentEquippedItem(), player.getCurrentEquippedItem().getItemDamage() + 1);
			}
			int armor = ((EntityLiving) aim).getTotalArmorValue();
			if (attacked && Lance.shouldTakeDamageFromArmour && this.counter1 >= 10) {
				this.counter1 = 0;
				if(armor > 0) {
					this.setDamage(player.getCurrentEquippedItem(), player.getCurrentEquippedItem().getItemDamage() + (int) ((100 / (11 - (armor / 2))) / 10) * Lance.armorBehaviour);
				} else {
					this.setDamage(player.getCurrentEquippedItem(), player.getCurrentEquippedItem().getItemDamage() + 1);
				}
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

                    if (this.pointedEntity instanceof EntityLiving)
                    {
                        this.pointedEntityLiving = (EntityLiving)this.pointedEntity;
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
/*
	private void knockBack(EntityLiving entity, EntityPlayer player) {
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
        for(int i = 0; i < speed; i++) {
        	entity.knockBack(player, speed, d0, d1);
        }
	}*/

	private boolean attack(EntityLiving entity, EntityPlayer player) {
		boolean isForwardKeyPressed = false;
		if(this.fwdTime >= MinecraftServer.getSystemTimeMillis()) {
			isForwardKeyPressed = true;
		}
		float hitUse = 0;
		
		if(this.hitTime >= MinecraftServer.getSystemTimeMillis()) {
			hitUse = Math.abs(this.hitValue) * 4;
		}
		if(hitUse != 0 || player.getDistanceToEntity(entity) <= 6) {
			float hurt = 0;
			if(player.isSprinting()) {
				if(isForwardKeyPressed) {
					hurt += 3F;
				}
				hurt *= 1.2F;
			} else if(player.isRiding()) {
				Entity ridingEntity = player.ridingEntity;
				if(ridingEntity instanceof EntityPig) {
					if(isForwardKeyPressed) {
						hurt += 1F;
					}
					hurt *= 1.1;
				} else if(ridingEntity != null) {
					hurt += this.getSpeed((EntityLiving) ridingEntity);
				} else if(isForwardKeyPressed) {
					hurt += 1F;
				}
			} else if(player.isSneaking()) {
				hurt *= 0.2F;
			} else if(isForwardKeyPressed) {
				hurt += 1F;
			}
			hurt += hitUse;
			hurt /= 10;
			hurt *= this.getStrengh();
			if(hurt != 0) {
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), hurt);
				if(!player.capabilities.isCreativeMode && Lance.shouldLanceBreak) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int getStrengh() {
		return 0;
	}
	
 	private double getSpeed(EntityLiving entity) {
 		return entity.getDistance(entity.prevPosX, entity.prevPosY, entity.prevPosZ) * 35;
 	}

	
	private Entity getRightEntity(World world, Entity entity) {
		return this.getRightEntity(world, entity.func_145782_y());
	}
	
	private Entity getRightEntity(World world, int id) {
		List list = world.loadedEntityList;
		for(int i = 0; i < world.loadedEntityList.size(); i++) {
			Entity current = (Entity) list.toArray()[i];
			if(current != null) {
				if(current.func_145782_y() == id) {
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
	private void sendID(Entity entity, EntityClientPlayerMP player)  {
		this.sendID(entity.func_145782_y(), player);
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void sendID(int id, EntityClientPlayerMP player) {
		ByteBuf data = buffer(4);
		data.writeInt(0);
		data.writeInt(player.func_145782_y());
		data.writeInt(id);
		C17PacketCustomPayload packet = new C17PacketCustomPayload("lance", data);
		player.sendQueue.func_147297_a(packet);
		
//		if(this.counter2 > 15) {
//			this.counter2 = 0;
//			player.sendChatMessage("/send entity " + id);
//		}
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void sendHitValue(float hitValue, EntityClientPlayerMP player)  {
		ByteBuf data = buffer(4);
		data.writeInt(1);
		data.writeInt(player.func_145782_y());
		data.writeFloat(hitValue);
		C17PacketCustomPayload packet = new C17PacketCustomPayload("lance", data);
		player.sendQueue.func_147297_a(packet);
		
//		player.sendChatMessage("/send hit " + hitValue);
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	private void sendIsForwardKeyPressed(boolean isForwardKeyPressed, EntityClientPlayerMP player)  {
//		Lance.packetPipeline.sendTo(new PacketHandler2(), player);
		
		if(isForwardKeyPressed) {
			ByteBuf data = buffer(4);
			data.writeInt(2);
			data.writeInt(player.func_145782_y());
			C17PacketCustomPayload packet = new C17PacketCustomPayload("lance", data);
	        player.sendQueue.func_147297_a(packet);
		}
		
//		if(isForwardKeyPressed && this.counter3 > 15) {
//			this.counter3 = 0;
//			player.sendChatMessage("/send fwd " + isForwardKeyPressed);
//		}
	}
	
	public boolean isRunningOnClient() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            return false;
        } else if (side == Side.CLIENT) {
            return true;
        } else {
                // We are on the Bukkit server.
        	return false;
        }
	}
}
