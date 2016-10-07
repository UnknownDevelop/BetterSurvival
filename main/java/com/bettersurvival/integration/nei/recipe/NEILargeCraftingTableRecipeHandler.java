package com.bettersurvival.integration.nei.recipe;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.DefaultOverlayRenderer;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.api.IStackPositioner;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.bettersurvival.crafting.LargeCraftingTableManager;
import com.bettersurvival.crafting.LargeCraftingTableShapedRecipes;
import com.bettersurvival.gui.GuiLargeCraftingTable;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEILargeCraftingTableRecipeHandler extends TemplateRecipeHandler 
{
	RecipeTransferRect rect;
	
	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("integration.nei.crafting.large_crafting_table.shaped");
	}

	@Override
	public String getGuiTexture() 
	{
		return new ResourceLocation("bettersurvival:textures/gui/large_crafting_table.png").toString();
	}
	
    public class CachedShapedRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedShapedRecipe(int width, int height, Object[] items, ItemStack out) 
        {
            result = new PositionedStack(out, 136, 40);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(width, height, items);
        }

        public CachedShapedRecipe(LargeCraftingTableShapedRecipes recipe) 
        {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        /**
         * @param width
         * @param height
         * @param items  an ItemStack[] or ItemStack[][]
         */
        public void setIngredients(int width, int height, Object[] items)
        {
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++) 
                {
                    if (items[y * width + x] == null)
                        continue;

                    PositionedStack stack = new PositionedStack(items[y * width + x], 7 + x * 18, 4 + y * 18, false);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
		public PositionedStack getResult()
        {
            return result;
        }
        
        public void computeVisuals() 
        {
            for (PositionedStack p : ingredients)
                p.generatePermutations();
        }
    }

    @Override
    public void loadTransferRects() 
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(108, 52, 22, 15), "crafting_bs_5x5"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiLargeCraftingTable.class;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) 
    {
        if (outputId.equals("crafting_bs_5x5") && getClass() == NEILargeCraftingTableRecipeHandler.class) 
        {
            for (IRecipe irecipe : (List<IRecipe>) LargeCraftingTableManager.getInstance().getRecipeList()) 
            {
                CachedShapedRecipe recipe = null;
                if (irecipe instanceof LargeCraftingTableShapedRecipes)
                    recipe = new CachedShapedRecipe((LargeCraftingTableShapedRecipes) irecipe);
                else if (irecipe instanceof ShapedOreRecipe)
                    recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);

                if (recipe == null)
                    continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else 
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(ItemStack result) 
    {
        for (IRecipe irecipe : (List<IRecipe>) LargeCraftingTableManager.getInstance().getRecipeList())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) 
            {
                CachedShapedRecipe recipe = null;
                if (irecipe instanceof LargeCraftingTableShapedRecipes)
                    recipe = new CachedShapedRecipe((LargeCraftingTableShapedRecipes) irecipe);
                else if (irecipe instanceof ShapedOreRecipe)
                    recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);

                if (recipe == null)
                    continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadUsageRecipes(ItemStack ingredient) 
    {
        for (IRecipe irecipe : (List<IRecipe>) LargeCraftingTableManager.getInstance().getRecipeList()) 
        {
            CachedShapedRecipe recipe = null;
            if (irecipe instanceof LargeCraftingTableShapedRecipes)
                recipe = new CachedShapedRecipe((LargeCraftingTableShapedRecipes) irecipe);
            else if (irecipe instanceof ShapedOreRecipe)
                recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);

            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem()))
                continue;

            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) 
            {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }
    
    public CachedShapedRecipe forgeShapedRecipe(ShapedOreRecipe recipe)
    {
        try
        {
            int width = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 4);
            int height = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 5);

            Object[] items = recipe.getInput();
            for (Object item : items)
                if (item instanceof List && ((List<?>) item).isEmpty())//ore handler, no ores
                    return null;

            return new CachedShapedRecipe(width, height, items, recipe.getRecipeOutput());
        } 
        catch (Exception e)
        {
            NEIClientConfig.logger.error("Error loading recipe: ", e);
            return null;
        }
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "crafting_bs_5x5";
    }

    @Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return super.hasOverlay(gui, container, recipe) ||
                isRecipe5x5(recipe) && RecipeInfo.hasDefaultOverlay(gui, "crafting_bs_5x5");
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) 
    {
        IRecipeOverlayRenderer renderer = super.getOverlayRenderer(gui, recipe);
        if (renderer != null)
            return renderer;

        IStackPositioner positioner = RecipeInfo.getStackPositioner(gui, "crafting_bs_5x5");
        if (positioner == null)
            return null;
        return new DefaultOverlayRenderer(getIngredientStacks(recipe), positioner);
    }

    @Override
    public IOverlayHandler getOverlayHandler(GuiContainer gui, int recipe) 
    {
        IOverlayHandler handler = super.getOverlayHandler(gui, recipe);
        if (handler != null)
            return handler;

        return RecipeInfo.getOverlayHandler(gui, "crafting_bs_5x5");
    }

    public boolean isRecipe5x5(int recipe) 
    {
        for (PositionedStack stack : getIngredientStacks(recipe))
            if (stack.relx > 11 || stack.rely > 14)
                return false;

        return true;
    }
    
    @Override
    public int recipiesPerPage() 
    {
        return 1;
    }
    
    @Override
	public void drawBackground(int recipe) 
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 95);
    }
}
