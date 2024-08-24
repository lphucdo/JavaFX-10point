package model;

public class XeMay {
	private String maXeMay;
	private String tenXeMay;
	private int dungTich;
	private int khoiLuong;
	private int giaBanLe;
	private String imgPath = " ";
	
	
	public XeMay(){}
	
	public XeMay(String maXeMay){
		this.maXeMay = maXeMay;
	}
	public XeMay(String maXeMay, String tenXeMay, int dungTich, int khoiLuong, int giaBanLe) {
		this.maXeMay = maXeMay;
		this.tenXeMay = tenXeMay;
		this.dungTich = dungTich;
		this.khoiLuong = khoiLuong;
		this.giaBanLe = giaBanLe;
		
	}
	public XeMay(String maXeMay, String tenXeMay, int dungTich, int khoiLuong, int giaBanLe, String imgPath) {
		this(maXeMay,tenXeMay,dungTich,khoiLuong,giaBanLe);
		this.imgPath = imgPath;
	}
	
	@Override
	public String toString() {
		
		return "maXeMay:" +maXeMay+ " tenXeMay:" + tenXeMay + " dungTich:" + dungTich + " khoiLuong:" + 
		khoiLuong + " giaBanLe:" + giaBanLe + " imgPath:"+ imgPath + "\n";
	}

	public String getMaXeMay() {
		return maXeMay;
	}

	public String getTenXeMay() {
		return tenXeMay;
	}

	public int getDungTich() {
		return dungTich;
	}

	public int getKhoiLuong() {
		return khoiLuong;
	}

	public int getGiaBanLe() {
		return giaBanLe;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setMaXeMay(String maXeMay) {
		this.maXeMay = maXeMay;
	}

	public void setTenXeMay(String tenXeMay) {
		this.tenXeMay = tenXeMay;
	}

	public void setDungTich(int dungTich) {
		this.dungTich = dungTich;
	}

	public void setKhoiLuong(int khoiLuong) {
		this.khoiLuong = khoiLuong;
	}

	public void setGiaBanLe(int giaBanLe) {
		this.giaBanLe = giaBanLe;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
