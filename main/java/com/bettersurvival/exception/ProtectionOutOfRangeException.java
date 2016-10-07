package com.bettersurvival.exception;

public class ProtectionOutOfRangeException extends Exception
{
	private static final long serialVersionUID = -4814445835285981650L;

	public ProtectionOutOfRangeException()
	{
		super("Radioactivity protection is out of range.");
	}
	
	public ProtectionOutOfRangeException(String message)
	{
		super(message);
	}
}
