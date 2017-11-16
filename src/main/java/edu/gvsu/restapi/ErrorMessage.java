package edu.gvsu.restapi;
import org.json.JSONObject;

/**
 * Represent an error.
 *
 * @author Jonathan Engelsma (http://themobilemontage.com)
 *
 */
public class ErrorMessage {

	public JSONObject toJSON() {
		JSONObject jsonobj = new JSONObject();
		try {
			jsonobj.put("error", "An error occured");
			return jsonobj;
		} catch (Exception e) {
			return null;
		}
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("error:");
		sb.append("An error occured");
		return sb.toString();
	}
}
