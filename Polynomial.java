import java.io.*;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;
    BufferedReader reader;

    public Polynomial() {
        this.coefficients = new double[] {0};
        this.exponents = new int[] {0};
    }
    public Polynomial(double [] coefficients, int [] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }
    public Polynomial(File f) throws Exception {
        // initialize the polynomial based on the contents of the file
        String line = "";
        reader = new BufferedReader(new FileReader(f));
        line = reader.readLine();
        String[] separated = line.split("\\+|(?=-)");
        coefficients = new double[separated.length];
        exponents = new int[separated.length];

        int coeffCounter = 0;
        int expCounter = 0;
        for (int i = 0; i<separated.length; i++) {
            int variableIndex = separated[i].indexOf("x");
            if (variableIndex == -1) {
                coefficients[coeffCounter] = Double.parseDouble(separated[i]);
            }
            else {
                String subCoefficient = separated[i].substring(0, variableIndex);
                if (subCoefficient.length() == 1 && Character.compare(subCoefficient.charAt(0), '-') == 0) {
                    coefficients[coeffCounter] = -1;
                }
                else if (subCoefficient.length() == 0) {
                    coefficients[coeffCounter] = 1;
                }
                else {
                    coefficients[coeffCounter] = Double.parseDouble(subCoefficient);
                }

                String subExponent = separated[i].substring(variableIndex+1, separated[i].length());
                if (subExponent.length() == 0){
                    exponents[expCounter] = 1;
                }
                else {
                    exponents[expCounter] = Integer.parseInt(subExponent);
                }
            }
            coeffCounter++;
            expCounter++;
        }
    }
    public void saveToFile(String filename) throws Exception {
        FileWriter writer = new FileWriter(filename, false);
        String coeffString;
        String expString;

        for (int i = 0; i<this.coefficients.length; i++) {
            coeffString = Integer.toString((int)this.coefficients[i]) + "x";
            expString = Integer.toString((int)this.exponents[i]);

            if (Character.compare(expString.charAt(0), '0') == 0) {
                expString = "";
                coeffString = coeffString.substring(0, coeffString.length()-1);
            }
            if (i > 0 && Character.compare(coeffString.charAt(0), '-') != 0) {
                writer.write("+");
            }
            writer.write(coeffString + expString);
        }
        writer.write("\n");
        writer.close();
    }

    public Polynomial multiply(Polynomial p) {
        //returns the polynomial from multiplying the object and the argument
        // return value should not have any redundant exponents
        int multipleLen = p.coefficients.length;
        int originalLen = this.coefficients.length;

        int maxOriginalExp = 0;
		for (int i = 0; i < originalLen; i++) {
			if (this.exponents[i] > maxOriginalExp) {
				maxOriginalExp = this.exponents[i];
			}
		}
		int maxMultipleExp = 0;
		for (int j = 0; j < multipleLen; j++) {
			if (p.exponents[j] > maxMultipleExp) {
				maxMultipleExp = p.exponents[j];
			}
		}
		int finalArrSize = maxMultipleExp+1 * maxOriginalExp+1;

        double[] coefficients = new double[finalArrSize];
		int[] exponents = new int[finalArrSize];

		for (int x = 0; x < p.exponents.length; x++) {
			for (int y = 0; y < this.exponents.length; y++) {
				double product = p.coefficients[x] * this.coefficients[y];
				int newExponent = p.exponents[x] + this.exponents[y];

				exponents[newExponent] = newExponent;
				coefficients[newExponent] += product;
			}
		}

        // take out terms w zero coeff
        int newArrayLength = 0;
        for (int a = 0; a<coefficients.length; a++) {
            if (coefficients[a] != 0.0) {
                newArrayLength++;
            }
        }

        double[] finalCoefficients = new double[newArrayLength];
        int[] finalExponents = new int[newArrayLength];
        int lastIndex = 0;
        for (int q = 0; q<coefficients.length; q++) {
            if (coefficients[q] != 0.0) {
                finalCoefficients[lastIndex] = coefficients[q];
                finalExponents[lastIndex] = exponents[q];

                lastIndex++;
            }
        }
        Polynomial toReturn = new Polynomial(finalCoefficients, finalExponents);
        return toReturn;
    }



    public Polynomial add(Polynomial p) {
        int multipleLen = p.coefficients.length;
        int originalLen = this.coefficients.length;
        int totalLen = multipleLen + originalLen;

        double[] coeff = new double[totalLen];
        int[] exponents = new int[totalLen];
        int finalPolyIndex = 0;
        int indexCenter = 0;
        int x = 0;
        int y = 0;

        while (indexCenter < totalLen) {
			if (x >= multipleLen) {
				exponents[finalPolyIndex] = this.exponents[y];
				coeff[finalPolyIndex] = this.coefficients[y];
				y++;
				finalPolyIndex++;
				indexCenter++;
			}
			else if (y >= originalLen) {
				exponents[finalPolyIndex] = p.exponents[x];
				coeff[finalPolyIndex] = p.coefficients[x];
				x++;
				finalPolyIndex++;
				indexCenter++;
			}
            else {
				if (p.exponents[x] == this.exponents[y]) {
					coeff[finalPolyIndex] = p.coefficients[x] + this.coefficients[y];
					exponents[finalPolyIndex] = p.exponents[x];
	
					x++;
					y++;
					finalPolyIndex++;
					indexCenter += 2;
				}
				else {
					if (p.exponents[x] < this.exponents[y]) {
                        coeff[finalPolyIndex] = p.coefficients[x];
						exponents[finalPolyIndex] = p.exponents[x];
						x++;
					}
					else {
                        coeff[finalPolyIndex] = this.coefficients[y];
						exponents[finalPolyIndex] = this.exponents[y];
						y++;
					}
					finalPolyIndex++;
					indexCenter++;
				}
			}
        }	

        int newArrayLength = 0;
        for (int a = 0; a<coefficients.length; a++) {
            if (coefficients[a] != 0.0) {
                newArrayLength++;
            }
        }

        double[] finalCoefficients = new double[newArrayLength];
        int[] finalExponents = new int[newArrayLength];
        int lastIndex = 0;
        for (int q = 0; q<coefficients.length; q++) {
            if (coefficients[q] != 0.0) {
                finalCoefficients[lastIndex] = coefficients[q];
                finalExponents[lastIndex] = exponents[q];

                lastIndex++;
            }
        }
        // create a new polynomial object with the array we created and pass that in
        Polynomial toReturn = new Polynomial(finalCoefficients, finalExponents);
        return toReturn;
    
    }
    



    public double evaluate(double x) {
        // put the number into the polynomial equation
        // have to account for the 0^0 case so you just start the loop at 1 and the total at the first coefficient (regardless of if its 0)
        double total = this.coefficients[0];
        // iterate thru the loop and pass x, the degree and the coefficient and add that to the total sum
        for (int i = 1; i<this.coefficients.length; i++) {
            total += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }
        return total;
    }
    // will determine if the value is a root of the polynomial or not (makes it evaluate to 0)
    public boolean hasRoot(double y){
        // if you evaluate and the value returned is == 0
        if (evaluate(y) == 0) {
            return true;
        }
        return false;
    }
}