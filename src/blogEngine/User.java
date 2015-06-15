package blogEngine;

import java.util.UUID;

class User {

	/**
	 * @param args
	 */
	UUID userId;
	String userName;
	
	User(String name){
		this.userId=UUID.randomUUID();
		this.userName=name;
	}
	
	public UUID getUserId(){
		return this.userId;
	}

}
