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
