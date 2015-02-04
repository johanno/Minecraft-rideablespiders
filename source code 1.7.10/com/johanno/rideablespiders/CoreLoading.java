package com.johanno.rideablespiders;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CoreLoading implements IFMLLoadingPlugin
{
	public static File location;
	

	@Override
	public String[] getASMTransformerClass() 
	{
		return new String []{CoreTransform.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return null;//"com.johanno.rideablespiders.SpiderModContainer";
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		location = (File) data.get("coremodLocation");
		System.out.print("**************** coreLoading");
		if(location == null)
		{location = new File("/mods/Rideablespiders-1.3-1.7.10.jar");
		System.out.println("***************cormodlocation failed try default");}
	}

	@Override
	public String getAccessTransformerClass() 
	{
		return null;
	}
	
	
	    
}
