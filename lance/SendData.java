package knight37x.lance;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class SendData extends CommandBase {

	@Override
	public String getCommandName() {
		return "send";
	}

	@Override
	public String getCommandUsage(ICommandSender var1) {
		return "Command not client usable!";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] msg) {
		try {
			MinecraftServer server = MinecraftServer.getServer();
			if(sender instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) sender;
				p.experience =+ 100;
//				System.out.println("player");
				
				if(msg.length == 1 && msg[0].equals("give")) {
					p.setCurrentItemOrArmor(0, new ItemStack(Lance.lanceUpIron, 1));
				}
				
				if(msg.length == 2) {
					if(msg[0].equals("entity")) {
//						System.out.println("entity");
						Item item = p.getCurrentEquippedItem().getItem();
						if(item instanceof ItemLance) {
							ItemLance lance = (ItemLance) item;
							lance.entity(Integer.valueOf(msg[1]), null, MinecraftServer.getServer().getEntityWorld());
						}
					} else if(msg[0].equals("hit")) {
						Item item = p.getCurrentEquippedItem().getItem();
						if(item instanceof ItemLance) {
							ItemLance lance = (ItemLance) item;
							float value = Float.valueOf(msg[1]);
							if(lance.hitValue < value || lance.hitTime < server.getSystemTimeMillis()) {
								lance.hitValue = value;
								lance.hitTime = server.getSystemTimeMillis() + 200;
							}
						}
					} else if(msg[0].equals("fwd")) {
						Item item = p.getCurrentEquippedItem().getItem();
						if(item instanceof ItemLance) {
							ItemLance lance = (ItemLance) item;
							lance.fwdTime = server.getSystemTimeMillis() + 200;
						}
					}
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(sender instanceof EntityPlayer) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

}
