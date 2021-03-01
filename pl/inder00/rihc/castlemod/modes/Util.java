package pl.inder00.rihc.castlemod.modes;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class Util {
	
	public static boolean chance(double chance){
	    Random random = new Random();
	    double random1 = random.nextDouble() * 100.0D;
	    if (random1 <= chance) {
	    	return true;
	    }
	    return false;
	}
	/*
	public static ArrayList<Block> sphere(final Location center, final int radius) {
	    ArrayList<Block> sphere = new ArrayList<Block>();
	    for (int Y = -radius; Y < radius; Y++)
	      for (int X = -radius; X < radius; X++)
	         for (int Z = -radius; Z < radius; Z++)
	            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
	               final Block block = center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
	               sphere.add(block);
	            }
	    return sphere;
	}
	*/

}
