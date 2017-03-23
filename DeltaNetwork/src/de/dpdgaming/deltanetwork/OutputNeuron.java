package de.dpdgaming.deltanetwork;

public class OutputNeuron extends Neuron{
	public double outputSoll;
	
	@Override
	public void feedbackward(){
		calcDelta();
		calcBias();
	}
	
	@Override
	protected void calcDelta(){
		delta = (outputSoll - output);
	}
	
	@Override
	public double aktivierungsfunktion(double x){
		return Math.sin(x);
//		if (x>0.5){
//			return 1;
//		}
//		return 0;
	}
	
	@Override
	public double ableitung(double x){
		return 0;
	}
}
