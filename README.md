src/
├── model/
│   ├── User.java
│   ├── Post.java
│   ├── Comment.java
│   └── DataStore.java
├── view/
│   ├── LoginFrame.java
│   ├── RegisterFrame.java
│   ├── MainFeedFrame.java
│   ├── ProfileFrame.java
│   ├── CreatePostDialog.java
│   └── components/
│       ├── PostPanel.java
│       ├── CommentPanel.java
│       └── UserCardPanel.java
├── controller/
│   ├── AuthController.java
│   ├── PostController.java
│   ├── UserController.java
│   └── FeedController.java
└── Main.java

Phase 1: Setup & Model Classes (Step 1)

Create basic project structure
Implement model classes:

User.java
Post.java
Comment.java


Create DataStore.java (Singleton) with hardcoded users and posts
Create Session.java for managing logged-in user

Phase 2: Authentication UI (Step 2)

Create LoginFrame.java

Username/password fields
Login button
Link to register


Create RegisterFrame.java

Input fields for user details
Register button


Implement AuthController.java

Login validation
Registration logic



Phase 3: Main Feed Interface (Step 3)

Create MainFeedFrame.java (main container)

Top navigation bar
Sidebar (profile info, navigation)
Center panel (feed)


Create PostPanel.java (reusable component)

User info display
Post content
Like button with count
Comment button
Comment section


Implement feed toggle (All Posts / Following Only)

Phase 4: Create Post Feature (Step 4)

Create CreatePostDialog.java

Text area for content
Image selection button (JFileChooser)
Post button


Implement PostController.java

Create post logic
Add post to feed



Phase 5: Social Interactions (Step 5)

Implement like/unlike functionality
Implement comment system

Add comment dialog/panel
Display comments under posts


Update UI dynamically when interactions occur

Phase 6: User Profile (Step 6)

Create ProfileFrame.java

Display user info (photo, name, bio)
User statistics (followers, following, posts)
Edit profile button (for own profile)
Follow/Unfollow button (for other profiles)
User's posts grid


Implement UserController.java

View profile
Edit profile
Follow/unfollow logic



Phase 7: Follow System (Step 7)

Implement follow/unfollow functionality
Create followers/following list views
Update feed to show only followed users' posts

Phase 8: Polish & Enhancements (Step 8)

Add icons and images
Improve UI styling (colors, fonts, spacing)
Add scroll panes for long content
Error handling and validation
Add timestamps to posts and comments