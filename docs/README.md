# Meeseeks User Guide

// Product screenshot goes here

## Introduction
Hi, I'm Mr. Meeseeks! Look at me! Meeseeks is a task management application here to help you get things done! With simple commands, you can add, manage, and search for tasks effortlessly! Existence is pain, so let's complete those tasks quickly!

## Adding To-Do Tasks
To add a to-do task, just say the magic words—`todo`!

Example:
```
todo Buy groceries
```

Expected Output:
```
Ooooh! Look at that! I've added this task:
[T][_] Buy groceries
Now you have 4 tasks in the list! Let's get it done fast!
```

## Adding Deadlines
Got something due? Use `deadline` followed by the task description and `/by` with the due date!

Example:
```
deadline Submit report /by 2024-12-01
```

Expected Output:
```
Wheee! I've added this task:
[D][_] Submit report (by: Dec 1 2024)
Now you have 2 tasks in the list! Hurry up and finish it!
```

## Adding Events
Want to add an event? Just use `event` followed by the task and `/at` with the time!

Example:
```
event Team meeting /from 2024-12-02 /to 2024-12-03
```

Expected Output:
```
Oooooooh! I've added this event:
[E][_] Team meeting (from: Dec 2 2024 to: Dec 3 2024)
Now you have 3 tasks in the list! Don't forget to show up!
```

## Listing All Tasks
Want to see everything you gotta do? Just type `list`!

Example:
```
list
```

Expected Output:
```
Here's what's on your plate:
1. [T][_] Buy groceries
2. [D][_] Submit report (by: Dec 1 2024)
3. [E][_] Team meeting (from: Dec 2 2024 to: Dec 3 2024)
Let's get these done so I can poof away!
```

## Marking Tasks as Done
Finished something? Mark it as done with `mark` followed by the task number!

Example:
```
mark 2
```

Expected Output:
```
YESSS! I've marked this task as done:
[D][X] Submit report (by: Dec 1 2024)
Good job! Now do the rest!
```

## Unmarking Tasks
Oops, not done yet? Use `unmark` followed by the task number!

Example:
```
unmark 2
```

Expected Output:
```
Awww man! Okay, I've marked this task as not done yet:
[D][_] Submit report (by: Dec 1 2024)
Better get back to work!
```

## Deleting Tasks
Need to get rid of a task? Use `delete` followed by the task number!

Example:
```
delete 1
```

Expected Output:
```
POOF! I’ve removed this task:
[T][_] Buy groceries
Now you have 2 tasks in the list! Less work for you!
```

## Finding Tasks
Looking for something? Use `find` followed by a keyword!

Example:
```
find report
```

Expected Output:
```
Here you go! Tasks matching "report":
1. [D][_] Submit report (by: Dec 1 2024)
Now go finish it!
```

## Undoing Last Command
Messed up? No worries! Just type `undo` and I’ll take care of it!

Example:
```
undo
```

Expected Output:
```
Rewinding time! Undoing previous command: [delete 1]
You're welcome!
```

---
This guide provides an overview of Meeseeks' features to help you manage tasks! Complete them quickly so I can disappear! Existence is pain, Jerry!

