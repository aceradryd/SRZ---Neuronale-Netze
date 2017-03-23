package de.dpdgaming.backpropagationnetwork;

import java.util.ArrayList;
import java.util.Random;

public class Neuron{
	public static final int BINAR = 0;
	public static final int LINEAR = 1;
	public static final int SIGMOID = 2;
	public static final int SINUS = 3;
	public static final int PIECEWISELINEAR = 4;
	
	public int function = 0;
	protected double epsilon = 0.7;
	
	protected ArrayList<Neuron> receiverList = new ArrayList<Neuron>();
	protected ArrayList<Double> receiverGewichte = new ArrayList<Double>();
	protected ArrayList<Neuron> senderList = new ArrayList<Neuron>();
	
	protected double bias = 0;
	protected double input = 0;
	protected double output = 0;
	protected double delta = 0;
	
	public Neuron(){
		Random randomizer = new Random();
		bias = randomizer.nextDouble();
	}
	
	public Neuron(int function){
		this.function = function;
		Random randomizer = new Random();
		bias = randomizer.nextDouble();
	}
	
	protected double getEpsilon(){
		double tmpEpsilon = epsilon;
		epsilon *= 0.99;
		return tmpEpsilon;
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
		calcGewichte();
		calcBias();
		calcDelta();
	}
	
	protected void calcGewichte(){
		for(int aktuellerReceiver=0; aktuellerReceiver<receiverList.size();aktuellerReceiver++){
			double aktuellesGewicht = receiverGewichte.get(aktuellerReceiver);
			aktuellesGewicht += getEpsilon() * receiverList.get(aktuellerReceiver).delta * output;
			receiverGewichte.set(aktuellerReceiver,aktuellesGewicht);
		}
	}
	
	protected void calcBias(){
		bias += getEpsilon() * delta;
	}
	
	protected void calcDelta(){
		delta = 0;
		for(int aktuellerReceiver = 0; aktuellerReceiver<receiverList.size(); aktuellerReceiver++){
			delta += receiverList.get(aktuellerReceiver).delta * receiverGewichte.get(aktuellerReceiver);
		}
		delta *= ableitung(input);
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
		switch(function){
		case BINAR:
			if (x>0.5){
				return 1;
			}
			return 0;
		case LINEAR:
			return x;
		case SIGMOID:
			return 1/(1+Math.exp(-x));
		case SINUS:
			return Math.sin(x);
		case PIECEWISELINEAR:
			if (x<0.25){
				return 0;
			} else if (x>0.75){
				return 1;
			} else {
				return 2*(x-0.25);
			}
		}
		return 0;
	}
	
	protected double ableitung(double x){
		switch(function){
		case BINAR:
			return 1;
		case LINEAR:
			return 1;
		case SIGMOID:
			return aktivierungsfunktion(x)*(1-aktivierungsfunktion(x));
		case SINUS:
			return Math.cos(x);
		case PIECEWISELINEAR:
			if (x<0.25){
				return 0;
			} else if (x>0.75){
				return 0;
			} else {
				return 2;
			}
		}
		return 0;
	}
}
