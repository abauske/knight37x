package knight37x.magic.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class ItemMana extends Item {

	public static IIcon equipped;
	
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		ItemMana.equipped = reg.registerIcon("magic:mana_equipped");
	}

}
