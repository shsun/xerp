package base.utils.json;

import java.util.List;

/**
 * 
 * @author shsun
 *
 * @param <T>
 */
public class ExtGrid<T> implements IJsonResult {

	private List<T> mData;
	private int total;

	public ExtGrid() {
	}

	public ExtGrid(List<T> data) {
		this.mData = data;
		this.total = data.size();
	}

	public ExtGrid(List<T> data, int total) {
		this.mData = data;
		this.total = total;
	}

	public List<T> getData() {
		return mData;
	}

	public void setData(List<T> data) {
		this.mData = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
