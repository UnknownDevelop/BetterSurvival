package com.bettersurvival.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.gui.container.ContainerBlueprintTable;
import com.bettersurvival.network.PacketAddBlueprint;
import com.bettersurvival.network.PacketBlueprintAbort;
import com.bettersurvival.network.PacketCraftBlueprint;
import com.bettersurvival.network.PacketOpenBlueprintChest;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tileentity.TileEntityBlueprintTable;
import com.bettersurvival.util.BlueprintStorage;
import com.bettersurvival.util.MathUtility;
import com.bettersurvival.util.Size;

import cpw.mods.fml.common.registry.GameRegistry;

public class GuiBlueprintTable extends GuiContainer 
{
	public static final ResourceLocation textureMain = new ResourceLocation("bettersurvival:textures/gui/blueprint_table.png");
	
	public TileEntityBlueprintTable tileentity;
	
	private Size rectAddBlueprint = new Size(27, 92, 0, 44, 108, 0);
	private Size rectChestTab = new Size(28, -26, 0, 56, 0, 0);
	private Size rectCraft = new Size(89, 17, 0, 133, 35, 0);
	
	public BlueprintScollList scrollList = new BlueprintScollList();

	private final Entity blueprintTableTab;
	private final Entity chestTab;
	
	public static TileEntityBlueprintTable lastTileEntity;
	
	private int currentIndex = 0;
	
	private BlueprintStorage current;
	private boolean canCraftCurrent = false;
	
	private Entity currentEntity;
	
	private float roll = 0f;
	private float yaw = 0f;
	private float lastRoll = 0f;
	private float lastYaw = 0f;
	private boolean rollDown = false;
	
