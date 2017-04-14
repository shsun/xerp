package base.utils.json;

import java.util.List;

public class JsonResultFactory {

	public static <T> IJsonResult extgrid(List<T> invdata) {
		return new ExtGrid<T>(invdata);
	}

	public static <T> IJsonResult extgrid(List<T> invdata, int total) {
		return new ExtGrid<T>(invdata, total);
	}

	public static <T> IJsonResult success(T bean) {
		return new JsonResultSuccess<T>(bean);
	}

	public static <T> IJsonResult success() {
		return new JsonResultSuccess<T>();
	}

	public static <T> IJsonResult error(String info) {
		return new JsonResultError(info);
	}
}
