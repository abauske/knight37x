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
public class PacketHandlerLance extends SimpleChannelInboundHandler<FMLProxyPacket> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
		if(packet.channel().equals("lance")) {
			try {
				ByteBuf payload = packet.payload();
				MinecraftServer server = MinecraftServer.getServer();
				EntityPlayer player = (EntityPlayer) server.getEntityWorld().getEntityByID(payload.readInt());
				ItemStack stack = player.getCurrentEquippedItem();
				Item item = null;
				if(stack != null) {
					item = stack.getItem();
				}

				Entity entity = ItemLance.getRightEntity(server.getEntityWorld(), payload.readInt());
				if(item instanceof ItemLance && player != null && entity != null) {
					ItemLance lance = (ItemLance) item;
					
					if(lance.attack((EntityLivingBase) entity, player, payload.readFloat() + lance.handleEnchants(stack, (EntityLivingBase) entity, player)) && !player.capabilities.isCreativeMode && Lance.shouldLanceBreak) {
						if(Math.random() < 1.0f / (EnchantmentHelper.getEnchantmentLevel(34, stack) + 1)) {
							lance.damageLance(entity, player);
						}
					}
					
				}
			} catch(Exception e) {
//				e.printStackTrace();
				System.out.println("problem");
			}
		}
	}
}
