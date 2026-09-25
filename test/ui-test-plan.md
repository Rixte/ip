# UI Test Plan

This plan tests SamSquare through its actual console interface. Run the cases in
order. Test Case 1 starts with an empty task list. Cases 2–17 continue in
the same process; Cases 18–20 explicitly restart the process while keeping
the isolated task file from the preceding case. Stop at the first failure.
Every `bye` must terminate its process after the response separator.

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
are significant. Input markers `<one trailing space>` and `<three spaces>`
stand for literal spaces, including when appended to a command.

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

## Test Case 13: Reject empty arguments and oversized task numbers

Continues the state left by Test Case 12.

Input:

```text
mark<one trailing space>
list
unmark<three spaces>
list
delete<one trailing space>
list
mark 2147483648
list
unmark 2147483648
list
delete 2147483648
list
unmark -1
list
todo<three spaces>
list
deadline
list
event
list
```

Expected responses:

```text
 WAIT PAUSE!! Please specify which task you want to mark.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Please specify which task you want to unmark.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Specify which task you want to delete please! D:
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! The task number must be a valid number.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! The task number must be a valid number.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! HEY!! The task number must be a valid number!
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! There is no task numbered -1.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! HEY!! The description of a todo cannot be empty!
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Hey... a deadline needs the format: deadline <task> /by <date>.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! An event needs the format: event <task> /from <time> /to <time>.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

## Test Case 14: Keep command names and no-argument commands exact

Continues the state left by Test Case 13.

Input:

```text
todoist read
list
list extra
list
bye extra
list
 list
list
```

Expected responses:

```text
 WAIT PAUSE!! Hold up... I don't recognise that command. Please use todo, deadline, event, mark, unmark, list or bye.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Hold up... I don't recognise that command. Please use todo, deadline, event, mark, unmark, list or bye.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Hold up... I don't recognise that command. Please use todo, deadline, event, mark, unmark, list or bye.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

```text
 WAIT PAUSE!! Hold up... I don't recognise that command. Please use todo, deadline, event, mark, unmark, list or bye.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

## Test Case 15: Accept whitespace around task arguments

Continues the state left by Test Case 14.

Input:

```text
todo   check parser<three spaces>
mark   3<three spaces>
list
unmark   3<three spaces>
list
delete   3<three spaces>
list
```

Expected responses:

```text
 Got it!! I've added this task:
   [T][ ] check parser
 Now you have 3 tasks in the list :)
```

```text
 WELL DONE!! I've marked this task as done:
   [T][X] check parser
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
 3.[T][X] check parser
```

```text
 OK, I've marked this task as not done yet:
   [T][ ] check parser
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
 3.[T][ ] check parser
```

```text
 Ahh noted! I've removed this task:
   [T][ ] check parser
 Now you have 2 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
 2.[T][ ] buy groceries
```

## Test Case 16: Empty the list and reject stale task numbers

Continues the state left by Test Case 15.

Input:

```text
delete 2
list
mark 2
list
delete 1
list
mark 1
list
unmark 1
list
delete 1
list
todo revived
mark 1
deadline persist deadline /by Friday
event persist event /from noon /to evening
mark 3
list
```

Expected responses:

```text
 Ahh noted! I've removed this task:
   [T][ ] buy groceries
 Now you have 1 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

```text
 WAIT PAUSE!! There is no task numbered 2.
```

```text
 Here are the tasks in your list:
 1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

```text
 Ahh noted! I've removed this task:
   [E][ ] project meeting (from: Mon 2pm to: 4pm)
 Now you have 0 tasks in the list.
```

```text
 Here are the tasks in your list:
```

```text
 WAIT PAUSE!! There is no task numbered 1.
```

```text
 Here are the tasks in your list:
```

```text
 WAIT PAUSE!! There is no task numbered 1.
```

```text
 Here are the tasks in your list:
```

```text
 WAIT PAUSE!! There is no task numbered 1.
```

```text
 Here are the tasks in your list:
```

```text
 Got it!! I've added this task:
   [T][ ] revived
 Now you have 1 tasks in the list :)
```

```text
 WELL DONE!! I've marked this task as done:
   [T][X] revived
```

```text
 Got it! I've added this task:
   [D][ ] persist deadline (by: Friday)
 Now you have 2 tasks in the list :)
```

```text
 Got it. I've added this task:
   [E][ ] persist event (from: noon to: evening)
 Now you have 3 tasks in the list.
```

```text
 WELL DONE!! I've marked this task as done:
   [E][X] persist event (from: noon to: evening)
```

```text
 Here are the tasks in your list:
 1.[T][X] revived
 2.[D][ ] persist deadline (by: Friday)
 3.[E][X] persist event (from: noon to: evening)
```

## Test Case 17: Exit with mixed saved tasks

Continues the state left by Test Case 16.

Input:

```text
bye
```

Expected responses:

```text
Byebye hope to see you again soon!
```

## Test Case 18: Reload all task types and update loaded tasks

Starts a new process in the same isolated working directory, retaining
the task file from Test Case 17. Compare the greeting above
before sending the first command.

Input:

```text
list
unmark 1
delete 2
list
bye
```

Expected responses:

```text
 Here are the tasks in your list:
 1.[T][X] revived
 2.[D][ ] persist deadline (by: Friday)
 3.[E][X] persist event (from: noon to: evening)
```

```text
 OK, I've marked this task as not done yet:
   [T][ ] revived
```

```text
 Ahh noted! I've removed this task:
   [D][ ] persist deadline (by: Friday)
 Now you have 2 tasks in the list.
```

```text
 Here are the tasks in your list:
 1.[T][ ] revived
 2.[E][X] persist event (from: noon to: evening)
```

```text
Byebye hope to see you again soon!
```

## Test Case 19: Reload updated tasks and save an empty list

Starts a new process in the same isolated working directory, retaining
the task file from Test Case 18. Compare the greeting above
before sending the first command.

Input:

```text
list
delete 2
delete 1
list
bye
```

Expected responses:

```text
 Here are the tasks in your list:
 1.[T][ ] revived
 2.[E][X] persist event (from: noon to: evening)
```

```text
 Ahh noted! I've removed this task:
   [E][X] persist event (from: noon to: evening)
 Now you have 1 tasks in the list.
```

```text
 Ahh noted! I've removed this task:
   [T][ ] revived
 Now you have 0 tasks in the list.
```

```text
 Here are the tasks in your list:
```

```text
Byebye hope to see you again soon!
```

## Test Case 20: Reload an empty saved list

Starts a new process in the same isolated working directory, retaining
the task file from Test Case 19. Compare the greeting above
before sending the first command.

Input:

```text
list
bye
```

Expected responses:

```text
 Here are the tasks in your list:
```

```text
Byebye hope to see you again soon!
```
