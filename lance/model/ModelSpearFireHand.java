package knight37x.lance.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpearFireHand extends ModelSpear
{
	  //fields
	    ModelRenderer Fire;
	  
	  public ModelSpearFireHand()
	  {
		  super();
	      Fire = new ModelRenderer(this, 0, 25);
	      Fire.addBox(-2.5F, -6F, -2F, 5, 10, 0);
	      Fire.setRotationPoint(0F, -62F, 0F);
	      Fire.setTextureSize(512, 512);
	      Fire.mirror = true;
	      setRotation(Fire, 0.5948578F, 0F, 0F);
	  }
	  
	  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
		  super.render(entity, f, f1, f2, f3, f4, f5);
		  Fire.render(f5);
	  }
	  
	  public void render(Object entity, float f, float f1, float f2, float f3, float f4, float f5)
	  {
		  super.render(entity, f, f1, f2, f3, f4, f5);
		  Fire.render(f5);
	  }
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z) {
			model.rotateAngleX = x;
			model.rotateAngleY = y;
			model.rotateAngleZ = z;
	  }

	}
