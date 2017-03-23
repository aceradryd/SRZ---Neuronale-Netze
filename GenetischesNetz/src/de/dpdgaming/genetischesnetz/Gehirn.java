package de.dpdgaming.genetischesnetz;

import java.util.ArrayList;
import java.util.Random;

public class Gehirn{
	
	protected ArrayList<Neuron> inputUnits = new ArrayList<Neuron>();
	protected ArrayList<ArrayList<Neuron>> hiddenUnits = new ArrayList<ArrayList<Neuron>>();
	protected ArrayList<Neuron> outputUnits = new ArrayList<Neuron>();
	
	public Gehirn(){
		inputUnits.add(new InputNeuron());
		inputUnits.add(new InputNeuron());
		for (int i = 0; i < 1; i++){
			hiddenUnits.add(new ArrayList<Neuron>());
			hiddenUnits.get(i).add(new Neuron(Neuron.BINAR));
			hiddenUnits.get(i).add(new Neuron(Neuron.BINAR));
		}
		outputUnits.add(new OutputNeuron(Neuron.BINAR));
		
		linkNeurons();
	}
	
	public Gehirn(Gehirn mutterhirn, Gehirn vaterhirn){
		this();
		kreuzeGehirne(mutterhirn, vaterhirn);
	}
	
	public void setInput(double x, double y){
		inputUnits.get(0).input = x;
		inputUnits.get(1).input = y;
	}
	
	public double getOutput(){
		return outputUnits.get(0).output;
	}
	
	public double getFitness(){
		double fitness = 0;
		
		setInput(0,0);
		feedforward();
		if (Math.round(getOutput())==BoolFunctions.xor(0,0)){
			fitness++;
		}
		
		setInput(0,1);
		feedforward();
		if (Math.round(getOutput())==BoolFunctions.xor(0,1)){
			fitness++;
		}
		
		setInput(1,0);
		feedforward();
		if (Math.round(getOutput())==BoolFunctions.xor(1,0)){
			fitness++;
		}
		
		setInput(1,1);
		feedforward();
		if (Math.round(getOutput())==BoolFunctions.xor(1,1)){
			fitness++;
		}
		
		return fitness;
	}
	
	public void kreuzeGehirne(Gehirn mutterhirn, Gehirn vaterhirn){
		kreuzeBias(mutterhirn,vaterhirn);
		kreuzeGewichte(mutterhirn,vaterhirn);
	}

	private void kreuzeBias(Gehirn mutterhirn,Gehirn vaterhirn){
		double newBias;
		for(int aktuellerSender = 0; aktuellerSender < inputUnits.size(); aktuellerSender++){
			Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
			newBias = getBias(aktuellerSender,hirn.inputUnits);
			setBias(inputUnits, aktuellerSender, newBias);
		}
		for (int aktuelleSchicht = 0; aktuelleSchicht < hiddenUnits.size()-1; aktuelleSchicht++){
			for(int aktuellerSender = 0; aktuellerSender < hiddenUnits.get(aktuelleSchicht).size(); aktuellerSender++){
				Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
				newBias = getBias(aktuellerSender,hirn.hiddenUnits.get(aktuelleSchicht));
				setBias(hiddenUnits.get(aktuelleSchicht), aktuellerSender, newBias);
			}
		}
		for(int aktuellerSender = 0; aktuellerSender < hiddenUnits.get(hiddenUnits.size()-1).size(); aktuellerSender++){
			Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
			newBias = getBias(aktuellerSender,hirn.hiddenUnits.get(hiddenUnits.size()-1));
			setBias(hiddenUnits.get(hiddenUnits.size()-1), aktuellerSender, newBias);
		}
	}

	private void setBias(ArrayList<Neuron> schicht,int aktuellerSender,double newBias){
		schicht.get(aktuellerSender).bias = newBias;
	}

	private double getBias(int aktuellerSender,ArrayList<Neuron> schicht){
		return schicht.get(aktuellerSender).bias;
	}

