package base.tool;


public class ProgressStatus{

	private int requestIndex = 0;
	

	private int total = 0;
	
	private int currentIndex = 0 ;
	
	private double progress = 0.0;

	public int getRequestIndex() {
		return requestIndex;
	}
	
	public void setRequestIndex(int requestIndex) {
		this.requestIndex = requestIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	
}
