package knight37x.lance.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpearTNT extends ModelSpear
{
	  //fields
	    ModelRenderer TNT;
	  
	  public ModelSpearTNT()
	  {
	      TNT = new ModelRenderer(this, 27, 30);
	      TNT.addBox(-4F, 0F, -4F, 8, 8, 8);
	      TNT.setRotationPoint(0F, -67F, 0F);
	      TNT.setTextureSize(512, 512);
	      TNT.mirror = true;
	      setRotation(TNT, 0F, 0F, 0F);
	  }
	  
	  public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float f5) {
	    super.render(par1Entity, par2, par3, par4, par5, par6, f5);
	    TNT.render(f5);
	  }
	  
	  public void render(Object par1Entity, float par2, float par3, float par4, float par5, float par6, float f5) {
		    super.render(par1Entity, par2, par3, par4, par5, par6, f5);
		    TNT.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z)
	  {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }

	}
