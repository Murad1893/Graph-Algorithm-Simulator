package application;

public class Edge {
	int source;
    int destination;
    double weight;

    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
    
}
