package knight37x.lance;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

public class PacketHandler2 extends AbstractPacket {

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {

		buffer.writeInt(15);
		System.out.println(buffer.readInt() + "  read");
//		int test = buffer.readInt();
//		System.out.println(buffer.readInt());
//		System.out.println("read");
//		MinecraftServer server = MinecraftServer.getServer();
//		Item item = buffer.isReadable() ? ((EntityPlayer) server.getEntityWorld().playerEntities.toArray()[buffer.readInt()]).getCurrentEquippedItem().getItem() : null;
//		
//		if(item != null && item instanceof ItemLance) {
//			ItemLance lance = (ItemLance) item;
//			
//			
//			if (buffer.capacity() == 8) {
//				lance.entity(buffer.readInt(), null, server.getEntityWorld());
//				
//				
//			} else if (buffer.capacity() > 8) {
//				float value = buffer.readFloat();
//				if (lance.hitValue < value || lance.hitTime < server.getSystemTimeMillis()) {
//					lance.hitValue = value;
//					lance.hitTime = server.getSystemTimeMillis() + 200;
//				}
//				
//				
//			} else if (buffer.capacity() < 8) {
//				lance.fwdTime = server.getSystemTimeMillis() + 200;
//			}
//		}
//		System.out.println(buffer.readInt());
//		System.out.println("read");
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		try {
			MinecraftServer server = MinecraftServer.getServer();
			int flag = buffer.readInt();
			int playerID = buffer.readInt();
			Item item = null;
			for(Object obj : server.getEntityWorld().playerEntities) {
				if(obj instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) obj;
					if(player.func_145782_y() == playerID) {
						item = player.getCurrentEquippedItem().getItem();
					}
				}
			}
			
			if(item instanceof ItemLance) {
				ItemLance lance = (ItemLance) item;
				
				
				if (flag == 0) {
					lance.entity(buffer.readInt(), null, server.getEntityWorld());
					
					
				} else if (flag == 1) {
					float value = buffer.readFloat();
					if (lance.hitValue < value || lance.hitTime < server.getSystemTimeMillis()) {
						lance.hitValue = value;
						lance.hitTime = server.getSystemTimeMillis() + 200;
					}
					
					
				} else if (flag == 2) {
					lance.fwdTime = server.getSystemTimeMillis() + 200;
				}
			}
		} catch(Exception e) {
			System.out.println("problem");
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		System.out.println(14);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}

//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
//		MinecraftServer server = MinecraftServer.getServer();
//		Item item = ((EntityPlayer) server.getEntityWorld().playerEntities.toArray()[packet.payload().readInt()]).getCurrentEquippedItem().getItem();
//		
//		if(item instanceof ItemLance) {
//			ItemLance lance = (ItemLance) item;
//			
//			
//			if (packet.channel().equals("lanceHitEntity")) {
//				lance.entity(packet.payload().readInt(), null, server.getEntityWorld());
//				
//				
//			} else if (packet.channel().equals("lanceHitValue")) {
//				float value = packet.payload().readFloat();
//				if (lance.hitValue < value || lance.hitTime < server.getSystemTimeMillis()) {
//					lance.hitValue = value;
//					lance.hitTime = server.getSystemTimeMillis() + 200;
//				}
//				
//				
//			} else if (packet.channel().equals("lanceIsForward")) {
//				lance.fwdTime = server.getSystemTimeMillis() + 200;
//			}
//		}
		
//	}
}
