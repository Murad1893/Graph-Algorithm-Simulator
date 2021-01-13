package application;

public class Node {
	double x_cord, y_cord;
	int degree;

	public Node() {
		super();
		this.x_cord = 0;
		this.y_cord = 0;
		this.degree = 0;
	}
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public double getX_cord() {
		return x_cord;
	}

	public void setX_cord(double x_cord) {
		this.x_cord = x_cord;
	}

	public double getY_cord() {
		return y_cord;
	}

	public void setY_cord(double y_cord) {
		this.y_cord = y_cord;
	}
	
	
}
