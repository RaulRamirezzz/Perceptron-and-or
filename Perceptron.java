import java.util.Random;

class Perceptron {

    private double[] weights;
    private double bias;
    private double learningRate;
    private boolean useBias;

    public Perceptron(int inputSize, double learningRate, boolean useBias) {
        this.weights = new double[inputSize];
        this.bias = new Random().nextDouble();
        this.learningRate = learningRate;
        this.useBias = useBias;
        initializeWeights();

    }

    public void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = rand.nextDouble();
        }
    }

    public double activationFunction(double sum) {
        return 1 / (1 + Math.exp(-sum)); // Función sigmoide
    }

    public int Heaviside(double sum) {
        if (sum >= 0) {
            return 1;
        }
        return 0;
    }

    public int ecuacion(int[] inputs) {
        
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
            System.out.print("("+inputs[i]+"*"+weights[i]+")");
            System.out.print("+");
        }

        if (useBias) {
            sum += bias;
            System.out.print(bias);
        }

        else {
            sum = Heaviside(sum);
        }

        System.out.println("=" + sum);

        double output = activationFunction(sum);
        if (output > 0.5) {
            System.out.println("Salida: 1");
            return 1;
        }
        System.out.println("Salida: 0");
        return 0;
    }

    public void train(int[][] inputs, int[] outputs, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            System.out.println("--------------------------------- EPOCA: " + epoch + " ---------------------------------");
            boolean converged = true;
            for (int i = 0; i < inputs.length; i++) {
                int prediction = ecuacion(inputs[i]);
                int error = outputs[i] - prediction;
                if (error != 0) converged = false;
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += learningRate * error * inputs[i][j];
                }
                if (useBias) {
                    bias += learningRate * error;
                }
            }
            if (converged) break;
        }
    }

    public void printModel() {
        System.out.println("Pesos óptimos: ");
        for (double w : weights) {
            System.out.println(w);
        }
        if (useBias) {
            System.out.println("Bias óptimo: " + bias);
        }
    }

    public static void main(String[] args) {
        double learningRate = 0.1;

        int[][] andInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        int[] andOutputs = {0, 0, 0, 1};
        int[][] orInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        int[] orOutputs = {0, 1, 1, 1};

        System.out.println("");

        System.out.println("Perceptrón sin sesgo para AND");
        Perceptron perceptronAnd = new Perceptron(2, learningRate, true);
        perceptronAnd.train(andInputs, andOutputs, 20);
        perceptronAnd.printModel();
        
        System.out.println("Perceptrón con sesgo para OR");
        Perceptron perceptronOr = new Perceptron(2, learningRate, true);
        perceptronOr.train(orInputs, orOutputs, 20);
        perceptronOr.printModel();
        
    }
}
