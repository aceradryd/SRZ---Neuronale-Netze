package de.dpdgaming.backpropagationnetwork;

public class OutputNeuron extends Neuron{
	public double outputSoll;
	
	public OutputNeuron(int function){
		super(function);
	}

	@Override
	public void feedbackward(){
		calcDelta();
		calcBias();
	}
	
	@Override
	protected void calcDelta(){
		delta = (outputSoll - output)*ableitung(input);
	}
}
