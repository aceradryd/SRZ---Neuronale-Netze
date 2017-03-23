package de.dpdgaming.deltanetwork;

public class InputNeuron extends Neuron{
	
	@Override
	public void feedforward(){
		output = input;
	}
	
	@Override
	public void feedbackward(){
		calcGewichte();
	}
	
	@Override
	public double aktivierungsfunktion(double x){
		return x;
	}
	
	@Override
	public double ableitung(double x){
		return 1;
	}
}
