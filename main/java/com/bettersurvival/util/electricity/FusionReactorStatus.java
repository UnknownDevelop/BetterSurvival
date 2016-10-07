package com.bettersurvival.util.electricity;

public class FusionReactorStatus 
{
	public static final int STATUS_DEACTIVATED = 0;
	public static final int STATUS_BOOTING = 1;
	public static final int STATUS_UP_AND_RUNNING = 2;
	public static final int STATUS_TOO_COLD = 3;
	public static final int STATUS_TOO_HOT = 4;
	public static final int STATUS_CORE_UNSTABLE = 5;
	public static final int STATUS_NEAR_MELTDOWN = 6;
	public static final int STATUS_SYSTEM_CORRUPTED = 7;
	public static final int STATUS_FORCED_SHUTDOWN = 8;
	public static final int STATUS_SHUTDOWN = 9;
	public static final int STATUS_HEATING_UP = 10;
}
