package knight37x.lance.render;

import java.lang.reflect.Method;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
 



import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
 
public class RenderMayorBow implements IItemRenderer {
    private RenderManager renderManager;
	private Minecraft mc;
	
	public RenderMayorBow() {
		this.renderManager = RenderManager.instance;
		this.mc = Minecraft.getMinecraft();
	}
 
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED;
	}
 
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}
 
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		EntityLivingBase entity = (EntityLivingBase)data[1];
		ItemRenderer irInstance = this.mc.entityRenderer.itemRenderer;
 
		GL11.glPopMatrix(); // prevents Forge from pre-translating the item
		{
			GL11.glPushMatrix();
 
			// contra-translate the item from it's standard translation
			// also apply some more scale or else the bow is tiny
            float f2 = 3F - (1F/3F);
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(f2, f2, f2);
            GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
            
            // render the item as 'real' bow
            float f3 = 0.625F;
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(f3, -f3, f3);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            
			this.renderItem(entity, item, 0);
			GL11.glPopMatrix();
		}
		GL11.glPushMatrix(); // prevents GL Underflow errors
	}
	
	private void renderItem(EntityLivingBase par1EntityLiving, ItemStack par2ItemStack, int par3) {
		IIcon icon = par1EntityLiving.getItemIcon(par2ItemStack, par3);
 
        if (icon == null)
        {
            return;
        }
 
        if (par2ItemStack.getItemSpriteNumber() == 0)
        {
            this.mc.renderEngine.bindTexture(new ResourceLocation("/terrain.png"));
        }
        else
        {
//            this.mc.renderEngine.bindTexture(new ResourceLocation("/gui/items.png"));
        }
 
        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-f4, -f5, 0.0F);
        float f6 = 1.5F;
        GL11.glScalef(f6, f6, f6);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
 
        if (par2ItemStack != null && par2ItemStack.hasEffect() && par3 == 0)
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.mc.renderEngine.bindTexture(new ResourceLocation("%blur%/misc/glint.png"));
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
 
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}
}
