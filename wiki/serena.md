# Wiki
**<font size="5">SE206-A3 Group 11</font>**
*Serena Lau</br>UPI: slau981*

*For the purpose of documenting the progression of A3 / Project*
- - - -

**First Team Meeting: Team Dynamic + Project Direction**

*16 Sept 2021.*

* Collaboration:
  * Discussed, decided and established Team Agreement
  * Set up communication and work platforms (Notion, Figma, Messenger and Zoom)
* Project scope and idea discussions:
  * Each member demoed their A2 submissions to gauge available tools and implementations
  * Brainstormed ideas for design/features using FigJam
  * Target Audience decided: 8-12 year olds 
  * Created a To-Do map to schedule desired progress 

- - - -
**GUI Design**

*17 ~ 20 Sept 2021.*
* Created GUI design and formatting via Figma 
  * Overall application theme
  - Iterated and polished design ideas on Figma for all screens and screen components 
    -  Main 'entry' screen
    -  Topic selection screen
    -  Game screen
    -  Rewards modal 
    -  Settings modal
    -  Exit screen
-  FXML files created using SceneBuilder

- - - -
**Gamescreen and rewards modal implementations**

*25 Sep, 2021.*
- Major progress in completing assignment project, most implementation completed and polished 
- Refactored structure to use a StackPane to better accommodate the use of modals
- Implemented hint label message
- Fixed design issues on SceneBuilder

- - - -
**A4 Team Meeting**

*2 Oct, 2021.*

- Collaboration
  - Discussed feedback from clients and improvements on A3
-  A4 ideas for mandatory requirements
-  A4 ideas for stretch goals
-  We collated all ideas and broke them into small 'unit' tasks, then spread them out in a scheduled way using Notion's To-Do map platform
  - Also used this to assign each task card to different members to aid the collaboration process
- Set meeting times for future dates, mainly for collaborative coding purposes - next one is on Monday 4 October.  

- - - - 

**Revamped game screen implementation** 

*4 Oct, 2021.*
- Completed most of the new game screen design and other design modifications on Figma
- For the purposes of design and intuitive user flow, we decided to combine the components of showing how many letters in the word + the input text field into one component, by having one small input box for each letter 
- Had some group discussions on issues that may arise with this, potential solutions and potential implementation methods
- Built more FXML files based on the new Figma designs

- - - -
**Fixing major bugs due to new implementation method + Implement small features**

*5 Oct, 2021.*
- Today we had a group meeting for collaborative coding 
- Lots of bugs and errors with running it due to the complete change of game screen implementation, but we managed to fix them and it runs!
- Refactored some duplicate code by adding a method findNodesByID that returns nodes by an ID specified
- Completed implementation of menu screen icon labels showing when hovered over using the new findNodesByID implementation
- Refactored Rewards Screen stars using the new findNodesByID implementation
- Continued building the new/modified FXMl files 

- - - - 
**Collaborative session: pause modal + results screen** 

*8 Oct, 2021*
- Collaborative coding sesh with some big strides:
  - Finished implementation of pause modal
  - Implemented pausing of timer on opening of modals
  - Results screen finished with user display
  - Major progress in game screen implementation of the text input fields
  - Tried implementing custom fonts with no success :( will try again later

- - - -
**Rewards screen makeover + collaborative planning and other miscellaneous implementations** 

*9 Oct, 2021*
- Finally got around to redesigning the rewards screen to look a bit better, also added a user high score display
- Implemented functionality of saving user high score and displaying on the rewards screen, with indication if the user sets a new high score
- Planned out things still to be done for the project in the (very) near future, planning done in collaborative meeting with Notion - discovered coloured property labels! :)
- Game screen almost complete, looks great and implementation with the separated textfield boxes working fine
- Still some more bits and bobs to be completed, aiming to patch these all out by tomorrow so we have a fully functioning prototype ready for submission
- Progress on backend also made
