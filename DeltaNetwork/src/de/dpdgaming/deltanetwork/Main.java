package de.dpdgaming.deltanetwork;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main{

	protected static ArrayList<Neuron> inputUnits = new ArrayList<Neuron>();
	protected static ArrayList<Neuron> outputUnits = new ArrayList<Neuron>();
	
	public static void main(String[] args){
		inputUnits.add(new InputNeuron());
		inputUnits.add(new InputNeuron());
		
		outputUnits.add(new OutputNeuron());
		
		linkNeurons();
		
		int counter = 0;
		int i = 0;
		while(counter < 50){
			i++;
			
			double x = getRandomBit();
			double y = getRandomBit();
			double z = BoolFunctions.not(x,y);
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
		
		Scanner s = new Scanner(System.in);
		while(true){
			System.out.print("X:");
			inputUnits.get(0).input = s.nextDouble();
			System.out.print("Y:");
			inputUnits.get(1).input = s.nextDouble();
			feedforward();
			System.out.println(outputUnits.get(0).output);
		}
	}

	private static void testAllCombinations(){
		System.out.print("X:0;Y:0;Z:");
		inputUnits.get(0).input = 0;
		inputUnits.get(1).input = 0;
		feedforward();
		System.out.println(outputUnits.get(0).output);
		System.out.print("X:0;Y:1;Z:");
		inputUnits.get(0).input = 0;
		inputUnits.get(1).input = 1;
		feedforward();
		System.out.println(outputUnits.get(0).output);
		System.out.print("X:1;Y:0;Z:");
		inputUnits.get(0).input = 1;
		inputUnits.get(1).input = 0;
		feedforward();
		System.out.println(outputUnits.get(0).output);
		System.out.print("X:1;Y:1;Z:");
		inputUnits.get(0).input = 1;
		inputUnits.get(1).input = 1;
		feedforward();
		System.out.println(outputUnits.get(0).output);
	}

	private static void linkNeurons(){
		for(Neuron sender : inputUnits){
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
		for(Neuron neuron : inputUnits){
			neuron.feedbackward();
		}
	}

	private static void feedforward(){
		for(Neuron neuron : inputUnits){
			neuron.feedforward();
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
