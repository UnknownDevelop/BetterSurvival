package com.bettersurvival.exception;

public class LayoutHasNotBeenGeneratedException extends Exception
{
	public LayoutHasNotBeenGeneratedException()
	{
		super("Layout has not been generated yet.");
	}
	
	public LayoutHasNotBeenGeneratedException(String message)
	{
		super(message);
	}
}
