package de.dpdgaming.deltanetwork;

import java.util.ArrayList;
import java.util.Random;

public class Neuron{
	protected double epsilon = 0.1;
	
	protected ArrayList<Neuron> receiverList = new ArrayList<Neuron>();
	protected ArrayList<Double> receiverGewichte = new ArrayList<Double>();
	protected ArrayList<Neuron> senderList = new ArrayList<Neuron>();
	
	protected double bias = 0;
	protected double input = 0;
	protected double output = 0;
	protected double delta = 0;
	
	public Neuron(){
		Random randomizer = new Random();
		bias = randomizer.nextDouble()*2-1;
	}
	
	public void addSender(Neuron sender){
		senderList.add(sender);
	}
	
	public void addReceiver(Neuron receiver){
		receiverList.add(receiver);
		Random randomizer = new Random();
		receiverGewichte.add(randomizer.nextDouble()*2-1);
	}
	
	public void feedforward(){
		input = 0;
		for(int aktuellerSender = 0; aktuellerSender<senderList.size();aktuellerSender++){
			input += senderList.get(aktuellerSender).getOutput(this);
		}
		output = aktivierungsfunktion(input + bias);
	}
	
	public void feedbackward(){
		//calcGewichte();
		//calcBias();
		//calcDelta();
	}
	
	protected void calcGewichte(){
		for(int aktuellerReceiver=0; aktuellerReceiver<receiverList.size();aktuellerReceiver++){
			double aktuellesGewicht = receiverGewichte.get(aktuellerReceiver);
			aktuellesGewicht += epsilon * receiverList.get(aktuellerReceiver).delta * output;
			receiverGewichte.set(aktuellerReceiver,aktuellesGewicht);
		}
	}
	
	protected void calcBias(){
		bias += epsilon * delta;
	}
	
	protected void calcDelta(){
		//delta = 0;
		//for(int aktuellerReceiver = 0; aktuellerReceiver<receiverList.size(); aktuellerReceiver++){
		//	delta += receiverList.get(aktuellerReceiver).delta * receiverGewichte.get(aktuellerReceiver);
		//}
		//delta *= ableitung(input);
	}
	
	protected double getOutput(Neuron receiver){
		for (int aktuellerReceiver = 0; aktuellerReceiver<receiverList.size(); aktuellerReceiver++){
			if (receiver.equals(receiverList.get(aktuellerReceiver))){
				return output * receiverGewichte.get(aktuellerReceiver);
			}
		}
		return 0;
	}
	
	protected double aktivierungsfunktion(double x){
		return 1/(1+Math.exp(-x));
	}
	
	protected double ableitung(double x){
		return aktivierungsfunktion(x)*(1-aktivierungsfunktion(x));
	}
}
