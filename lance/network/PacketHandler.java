package knight37x.lance.network;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import knight37x.lance.Lance;
import knight37x.lance.StaticMethods;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class PacketHandler extends SimpleChannelInboundHandler<FMLProxyPacket> {

	private EnumMap<Side, FMLEmbeddedChannel> channels;
	
	private static HashMap<Integer, NetworkBase> handlers = new HashMap();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
		if(packet.channel().equals("lance")) {
			try {
				ByteBuf payload = packet.payload();
				
				/**
				 * Packet ids:
				 * 0 -> Lance
				 * 1 -> Spear
				 * 2 -> Sks
<<<<<<< HEAD
				 * 3 -> Bow Config Block
=======
>>>>>>> origin/master
				 * 
				 * 10 -> Pass through: Lance state
				 * 11 -> Pass through: Spear state
				 * 12 -> Pass through: Bow state
				 */
				int packetID = payload.readInt();
				
				PacketHandler.handlers.get(packetID).handle(payload, packetID);
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("problem");
			}
		}
	}
	
<<<<<<< HEAD
	public static boolean registerHandler(int packetID, NetworkBase handler) {
		if(PacketHandler.handlers.containsKey(packetID)) {
			return false;
=======
	@SideOnly(Side.CLIENT)
	private void handleClient(ByteBuf msg, int packetID) {
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
			RenderLance.data.put(msg.readInt(), msg.readFloat());
		} else if(packetID == 11) {
			int i = msg.readInt();
			float t = msg.readFloat();
//			StaticMethods.out(t);
			RenderSpear.data.put(i, t);
		} else if(packetID == 12) {
			EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(msg.readInt());
			ItemStack stack = player.getCurrentEquippedItem();
			Item item = null;
			if (stack != null) {
				item = stack.getItem();
			}
			if (item instanceof ItemMayorBow && player != null) {
				ItemMayorBow bow = (ItemMayorBow) item;
				bow.setActive(msg.readBoolean());
			}
>>>>>>> origin/master
		}
		PacketHandler.handlers.put(packetID, handler);
		return true;
	}
	
<<<<<<< HEAD
	public static int getFreePacketID() {
		int i = 0;
		while(PacketHandler.handlers.containsKey(i)) {
			i++;
=======
	private void handleServer(ByteBuf msg, int packetID) {
		MinecraftServer server = MinecraftServer.getServer();
		
		if(packetID == 0) {
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
			this.sendToAll(clientmsg);
			
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
			this.sendToAll(clientmsg);
			
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
		} else if(packetID >= 10) {
			FMLProxyPacket clientmsg = new FMLProxyPacket(msg, "lance");
			this.sendToAll(clientmsg);
>>>>>>> origin/master
		}
		return i;
	}
	
	public void initialise() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("lance", this);
        
        /**
		 * Packet ids:
		 * 0 -> Lance
		 * 1 -> Spear
		 * 2 -> Sks
		 * 3 -> Bow Config Block
		 * 
		 * 10 -> Pass through: Lance state
		 * 11 -> Pass through: Spear state
		 * 12 -> Pass through: Bow state
		 */
        BasicHandler handler = new BasicHandler();
		PacketHandler.handlers.put(0, handler);
		PacketHandler.handlers.put(1, handler);
		PacketHandler.handlers.put(2, handler);
		PacketHandler.handlers.put(3, handler);
		PacketHandler.handlers.put(10, handler);
		PacketHandler.handlers.put(11, handler);
		PacketHandler.handlers.put(12, handler);
        
    }
	
	/**
     * Send this message to everyone.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToAll(FMLProxyPacket message) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the specified player.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param player  The player to send it to
     */
    public void sendTo(FMLProxyPacket message, EntityPlayerMP player) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     * @param point   The {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint} around which to send
     */
    public void sendToAllAround(FMLProxyPacket message, NetworkRegistry.TargetPoint point) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to everyone within the supplied dimension.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message     The message to send
     * @param dimensionId The dimension id to target
     */
    public void sendToDimension(FMLProxyPacket message, int dimensionId) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    /**
     * Send this message to the server.
     * <p/>
     * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
     *
     * @param message The message to send
     */
    public void sendToServer(FMLProxyPacket message) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(message);
    }

}
