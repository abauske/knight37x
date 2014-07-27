package knight37x.lance.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import knight37x.lance.StaticMethods;

public abstract class NetworkBase {
	
	public void handle(ByteBuf msg, int packetID) {
		if(StaticMethods.isRunningOnClient()) {
        	this.handleClient(msg, packetID);
		} else {
        	this.handleServer(msg, packetID);
		}
	}
	@SideOnly(Side.CLIENT)
	public abstract void handleClient(ByteBuf msg, int packetID);
	public abstract void handleServer(ByteBuf msg, int packetID);
}
