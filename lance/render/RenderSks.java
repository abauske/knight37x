package knight37x.lance.render;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

import org.lwjgl.opengl.GL11;

import knight37x.lance.item.ItemSks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderSks implements IItemRenderer {
	
	private static RenderItem renderItem = RenderItem.getInstance();
	
    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
        switch (type)
        {
                case INVENTORY:
                	return true;

                default:
                	return false;
        }
}

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
            return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) {
    	if(itemstack != null) {
    		Item item = itemstack.getItem();
    		if(item != null) {
    	    	IIcon icon = item.getIcon(itemstack, 1);
    	    	renderItem.renderIcon(0, 0, icon, 16, 16);
    	    	if(itemstack.isItemEnchanted()) {
        	    	renderItem.renderEffect(Minecraft.getMinecraft().renderEngine, 0, 0);
    	    	}
    		}
    	}

    }
}