package knight37x.lance;

import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

public class PacketHandler extends NetworkManager
{
	public static int entityID;
	public static boolean isForwardKeyPressed;

	public PacketHandler(boolean p_i45147_1_) {
		super(p_i45147_1_);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext handler, Packet packet) {
		if(packet.func_148835_b().equals("lance")) {
			
		}
	}
	
	
//	@Override
//    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
//	{
//		if (packet.channel.equals("lance"))
//		{
//			handlePacket(packet);
//		} else if (packet.channel.equals("lanceHitEntity"))
//		{
//			handlePacketHitEntity(packet);
//		} else if (packet.channel.equals("lanceHitValue"))
//		{
//			handlePacketHitValue(packet);
//		} else if (packet.channel.equals("lanceIsForward"))
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
