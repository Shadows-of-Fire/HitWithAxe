package shadows.hitwithaxe;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import shadows.placebo.item.ItemBigEnum;
import shadows.placebo.registry.RegistryInformation;

public class ItemBark extends ItemBigEnum<EnumBarkType> {

	public ItemBark(String name, EnumBarkType[] values, RegistryInformation info) {
		super(name, values, info);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) for (EnumBarkType e : values)
			if (Loader.isModLoaded(e.getReqMod())) items.add(new ItemStack(this, 1, e.ordinal()));
	}

}
