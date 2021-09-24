# Wiki
**<font size="5">SE206-A3 Group 11</font>**
*John Chen.</br>upi: jche428*

*For the purpose of documenting the progression of A3 / Project*
- - - -
**<font size="5">Meeting: Team Dynamic + Project Direction</font>**

*16-Sep, 2021.*

* Collaboration:
  * Team agreement formed and agreed upon
  * Platforms for communication decided
    * Notion: for meetings, documents, todos
    * Figma: for design and brainstorming
    * Messenger: for communication within the team
    * Zoom: for virtual meetings
* Development Environment setup
  * GitHub repo cloned
  * VSC setup w/ Scenebuilder + Java extensions
  * Scenebuilder installed and runtime arguments changed
  * VM v2 installed
* A3 / Project Ideas
  * Assignment 2 demo-ed
  * Target Audience: 8-12 year olds
  * Theme: Bee / Nature.
  * Brainstorming: FigJam undertaken + design started

- - - -
**<font size="5">Code restructuring</font>**

*17 ~ 18-Sep, 2021.*
* Code heirarchy established with controllers
  * `ApplicationController` as the root controller with scene controllers extending from such controller for each respective scene
* Codebase restructured for better readability
  * `models` folder to hold models
  * `screens` to hold all FXML screen controllers
* Other work done by members:
  * wordlists extracted and placed in `words` folder
  * `systeminterface` java file created and tests implemented
  * Design worked on and near finished.
  * FXML files created
- - - -
**<font size="5">Menu Screen Progress + Design Iteration</font>**

*19-Sep, 2021.*
* Wiki folders established
  * Wiki files created to track progress
* Menu/Topic/Exit Screens
  * Branch created to sort out simple navigation and topic selection.
  * Functionality complete but yet to be tested on FXML files
* Other work done by members:
  * Design on Figma iteratively worked upon with collaborative effort. Making heaps of progress there!
- - - -
**<font size="5">Revisiting Workflow and Collaboration Methodology</font>**

*20-Sep, 2021.*
* Questions collated for client meeting tomorrow including the use of copyrighted assets, as well as the use of backend API calls for the project scope. Document attached on Notion.
* We recognise that working on delegated tasks and coordinating who codes what ("divide and conquer") might not be the best practice going forward, both for code quality and collaboration.
* We plan to meet tomorrow to sort out how we are to deliver the MVP in a collaborative manner as well as incorporate client interests. Doing so will be difficult given the rather busy schedules this week with many internship interviews taking place.
* Other work done by members:
  * Design asset-ised and added to codebase.
  * Refactoring on `assets` directory required.

- - - -
**<font size="5">Client Meeting + Live Share Collaboration</font>**

*21-Sep, 2021.*
* Client Meeting notes are recorded on Notion. Comments were helpful and these will be taken as we iterate our desisn. However, it does seem the general direction is correct which is comforting.
* All assets were added to the codebase.
* Live Share Collaboration:
    * After the client meeting, the team met over zoom and VSC live share to get some work done on the assigment. We made quite a few big strides in a true collaborative effort which was nice. We've finished the navigation and started topic selection.

- - - -
**<font size="5">Ironing out Topic Selection</font>**

*22-Sep, 2021.*
* I quickly fixed up the topic selection implementation in my own time. There was also work done by others to make the 'buttons' pop out as well as finishing the design on Figma.
* Live Share Collaboration:
    * We then again collaborated via Live Share and Zoom and got much of the topic selection functionality and word list retrieval working.

- - - -
**<font size="5">Festival</font>**

*23-Sep, 2021.*
* I had a go at refactoring the readWords() function to accommodate different languages. This is finished in implementation. The changes of the word index banner is also implemented. A lot more still to be done in hopefully one more collaborative session tomorrow and Saturday.

- - - -
**<font size="5">Gamescreen + modals</font>**

*24-Sep, 2021.*
* Big strides were made today as we very very near completed the MVP (with only a few bugs to be fixed). Gamescreen finished implementation and modals setup.
* Collaboration was done well all in all; and again via Live Share and Zoom.
* Tomorrow we need to fix up the hint labels, check and implement the settings modal, and refactor the rewards modal. We also need macron integration and checking words for proper macron display. Other stretch goals are adding sound effects, adding fonts, and changing image assets.
* Reports also need to start being written.
