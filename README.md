# Blogger
Just an other blogging system in Java but stored in-memory.. buh.. 
Supports:
- adding new Post,labels,comments
- retrieving posts, posts for particular labels and users etc
- bulk push comments,labels

No proper user management system included yet

# Usage Examples

 - Create a new user:
  UUID u=b.SignUpUser("Baskar");
  
 - Submit a new post:
  UUID x=b.SubmitPost("Hello world", u);
 
 - Add comment/list of comments to a post:
  b.AddCommentToPost("intro", x);

 - Add label/list of labels to a post:
  b.AddLabelToPost("computer", x);

 - Get the labels of a post:
  b.GetLabelsOfPost(x)
 
 - Get the posts with a label and by a user:
  b.GetPostsWithLabelOfUser("computer", u)
  
 And more...
 

