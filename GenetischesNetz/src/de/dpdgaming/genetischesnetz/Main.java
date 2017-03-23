package de.dpdgaming.genetischesnetz;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Main{
	
	private static ArrayList<Gehirn> population = new ArrayList<Gehirn>();
	
	public static void main(String[] args){
		for (int i = 0; i < 10; i++){
			population.add(new Gehirn());
		}
		while(population.get(0).getFitness() < 4){
			population.sort(new Comparator<Gehirn>(){
				public int compare(Gehirn a,Gehirn b){
					return (int)(b.getFitness()-a.getFitness());
				}
			});
			for (int i = 0; i<4; i++){
				population.remove(population.size()-1);
			}
			for (int i = 0; i< population.size(); i++){
				//System.out.println(i + ": " + population.get(i).getFitness());
			}
			Random randomizer = new Random();
			population.add(new Gehirn(population.get(randomizer.nextInt(5)),population.get(randomizer.nextInt(5))));
			population.add(new Gehirn(population.get(randomizer.nextInt(5)),population.get(randomizer.nextInt(5))));
			population.add(new Gehirn(population.get(randomizer.nextInt(5)),population.get(randomizer.nextInt(5))));
			population.add(new Gehirn(population.get(randomizer.nextInt(5)),population.get(randomizer.nextInt(5))));
		}
		System.out.println(population.get(0).getFitness());
	}

	public static int getRandomBit() {
		Random randomizer = new Random();
		return (randomizer.nextBoolean()) ? 1 : 0;
	}

}
