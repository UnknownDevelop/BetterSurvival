package com.bettersurvival.integration.nei.recipe;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.DefaultOverlayRenderer;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.api.IStackPositioner;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.bettersurvival.crafting.CrusherRecipe;
import com.bettersurvival.gui.GuiCrusher;
import com.bettersurvival.registry.CrusherRegistry;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEICrusherRecipeHandler extends TemplateRecipeHandler 
{
	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("integration.nei.crafting.crusher");
	}

	@Override
	public String getGuiTexture() 
	{
		return new ResourceLocation("bettersurvival:textures/gui/crusher.png").toString();
	}
	
    public class CachedCrusherRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedCrusherRecipe(ItemStack in, ItemStack out) 
        {
            result = new PositionedStack(out, 98, 17);
            ingredients = new ArrayList<PositionedStack>();
            
            PositionedStack stack = new PositionedStack(in, 42, 17);
            stack.setMaxSize(1);
            ingredients.add(stack);
        }

        public CachedCrusherRecipe(CrusherRecipe recipe) 
        {
            this(new ItemStack(recipe.getRequiredItem(), recipe.getRequiredItemAmount()), new ItemStack(recipe.getResultItem(), recipe.getResultAmount()));
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
        transferRects.add(new RecipeTransferRect(new Rectangle(70, 29, 22, 15), "crafting_bs_crusher"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiCrusher.class;
    }
    
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) 
    {
        if (outputId.equals("crafting_bs_crusher") && getClass() == NEICrusherRecipeHandler.class) 
        {
            for (CrusherRecipe irecipe : CrusherRegistry.getAllRecipes()) 
            {
                CachedCrusherRecipe recipe = null;
                recipe = new CachedCrusherRecipe(irecipe);

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else 
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

	@Override
    public void loadCraftingRecipes(ItemStack result) 
    {
        for (CrusherRecipe irecipe : CrusherRegistry.getAllRecipes())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(new ItemStack(irecipe.getResultItem(), irecipe.getResultAmount()), result)) 
            {
                CachedCrusherRecipe recipe = null;
                recipe = new CachedCrusherRecipe(irecipe);

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

	@Override
    public void loadUsageRecipes(ItemStack ingredient) 
    {
        for (CrusherRecipe irecipe : CrusherRegistry.getAllRecipes()) 
        {
        	CachedCrusherRecipe recipe = null;
                recipe = new CachedCrusherRecipe(irecipe);
                
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

    @Override
    public String getOverlayIdentifier()
    {
        return "crafting_bs_crusher";
    }

    @Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return super.hasOverlay(gui, container, recipe) ||
                RecipeInfo.hasDefaultOverlay(gui, "crafting_bs_crusher");
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) 
    {
        IRecipeOverlayRenderer renderer = super.getOverlayRenderer(gui, recipe);
        if (renderer != null)
            return renderer;

        IStackPositioner positioner = RecipeInfo.getStackPositioner(gui, "crafting_bs_crusher");
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

        return RecipeInfo.getOverlayHandler(gui, "crafting_bs_crusher");
    }
    
    @Override
    public int recipiesPerPage() 
    {
        return 2;
    }
    
    @Override
	public void drawBackground(int recipe) 
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 121, 48);
    }
    
    @Override
    public void drawExtras(int recipe) 
    {
        drawProgressBar(63, 16, 176, 53, 24, 17, 50, 0);
    }
}
