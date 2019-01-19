package shadows.hitwithaxe.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import shadows.hitwithaxe.HitWithAxe;
import shadows.hitwithaxe.TransformRecipe;

public class ChoppingWrapper implements IRecipeWrapper {

	protected final TransformRecipe rec;

	public ChoppingWrapper(TransformRecipe recipe) {
		rec = recipe;
	}

	@Override
	public void getIngredients(IIngredients ig) {
		rec.addIngredients(ig);
	}

	@Override
	public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		mc.fontRenderer.drawString(I18n.format("jei." + HitWithAxe.MODID + ".harvestlevel", rec.getHarvestLevel()), 16, -1, 0);
	}

}
