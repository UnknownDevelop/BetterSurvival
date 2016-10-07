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

import com.bettersurvival.crafting.CableRollingMachineRecipe;
import com.bettersurvival.gui.GuiCableRollingMachine;
import com.bettersurvival.registry.CableRollingMachineRegistry;

import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "codechicken.nei.api.API", modid = "NotEnoughItems")
public class NEICableRollingMachineRecipeHandler extends TemplateRecipeHandler 
{
	@Override
	public String getRecipeName()
	{
		return StatCollector.translateToLocal("integration.nei.crafting.cablerollingmachine");
	}

	@Override
	public String getGuiTexture() 
	{
		return new ResourceLocation("bettersurvival:textures/gui/cable_rolling_machine.png").toString();
	}
	
    public class CachedCRMRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedCRMRecipe(ItemStack[] in, ItemStack out) 
        {
            result = new PositionedStack(out, 110, 22);
            ingredients = new ArrayList<PositionedStack>();
            
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
		            PositionedStack stack = new PositionedStack(in[i+j*3], i*18+16, j*18+4);
		            stack.setMaxSize(1);
		            ingredients.add(stack);
                }
            }
        }

        public CachedCRMRecipe(CableRollingMachineRecipe recipe) 
        {
            this(recipe.getInputItemStacks(), recipe.getOutcome().copy());
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
        transferRects.add(new RecipeTransferRect(new Rectangle(83, 35, 22, 15), "crafting_bs_cablerollingmachine"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiCableRollingMachine.class;
    }
    
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) 
    {
        if (outputId.equals("crafting_bs_cablerollingmachine") && getClass() == NEICableRollingMachineRecipeHandler.class) 
        {
            for (CableRollingMachineRecipe irecipe : CableRollingMachineRegistry.getAllRecipes()) 
            {
            	CachedCRMRecipe recipe = null;
                recipe = new CachedCRMRecipe(irecipe);

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
        for (CableRollingMachineRecipe irecipe : CableRollingMachineRegistry.getAllRecipes())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getOutcome(), result)) 
            {
            	CachedCRMRecipe recipe = null;
                recipe = new CachedCRMRecipe(irecipe);

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

	@Override
    public void loadUsageRecipes(ItemStack ingredient) 
    {
        for (CableRollingMachineRecipe irecipe : CableRollingMachineRegistry.getAllRecipes()) 
        {
        	CachedCRMRecipe recipe = null;
                recipe = new CachedCRMRecipe(irecipe);
                
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
        return "crafting_bs_cablerollingmachine";
    }

    @Override
	public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return super.hasOverlay(gui, container, recipe) ||
                RecipeInfo.hasDefaultOverlay(gui, "crafting_bs_cablerollingmachine");
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) 
    {
        IRecipeOverlayRenderer renderer = super.getOverlayRenderer(gui, recipe);
        if (renderer != null)
            return renderer;

        IStackPositioner positioner = RecipeInfo.getStackPositioner(gui, "crafting_bs_cablerollingmachine");
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

        return RecipeInfo.getOverlayHandler(gui, "crafting_bs_cablerollingmachine");
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
        drawTexturedModalRect(10, 0, 20, 11, 130, 72);
    }
    
    @Override
    public void drawExtras(int recipe) 
    {
        drawProgressBar(74, 21, 176, 0, 27, 17, 140, 0);
        drawTexturedModalRect(77, 42, 176, 18, 27, 18);
        drawTexturedModalRect(110, 44, 81, 18, 18, 18);
    }
}
