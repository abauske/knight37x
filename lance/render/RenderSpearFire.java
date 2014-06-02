package knight37x.lance.render;

import knight37x.lance.item.ItemSpear;
import knight37x.lance.model.ModelSpear;
import knight37x.lance.model.ModelSpearFireHand;
import knight37x.lance.model.ModelSpearTNT;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;

public class RenderSpearFire extends RenderSpear implements IItemRenderer {
	
	@Override
	public ModelSpear getModel() {
		return new ModelSpearFireHand();
	}

	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation("lance:textures/models/modelSpearFireHand.png");
	}
	
}
