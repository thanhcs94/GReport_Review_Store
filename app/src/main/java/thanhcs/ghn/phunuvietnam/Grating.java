package thanhcs.ghn.phunuvietnam;

import com.google.gson.annotations.Expose;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Grating{

	@SerializedName("note")
	@Expose
	private String note;

	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("created_date")
	@Expose
	private String createdDate;

	@SerializedName("point")
	@Expose
	private int point;

	@SerializedName("client_id")
	@Expose
	private int clientId;

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setPoint(int point){
		this.point = point;
	}

	public int getPoint(){
		return point;
	}

	public void setClientId(int clientId){
		this.clientId = clientId;
	}

	public int getClientId(){
		return clientId;
	}

	@Override
 	public String toString(){
		return 
			"Grating{" + 
			"note = '" + note + '\'' + 
			",id = '" + id + '\'' + 
			",created_date = '" + createdDate + '\'' + 
			",point = '" + point + '\'' + 
			",client_id = '" + clientId + '\'' + 
			"}";
		}
}