Here's my description of each package in the frontend code. This README
is probably really confusing, so let me know if you have questions.
Go State.
	-Ya boy, Grayson.

activities:
	Contains all Android Activities in our app. Right now, the app starts in SignInActivity, where the user
	either logs in with Google or chooses not to log in. Then, it starts MainActivity, passing the GoogleSignInAccount
	object to MainActivity. If the user didn't sign in, the object will be null, and MainActivity will not display
	ProfileFragment and FavoritesFragment.

api:
	Has Webservice interface, which handles HTTP requests and stuff to our server with the Retrofit library.
	Retrofit automatically parses the JSON from the backend and creates objects using the classes in database.entity.
	Look up how to use Retrofit annotations before adding methods here; it's pretty simple.

database:
	This is for data persistance with Room. There are three sub-packages:
	converter:
		Converter classes for date formatting. We can ignore this.
	dao:
		All database access objects. We'll need a DAO for every class in entity.
	entity:
		These are our main objects (i.e., User, Food, Restaurant). Retrofit creates these
		objects when pulling JSON info from our backend.

di:
	This is contains all the necessary files for dependency injection with Google's Dagger 2 library.
	I don't really understand how all this works yet. I'll get back to you. It has these sub-packages, though:
	component:
		Component classes. I have no idea what these do.
	key:
		Key classes that inform Dagger 2 which ViewModel goes with which Fragment and what not. If we make a 
		new ViewModel, we have to add it to ViewModelKey.
	module:
		AppModule has a lot of important stuff going on. Other than that, I don't know what's going on in here yet.

fragments:
	All the fragments (miniature activities, basically) in our app, including our three main screens.
	This is where we'll be adding all the fancy GUI	components like RecyclerViews.

repositories:
	Contains repositories which pull data from our Room database and our backend and supply it to all the ViewModels.
	This is the "single source of data" in our design pattern. I think, for simplicity, we should try to have just one
	repository class, but we might decide to make separate repo's for Food and Users.

view_models:
	Contains the ViewModel factory (FactoryViewModel.java) and all our ViewModels. There should be a ViewModel for every
	Fragment in our app. A ViewModel is basically a container for all the data that is shown in a Fragment.


		