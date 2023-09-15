public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[] {0};
    }
    public Polynomial(double [] coefficients) {
        this.coefficients = coefficients;
    }
    public Polynomial add(Polynomial p) {
        // which polynomial is bigger the new one we are adding or the one we already have
        boolean pBig = false;
        if (p.coefficients.length > this.coefficients.length){
            pBig = true;
        } 
        int numMaxTerms = Math.max(p.coefficients.length, this.coefficients.length);
        int numMinTerms = Math.min(p.coefficients.length, this.coefficients.length);
        // create a new lst of the terms that has the length of the max number of coefficients
        // this means the result will also be of length of the max_terms
        double [] newArr = new double[numMaxTerms];
        // go through what ever is in common and add the coefficients and assign this value to new_arr at index i 
        for (int i = 0; i<numMinTerms; i++) {
            newArr[i] = p.coefficients[i] + this.coefficients[i];
        }
        // whatever is remaining whether it be the new polynomial or the existing one, will get assigned to new_arr leftover indices
        for (int i=numMinTerms; i<numMaxTerms; i++) {
            if (pBig){
                newArr[i] = p.coefficients[i];
            } else {
                newArr[i] = this.coefficients[i];
            }
        }
        // create a new polynomial object with the array we created and pass that in
        Polynomial newPolynomial = new Polynomial(newArr);
        return newPolynomial;
    }
    public double evaluate(double x) {
        // put the number into the polynomial equation
        // have to account for the 0^0 case so you just start the loop at 1 and the total at the first coefficient (regardless of if its 0)
        double total = this.coefficients[0];
        // iterate thru the loop and pass x, the degree and the coefficient and add that to the total sum
        for (int i = 1; i<this.coefficients.length; i++) {
            total += this.coefficients[i] * Math.pow(x, i);
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