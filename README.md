# Jelly

This application is a manager and planner to help a user plan out and keep track of the progress of a project. The user will be able to handle multiple projects, and set up time constraints that they should respect, at different depth of the project.

A project contains multiple boards, which themselves contain multiple tasks, which themselves contain to-do lists. A board represents the key phase of a project (for example : in the software engineering project, we have the designing phase, the implementation phase and the testing phase). 
The user can create, modify, view and delete projects, boards, tasks, and to-do list items. Deleting a project deletes its corresponding boards, deleting a board deletes its corresponding tasks and deleting a task deletes its to-do list. 
When creating a project or once a project has been created, the user will have the choice to fill in a Gantt chart to generate boards for a project with deadlines to respect. To fill in the Gantt chart, the user will provide the different names of the different phases, and give the starting dates and deadlines of each phase. Upon verification of the user and validation of the Gantt chart, the boards will be created in the selected project. The different phases of the Gantt can later on be modified, hence modifying the boards of the project.
Multiple users can collaborate on a project. A user can grant or revoke the access to a project for another user. A user can also request to access a project, and refuse the access to another user when they receive a request. 
Users are notified of actions (creation, modification, deletion) made on their projects (whether it’s been made on the projects, boards, tasks, items etc). They also receive notifications when a deadline is approaching. 


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites



### Installing

```
Step 1 : Download the jar Jelly.jar.
```

```
Step 2 :Click on it to launch the application. You are then on the login view, able to login to use the application.
```

## Running the tests

Explain how to run the automated tests for this system

## Built With

* [JavaFX SceneBuilder](https://gluonhq.com/products/scene-builder/) - The software used to generate the views of the application
* [Eclipse](https://www.eclipse.org/) - Used to code by some members
* [IntelliJ](https://www.jetbrains.com/fr-fr/idea/) - Used to code by other members

## Contributing

Please contact us for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **Anthony Dupré** - [AnthonyDupre98](https://github.com/AnthonyDupre98)
* **Arthur Leblanc** - [ArthurLeblanc](https://github.com/ArthurLeblanc)
* **Pierre Partinico** - [pierreprtnc](https://github.com/PierrePrtnc)
* **Weslie Rabeson** - [rabesonw](https://github.com/rabesonw)



See also the list of [contributors](https://github.com/PierrePrtnc/Jelly/contributors) who participated in this project.


