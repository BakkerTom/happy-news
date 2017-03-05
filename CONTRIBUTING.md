# Contributing

We use the [GitHub Flow](https://guides.github.com/introduction/flow/) in this project. Specific guidelines are discussed below.
 
## Getting Started

Follow these instructions to set up your development environment. You can find additional instructions for IntelliJ below.

1. Clone the repository.
2. Download the [Android SDK](https://developer.android.com/sdk/index.html).
3. Create a `local.properties` file in the project root containing the path to your Android SDK:

    ```properties
    sdk.dir=/path/to/android/sdk
    ```

That's everything you need to start developing. The Gradle wrapper (`gradlew` and `gradlew.bat`) can be used to execute gradle tasks.

If you want some information on getting your IDE ready, please do read on.

### IntelliJ

1. Create a new project from existing sources and select the cloned project.
2. Choose to import from Gradle.
3. When asked choose to enable auto-import.
4. When asked auto configure the Android project.
5. Run the `idea` Gradle task.

## Creating Changes

1. Start a new branch from `master` and name it according to the relevant user story. The format is `#-name`. Example:
    
    ```bash
    git checkout master
    git checkout -b 15-create-contributing-guidelines
    ```
2. Make some modifications and commit the changes.
 
    ```bash
    git add CONTRIBUTING.md
    git commit -m "Update CONTRIBUTING.md"
    ```

3. Push the new branch.

    ```bash
    git push -u origin 15-create-contributing-guidelines
    ```

4. Create some more changes and push them.

    ```bash
    git push
    ```

5. When the feature is done create a new Pull Request (PR) using the GitHub web interface.
See [this blog](https://github.com/blog/1943-how-to-write-the-perfect-pull-request) for tips on writing a good Pull Request.
6. The PR has to be reviewed before it can be merged. You can request a reviewer.
The build will also be tested automatically, these tests have to pass.
(See Travis CI status: [![Build Status](https://travis-ci.org/BakkerTom/happy-news.svg?branch=master)](https://travis-ci.org/BakkerTom/happy-news))
7. If somethings not quite right you can create some more changes and push them to the same branch. The changes will automatically be picked
up in the PR.
8. When all conditions are met the PR can be merged and the branch can be removed.

You can start a PR before the code is ready, just add a `[WIP]` tag before the title to indicate it is a Work In Progress.
This can be useful if you want to discuss the feature.

## Reviewing a Pull Request

To review a PR go to the 'Files Changed' tab of the PR and click the 'Review changes' button.
Write some feedback and select if you want to approve the merge.

Comments on a specific lines can be added by hovering the line in the source code viewer and selecting the '+' icon.

Changes added after your last review can be viewed using the 'View changes' button next to your review in the 'Conversation' tab.
