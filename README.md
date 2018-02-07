# SmartMeds

A tool for identifying prescription medicines

- [Work Flows](#work-flows)

## Project start up instructions
IDE
1.	If you don’t have it, [download and install current version of Android Studio.](https://developer.android.com/studio/index.html)
2.	**If you currently have Android Studio, update it to the most current version**. At the time of writing, the current version is 3.0.1. You can do so by going to the toolbar, select Help -> Check for Updates. After updating, you might get a prompt to update Gradle, do so. After everything restarts and Gradle rebuilds, close any open projects, close AS and restart your machine. 
3.	Create a new application with a Basic Activity. Run and view on an emulator. If you don’t have an emulator installed, it will prompt you to install and make one. This might involve AS downloading software a couple times, just follow the prompts.  I chose a Nexus running API 24, but it doesn’t really matter.
4.	If you don’t have an emulator showing you an app that says “Hello World” something went wrong, fire up Google and try again.
Project
5.	Have a [GitHub](https://github.com) account.
6.	Have [Git installed](https://git-scm.com/) on your machine.
7.	Create a local folder. Mine is C:/Repos/4450Project
8.	Open command prompt or terminal to that location. Example: cd Repos/4450Project
9.	Go to the remote repository
10.	Click the caret on the green Clone or download button
11.	Click the clipboard icon next to the url to copy the link.
12.	Back at the command line, still in the same directory, type git clone paste_in_that_ url_here
13.	This will pull down all the files from the remote and create a local repository.
14.	When it is done churning, type git status, this should say that you are on the master branch and everything is just peachy
Build
15.	Take a deep breath, this might get ugly.
16.	Click Open, navigate to the directory where the new repo lives. Mine would be in C:/Repos/4450Project/actualRepoNameHere
17.	After opening, you might see errors about build tools highlighted in the output window, click the link.
18.	Be aware that AS is slow and particularly so when building a new project, watch for the tiny progress notifications in the bottom right.
19.	After it tries to build, you might receive errors about missing files from the build, take the option to exclude these files from the build.
20.	Click the hammer icon to Make the project
21.	If the build fails and gives an error about the SDK path, go to File -> Invalidate Caches / Restart . This will clean and reset the magical build path stuff that no one understands. AS will restart. After a few minutes of churning it will rebuild. Click the hammer icon again. After it finishes, click the green play button to run the app. If you get any other unintelligible errors, copy and paste them into google and pick the first Stack Overflow answer and give it a whirl. 
Editing Code
22.	BRANCHING: The code you have in your editor is on the Master branch of your local repository. Editing the master branch is not usually desirable. Go to the remote repo at Github and click the caret in the Branch: button. Select the branch you would like to branch from. For instance, we might want to add a feature where we add a title screen to the app, so we would make a branch off of master by having master highlighted and type in the new branch name in the text input. Please follow Linux style naming conventions, NO SPACES!  A good name describes the purpose of the feature or ties it to a task name or id. I would use title_screen for the example provided.
23.	From here, [Github Desktop](https://desktop.github.com/) will probably be sufficient, but command line is great, too. I’ll describe the following using desktop. However, desktop is always changing and not really good for anything more than the most basic functions, so I recommend learning some command line. Personally, I don’t memorize many of the commands, I just look them up when I need them. There are loads of good guides, [here is one](http://rogerdudler.github.io/git-guide/) that is rather simple.
24.	Open Github desktop and go to File -> Add local Repo (I have an older version, so yours might be different, good luck), browse to the repo we made earlier and add it. You should be able to see the project and any changes to it in Desktop now. 
25.	Click on Current Branch, this should show a list of branches, remote and local. If you can’t see the new branch you just made on Github.com, click the Fetch Origin button, this goes and gets the changes to the remote repo but doesn’t update your local in any way. You should be able to see the new branch now. Select the new branch you made.
26.	The code in your editor will update to the new branch (sometimes this takes a second in AS), any changes you make now will be on that new branch. 
27.	Make small commits. In our title screen example, we might add a fragment and it’s corresponding xml file. Once the empty java file and corresponding xml file are made, save all and go to Github desktop. Make sure you’re on the right branch. Write a short but meaningful commit message and commit.
28.	Proceed making commits after each function is written or file is added until the feature is done. 
29.	Test it, test it, test it. 
30.	Save all. Commit any open changes.
31.	Push to origin remote. The button might say push, publish, sync.
32.	Verify your changes to the remote repo. Go to the Github repo and select the appropriate branch on the branch button. Look at your commits.
33.	If all is well, create a Pull Request for your branch. There is typically a button offering this option after you have pushed. If not click the pull requests tab at the top. Make sure that you are requesting for your branch to be merged into the parent branch of this branch. It isn’t required, but request multiple reviewers. Please don’t merge your own pull requests unless absolutely necessary. Sometimes things work on your machine, and only your machine. 
34.	Once feature branches are merged, github will offer to delete that feature branch. Accept the delete. It’s not really gone, we can get it back if we wanted it, but we will never want it back. Keeping clutter around is really bad practice.
35.	Do this often and quickly. In every project I’ve worked on, people are afraid to make pull requests and their branches go for way too long. This makes it horrible to merge back in, often it is less work to re-write the feature to the current branch than to try to merge, in that case those kind of branches get snipped and all the work was done twice. Sometimes that is okay, you might want to try things out and make a mess on an experimental branch. Branches are cheap and can be deleted easily. In a nut-shell:
* Always Pull when you open the editor
* Make features small
* Always be committing
* Branches are cheap
* Make Feature Branch and it’s Pull Request on same day, no lingering branches
* No “Me” branches, where you go off on your own, don’t merge, and don’t let collaborators in. 

## Work Flows

### Search flow
![Search Flow](https://raw.githubusercontent.com/stv2pointo/smart_meds/master/docImages/search.png)
### myMeds flow
![myMeds Flow](https://raw.githubusercontent.com/stv2pointo/smart_meds/master/docImages/myMeds.png)
### crudMyMeds flow
![crudMyMeds Flow](https://raw.githubusercontent.com/stv2pointo/smart_meds/master/docImages/crudMyMeds.png)
