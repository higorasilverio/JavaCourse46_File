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

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner input = new Scanner(System.in);

		System.out.print("Enter the .csv file path: ");
		String inStringPath = input.next();
		File inFilePath = new File(inStringPath);
		String outStringPath = inFilePath.getParent();
		new File(outStringPath + "\\out").mkdir();
		try (BufferedReader br = new BufferedReader(new FileReader(inStringPath))) {
			String lineInRead = br.readLine();
			boolean fileCriationControl = false;
			while (lineInRead != null) {
				String lineParts[] = new String[3];
				lineParts = lineInRead.split(",");
				double totalValue = Double.valueOf(lineParts[1]) * Double.valueOf(lineParts[2]);
				if (fileCriationControl == false) {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(outStringPath + "\\out\\summary.csv"))) {
						bw.write(lineParts[0] + "," + totalValue);
						bw.newLine();
					} catch (IOException e) {
						System.out.println("Error: " + e.getMessage());
					} finally {
						fileCriationControl = true;
					}
				} else {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(outStringPath + "\\out\\summary.csv", true))) {
						totalValue = Double.valueOf(lineParts[1]) * Double.valueOf(lineParts[2]);
						bw.write(lineParts[0] + "," + totalValue);
						bw.newLine();
					} catch (IOException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
				lineInRead = br.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		input.close();
	}

}
