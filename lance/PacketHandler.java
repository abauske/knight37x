package knight37x.lance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import java.io.ByteArrayInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

@Sharable
public class PacketHandler extends SimpleChannelInboundHandler<FMLProxyPacket> {

//	@Override
//    protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
//		System.out.println(12);
//        if (packet.channel().equals("lance")) {
//            ByteBuf payload = packet.payload();
//            if (payload.readableBytes() == 4) {
//                int number = payload.readInt();
//                System.out.println("number = " + number);
//            }
//        }
//    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FMLProxyPacket packet) throws Exception {
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
		
	}
	
	
//	@Override
//    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
//	{
//		if (packet.channel().equals("lance"))
//		{
//			handlePacket(packet);
//		} else if (packet.channel().equals("lanceHitEntity"))
//		{
//			handlePacketHitEntity(packet);
//		} else if (packet.channel().equals("lanceHitValue"))
//		{
//			handlePacketHitValue(packet);
//		} else if (packet.channel().equals("lanceIsForward"))
//		{
//			handlePacketIsForwardKeyPressed(packet);
//		}
//	}
//	
//	private void handlePacketIsForwardKeyPressed(Packet250CustomPayload packet) {
//		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//        
//        try
//        {
//        	isForwardKeyPressed = inputStream.readBoolean();
//        }
//        catch(IOException e)
//        {
//        	e.printStackTrace();
//        	return;
//        }
//	}
//
//	private void handlePacketHitValue(Packet250CustomPayload packet) {
//		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//        
//        try
//        {
//        	hit = new HitValueUntil(inputStream.readFloat(), inputStream.readLong());
//        }
//        catch(IOException e)
//        {
//        	e.printStackTrace();
//        	return;
//        }
//	}
//
//	private void handlePacketHitEntity(Packet250CustomPayload packet) {
//		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//            
//        try
//        {
//        	entityID = inputStream.readInt();
//        }
//        catch(IOException e)
//        {
//        	e.printStackTrace();
//        	return;
//        }
//	}
//
//	private void handlePacket(Packet250CustomPayload packet)
//    {
//		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
//            
//        int randomInt1;
//        int randomInt2;
//            
//        try
//        {
//        	randomInt1 = inputStream.readInt();    
//        	randomInt2 = inputStream.readInt();
//        }
//        catch(IOException e)
//        {
//        	e.printStackTrace();
//        	return;
//        }
//        
//        System.out.println(randomInt1 + " " + randomInt2);
//    }
}
