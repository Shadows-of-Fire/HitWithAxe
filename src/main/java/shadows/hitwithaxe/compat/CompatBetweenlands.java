package shadows.hitwithaxe.compat;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.block.BlockReplacedRubberLog;
import shadows.placebo.util.PlaceboUtil;
import thebetweenlands.common.registries.BlockRegistry;

public class CompatBetweenlands {

	public static void replaceLog(IForgeRegistry<Block> reg) {
		Block b = new BlockReplacedRubberLog().setRegistryName("thebetweenlands:log_rubber").setTranslationKey("thebetweenlands.log_rubber");
		try {
			EnumHelper.setFailsafeFieldValue(ObfuscationReflectionHelper.findField(BlockRegistry.class, "LOG_RUBBER"), null, b);
			reg.register(b);
			HitWithAxe.LOG.info("That's not dangerous, it's intentional!");
		} catch (Exception e1) {
			HitWithAxe.LOG.error("Failed to inject clone rubber log block into Betweelands registry!");
		}
	}

	public static void initModelsBL() {
		PlaceboUtil.sMRL("thebetweenlands", "log_rubber", BlockRegistry.LOG_RUBBER, 0, "inventory");
	}

}
