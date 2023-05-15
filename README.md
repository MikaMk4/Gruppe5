# HWR OOP Lecture - Group 5 Project (Leitner System) 

This repository contains a student project created for an ongoing lecture on object-oriented programming with Java at HWR Berlin (summer term 2022).

> :warning: This code is for educational purpose only. Do not rely on it!

## Abstract

For our project, we are creating a library that helps to implement the Leitner system for index card supported learning.

The most important features of our project are:
1. Topic creation
2. Card creation
3. Choice between 3,(5 and 7) box systems per training session
4. Drawing a random card from boxes that need to be learned
5. Card history


We encountered the following interesting problems:
- object oriented design (especially SRP)


## Feature List

[TODO]: # (For each feature implemented, add a row to the table!)

| Number | Feature                                       | Tests                                                                           |
|--------|-----------------------------------------------|---------------------------------------------------------------------------------|
| 1      | Topic creation                                | canCreateTopicWithName                                                          |
| 2      | Card creation                                 | canCreateCard, canGetQuestion, canGetAnswer, canGetId                           |
| 3      | Box creation                                  | x                                                                               |
| 4      | Box can hold cards                            | canContainCards, boxIsEmptyAfterDrawingAllCards                                 |
| 5      | Box can return random card                    | canReturnRandomCard                                                             |
| 6      | Cards can be moved into a box                 | canMoveCardIntoBox                                                              |
| 7      | Loading cards from json                       | load, canLoadSavedContent,                                                      |
| 8      | Saving cards to json                          | save, canSaveSingleCard, canSaveMultipleCards, makeSureToOverwritePreviousSaves |
| 9      | loading boxes from training-session from json | load,                                                                           |
| 10     | loading boxes from training-session from json | save, canSave3Boxes, makeSureToOverwritePreviousSaves                                                           |


## Additional Dependencies

[TODO]: # (For each additional dependency your project requires- Add an additional row to the table!)

| Number | Dependency Name | Dependency Description | Why is it necessary? |
|--------|-----------------|------------------------|----------------------|
| 1      | /               | /                      | /                    |

## Instructions

Don't use this.
[TODO]: # (Remove these instructions once you finished your fork's setup.)

Use a fork of this repository to do implement your project.

Remember to add this repository as a second remote repository (upstream) and pull from the correct remotes.
This is necessary, because we might apply changes to this template during the next month.

The following section describes how to add multiple remote repositories to your local repository, which is cloned from the fork.

### Multiple remote repositories

Your local repository should have a reference to both the fork (your own remote repository) and the original remote repository.
To configure your git remote repositories, use the `git remote` command set.

1. Clone your fork and go enter the repository.
```
git clone <fork-url>
cd <created-folder>
```
2. Now your fork is configured as primary remote repository (origin).
Next to origin, you should add the original repository as a second remote repository (upstream).
```
git remote add upstream <repository-url>
```
3. Verify that both remotes are configured correctly.
The following command should list both remotes: origin and upstream.
```
git remote -v
```
4. To fetch changes from all remote repositories, use:
```
git fetch --all
```
5. If there are interesting changes (in e.g. the `main` branch) to merge into your branch, use:
```
git pull upstream main
```
