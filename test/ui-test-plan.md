# UI Test Plan

This plan tests SamSquare through its actual console interface. Run the cases in
order in one process. Test Case 1 starts with an empty task list; every later
case continues from the state left by the preceding case. Stop at the first
failure.

Run the process in a fresh working directory with no `data/samsquare.txt`, so
saved personal tasks do not affect the starting state. Compile and run using
Java 25, preserving UTF-8 output for the banner.

The greeting appears exactly once before Test Case 1.

Expected greeting:

```text
      __________
    /            \
   /   •     •    \
  |      ᴥ         |
   \    _____     /
    \____________/
      ||    ||
      ||____||
HELLO!! I am SamSquare :D
What can I do for you?
```

After the greeting and every command response, SamSquare prints this separator:

```text
____________________________________________________________
```

The separator is part of the expected output for every case, although it is
shown once here to keep the cases readable. Leading spaces inside output blocks
are significant.

## Test Case 1: List an empty collection

Input: `list`

Expected response:

```text
 Here are the tasks in your list:
```

The task list remains empty.

## Test Case 2: Reject an empty ToDo without changing state

Input:

```text
todo
list
```

Expected responses:

```text
 WAIT PAUSE!! HEY!! The description of a todo cannot be empty!
```

```text
 Here are the tasks in your list:
```

The follow-up `list` confirms that the rejected command added nothing.

## Test Case 3: Add all task types

Input:

```text
todo borrow book
deadline return book /by no idea :-p
event project meeting /from Mon 2pm /to 4pm
list
```

Expected responses:

```text
 Got it!! I've added this task:
   [T][ ] borrow book
 Now you have 1 tasks in the list :)
```

```text
 Got it! I've added this task:
   [D][ ] return book (by: no idea :-p)
 Now you have 2 tasks in the list :)
```

```text
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 3 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

This also verifies that arbitrary date/time strings are stored unchanged.

## Test Case 4: Reject malformed Deadline commands without changing state

Input:

```text
deadline submit report
deadline  /by Friday
deadline submit report /by<one trailing space>
list
```

Expected responses:

```text
 WAIT PAUSE!! Hey... a deadline needs the format: deadline <task> /by <date>.
```

```text
 WAIT PAUSE!! Hey... a deadline needs the format: deadline <task> /by <date>.
```

```text
 WAIT PAUSE!! Hey... a deadline needs the format: deadline <task> /by <date>.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 5: Reject malformed Event commands without changing state

Input:

```text
event team meeting
event team meeting /from Monday
event  /from Monday /to Tuesday
event team meeting /from  /to Tuesday
event team meeting /from Monday /to<one trailing space>
list
```

Expected responses:

```text
 WAIT PAUSE!! An event needs the format: event <task> /from <time> /to <time>.
```

```text
 WAIT PAUSE!! An event needs both a starting and ending time.
```

```text
 WAIT PAUSE!! An event needs the format: event <task> /from <time> /to <time>.
```

```text
 WAIT PAUSE!! Broski.. an event must have a starting time!
```

```text
 WAIT PAUSE!! An event needs both a starting and ending time.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 6: Reject invalid mark arguments without changing state

Input:

```text
mark
mark abc
mark 0
mark -1
mark 4
list
```

Expected responses:

```text
 WAIT PAUSE!! Specify the task number to mark please!
```

```text
 WAIT PAUSE!! The task number must be a valid number.
```

```text
 WAIT PAUSE!! There is no task numbered 0.
```

```text
 WAIT PAUSE!! There is no task numbered -1.
```

```text
 WAIT PAUSE!! There is no task numbered 4.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 7: Mark the first and last tasks

Input:

```text
mark 1
mark 3
```

Expected responses:

```text
 WELL DONE!! I've marked this task as done:
   [T][X] borrow book
```

```text
 WELL DONE!! I've marked this task as done:
   [E][X] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 8: Reject invalid unmark arguments and preserve state

Input:

```text
unmark
unmark xyz
unmark 0
unmark 4
list
```

Expected responses:

```text
 WAIT PAUSE!! Specify the task number to unmark please!
```

```text
 WAIT PAUSE!! The task number must be a valid number.
```

```text
 WAIT PAUSE!! There is no task numbered 0.
```

```text
 WAIT PAUSE!! There is no task numbered 4.
```

```text
 Here are the tasks in your list:
 1.[T][X] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][X] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 9: Unmark the first and last tasks

Input:

```text
unmark 1
unmark 3
```

Expected responses:

```text
 OK, I've marked this task as not done yet:
   [T][ ] borrow book
```

```text
 OK, I've marked this task as not done yet:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 10: Reject invalid delete arguments and preserve state

Input:

```text
delete
delete nope
delete 0
delete -1
delete 4
list
```

Expected responses:

```text
 WAIT PAUSE!! Specify the task number to delete please!
```

```text
 WAIT PAUSE!! HEY!! The task number must be a valid number!
```

```text
 WAIT PAUSE!! There is no task numbered 0.
```

```text
 WAIT PAUSE!! There is no task numbered -1.
```

```text
 WAIT PAUSE!! There is no task numbered 4.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: no idea :-p)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

## Test Case 11: Remove from the collection and verify reindexing

Input:

```text
delete 2
list
todo buy groceries
delete 1
list
```

Expected responses:

```text
 Ahh noted! I've removed this task:
   [D][ ] return book (by: no idea :-p)
 Now you have 2 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

```text
 Got it!! I've added this task:
   [T][ ] buy groceries
 Now you have 3 tasks in the list :)
```

```text
 Ahh noted! I've removed this task:
   [T][ ] borrow book
 Now you have 2 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

This is the collection-specific regression case. It verifies removal from the
middle and front, automatic index shifting, and adding after a removal.

## Test Case 12: Reject whitespace and unknown commands

The first input line is `<three spaces>`: enter three literal spaces, not the
angle-bracketed text.

Input:

```text
<three spaces>
hello
list
```

Expected responses:

```text
 WAIT PAUSE!! Please don't leave your input empty D:
```

```text
 WAIT PAUSE!! Hold up... I don't recognise that command. Please use todo, deadline, event, mark, unmark, list or bye.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

## Test Case 13: Exit

Input: `bye`

Expected response:

```text
Byebye hope to see you again soon!
```

The process must terminate after printing the separator.
