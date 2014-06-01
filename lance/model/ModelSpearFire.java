package knight37x.lance.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpearFire extends ModelSpear
{
  //fields
    ModelRenderer Fire1;
    ModelRenderer Fire2;
  
  public ModelSpearFire()
  {
	  super();
	  Fire1 = new ModelRenderer(this, 0, 41);
      Fire1.addBox(0F, -11F, 0F, 0, 23, 8);
      Fire1.setRotationPoint(0F, -52F, 0F);
      Fire1.setTextureSize(64, 32);
      Fire1.mirror = true;
      setRotation(Fire1, 0F, 0F, 0.4461433F);
      Fire2 = new ModelRenderer(this, 0, 41);
      Fire2.addBox(0F, -10F, 0F, 0, 23, 8);
      Fire2.setRotationPoint(0F, -11F, 0F);
      Fire2.setTextureSize(64, 32);
      Fire2.mirror = true;
      setRotation(Fire2, 0F, 0F, -0.4089647F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    super.render(entity, f, f1, f2, f3, f4, f5);
    Fire1.render(f5);
    Fire2.render(f5);
  }
  
  public void render(Object entity, float f, float f1, float f2, float f3, float f4, float f5) {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    Fire1.render(f5);
	    Fire2.render(f5);
	  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

}
