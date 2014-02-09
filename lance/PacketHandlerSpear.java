package knight37x.lance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import java.io.ByteArrayInputStream;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

@Sharable
public class PacketHandlerSpear extends SimpleChannelInboundHandler<FMLProxyPacket> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
		if(packet.channel().equals("spear")) {
			try {
				ByteBuf payload = packet.payload();
				MinecraftServer server = MinecraftServer.getServer();
				EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(payload.readInt());
				ItemStack stack = player.getCurrentEquippedItem();
				Item item = null;
				if(stack != null) {
					item = stack.getItem();
				}
				if(item instanceof ItemSpear && player != null) {
					ItemSpear spear = (ItemSpear) item;
					
					spear.throwSpear(player, server.getEntityWorld());
					
				}
			} catch(Exception e) {
//				e.printStackTrace();
				System.out.println("problem");
			}
		}
	}
}
