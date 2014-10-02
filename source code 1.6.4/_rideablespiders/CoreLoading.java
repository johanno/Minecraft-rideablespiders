package _rideablespiders;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class CoreLoading implements IFMLLoadingPlugin
{
	public static File location;
	
	@Override
	public String[] getLibraryRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getASMTransformerClass() 
	{
		return new String []{CoreTransform.class.getName()};
	}

	@Override
	public String getModContainerClass()
	{
		return null;
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
		if(this.location == null)
		{location = new File("/mods/RideAbleSpiders.jar");
		System.out.println("***************cormodlocation failed try default");}
	}

}
