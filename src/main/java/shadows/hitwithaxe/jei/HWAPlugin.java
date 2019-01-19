package shadows.hitwithaxe.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.TransformRecipe;

@JEIPlugin
public class HWAPlugin implements IModPlugin {

	public static final String C_UID = "hitwithaxe.chopping";

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {
		reg.addRecipeCategories(new ChoppingCategory(reg.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void register(IModRegistry reg) {
		reg.handleRecipes(TransformRecipe.class, ChoppingWrapper::new, C_UID);
		reg.addRecipeCatalyst(new ItemStack(Items.DIAMOND_AXE), C_UID);
		List<TransformRecipe> noCopies = new ArrayList<>();
		for (TransformRecipe r : HitWithAxe.RECIPES) {
			if (noCopies.stream().noneMatch(recipe -> recipe.getFirstDisplayIn().isItemEqual(r.getFirstDisplayIn()))) noCopies.add(r);
		}
		reg.addRecipes(noCopies, C_UID);
	}
}
