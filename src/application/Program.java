package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);
		
		List<Product> list = new ArrayList<Product>();

		System.out.print("Enter the .csv file path: ");
		String inStringPath = input.next();
		
		File inFilePath = new File(inStringPath);
		String outStringPath = inFilePath.getParent();
		new File(outStringPath + "\\out").mkdir();
		System.out.println("Folder '...\\out' created successfully!");
		
		try (BufferedReader br = new BufferedReader(new FileReader(inStringPath))) {
			String lineInRead = br.readLine();
				
			while (lineInRead != null) {
				String lineParts[] = lineInRead.split(",");
				String name = lineParts[0];
				double price = Double.parseDouble(lineParts[1]);
				int quantity = Integer.parseInt(lineParts[2]);
				
				list.add(new Product(name, price, quantity));
				
				lineInRead = br.readLine();
			}
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(outStringPath + "\\out\\summary.csv"))){
				for (Product item : list) {
					bw.write(item.getName() + "," + String.format("%.2f", item.totalValue()));
					bw.newLine();
				}
				System.out.println("File '...\\out\\summary.csv' created successfully!");
			}
			catch (IOException e) {
				System.out.println("Error creating file '...\\out\\summary.csv': " + e.getMessage());
			}
				
		} 
		catch (FileNotFoundException e) {
			System.out.println("Error searching for file: " + e.getMessage());
		} 
		catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		System.out.println("Data for product total value available in: " + outStringPath + "\\out\\summary.csv");
		input.close();
	}

}
