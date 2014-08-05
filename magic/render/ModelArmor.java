package knight37x.magic.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelArmor extends ModelBiped {
	public ModelArmor() {
		super(1.5F);
	}
	
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float  par6, float par7){
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
    }
}
