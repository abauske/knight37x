package knight37x.lance.render;

import knight37x.lance.item.ItemSpear;
import knight37x.lance.model.ModelSpear;
import knight37x.lance.model.ModelSpearTNT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class RenderSpearTNT extends RenderSpear implements IItemRenderer {

	@Override
	public ModelSpear getModel() {
		return new ModelSpearTNT();
	}
	
	@Override
	public ResourceLocation getTexture() {
		return new ResourceLocation("lance:textures/models/modelSpearTNT.png");
	}
	
}
