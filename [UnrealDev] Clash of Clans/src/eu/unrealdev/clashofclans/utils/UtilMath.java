package eu.unrealdev.clashofclans.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class UtilMath {

	
	public static double trim(int degree, double d) {
		
		String format = "#.#";
		
		for(int i = 1; i < degree; i++) {
			
			format += "#";
		}
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
		DecimalFormat twoDFormat = new DecimalFormat(format, symbols);
		
		return Double.valueOf(twoDFormat.format(d));
	}
	
	
	public static Random random = new Random();
	
	public static int random(int i) {
		
		return random.nextInt(i);
	}
	
	
	public static double offset2d(Entity a, Entity b) {
		
		return offset2d(a.getLocation().toVector(), b.getLocation().toVector());
	}
	
	
	public static double offset2d(Location a, Location b) {
		
		return offset2d(a.toVector(), b.toVector());
	}
	
	
	public static double offset2d(Vector a, Vector b) {
		
		a.setY(0);
		b.setY(0);
		
		return a.subtract(b).length();
	}
	
	
	public static double offset(Entity a, Entity b) {
		
		return offset(a.getLocation().toVector(), b.getLocation().toVector());
	}
	
	
	public static double offset(Location a, Location b) {
		
		return offset(a.toVector(), b.toVector());
	}
	
	
	public static double offset(Vector a, Vector b) {
		
		return a.subtract(b).length();
	}
	
	
	public static double offsetSquared(Entity a, Entity b) {
		
		return offsetSquared(a.getLocation().toVector(), b.getLocation().toVector());
	}
	
	
	public static double offsetSquared(Location a, Location b) {
		
		return offsetSquared(a.toVector(), b.toVector());
	}
	
	
	public static double offsetSquared(Vector a, Vector b) {
		
		return a.distanceSquared(b);
	}
	
	
	public static double rr(double d, boolean bidirectional) {
		
		if(bidirectional) {
			
			return Math.random() * (2 * d) - d;
		}
		
		return Math.random() * d;
	}
}
