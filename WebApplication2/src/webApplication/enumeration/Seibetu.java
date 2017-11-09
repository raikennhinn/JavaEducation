package webApplication.enumeration;

public enum Seibetu {
	 Otoko(0, "男"),// 男
	 Onna(1, "女");// 女

	private final int scode;
	private final String sname;

	 private Seibetu(int scode,String sname) {
		 this.scode = scode;
		 this.sname = sname;
	 }

	 public static Seibetu getSeibetu(int code) {
		 if(code == Seibetu.Otoko.scode) {
			 return Seibetu.Otoko;
		 } else if(code == Seibetu.Onna.scode) {
			 return Seibetu.Onna;
		 } else {
			 throw new IllegalArgumentException("不明な性別です。");
		 }
	 }

	 public int getScode() {
		 return this.scode;
	 }

	 public String getName() {
		 return this.sname;
	 }
}
