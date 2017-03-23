package de.dpdgaming.genetischesnetz;

public class InputNeuron extends Neuron{
	
	@Override
	public void feedforward(){
		output = input;
	}
	
	@Override
	public void feedbackward(){
		calcGewichte();
	}
}
