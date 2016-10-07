package com.bettersurvival.tribe.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bettersurvival.BetterSurvival;
import com.bettersurvival.proxy.ClientProxy;
import com.bettersurvival.tribe.client.TribeInfo;
import com.bettersurvival.tribe.client.TribeInvite;
import com.bettersurvival.tribe.client.TribeInviteAccepted;
import com.bettersurvival.tribe.client.TribeInviteDenied;
import com.bettersurvival.tribe.network.PacketAcceptInvite;
import com.bettersurvival.tribe.network.PacketDenyInvite;
import com.bettersurvival.util.KeyBindings;
import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiTribeCreated extends Gui
{
	private Minecraft mc;
	
	ArrayList<TribeInfo> queue = new ArrayList<TribeInfo>();
	ArrayList<TribeInvite> inviteQueue = new ArrayList<TribeInvite>();
	ArrayList<TribeInviteDenied> denyQueue = new ArrayList<TribeInviteDenied>();
	ArrayList<TribeInviteAccepted> acceptQueue = new ArrayList<TribeInviteAccepted>();
	
	public static GuiTribeCreated INSTANCE;
	public int ticks = 0;
	public int ticksInvite = 0;
	public int ticksDeny = 0;
	public int ticksAccept = 0;
	public final int displayTribe = 5;
	public final int displayJoin = 5;
	public final int displayDeny = 5;
	public final int displayAccept = 5;
	private int width = 175;
	private int height = 45;
	
	public GuiTribeCreated()
	{
		super();
	    
		INSTANCE = this;
		
		// We need this to invoke the render engine.
		this.mc = Minecraft.getMinecraft();
	}
	
	@SuppressWarnings("unchecked")
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRender(RenderGameOverlayEvent event)
	{
	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE)
	    {      
	      return;
	    }
	    
	    int lastY = 0;
	    
	    if(queue.size() > 0)
	    {
	    	if(ticks < displayTribe*20)
	    	{
		    	TribeInfo info = queue.get(0);
		    	
		    	List list = new ArrayList();
		  
		    	list.add(ChatFormatting.AQUA + StatCollector.translateToLocal("gui.tribe.overlay.newtribe"));
		    	list.add(ChatFormatting.BLUE + StatCollector.translateToLocal("gui.tribe.overlay.newtribe.owner") + ChatFormatting.ITALIC + ChatFormatting.GOLD + info.ownerName());
		    	list.add(ChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("gui.tribe.overlay.newtribe.name") + ChatFormatting.ITALIC + info.name() + ChatFormatting.RESET);
		        
		    	lastY = drawHoveringText(list, 0, lastY+16, mc.fontRenderer);
	    	}
	    	else
	    	{
	    		queue.remove(0);
	    		ticks = 0;
	    	}
	    }
	    
	    if(acceptQueue.size() > 0)
	    {
	    	if(ticksAccept < displayAccept*20)
	    	{
		    	TribeInviteAccepted accept = acceptQueue.get(0);
		    	
		    	List list = new ArrayList();

		    	list.add(ChatFormatting.GOLD + accept.from());
		    	list.add(ChatFormatting.AQUA + StatCollector.translateToLocal("gui.tribe.overlay.accepted"));
		    	
		    	lastY = drawHoveringText(list, 0, lastY+16, mc.fontRenderer);
	    	}
	    	else
	    	{
	    		acceptQueue.remove(0);
	    		ticksAccept = 0;
	    	}
	    }
	    
	    if(denyQueue.size() > 0)
	    {
	    	if(ticksDeny < displayDeny*20)
	    	{
		    	TribeInviteDenied deny = denyQueue.get(0);
		    	
		    	List list = new ArrayList();

		    	list.add(ChatFormatting.GOLD + deny.from());
		    	list.add(ChatFormatting.AQUA + StatCollector.translateToLocal("gui.tribe.overlay.deny"));
		    	
		    	lastY = drawHoveringText(list, 0, lastY+16, mc.fontRenderer);
	    	}
	    	else
	    	{
	    		denyQueue.remove(0);
	    		ticksDeny = 0;
	    	}
	    }
	    
	    for(int i = 0; i < inviteQueue.size(); i++)
	    {
	    	List list = new ArrayList();

	    	list.add(ChatFormatting.AQUA + StatCollector.translateToLocal("gui.tribe.overlay.invited"));
	    	list.add(ChatFormatting.GOLD + inviteQueue.get(i).from());
	    	list.add(ChatFormatting.AQUA + StatCollector.translateToLocal("gui.tribe.overlay.invited.tribe"));
	    	list.add(ChatFormatting.DARK_PURPLE + "" + ChatFormatting.ITALIC + inviteQueue.get(i).name() + ChatFormatting.RESET);
	    	
	    	if(i == 0)
	    	{
	    		list.add(StatCollector.translateToLocalFormatted("gui.tribe.overlay.invited.accept", Keyboard.getKeyName(KeyBindings.acceptTribeInvitation.getKeyCode())));
	    		list.add(StatCollector.translateToLocalFormatted("gui.tribe.overlay.invited.deny", Keyboard.getKeyName(KeyBindings.denyTribeInvitation.getKeyCode())));
	    	}
	    	
	    	lastY = drawHoveringText(list, 0, lastY+16, mc.fontRenderer);
	    }
	}
	
	protected int drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
        if (!p_146283_1_.isEmpty())
        {
            //GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            //RenderHelper.disableStandardItemLighting();
            //GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            //int j2 = p_146283_2_ + 12;
            int j2 = p_146283_2_ + 4;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            if (j2 + k > this.width)
            {
                j2 -= 28 + k;
            }

            //if (k2 + i1 + 6 > this.height)
            //{
                //k2 = this.height - i1 - 6;
            //}

            this.zLevel = 300.0F;
            //itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            int lastY = k2 + i1 + 4;
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            this.zLevel = 0.0F;
            //itemRender.zLevel = 0.0F;
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            //RenderHelper.enableStandardItemLighting();
            //GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            
            return lastY;
        }
        
        return p_146283_3_;
    }
	
	public static void addTribeToQueue(TribeInfo info)
	{
		INSTANCE.queue.add(info);
	}
	
	public static void clearQueue()
	{
		INSTANCE.queue.clear();
	}
	
	public static boolean queueEmpty()
	{
		return INSTANCE.queue.size() == 0;
	}
	
	public static boolean inviteQueueEmpty()
	{
		return INSTANCE.inviteQueue.size() == 0;
	}
	
	public static boolean deniedQueueEmpty()
	{
		return INSTANCE.denyQueue.size() == 0;
	}
	
	public static boolean acceptQueueEmpty()
	{
		return INSTANCE.acceptQueue.size() == 0;
	}
	
	public static void tick()
	{
		if(!queueEmpty())
		{
			INSTANCE.ticks++;
		}
		
		if(!inviteQueueEmpty())
		{
			INSTANCE.ticksInvite++;
		}
		
		if(!deniedQueueEmpty())
		{
			INSTANCE.ticksDeny++;
		}
		
		if(!acceptQueueEmpty())
		{
			INSTANCE.ticksAccept++;
		}
	}
	
	public static void addTribeInviteToQueue(TribeInvite info)
	{
		INSTANCE.inviteQueue.add(info);
	}
	
	public static void addTribeDenyToQueue(TribeInviteDenied info)
	{
		INSTANCE.denyQueue.add(info);
	}
	
	public static void addTribeAcceptToQueue(TribeInviteAccepted info)
	{
		INSTANCE.acceptQueue.add(info);
	}
	
	public static void acceptInvitation()
	{
		if(INSTANCE.inviteQueue.size() > 0)
		{
			TribeInvite invite = INSTANCE.inviteQueue.get(0);
			BetterSurvival.network.sendToServer(new PacketAcceptInvite(invite.id(), ClientProxy.INSTANCE.getPlayer().getDisplayName()));
			INSTANCE.inviteQueue.clear();
		}
	}
	
	public static void denyInvitation()
	{
		if(INSTANCE.inviteQueue.size() > 0)
		{
			TribeInvite invite = INSTANCE.inviteQueue.get(0);
			BetterSurvival.network.sendToServer(new PacketDenyInvite(invite.id(), ClientProxy.INSTANCE.getPlayer().getDisplayName()));
			INSTANCE.inviteQueue.remove(0); 
		}
	}
}
