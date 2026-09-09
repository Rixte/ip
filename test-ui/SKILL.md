# test-ui

## Purpose

This is a project-specific UI testing skill for SamSquare.

The skill tests SamSquare through its actual console interface using the test
cases defined in `test/ui-test-plan.md`.

## Instructions

1. Read `test/ui-test-plan.md` before starting the test session.
2. Run the SamSquare program using the project's normal method.
3. Execute the test cases from `test/ui-test-plan.md` in the order they are listed.
4. For each test case:

    * enter the specified console input;
    * capture the actual console output;
    * compare the actual output against the expected output in the test plan.
5. Treat the expected output in `test/ui-test-plan.md` as the specification.
6. Do not change the expected output just to make a test pass.
7. After each test case, record the console input and actual console output.
8. If a test case fails:

    * stop the test session immediately;
    * do not continue to later test cases;
    * report the name or number of the failed test case;
    * report the actual output;
    * report the expected output.
9. After a failing test has been fixed, run the UI test session again from the
   beginning, unless there is a clear project-specific reason to run a smaller
   relevant set of tests.
10. If all test cases pass, report that the UI test session passed and show the
    console input and output from the session.

## Test Plan Maintenance

When user-facing behaviour changes:

1. Check whether `test/ui-test-plan.md` needs to be updated.
2. Add or modify test cases when new behaviour is introduced.
3. Keep the test plan consistent with the current SamSquare requirements.
4. Add both positive and negative cases for each command where the requirements
   define invalid input behaviour.
5. Interleave positive and negative cases when state can carry across commands.
   After rejected input, run `list` or another observable follow-up operation to
   confirm that no task was added, removed, marked, or unmarked accidentally.
6. Include boundary cases such as the first and last valid task number, zero,
   negative numbers, numbers beyond the list size, non-numeric arguments, empty
   descriptions, missing command components, and significant whitespace when
   applicable.
7. Every test case must state its starting state. Say explicitly whether it
   starts a fresh SamSquare process or continues the state produced by named
   earlier cases.
8. Do not infer expected behaviour from the current implementation. Derive it
   from the project requirements, and flag an unclear requirement instead of
   encoding an implementation accident into the test plan.

## Testing Scope

The tests should cover the actual console behaviour of SamSquare, including:

* adding ToDos;
* adding Deadlines;
* adding Events;
* listing tasks;
* marking tasks as done;
* unmarking tasks;
* storing arbitrary date/time strings;
* handling multiple task types together;
* exiting with `bye`.

The scope must also include incorrect and incomplete input for the supported
commands, plus checks that rejected commands leave task state unchanged.

## Test Design Review

Before running a session:

1. Map each user-facing command and validation rule to at least one test case.
2. Identify important branches that have only a successful case or only a
   failing case, and add the missing counterpart when the requirement is known.
3. Check that expected task counts and list contents follow from the documented
   starting state and every preceding accepted command.
4. Check that the test sequence can expose state corruption rather than merely
   printing an error message.

## Optional Mutation Check

Use a mutation check only when the user requests or authorises an assessment of
test effectiveness.

1. Record the clean or pre-existing working-tree diff before editing.
2. Make one minimal, targeted temporary production-code change representing a
   plausible defect in behaviour covered by the test plan.
3. Run the relevant UI tests and require them to fail for the intended reason.
4. Stop immediately if they do not fail; report the surviving mutation as a
   test gap and improve the test plan before trying again.
5. Restore only the exact temporary change, preserving all pre-existing user
   edits.
6. Rerun the relevant UI tests against the restored code and inspect the diff to
   verify that no mutation remains.
7. Never commit, tag, or leave a temporary defect in the working tree.

## Failure Handling

A test failure must immediately stop the test session.

The failure report must contain:

### Failed Test

The test case that failed.

### Actual Output

The output produced by SamSquare.

### Expected Output

The output specified in `test/ui-test-plan.md`.

Do not hide, skip, or ignore failures.

## Completion Criteria

A code update affecting user-facing behaviour is not considered complete until
the relevant UI tests have been run.

All relevant UI tests must pass before the testing task is considered complete.
