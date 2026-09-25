# SamSquare User Guide

SamSquare helps you keep track of ToDos, deadlines, and events. Type a command
and press Enter to manage your tasks.

## Getting started

1. Open the project in IntelliJ IDEA. Under **File > Project Structure > Project**,
   select **JDK 25** and the SDK default language level.
2. Run `SamSquare.main()` in `src/main/java/ui/SamSquare.java`. Use the project
   folder as the run configuration's working directory.
3. Enter `todo borrow book` in the **Run** console, then `list` to see your task.

## Features

In the formats below, replace `UPPERCASE` words with your own values. All values
are required; descriptions can contain spaces. Use lowercase command names.
Spaces before and after a command are ignored: `  list  ` works like `list`.
Keep spaces around `/by`, `/from`, and `/to`. Commands `list` and `bye` take no extra words.

### Adding a ToDo: `todo`

Adds a task without a date.

Format: `todo DESCRIPTION`

Example: `todo borrow book`

### Adding a deadline: `deadline`

Adds a task with a due date.

Format: `deadline DESCRIPTION /by DATE`

Example: `deadline return book /by 2019-10-15`

Use a real date in **`yyyy-MM-dd`** format. The example displays as
`return book (by: Oct 15 2019)`. Dates such as `2023-02-29`, words such as
`Friday`, and dates with a time appended are rejected.

### Adding an event: `event`

Adds a task with a start and end time.

Format: `event DESCRIPTION /from START /to END`

Example: `event project meeting /from Mon 2pm /to 4pm`

Times are kept as text. SamSquare does not check whether the end comes after
the start.

### Viewing tasks: `list`

Enter `list` to see all tasks and their numbers. After adding the three examples
above to an empty list, you will see:

```text
 Here are the tasks in your list:
 1.[T][ ] borrow book
 2.[D][ ] return book (by: Oct 15 2019)
 3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
```

`[T]`, `[D]`, and `[E]` mean ToDo, deadline, and event. `[ ]` means incomplete;
`[X]` means complete.

### Changing completion status: `mark` and `unmark`

Use `mark NUMBER` to complete a task, or `unmark NUMBER` to mark it as incomplete.

Examples: `mark 1` completes the first task; `unmark 1` reverses that status.

**Use an existing number from the latest `list` output**, starting at 1.

### Finding tasks: `find`

Searches task descriptions without changing your list.

Format: `find KEYWORD`

Example: `find book`

- Matching is case-sensitive: `book` matches `notebook`, but not `Book`.
- You can search a whole phrase, such as `find read book`.
- Dates and event times are not searched. No matches means an empty result list.
- Results are numbered separately from 1. **Run `list` before marking or deleting**
  to get the task's full-list number.

### Removing a task: `delete`

Removes a task permanently. There is no undo command.

Format: `delete NUMBER`

Example: `list` followed by `delete 2` removes the second task.

Use an existing number from `list`. Remaining tasks are renumbered after deletion.

### Exiting: `bye`

Enter `bye` to close SamSquare.

### Saving your tasks

Changes are saved automatically to `data/samsquare.txt` and loaded next time.
Always launch from the same working directory to use the same file. Copy this
file to keep a backup. Avoid ` | ` in descriptions and event times, as it can
prevent tasks from reloading correctly.

## If something goes wrong

- **Command rejected:** check its format and values, then try again. Your tasks
  are unchanged.
- **`Error saving tasks.`:** check that the data folder and file are writable;
  your latest changes may not survive a restart.
- **Old deadline dates not loaded:** re-add them using `yyyy-MM-dd`. Before the
  next save, the original file is backed up as `data/samsquare-legacy-dates-*.txt`.