	private void kreuzeGewichte(Gehirn mutterhirn,Gehirn vaterhirn){
		double newWeight;
		for(int aktuellerSender = 0; aktuellerSender < inputUnits.size(); aktuellerSender++){
			for(int aktuellerReceiver = 0; aktuellerReceiver < inputUnits.size(); aktuellerReceiver++){
				Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
				newWeight = getWeight(hirn.inputUnits,aktuellerSender,aktuellerReceiver);
				setWeight(inputUnits,aktuellerSender,aktuellerReceiver,newWeight);
			}
		}
		for (int aktuelleSchicht = 0; aktuelleSchicht < hiddenUnits.size()-1; aktuelleSchicht++){
			for(int aktuellerSender = 0; aktuellerSender < hiddenUnits.get(aktuelleSchicht).size(); aktuellerSender++){
				for(int aktuellerReceiver = 0; aktuellerReceiver < hiddenUnits.get(aktuelleSchicht+1).size(); aktuellerReceiver++){
					Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
					newWeight = getWeight(hirn.hiddenUnits.get(aktuelleSchicht),aktuellerSender,aktuellerReceiver);
					setWeight(hiddenUnits.get(aktuelleSchicht),aktuellerSender,aktuellerReceiver,newWeight);
				}
			}
		}
		for(int aktuellerSender = 0; aktuellerSender < hiddenUnits.get(hiddenUnits.size()-1).size(); aktuellerSender++){
			for(int aktuellerReceiver = 0; aktuellerReceiver < outputUnits.size(); aktuellerReceiver++){
				Gehirn hirn = getAncestor(mutterhirn, vaterhirn);
				newWeight = getWeight(hirn.hiddenUnits.get(hiddenUnits.size()-1),aktuellerSender,aktuellerReceiver);
				setWeight(hiddenUnits.get(hiddenUnits.size()-1),aktuellerSender,aktuellerReceiver,newWeight);
			}
		}
	}

	private void setWeight(ArrayList<Neuron> schicht, int aktuellerSender,int aktuellerReceiver,double newWeight){
		schicht.get(aktuellerSender).receiverGewichte.set(aktuellerReceiver,newWeight);
	}

	private Double getWeight(ArrayList<Neuron> schicht,int aktuellerSender,int aktuellerReceiver){
		return schicht.get(aktuellerSender).receiverGewichte.get(aktuellerReceiver);
	}
	
	public Gehirn getAncestor(Gehirn mutterhirn, Gehirn vaterhirn){
		Random randomizer = new Random();
		double selector = randomizer.nextDouble();
		if (selector > 0.99){
			return new Gehirn();
		} else if (selector > 0.49){
			return mutterhirn;
		} else {
			return vaterhirn;
		}
	}
	
	private void linkNeurons(){
		for(Neuron sender : inputUnits){
			for(Neuron receiver : hiddenUnits.get(0)){
				sender.addReceiver(receiver);
				receiver.addSender(sender);
			}
		}
		for (int aktuelleSchicht = 0; aktuelleSchicht < hiddenUnits.size()-1; aktuelleSchicht++){
			for(Neuron sender : hiddenUnits.get(aktuelleSchicht)){
				for(Neuron receiver : hiddenUnits.get(aktuelleSchicht+1)){
					sender.addReceiver(receiver);
					receiver.addSender(sender);
				}
			}
		}
		for(Neuron sender : hiddenUnits.get(hiddenUnits.size()-1)){
			for(Neuron receiver : outputUnits){
				sender.addReceiver(receiver);
				receiver.addSender(sender);
			}
		}
	}
	
	public void feedforward(){
		for(Neuron neuron : inputUnits){
			neuron.feedforward();
		}
		for (int aktuelleSchicht = 0; aktuelleSchicht < hiddenUnits.size(); aktuelleSchicht++){
			for(Neuron neuron : hiddenUnits.get(aktuelleSchicht)){
				neuron.feedforward();
			}
		}
		for(Neuron neuron : outputUnits){
			neuron.feedforward();
		}
	}
	
}
