# UI Test Plan

This document defines the UI test cases for SamSquare.

The tests interact with SamSquare through its console interface. Each test case
contains an aim, the required console input, and the expected output.

Tests should be executed in the order listed below. If a test case fails, stop
the test session immediately and report the actual and expected output.

---

## Test Case 1: Add a ToDo

### Aim

Verify that SamSquare can add a ToDo task.

### Input

```text
todo borrow book
```

### Expected Output

```text
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
```

---

## Test Case 2: Add a Deadline

### Aim

Verify that SamSquare can add a Deadline task and store the `/by` value as a
string.

### Input

```text
deadline return book /by Sunday
```

### Expected Output

```text
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
```

---

## Test Case 3: Add an Event

### Aim

Verify that SamSquare can add an Event task and store the `/from` and `/to`
values as strings.

### Input

```text
event project meeting /from Mon 2pm /to 4pm
```

### Expected Output

```text
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
```

---

## Test Case 4: List Different Task Types

### Aim

Verify that ToDos, Deadlines, and Events are displayed correctly in the task
list.

### Input

```text
list
```

### Expected Output

```text
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
```

---

## Test Case 5: Mark a Task as Done

### Aim

Verify that an existing task can be marked as done.

### Input

```text
mark 1
```

### Expected Output

```text
Nice! I've marked this task as done:
[T][X] borrow book
```

---

## Test Case 6: Unmark a Task

### Aim

Verify that a completed task can be marked as not done.

### Input

```text
unmark 1
```

### Expected Output

```text
OK, I've marked this task as not done yet:
[T][ ] borrow book
```

---

## Test Case 7: Deadline with Arbitrary String

### Aim

Verify that Deadline date/time information is treated as a string and does not
need to be a valid date.

### Input

```text
deadline do homework /by no idea :-p
```

### Expected Output

```text
Got it. I've added this task:
[D][ ] do homework (by: no idea :-p)
```

---

## Test Case 8: Event with Arbitrary Strings

### Aim

Verify that Event date/time information is treated as strings and does not need
to be converted into actual dates or times.

### Input

```text
event unusual event /from whenever /to sometime later
```

### Expected Output

```text
Got it. I've added this task:
[E][ ] unusual event (from: whenever to: sometime later)
```

---

## Test Case 9: Multiple Tasks and Task Types

### Aim

Verify that different task types can coexist in the same task list and retain
their individual type and date/time information.

### Input

```text
todo buy groceries
deadline submit report /by Friday 5pm
event team meeting /from Monday 2pm /to Monday 4pm
list
```

### Expected Output

```text
Got it. I've added this task:
[T][ ] buy groceries
Got it. I've added this task:
[D][ ] submit report (by: Friday 5pm)
Got it. I've added this task:
[E][ ] team meeting (from: Monday 2pm to: Monday 4pm)
Here are the tasks in your list:
1.[T][ ] buy groceries
2.[D][ ] submit report (by: Friday 5pm)
3.[E][ ] team meeting (from: Monday 2pm to: Monday 4pm)
```

---

## Test Case 10: Mark a Deadline as Done

### Aim

Verify that Deadline tasks can use the existing Level-3 mark functionality.

### Input

```text
mark 2
```

### Expected Output

```text
Nice! I've marked this task as done:
[D][X] submit report (by: Friday 5pm)
```

---

## Test Case 11: Mark an Event as Done

### Aim

Verify that Event tasks can use the existing Level-3 mark functionality.

### Input

```text
mark 3
```

### Expected Output

```text
Nice! I've marked this task as done:
[E][X] team meeting (from: Monday 2pm to: Monday 4pm)
```

---

## Test Case 12: Unmark a Deadline

### Aim

Verify that Deadline tasks can be marked as not done.

### Input

```text
unmark 2
```

### Expected Output

```text
OK, I've marked this task as not done yet:
[D][ ] submit report (by: Friday 5pm)
```

---

## Test Case 13: Unmark an Event

### Aim

Verify that Event tasks can be marked as not done.

### Input

```text
unmark 3
```

### Expected Output

```text
OK, I've marked this task as not done yet:
[E][ ] team meeting (from: Monday 2pm to: Monday 4pm)
```

---

## Test Case 14: Exit the Program

### Aim

Verify that SamSquare exits correctly when the `bye` command is entered.

### Input

```text
bye
```

### Expected Output

```text
Byebye hope to see you again soon!
```

---

# Test Execution Requirements

For every test session:

1. Run SamSquare using the actual console interface.
2. Execute the test cases in the order listed.
3. Compare the actual output with the expected output.
4. Show the console input and output for the test session.
5. If any test case fails, stop the test session immediately.
6. Report:

    * the test case that failed,
    * the actual output,
    * the expected output.
7. After fixing the problem, run the UI tests again.
8. Do not modify the expected output simply to make a failing test pass.

When user-facing behaviour changes, update this test plan with the relevant
new or modified test cases.
