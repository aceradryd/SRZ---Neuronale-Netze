package de.dpdgaming.backpropagationnetwork;

public final class BoolFunctions{

	public static double and(double x, double y){
		if(x == y && x == 1){
			return 1;
		}
		return 0;
	}
	
	public static double or(double x, double y){
		if(x + y >= 1){
			return 1;
		}
		return 0;
	}
	
	public static double xor(double x, double y){
		if(x != y){
			return 1;
		}
		return 0;
	}
}
