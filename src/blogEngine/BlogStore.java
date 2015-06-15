package blogEngine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BlogStore {

	/**
	 * @param args
	 */
	
	Map<UUID,User> userStore;				//map of username as key and User Object as value
	Map<UUID,Post> postStore;				//map of postId as key and Post Object as value
	Map<UUID,List<UUID>> userPosts; 		//map of userId as key and list of postIds as value
	Map<String,List<UUID>> labeledPosts;	//map of label as key and list of postIds as value
	
	// set this to a print stream if you want debug info
	// sent to it; otherwise, leave it null
	static private PrintStream debugStream;

	// call this to send the debugging output somewhere
	static public void setDebugStream(PrintStream ps) {
		debugStream = ps;
	}
	
	// send debug info to the print stream, if there is one
	static public void debug(String s) {
	  if (debugStream != null)
	    debugStream.println(s);
	}
	
	// All hashmap initializations
	public BlogStore(){
		userStore = new HashMap<UUID,User>();
		postStore = new HashMap<UUID,Post>();
		userPosts = new HashMap<UUID,List<UUID>>();
		labeledPosts = new HashMap<String,List<UUID>>();
	}
	
	
	public String Sanitize(String s){
		if(s!=null)
			return s.trim().toLowerCase();
		else
			return s;
	}
	
	
	// Create a new user. I've not written the code for password storage
	// which requires a SALT+hashing. This function is just for testing
	// purposes to add users
	
	public UUID SignUpUser(String username){
		String usernameSanitized = Sanitize(username);
		User user = new User(usernameSanitized);
		if(userStore.containsKey(usernameSanitized))
			debug("User with the same username already exists");
		userStore.put(user.userId, user);
		return user.userId;
	}
	
	// Get the user name of an user with Id
	
	public String GetUserName(UUID userId){
		
		if(!userStore.containsKey(userId))
		{
			debug("User does not exist");
			return null;
		}
		return userStore.get(userId).userName;
	}
	
	
	// Create a post for the user
	
	public UUID SubmitPost(String postContent,UUID userId){
		
		if(!userStore.containsKey(userId))
		{
			debug("User does not exist");
			return null;
		}
		
		Post post = new Post(postContent);
		List<UUID> postsList;
		UUID postId = post.GetPostId();
		postStore.put(postId, post);
		
		if(userPosts.containsKey(userId))
		{
			userPosts.get(userId).add(postId);
		}
		else
		{
			postsList = new ArrayList<UUID>();
			postsList.add(postId);
			userPosts.put(userId, postsList);
		}
		debug("Post added with id:"+postId);
		return postId;	
	}
	
	// Retrieve all posts of one user
	
	public List<String> GetPostsOfUser(UUID userId){
		List<String> posts = new ArrayList<String>();
		if(!userStore.containsKey(userId))
		{
			debug("User does not exist");
			return null;
		}
		for(UUID postId:userPosts.get(userId))
		{
			if(postStore.containsKey(postId))
				posts.add(postStore.get(postId).postContent);
		}
			
		return posts;
	}
	
	// Retrieve a post with its id
	
	public String GetPostWithId(UUID postId){
		if(!postStore.containsKey(postId))
			return null;
		
		return postStore.get(postId).postContent;
		
	}
	
	
	// Retrieve a post's creation time with its id
	
	public String GetPostCreatedTime(UUID postId){
		if(!postStore.containsKey(postId))
			return null;
		
		return postStore.get(postId).timeCreated;
		
	}
		

	// Search for all posts with a given keyword
	
	public List<String> SearchPosts(String keyword){
		List<String> searchResults = new ArrayList<String>();
		for(UUID postid:postStore.keySet())
		{
			if(postStore.get(postid).postContent.contains(keyword))
				searchResults.add(postStore.get(postid).postContent);
		}
		return searchResults;
	}
	

	// Retrieve IDs all posts

	public Set<UUID> GetAllPosts(){
		
		return postStore.keySet();
	}

	// Retrieve names all users

	public Set<UUID> GetAllUsers(){
		
		return userStore.keySet();
	}
		
		
	// Add a single label to a post
	
	public boolean AddLabelToPost(String label,UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return false;
		}
		
		if(!labeledPosts.containsKey(label))
		{
			List<UUID> postIds = new ArrayList<UUID>();
			labeledPosts.put(label,postIds);
		}
		labeledPosts.get(label).add(postId);
		return postStore.get(postId).AddLabel(label);
	}
	
	// Add labels to a post
	
	public boolean AddLabelsToPost(List<String> labels,UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return false;
		}
		
		for(String label:labels){
			if(!labeledPosts.containsKey(label))
			{
				List<UUID> postIds = new ArrayList<UUID>();
				labeledPosts.put(label,postIds);
			}
			labeledPosts.get(label).add(postId);
			return postStore.get(postId).AddLabel(label);
		}
			
		return true;
		
	}
	
	// Add a single comment to the post
	
	public boolean AddCommentToPost(String comment,UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return false;
		}
		
		return postStore.get(postId).AddComment(comment);
	}
	
	// Add comments to the post
	
	public boolean AddCommentsToPost(List<String> comments,UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return false;
		}
		
		for(String comment:comments)
			return postStore.get(postId).AddComment(comment);
		
		return true;
	}
	
	// Get all the labels of a post
	
	public List<String> GetLabelsOfPost(UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return null;
		}
		else
			return postStore.get(postId).labels;
	}
	
	// Get all the comments of a post
	
	public List<String> GetCommentsOfPost(UUID postId){
		if(!postStore.containsKey(postId))
		{
			debug("Post does not exist");
			return null;
		}
		else
			return postStore.get(postId).comments;
	}
	
	// Get all the posts with the given label
	
	public List<String> GetPostsWithLabel(String label){
		if(!labeledPosts.containsKey(label))
		{
			debug("Label does not exist");
			return null;
		}
		List<UUID> postIds = labeledPosts.get(label);
		List<String> posts = new ArrayList<String>();
		for(UUID postId:postIds)
		{
			posts.add(postStore.get(postId).postContent);
		}
		return posts;
	}
	
	// Get all the posts with the given label and from a given user
	
	public List<String> GetPostsWithLabelOfUser(String label,UUID userId){
		
		if(!labeledPosts.containsKey(label))
		{
			debug("Label does not exist");
			return null;
		}
		if(!userPosts.containsKey(userId))
		{
			debug("User does not have any post made yet");
			return null;
		}
		
		// retrieve required PostIds of given label and user and find the
		// intersection. This runs on average O(n*log m)
		List<UUID> postIdsOfLabel = labeledPosts.get(label);
		List<UUID> postIdsOfUser = userPosts.get(userId);
		postIdsOfLabel.retainAll(postIdsOfUser);
		
		List<String> posts = new ArrayList<String>();
		for(UUID postId:postIdsOfLabel)
		{
			posts.add(postStore.get(postId).postContent);
		}
		return posts;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				BlogStore b = new BlogStore();
				BlogStore.setDebugStream(System.out);
				UUID u=b.SignUpUser("Baskar");
				UUID v=b.SignUpUser("Avinash");
								
				UUID x=b.SubmitPost("Hello world", u);
				UUID y=b.SubmitPost("Hello earth", v);
				
				b.AddCommentToPost("waste", x);
				b.AddCommentToPost("cool", y);
				b.AddLabelToPost("computer", x);
				b.AddLabelToPost("computer", y);
				b.AddCommentToPost("anything", y);
				b.AddLabelToPost("nature", y);
				
				for(String s:b.GetLabelsOfPost(x))
					System.out.println(s);
				System.out.println(b.GetPostsWithLabelOfUser("nature", v));
	}

}
