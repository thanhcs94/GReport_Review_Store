package thanhcs.ghn.phunuvietnam;

public class GHNObjects{

	private String imgUrl;
	private String imgName;
	private String dateTime;
	private int uid;
	private String phone;
	private String name;
	private String message;

	public GHNObjects(String imgUrl, String imgName, int uid, String message) {
		this.imgUrl = imgUrl;
		this.imgName = imgName;
		this.uid = uid;
		this.message = message;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setImgName(String imgName){
		this.imgName = imgName;
	}

	public String getImgName(){
		return imgName;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setUid(int uid){
		this.uid = uid;
	}

	public int getUid(){
		return uid;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"GHNObjects{" + 
			"imgUrl = '" + imgUrl + '\'' + 
			",imgName = '" + imgName + '\'' + 
			",dateTime = '" + dateTime + '\'' + 
			",uid = '" + uid + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}