import java.io.*;

public class Driver {
	public static void main(String[] args) {
		double[] c1 = { 6, 0, 0, 5 };
        int [] e1 = {0, 2, 4, 4};
        Polynomial p1 = new Polynomial(c1, e1); 
		double[] c2 = { 0, -2, 0, 0, -9 };
        int [] e2 = {0, 2, 7, 4};
        Polynomial p2 = new Polynomial(c2, e2);


		File f = new File("poly.txt");
		try {
			Polynomial p3 = new Polynomial(f);
		} catch (Exception e) {
			System.out.println("Error!");
		}
        for (int i = 0; i < p2.coefficients.length; i++) {
            System.out.print("Coff: " + p2.coefficients[i]);
            System.out.println(", Exp: " + p2.exponents[i]);
        }
		try {
			p2.saveToFile("output.txt");
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
}
