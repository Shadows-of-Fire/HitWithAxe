package shadows.hitwithaxe.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.compat.CompatBetweenlands;
import shadows.placebo.client.IHasModel;

public class ClientProxy extends Proxy {

	@Override
	@SubscribeEvent
	public void initModels(ModelRegistryEvent e) {
		for (Block b : HitWithAxe.INFO.getBlockList())
			if (b instanceof IHasModel) ((IHasModel) b).initModels(e);
		for (Item i : HitWithAxe.INFO.getItemList())
			if (i instanceof IHasModel) ((IHasModel) i).initModels(e);
		if (Loader.isModLoaded(HitWithAxe.BETWEENLANDS)) CompatBetweenlands.initModelsBL();
	}
}
