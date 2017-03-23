package de.dpdgaming.backpropagationnetwork;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{

	protected static ArrayList<Neuron> inputUnits = new ArrayList<Neuron>();
	protected static ArrayList<ArrayList<Neuron>> hiddenUnits = new ArrayList<ArrayList<Neuron>>();
	protected static ArrayList<Neuron> outputUnits = new ArrayList<Neuron>();
	
	public static void main(String[] args){
		inputUnits.add(new InputNeuron());
		inputUnits.add(new InputNeuron());
		for (int i = 0; i < 1; i++){
			hiddenUnits.add(new ArrayList<Neuron>());
			hiddenUnits.get(i).add(new Neuron(Neuron.SINUS));
			hiddenUnits.get(i).add(new Neuron(Neuron.SINUS));
		}
		outputUnits.add(new OutputNeuron(Neuron.BINAR));
		
		linkNeurons();
		
		int counter = 0;
		int i = 0;
		while(counter < 50){
			i++;
			
			double x = getRandomBit();
			double y = getRandomBit();
			double z = BoolFunctions.xor(x,y);
			inputUnits.get(0).input=x;
			inputUnits.get(1).input=y;
			((OutputNeuron)outputUnits.get(0)).outputSoll = z;
			
			feedforward();
			if (z == Math.round(outputUnits.get(0).output)){
				System.out.println(i);
				counter++;
			} else {
				counter = 0;
			}
			feedbackward();
		}
		
		testAllCombinations();
		
		Scanner s= new Scanner(System.in);
		while(true){
			System.out.print("X:");
			inputUnits.get(0).input = s.nextInt();
			System.out.print("Y:");
			inputUnits.get(1).input = s.nextInt();
			feedforward();
			System.out.println(outputUnits.get(0).output);
		}
	}

	private static void testAllCombinations(){
		System.out.print("X:0;Y:0;Z:");
		inputUnits.get(0).input = 0;
		inputUnits.get(1).input = 0;
		feedforward();
		System.out.print(outputUnits.get(0).output + " - ");
		System.out.println(Math.round(outputUnits.get(0).output));
		System.out.print("X:0;Y:1;Z:");
		inputUnits.get(0).input = 0;
		inputUnits.get(1).input = 1;
		feedforward();
		System.out.print(outputUnits.get(0).output + " - ");
		System.out.println(Math.round(outputUnits.get(0).output));
		System.out.print("X:1;Y:0;Z:");
		inputUnits.get(0).input = 1;
		inputUnits.get(1).input = 0;
		feedforward();
		System.out.print(outputUnits.get(0).output + " - ");
		System.out.println(Math.round(outputUnits.get(0).output));
		System.out.print("X:1;Y:1;Z:");
		inputUnits.get(0).input = 1;
		inputUnits.get(1).input = 1;
		feedforward();
		System.out.print(outputUnits.get(0).output + " - ");
		System.out.println(Math.round(outputUnits.get(0).output));
	}
	
	private static void linkNeurons(){
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

	private static void feedbackward(){
		for(Neuron neuron : outputUnits){
			neuron.feedbackward();
		}
		for (int aktuelleSchicht = 0; aktuelleSchicht < hiddenUnits.size(); aktuelleSchicht++){
			for(Neuron neuron : hiddenUnits.get(aktuelleSchicht)){
				neuron.feedbackward();
			}
		}
		for(Neuron neuron : inputUnits){
			neuron.feedbackward();
		}
	}

	private static void feedforward(){
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

	private static int getRandomBit() {
		Random randomizer = new Random();
		return (randomizer.nextBoolean()) ? 1 : 0;
	}

}
