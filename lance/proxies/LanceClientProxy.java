package knight37x.lance.proxies;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import knight37x.lance.Lance;
import knight37x.lance.entity.EntitySpear;
import knight37x.lance.entity.EntitySpearFire;
import knight37x.lance.entity.EntitySpearPoison;
import knight37x.lance.entity.EntitySpearTNT;
import knight37x.lance.model.ModelLanceUp;
import knight37x.lance.model.ModelSpear;
import knight37x.lance.model.ModelSpearFire;
import knight37x.lance.model.ModelSpearTNT;
import knight37x.lance.proxies.*;
import knight37x.lance.render.RenderLance;
import knight37x.lance.render.RenderLanceUp;
import knight37x.lance.render.RenderMayorBow;
import knight37x.lance.render.RenderSks;
import knight37x.lance.render.RenderSpear;
import knight37x.lance.render.RenderSpearEntity;
import knight37x.lance.render.RenderSpearFire;
import knight37x.lance.render.RenderSpearFireEntity;
import knight37x.lance.render.RenderSpearTNT;
import knight37x.lance.render.RenderSpearTNTEntity;

public class LanceClientProxy extends LanceCommonProxy {
	
	@Override
	public void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnIron, (IItemRenderer) new RenderLance("lance:textures/models/modelLanceIron"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpIron, (IItemRenderer) new RenderLanceUp("lance:textures/models/modelLanceIron"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnDia, (IItemRenderer) new RenderLance("lance:textures/models/modelLanceDia"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpDia, (IItemRenderer) new RenderLanceUp("lance:textures/models/modelLanceDia"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnCopper, (IItemRenderer) new RenderLance("lance:textures/models/modelLanceCopper"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpCopper, (IItemRenderer) new RenderLanceUp("lance:textures/models/modelLanceCopper"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceOnSteel, (IItemRenderer) new RenderLance("lance:textures/models/modelLanceSteel"));
		MinecraftForgeClient.registerItemRenderer(Lance.lanceUpSteel, (IItemRenderer) new RenderLanceUp("lance:textures/models/modelLanceSteel"));
		
		MinecraftForgeClient.registerItemRenderer(Lance.spear, (IItemRenderer) new RenderSpear());
		MinecraftForgeClient.registerItemRenderer(Lance.spearTNT, (IItemRenderer) new RenderSpearTNT());
		MinecraftForgeClient.registerItemRenderer(Lance.spearPoison, (IItemRenderer) new RenderSpear("lance:textures/models/modelSpearPoison.png"));
		MinecraftForgeClient.registerItemRenderer(Lance.spearFire, (IItemRenderer) new RenderSpearFire());
		
		MinecraftForgeClient.registerItemRenderer(Lance.wood_sks, (IItemRenderer) new RenderSks());
		MinecraftForgeClient.registerItemRenderer(Lance.stone_sks, (IItemRenderer) new RenderSks());
		MinecraftForgeClient.registerItemRenderer(Lance.iron_sks, (IItemRenderer) new RenderSks());
		MinecraftForgeClient.registerItemRenderer(Lance.gold_sks, (IItemRenderer) new RenderSks());
		MinecraftForgeClient.registerItemRenderer(Lance.diamond_sks, (IItemRenderer) new RenderSks());

		RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpearEntity());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpearTNT.class, new RenderSpearTNTEntity(new ModelSpearTNT()));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpearPoison.class, new RenderSpearEntity("lance:textures/models/modelSpearPoison.png"));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpearFire.class, new RenderSpearFireEntity(new ModelSpearFire()));
		
		MinecraftForgeClient.registerItemRenderer(Lance.mayorBow, new RenderMayorBow());
    }
}
