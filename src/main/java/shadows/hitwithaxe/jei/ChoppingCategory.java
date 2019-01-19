package shadows.hitwithaxe.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shadows.hitwithaxe.HitWithAxe;

public class ChoppingCategory implements IRecipeCategory<ChoppingWrapper> {

	private final IDrawable background;
	private static final ResourceLocation TEX = new ResourceLocation(HitWithAxe.MODID, "textures/gui/jei_recipe.png");

	public ChoppingCategory(IGuiHelper guiHelper) {
		background = guiHelper.createDrawable(TEX, 0, 60, 116, 54);
	}

	@Override
	public String getUid() {
		return HWAPlugin.C_UID;
	}

	@Override
	public String getTitle() {
		return I18n.format("jei." + HitWithAxe.MODID + ".chopping.name");
	}

	@Override
	public String getModName() {
		return HitWithAxe.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout layout, ChoppingWrapper wrapper, IIngredients ingredients) {
		IGuiIngredientGroup<ItemStack> stacks = layout.getIngredientsGroup(VanillaTypes.ITEM);

		stacks.init(0, false, 72, 8);
		stacks.init(1, false, 72, 26);
		stacks.set(0, wrapper.rec.getDisplayOut().get(0));
		stacks.set(1, wrapper.rec.getDisplayOut().get(1));

		List<ItemStack> inputs = ingredients.getInputs(VanillaTypes.ITEM).get(0);
		stacks.init(2, true, 18, 18);
		stacks.set(2, inputs);
	}

}
