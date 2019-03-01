package shadows.hitwithaxe.client;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.compat.CompatBetweenlands;
import shadows.placebo.client.IHasModel;

@EventBusSubscriber(modid = HitWithAxe.MODID, value = Side.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void initModels(ModelRegistryEvent e) {
		for (Block b : HitWithAxe.INFO.getBlockList())
			if (b instanceof IHasModel) ((IHasModel) b).initModels(e);
		for (Item i : HitWithAxe.INFO.getItemList())
			if (i instanceof IHasModel) ((IHasModel) i).initModels(e);
		if (Loader.isModLoaded(HitWithAxe.BETWEENLANDS)) CompatBetweenlands.initModelsBL();
	}
}
