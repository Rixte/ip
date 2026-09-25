# Find tasks

Use `find <keyword>` to search task descriptions:

```text
find book
```

The response begins with `Here are the matching tasks in your list:` and lists
matching ToDos, deadlines, and events in their existing order, including their
completion status. A search with no matches shows the heading and separator.

Matching is case-sensitive and uses a substring: `book` also matches `notebook`,
but does not match `Book`. You can search for a phrase with spaces, such as
`find read book`. Spaces surrounding the phrase are ignored. An empty search
is rejected. Only descriptions are searched, not dates, times, or status icons.

Results are numbered from 1 for display. Search does not replace or reorder
your task list. Run `list` to see the original task numbers before using
`mark`, `unmark`, or `delete`. Searching does not save or change task state.
