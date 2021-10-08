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

- - - -
**<font size="5">MVP + still a few bugs :(</font>**

*25-Sep, 2021.*
* Fixed up labelling, added voice speed modulation, and refactored modals. Macron integration and sound effects also added
* Much polishing of the codebase needs to be undertaken. Bugs exist in the rewards modal, festival callback needs implementation, and attribution modal needs to be finalised.
* Plan tomorrow/tonight is to address those areas and spend some time polishing the codebase + checking for errors. We should submit by tomorrow evening.

- - - -
**<font size="5">Submission!</font>**

*26-Sep, 2021.*
* After a scary time with the jar file not compiling, we submitted woohoo! A few bugs were spotted and resulted. Monday's task will be ensuring it works well with the all the word lists.

- - - -
**<font size="5">Checking Submission</font>**

*27-Sep, 2021.*
* Submission checked with no concerns :))

- - - -
**<font size="5">Presentations</font>**

*28 ~ 30-Sep, 2021.*
* We met several times to construct our presentation, prepare our scripts, and present to our client (Nasser)
* I presented on collaboration and improvements, Serena introduced and also talked about design decisions and Jo talked on the demo with error handling
* We received very good feedback overall and suggestions for functional improvements have been documented on Notion. In addition, the discussion points from the client meeting have also been documented on Notion.

- - - -
**<font size="5">A4 Team Meeting</font>**

*2-Oct, 2021.*
* We met to break up tasks into what we are to fix for our A3 implemetation, what is needed to be done to satisfy the A4/Project brief, and the stretch goals we would like to achieve. We are conscious of the approximately one week we have to deliver our project.
* We've set next Wednesday evening as the deadline to get all the mandatory features done. We're planning to have two collaborative sessions - one on Monday and one on Tuesday - to assist with this.
* We use the 'trello-like' interface on Notion to assign responsibilities.
* I've done some restructuring of existing QuizGameController today after lengthy conversation in our meeting. Design and codebase restructuring to be done by tomorrow preferably.

- - - -
**<font size="5">Collaborative Session + Timer + Ambitious Goals!</font>**

*4-Oct, 2021.*
* We met today in a collaborative session to review the new design and give feedback. We're taking an ambition stance with trying to animate a timer and also creating a 'skribblio-like' input approach instead of one textfield.
* Design to be finalised today and FXML to be added
* Many modals need to be made and implemented still.
* Timer implementation and colour interpolation finished.
* Planning to meet again tomorrow in yet another collaborative session

- - - -
**<font size="5">Collaborative Session + FXML + Custom Input Field Pain</font>**

*5-Oct, 2021.*
* We met today in a collaborative session to review the work that has been done. Major strides in refactoring and design implementation have been performed.
* A few bugs were fixed. The custom input field is proving to be a pain but we hope to resolve this tomorrow or the day after. Many modals are yet to be implemented.
* Will need to confirm with team on the next collaborative session.

- - - -
**<font size="5">Collaborative Session + Custom Input Field Progress</font>**

*6-Oct, 2021.*
* We met today in a collaborative session to review the work that has been done. Major strides in the custom input field / with much of the functionality working! In terms of technical feasibility, it should be sufficient. All that is left to do is just smash out all the rest of the work as fast as possible.
* Again, will meet tomorrow for another collaborative session where we will hopefully get all modals done and the basic functionality sorted. Then, Friday can focus on fixing bugs and starting stretch goals.

- - - -
**<font size="5">Results Screen Implementation Started</font>**

*7-Oct, 2021.*
* Was a difficult day to get collaboration as assignments loomed for all of us. Results screen dynamic generation has been started and is newar completion (just needs onEnter press and some shifting of assets)
* We do need a lot of work still to be done. Gamescreen input field functionality requires conversation within team as there are issues with calling onEnterContinue from within a handler of a static class.

- - - -
**<font size="5">Collaborative Session + Much Progress!</font>**

*8-Oct, 2021.*
* We met today in a collaborative session to review the work that has been done. Major strides in the custom input field again with just macrons, disabling the textfield, and hint values to be added.
* Pause modal finished with timer integration.
* Results screen finished. Needs to hide the text field :)
* Backend started.
* Tomorrow, we aim to have a very big collaborative session where we finish the inputfield, implement
the ranked/practice modal and implement that, fix the numbering of the rewards screen and iron out any other bugs. If time permits, we will work on finishing the backend REST API and implementing user profile.