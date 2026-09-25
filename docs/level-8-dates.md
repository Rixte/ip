# Deadline dates

Enter deadline dates as `yyyy-MM-dd`:

```text
deadline return book /by 2019-10-15
```

SamSquare displays the task as `[D][ ] return book (by: Oct 15 2019)`.
Dates are real calendar dates: `2024-02-29` is valid, while `2023-02-29`
and `2026-04-31` are rejected without adding a task. Deadline dates do not
include a time. Event start/end values still accept text such as `Mon 2pm`.

## Deadlines on a date

Use `on 2019-10-15` to list deadlines due on that calendar date. This compares
the stored dates, rather than searching date text in descriptions. Completed
deadlines are included. ToDos and events are excluded; event times remain text.
Results retain their original order and are numbered from 1. Run `list` to
obtain full-list numbers before marking, unmarking, or deleting a task.

An empty result shows the date heading and separator. Missing or invalid dates
are rejected. Searching does not change or save tasks.

## Saved dates

The saved file keeps dates in ISO format, for example:

```text
D | 0 | return book | 2019-10-15
```

Existing deadlines with ISO dates continue to load. Older free-text deadline
dates such as `Friday` cannot be interpreted reliably. These entries are
skipped with a startup notice. Before the next successful save, the original
file is copied to a uniquely named `data/samsquare-legacy-dates-*.txt` backup.
If the backup cannot be made, the save is aborted and the original file remains
untouched. Refer to the backup to re-enter those deadlines with explicit dates.

Changes are saved after each add, mark, unmark, or delete command. `bye` exits;
it does not perform an additional save.
