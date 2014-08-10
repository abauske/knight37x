package knight37x.magic;

import java.util.Iterator;
import java.util.List;

import knight37x.magic.entity.EntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class TrollDamage extends EntityDamageSource {

	public TrollDamage(String par1Str, EntityTroll par2Entity) {
		super(par1Str, par2Entity);
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase entity) {
        return new ChatComponentText(entity.getCommandSenderName() + " was stamped into the ground");
    }
}
