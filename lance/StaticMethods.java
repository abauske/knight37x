package knight37x.lance;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public abstract class StaticMethods {
	
	public static boolean isRunningOnClient() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.SERVER) {
            return false;
        } else if (side == Side.CLIENT) {
            return true;
        } else {
                // We are on the Bukkit server.
        	return false;
        }
	}
	
	public static void out(Object o) {
		System.out.println(o);
	}

}
