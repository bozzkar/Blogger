/**
 * 
 */
package blogEngine;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Baskar
 *
 */
class Post {

	/**
	 * @param args
	 */
	UUID postId;
	String postContent;
	String timeCreated;
	List<String> labels = new ArrayList<String>();
	List<String> comments = new ArrayList<String>();
	
	Post(String content){
		this.postId = UUID.randomUUID();
		this.postContent = content;
		this.timeCreated = new Timestamp(System.currentTimeMillis()).toString();
	}
			
	public String getTimeCreated(){
		return this.timeCreated;
	}
	
	public UUID GetPostId(){
		return this.postId;
	}
	
	public boolean AddLabel(String label){
		return labels.add(label);
	}
	
	public boolean AddComment(String comment){
		return comments.add(comment);
	}
	
}
