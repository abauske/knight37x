package knight37x.lance;

import static io.netty.buffer.Unpooled.buffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class LanceAutoUpdate extends CommandBase {

	@Override
	public String getCommandName() {
		return "lanceAutoUpdate";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "Downloads and installs newest version of Lance Mod (only works when Minecraft version is the same)";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(sender instanceof EntityPlayer) {
			sender.addChatMessage(new ChatComponentText("Don't worry, this might take a while (depends on your internet connection)"));
			this.sendUpdate((EntityPlayerMP) sender);
			sender.addChatMessage(new ChatComponentText("Restart Minecraft to play new version"));
		} else if(sender instanceof CommandBlockLogic) {
			return ;
		} else if (sender instanceof MinecraftServer || sender instanceof RConConsoleSource) {
			LanceAutoUpdate.update();
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender) {
		return true;
	}
	
	public static void update() {
		try {
			java.net.URL url = new URL(Lance.newestVersion.getDownloadURL());
			URLConnection connection = url.openConnection();
			BufferedInputStream stream = new BufferedInputStream(connection.getInputStream());
			int available = stream.available();
			byte b[] = new byte[available];
			stream.read(b);
			File file = new File("./mods/" + Lance.newestVersion.name);
			OutputStream out = new FileOutputStream(file);
			out.write(b);
			out.close();
			System.out.println("New version installed");
			
			File oldMod = new File("./mods/" + Version.getInstalledVersion());
			 
    		if(oldMod.delete()){
    			System.out.println(oldMod.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
    private void sendUpdate(EntityPlayerMP player) {
		ByteBuf data = buffer(4);
		data.writeInt(13);
		FMLProxyPacket packet = new FMLProxyPacket(data, "lance");
		Lance.packetHandler.sendTo(packet, player);
	}
}