	public GuiBlueprintTable(InventoryPlayer inventory, TileEntityBlueprintTable tileentity) 
	{
		super(new ContainerBlueprintTable(inventory, tileentity));
		
		this.tileentity = tileentity;
		
		this.xSize = 176;
		this.ySize = 201;
		
		blueprintTableTab = new EntityFallingBlock(ClientProxy.INSTANCE.getWorld(), 0, 0, 0, BetterSurvival.blockBlueprintTable);
		chestTab = new EntityFallingBlock(ClientProxy.INSTANCE.getWorld(), 0, 0, 0, Blocks.crafting_table);
		
		lastTileEntity = tileentity;
		
		clickIndex(tileentity.clientClickedElement);
		scrollList.setScrollIndex(tileentity.clientScrollIndex);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton)
    {
    	super.mouseClicked(x, y, mouseButton);
    	
    	x -= guiLeft;
    	y -= guiTop;
    	
    	scrollList.mouseClicked(x, y, mouseButton);
    	
    	if(rectAddBlueprint.isInRect2D(x, y) && tileentity.getStackInSlot(0) != null)
    	{
    		BetterSurvival.network.sendToServer(new PacketAddBlueprint(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
    	}
    	
    	if(rectChestTab.isInRect2D(x, y) && tileentity.getChest() != null)
    	{
    		BetterSurvival.network.sendToServer(new PacketOpenBlueprintChest(tileentity.getChest().xCoord, tileentity.getChest().yCoord, tileentity.getChest().zCoord));
    	}
    	
    	if(mouseButton == 0)
    	{
	    	if(rectCraft.isInRect2D(x, y) && tileentity.getChest() != null)
	    	{
	    		if(current != null)
	    		{	
		    		BetterSurvival.network.sendToServer(new PacketCraftBlueprint(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord, GameRegistry.findUniqueIdentifierFor(current.getItem()).toString(), current.getItem() instanceof ItemBlock, current.getDamage()));
	    		}
	    	}
    	}
    	else if(mouseButton == 1)
    	{
	    	if(rectCraft.isInRect2D(x, y) && tileentity.getChest() != null)
	    	{
	    		if(current != null)
	    		{	
		    		BetterSurvival.network.sendToServer(new PacketBlueprintAbort(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord));
	    		}
	    	}
    	}
    }
	
	@Override
	protected void mouseClickMove(int x, int y, int mouseButton, long timeSinceClick)
	{
		super.mouseClickMove(x, y, mouseButton, timeSinceClick);
    	x -= guiLeft;
    	y -= guiTop;
    	
    	scrollList.mouseClickMove(x, y, mouseButton, timeSinceClick);
	}
	
    @Override
	protected void mouseMovedOrUp(int x, int y, int which)
    {
    	super.mouseMovedOrUp(x, y, which);
    	x -= guiLeft;
    	y -= guiTop;
    	scrollList.mouseMovedOrUp(x, y, which);
    }
	
	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.blueprint_table"), this.xSize / 2 - this.fontRendererObj.getStringWidth(StatCollector.translateToLocal("container.blueprint_table")) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 27, this.ySize-96+4, 4210752);
		
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		
        if(tileentity.craftingProgress > 0)
        {
	    	GL11.glPushMatrix();
	    	GL11.glScalef(0.8f, 0.8f, 0.8f);
	    	fontRendererObj.drawString(StatCollector.translateToLocal("gui.blueprint_table.crafting"), 137-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.blueprint_table.crafting"))/2, 27, 4210752);
	    	GL11.glPopMatrix();
        }
        else
        {
        	if(canCraftCurrent)
        	{
		    	GL11.glPushMatrix();
		    	GL11.glScalef(0.8f, 0.8f, 0.8f);
		    	fontRendererObj.drawString(StatCollector.translateToLocal("gui.blueprint_table.craft"), 137-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.blueprint_table.craft"))/2, 27, 4210752);
		    	GL11.glPopMatrix();
        	}
        }
        
        List<BlueprintStorage> storages = GuiBlueprintTable.this.tileentity.getBlueprints();
		
		if(storages.size() > 0)
		{
			BlueprintStorage storage = storages.get(currentIndex);
			
			if(storage != null)
			{
				GL11.glPushMatrix();
				GL11.glScalef(0.5f, 0.5f, 0.5f);
				
				Object[] ingredients = tileentity.checkForIngredientAvailability(storage.getItem(), storage.getDamage());
				
				if(ingredients != null)
				{
					if((Boolean)ingredients[0])
					{
						this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.blueprint_table.ingredients.available"), 340-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.blueprint_table.ingredients.available")), 105, 4210752);
					}
					else
					{
						this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.blueprint_table.ingredients.not_available"), 340-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.blueprint_table.ingredients.not_available")), 105, 4210752);
					}
					
					canCraftCurrent = (Boolean)ingredients[0];
					
					this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.blueprint_table.required_ingredients"), 340-fontRendererObj.getStringWidth(StatCollector.translateToLocal("gui.blueprint_table.required_ingredients")), 115, 4210752);
					
					for(int m = 1; m < ingredients.length; m += 2)
					{
						ItemStack stack = (ItemStack)ingredients[m];
						boolean available = (Boolean)ingredients[m+1];
						
						String draw = String.format("%sx %s", stack.stackSize, StatCollector.translateToLocal(stack.getItem().getUnlocalizedName(stack)+".name"));
						this.fontRendererObj.drawString(draw, 340-fontRendererObj.getStringWidth(draw), 125+((m-1)/2)*10, available ? 16134 : 16711680);
					}
				}
				
