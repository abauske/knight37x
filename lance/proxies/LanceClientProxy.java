package knight37x.lance.proxies;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import knight37x.lance.EntitySpear;
import knight37x.lance.Lance;
import knight37x.lance.RenderLance;
import knight37x.lance.RenderLanceUp;
import knight37x.lance.RenderSpear;
import knight37x.lance.RenderSpearEntity;
import knight37x.lance.proxies.*;

public class LanceClientProxy extends LanceCommonProxy {
	
	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnIron, (IItemRenderer) new RenderLance("textures/models/modelLanceUpIron.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpIron, (IItemRenderer) new RenderLanceUp("textures/models/modelLanceUpIron.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnDia, (IItemRenderer) new RenderLance("textures/models/modelLanceUpDia.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpDia, (IItemRenderer) new RenderLanceUp("textures/models/modelLanceUpDia.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnCopper, (IItemRenderer) new RenderLance("textures/models/modelLanceUpCopper.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpCopper, (IItemRenderer) new RenderLanceUp("textures/models/modelLanceUpCopper.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnSteel, (IItemRenderer) new RenderLance("textures/models/modelLanceUpSteel.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpSteel, (IItemRenderer) new RenderLanceUp("textures/models/modelLanceUpSteel.png"));
		
		MinecraftForgeClient.registerItemRenderer(Lance.spear, (IItemRenderer) new RenderSpear());
    }
}
