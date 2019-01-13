# MKS21X-FinalProject
## Development Logs:
### January 3rd, 2019: 
#### Bryan Lai: <img src="https://i.pinimg.com/originals/8a/8d/4a/8a8d4af305ade9eb43684e83d70874e8.png" width="40" length="40">
> * Created Pokemon Class
>> * Added Instance Variables
>> * Added Accessor Methods
>> * Added the ability to add moves to its moveset.
>> * Added basic toString()
> * Created Move Class
>> * Added Instance Variables
>> * Added Accessor Methods
>> * Added basic toString()
>> * Added implementation of decrease and increase of PP.
> * Create Sample Test Driver to test methods for Move and Pokemon.
#### Qiong Zhou Huang: <img src="https://yt3.ggpht.com/a-/AAuE7mA70LJHFeabrZd2uABvShclmSZFVYveSIUUpg=s900-mo-c-c0xffffffff-rj-k-no" width = "40" length = "40">
> * Created Tile Class
>> * Added Instance Variables
>> * Added Constructors
>> * Added Accessors
>> * Added makeWalkable method to setColor and change variable at same time
> * Created Map Class
>> * Created Instance Variables for start coordinates and a 2D array of the Tiles
>> * Created buildArea method to create an area around a point is walkable with randomly generated sizes
>> * Started work on terrain generation using buildArea
> * Wrote main for PokemonMysteryD class to view Map class using Lanterna
### January 4th, 2019: 
#### Qiong Zhou Huang: <img src="https://yt3.ggpht.com/a-/AAuE7mA70LJHFeabrZd2uABvShclmSZFVYveSIUUpg=s900-mo-c-c0xffffffff-rj-k-no" width = "40" length = "40">
> * Tweaked Map Class
>> * Changed the way swaths of walkable land were generated by changing how the path was generated
>> * Adjusted values to ensure that all the walkable land were always connected
>> * Fixed new bugs that prevented map from displaying properly
#### Bryan Lai:  <img src="https://i.pinimg.com/originals/8a/8d/4a/8a8d4af305ade9eb43684e83d70874e8.png" width="40" length="40">
> * Add location x,y instance variables for the Pokemon class.
>> * Also edited constructor and add the "symbol" instance variable.
> * Create Player Class that utilizes the Pokemon class.
>> * Create accessor methods, mutator methods, etc.
> * Update Driver with code utilizing the Player class.
### January 5th - 6th, 2019: Occupied with StuyPulse Robotics. (Start of Build Season)
>>> To sleep or to work on APCS project? A wise pokemon trainer told me to sleep.
### January 7th, 2019:
#### Bryan Lai: <img src="http://data.whicdn.com/images/32538816/mudkip_by_cheepers-d46kd61_large.png" width="40" length="40">
> * Merge the changes in bl/pokemon to master.
> * Change int color to an array of ints to be used for specific methods dealing with background/fore-color in the lanterna documentation.
> * Git rebase to remove changes in a previous commit that caused issues before merging.
#### Qiong Zhou Huang: <img src = "https://pbs.twimg.com/profile_images/651942416866480128/chUjFeMM.jpg" width = "40" length = "40">
> * Changed map starting size and randomized starting location near center on map
> * Changed from using Terminal.color to using rgb values for the map
> * Created setBg method to use replace how the background is currently set and to be used to reset background color after Pokemon has moved on it.
> * Dealt with lots of problems after merging before pushing commits and messing up with moving the HEAD. 
### January 8th, 2019:
#### Qiong Zhou Huang: <img src="https://runes.lol/image/generated/championtiles/Teemo.jpg" width="40" length="40">
> * Changed setBg
> * Created method to spawn pokemon at given location
> * Created methods to randomly spawn player pokemon
#### Bryan Lai: <img src="http://data.whicdn.com/images/32538816/mudkip_by_cheepers-d46kd61_large.png" width="40" length="40">
> * Started on options screen.
> * Created new branch: "options"
> * Understand Lanterna implementation
>> * putString(), refresh(), etc.
### January 9th, 2019:
#### Bryan Lai:
> * Completed transition between options and gameplay.
> * Background and color schemes were edited.
> * Added new imports for "TerminalSize", etc.
#### Qiong Zhou Huang:<img src="https://images-na.ssl-images-amazon.com/images/I/8166xCVDGnL._SY355_.jpg" width="40" length="40">
> * Made sure islands don't spawn on map
> * Spawns moving pokemon
> * Figured out how to revert to orignal colors on the path behind pokemon
> * Made walls not walkable
> * Made pokemon not move when it reaches a wall, but there's an unknown bug with that that freezes the pokemon
### January 10th, 2019:
#### Qiong Zhou Huang:<img src="https://images-na.ssl-images-amazon.com/images/I/8166xCVDGnL._SY355_.jpg" width="40" length="40">
> * Did lots and lots of debugging and fixed the code such that the player Pokemon stops when it hits a wall
> * Changed map size
### January 12th, 2019:
#### Qiong Zhou Huang:<img src="https://scontent-lga3-1.xx.fbcdn.net/v/t1.0-1/c0.76.240.240a/p240x240/36175988_608022832930083_429753688903385088_n.jpg?_nc_cat=111&_nc_ht=scontent-lga3-1.xx&oh=9a19b862eac66ca12b7cd1bed78bfbd1&oe=5CCE39C7" width="40" length="40">
> * Spawned Partner Pokemon
> * Move partner Pokemon along with player pokemon
> * Made Pokemon randomizer class to choose random pokemons to start with
> * Created stairs on map. Starts new level once you reach those stairs 
> * Used POkemon randomizer class to spawn random pokemons on map
> * added code to make sure that Pokemons don't walk on top of each other