				GL11.glPopMatrix();
			}
			
			current = storage;
		}
		
		if(currentEntity != null)
		{
			GL11.glPushMatrix();
			
			float scale = 17f;
			
			if(currentEntity instanceof EntityItem)
			{
				scale = 50f;
				
				GL11.glTranslatef(151, 45-((EntityItem)currentEntity).hoverStart, 100);
			}
			else
			{
				GL11.glTranslatef(151, 32, 100);
			}
			
			GL11.glScalef(-scale, scale, scale);
			
			GL11.glRotatef(180, 0, 0, 1);
			float usedRoll = MathUtility.lerp(lastRoll, roll, 0.8f);
			float usedYaw = MathUtility.lerp(lastYaw, yaw, 0.8f);
			GL11.glRotatef(usedRoll, 1, 0, 0);
			GL11.glRotatef(usedYaw, 0, 1, 0);
			lastRoll = usedRoll;
			lastYaw = usedYaw;
			
			RenderManager.instance.renderEntityWithPosYaw(currentEntity, 0, 0, 0, 0, 0);
			
			GL11.glPopMatrix();
		}
	}
	
	public void tickRotation()
	{
		yaw += 1f;
		
		if(rollDown)
		{
			roll -= 0.1f;
			
			if(roll < -5f)
			{
				rollDown = false;
				roll = -5f;
			}
		}
		else
		{
			roll += 0.1f;
			
			if(roll > 25f)
			{
				rollDown = false;
				roll = 25f;
			}
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) 
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(textureMain);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        int i1 = this.tileentity.getEnergyScaled(17);
        this.drawTexturedModalRect(k + 83, l + 93 + 17 - i1, 176, 145 + 17 - i1, 10, i1);
        
        if(tileentity.getStackInSlot(0) != null)
        {
        	this.drawTexturedModalRect(k+26, l+91, 176, 162, 18, 17);
        }
        
        if(tileentity.getChest() != null)
        {
        	drawTab(k, l+4-32, 0);
            this.mc.getTextureManager().bindTexture(textureMain);
        	drawTab(k+28, l+4-27, 1);
            this.mc.getTextureManager().bindTexture(textureMain);
            this.drawTexturedModalRect(k+88, l+16, 194, 145, 44, 18);
            
            if(tileentity.craftingProgress > 0)
            {
	            int progress = tileentity.getProgressScaled(42);
	            
	            if(progress > 0)
	            {
	            	if(!tileentity.craftAll)
	            	{
	            		this.drawTexturedModalRect(k+89, l+17, 176, 106, progress, 16);
	            	}
	            	else
	            	{
	            		this.drawTexturedModalRect(k+89, l+17, 176, 179, progress, 16);
	            	}
	            }
            }
    	}
                
        scrollList.render();
		scrollList.postRender();
	}
	
	public void drawTab(int x, int y, int type)
	{
		if(type == 0)
		{
			this.drawTexturedModalRect(x, y, 176, 15, 28, 31);
			
			float scale = 14f;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(x+14, y+15, 100);
			
			GL11.glScalef(-scale, scale, scale);
			
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(20, 0, 1, 0);
			GL11.glRotatef(20, 1, 0, 0);
			
			RenderHelper.enableStandardItemLighting();
			RenderManager.instance.renderEntityWithPosYaw(blueprintTableTab, 0, 0, 0, 0, 0);
			RenderHelper.disableStandardItemLighting();
			
			GL11.glPopMatrix();
		}
		else if(type == 1)
		{
			this.drawTexturedModalRect(x, y, 205, 79, 28, 26);
			
			float scale = 12f;
			
			GL11.glPushMatrix();
			
			GL11.glTranslatef(x+13, y+13, 100);
			
			GL11.glScalef(-scale, scale, scale);
			
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glRotatef(20, 0, 1, 0);
			GL11.glRotatef(20, 1, 0, 0);
			
			RenderHelper.enableStandardItemLighting();
			RenderManager.instance.renderEntityWithPosYaw(chestTab, 0, 0, 0, 0, 0);
			RenderHelper.disableStandardItemLighting();
			
			GL11.glPopMatrix();
		}
	}
	
	public void clickIndex(int index)
	{
		currentIndex = index;
		tileentity.clientClickedElement = index;
		
        List<BlueprintStorage> storages = GuiBlueprintTable.this.tileentity.getBlueprints();
		
		if(storages.size() > 0)
		{
			BlueprintStorage storage = storages.get(currentIndex);
			
			if(storage.block())
			{
				currentEntity = new EntityFallingBlock(tileentity.getWorldObj(), 0, 0, 0, storage.getBlock(), 0);
			}
			else
			{
				currentEntity = new EntityItem(tileentity.getWorldObj(), 0, 0, 0, new ItemStack(storage.getItem()));
			}
		}
	}
	
	public class BlueprintScollList
	{
		private int scrollIndex = 0;
		private final int maxIndexesPerPage = 4;
		
		private int[] longNameScrollIndex = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		private final int maxNameLengthWithoutScroll = 10;
		
		private Size[] listRects = {
				new Size(9, 18, 0, 69, 35, 0),
				new Size(9, 36, 0, 69, 53, 0),
				new Size(9, 54, 0, 69, 71, 0),
				new Size(9, 72, 0, 69, 89, 0)
		};
		
		private boolean isMouseHeld = false;
		private int scrollY = 0;
		private int scrollDistance = 0;
		
		private int mouseX = 0, mouseY = 0;
		
		public void setScrollIndex(int index)
		{
			scrollIndex = index;
		}
		
		public void render()
		{
			List<BlueprintStorage> storages = GuiBlueprintTable.this.tileentity.getBlueprints();
			
	        int k = (GuiBlueprintTable.this.width - GuiBlueprintTable.this.xSize) / 2;
	        int l = (GuiBlueprintTable.this.height - GuiBlueprintTable.this.ySize) / 2;
			
	        int j = 0;
			for(int i = scrollIndex; i < scrollIndex+maxIndexesPerPage; i++)
			{
				if(i >= storages.size()) break;
				GuiBlueprintTable.this.drawTexturedModalRect(k+8, l+17+j*18, 176, 127, 61, 18);
				j++;
			}
			
			if(storages.size() > maxIndexesPerPage)
			{
				GuiBlueprintTable.this.drawTexturedModalRect(k+72, l+17+getScrollStateScaled(72-15), 176, 0, 12, 15);
			}
			else
			{
				GuiBlueprintTable.this.drawTexturedModalRect(k+72, l+17, 188, 0, 12, 15);
			}
			
	        mouseX = Mouse.getEventX() * GuiBlueprintTable.this.width / GuiBlueprintTable.this.mc.displayWidth - guiLeft;
	        mouseY = GuiBlueprintTable.this.height - Mouse.getEventY() * GuiBlueprintTable.this.height / GuiBlueprintTable.this.mc.displayHeight - 1 - guiTop;
		}
		
		public int getScrollStateScaled(int scale) 
		{
			return scrollIndex*scale/(GuiBlueprintTable.this.tileentity.getBlueprints().size()-maxIndexesPerPage);
		}
		
		public void postRender()
		{
			List<BlueprintStorage> storages = GuiBlueprintTable.this.tileentity.getBlueprints();
			
	        int k = (GuiBlueprintTable.this.width - GuiBlueprintTable.this.xSize) / 2;
	        int l = (GuiBlueprintTable.this.height - GuiBlueprintTable.this.ySize) / 2;

	        int j = 0;
			for(int i = scrollIndex; i < scrollIndex+maxIndexesPerPage; i++)
			{
				if(i >= storages.size()) break;
				
				String name = storages.get(i).getDisplayName();
				
				longNameScrollIndex[j+8] = name.length();
				
				if(name.length() <= maxNameLengthWithoutScroll)
				{
					GuiBlueprintTable.this.fontRendererObj.drawString(name, k+8+3, l+17+j*18+1+GuiBlueprintTable.this.fontRendererObj.FONT_HEIGHT/2, 4210752);
				}
				else
				{
					int nameIndex = longNameScrollIndex[j];
					
					int index = nameIndex+maxNameLengthWithoutScroll;
					
					if(index > name.length()) index = name.length()-1;
					if(index < 0) index = 0;
					
					String substring = name.substring(nameIndex, index);
					
					GuiBlueprintTable.this.fontRendererObj.drawString(substring, k+8+3, l+17+j*18+1+GuiBlueprintTable.this.fontRendererObj.FONT_HEIGHT/2, 4210752);
				}
				
				if(listRects[j].isInRect2D(mouseX, mouseY))
				{
					List<String> texts = new ArrayList<String>();
					texts.add(name);
					
					GuiBlueprintTable.this.drawHoveringText(texts, k+mouseX, l+mouseY+10, GuiBlueprintTable.this.fontRendererObj);
				}
				
				j++;
			}
		}
		
		public void mouseClicked(int x, int y, int mouseButton)
		{
			for(int i = 0; i < listRects.length; i++)
			{
				if(listRects[i].isInRect2D(x, y))
				{
					if(i+scrollIndex < GuiBlueprintTable.this.tileentity.getBlueprints().size())
					{
						GuiBlueprintTable.this.clickIndex(scrollIndex+i);
					}
					break;
				}
			}
			
			if(x > 72 && x < 85 && y > 17 && y < 90)
			{
				if(!isMouseHeld)
				{
					isMouseHeld = true;
					scrollY = y;
				}
			}
			
			if(isMouseHeld)
			{
				if(GuiBlueprintTable.this.tileentity.getBlueprints().size() > maxIndexesPerPage)
				{
					scrollTo(scrollY, y);
				}
			}
		}
		
		public void mouseClickMove(int x, int y, int mouseButton, long timeSinceClick)
		{
			if(!isMouseHeld)
			{
				if(x > 72 && x < 85 && y > 17 && y < 90)
				{
					isMouseHeld = true;
					scrollY = y;
				}
			}
			
			if(isMouseHeld)
			{
				if(GuiBlueprintTable.this.tileentity.getBlueprints().size() > maxIndexesPerPage)
				{
					scrollTo(scrollY, y);
				}
			}
		}
		
		public void mouseMovedOrUp(int x, int y, int which)
	    {
			if(which == 0)
			{
				isMouseHeld = false;
			}
	    }
		
		private void scrollTo(int oldY, int newY)
		{
			int distanceMouse = newY - oldY;
			scrollY = newY;
			scrollDistance += distanceMouse;
			
			if(scrollDistance > 72)
			{
				scrollDistance = 72;
			}
			else if(scrollDistance < 0)
			{
				scrollDistance = 0;
			}
			
			scrollIndex = scrollDistance*(GuiBlueprintTable.this.tileentity.getBlueprints().size()-maxIndexesPerPage)/72;
		
			GuiBlueprintTable.this.tileentity.clientScrollIndex = scrollIndex;
			
			for(int i = 0; i < longNameScrollIndex.length; i++)
			{
				longNameScrollIndex[i] = 0;
			}
		}
		
		public void onTick()
		{
			for(int i = 0; i < 4; i++)
			{
				if(longNameScrollIndex[i+8] <= maxNameLengthWithoutScroll)
				{
					longNameScrollIndex[i+4] = 0;
					continue;
				}
				
				longNameScrollIndex[i+4]++;
				
				if(longNameScrollIndex[i+4]%20 == 0)
				{
					if(longNameScrollIndex[i] == longNameScrollIndex[i+8]-maxNameLengthWithoutScroll)
					{
						longNameScrollIndex[i+12] = 0;
					}
					else if(longNameScrollIndex[i] == 0)
					{
						longNameScrollIndex[i+12] = 1;
					}
					
					if(longNameScrollIndex[i+12] == 1)
					{
						longNameScrollIndex[i]++;
					}
					else
					{
						longNameScrollIndex[i]--;
					}
					
					longNameScrollIndex[i+4] = 0;
				}
			}
		}
	}
}
