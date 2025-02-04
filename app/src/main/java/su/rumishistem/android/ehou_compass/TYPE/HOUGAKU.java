package su.rumishistem.android.ehou_compass.TYPE;

public class HOUGAKU {
	private int YEAR_SHIMO;
	private float DO;
	private String HOUGAKU;
	private String DETAILS_HOUGAKU;
	private String ZHIKKAN;

	public HOUGAKU(int YEAR_SHIMO, float DO, String HOUGAKU, String DETAILS_HOUGAKU, String ZHIKKAN) {
		this.YEAR_SHIMO = YEAR_SHIMO;
		this.DO = DO;
		this.HOUGAKU = HOUGAKU;
		this.DETAILS_HOUGAKU = DETAILS_HOUGAKU;
		this.ZHIKKAN = ZHIKKAN;
	}

	public int GetYEARSHIMO() {
		return YEAR_SHIMO;
	}

	public float GetDO() {
		return DO;
	}

	public String GetHOUGAKU() {
		return HOUGAKU;
	}

	public String GetDETAILS_HOUGAKU() {
		return DETAILS_HOUGAKU;
	}

	public String GetZHIKKAN() {
		return ZHIKKAN;
	}
}
