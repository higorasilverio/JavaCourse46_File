package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the .csv file path: ");
		String inStringPath = input.next();
		File inFilePath = new File(inStringPath);
		String outStringPath = inFilePath.getParent();
		new File(outStringPath + "\\out").mkdir();
		System.out.println("Folder '...\\out' created successfully!");
		
		try (BufferedReader br = new BufferedReader(new FileReader(inStringPath))) {
			String lineInRead = br.readLine();
			String lineParts[] = new String[3];
			boolean fileCriationControl = false;				
			while (lineInRead != null) {
				lineParts = lineInRead.split(",");
				String name = lineParts[0];
				double price = Double.parseDouble(lineParts[1]);
				int quantity = Integer.parseInt(lineParts[2]);
				Product product = new Product(name, price, quantity);
				if (fileCriationControl == false) {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(outStringPath + "\\out\\summary.csv"))) {
						bw.write(product.getName() + "," + product.totalValue());
						bw.newLine();
					} catch (IOException e) {
						System.out.println("Error creating file '...\\out\\summary.csv': " + e.getMessage());
					} finally {
						fileCriationControl = true;
					}
				} else {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(outStringPath + "\\out\\summary.csv", true))) {
						bw.write(product.getName() + "," + product.totalValue());
						bw.newLine();
					} catch (IOException e) {
						System.out.println("Error updating file '...\\out\\summary.csv': " + e.getMessage());
					}
				}
				lineInRead = br.readLine();
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} 
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		input.close();
	}

}
