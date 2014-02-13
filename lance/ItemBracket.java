package knight37x.lance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemBracket extends ItemHangingEntity {

	public ItemBracket(Class p_i45342_1_) {
		super(p_i45342_1_);
	}
	
//	@Override
//	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
//    {
//        if (par7 == 0) {
//            return false;
//        } else if (par7 == 1) {
//            return false;
//        } else {
//            int i1 = Direction.facingToDirection[par7];
//            EntityBracket entity = new EntityBracket(world, par4, par5, par6, i1);
//            entity.setClickedOn(par4, par5, par6);
//
//            if (!player.canPlayerEdit(par4, par5, par6, par7, itemstack)) {
//                return false;
//            } else {
////            	System.out.println(par4 + " " + par5 + " " + par6);
////            	world.func_147449_b(par4, par5, par6, Blocks.iron_block);
//                if (entity != null && entity.onValidSurface()) {
//                    if (!world.isRemote) {
//                        world.spawnEntityInWorld(entity);
//                    	System.out.println("spawned");
//                    }
//
//                    --itemstack.stackSize;
//                }
//
//                return true;
//            }
//        }
//    }
	
}
