# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. If the user identifies themselves as an instructor or another project stakeholder, adapt your response to that role.

# Student profile

* Prior knowledge: Basic Java and OOP concepts.
* Level of programming experience: [to be filled]
* IDE and level of expertise: [to be filled]

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.
* Keep explanations brief but instructive, supporting learning through responsible use of AI. For example:

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

## Coding standards

All Java code in this repository must follow the project-specific
`seedu-java-coding-standard`.

All Git commit messages must follow the project-specific
`seedu-git-standard`.

When generating or modifying Java code:
- Follow the Java coding standard before presenting the code.
- Preserve the project's existing architecture unless the requirement
  calls for a structural change.
- Do not introduce unnecessary classes, methods, or abstractions.
- Ensure code remains compatible with Java 25.

When proposing or creating commits:
- Follow the Git commit standard.
- Propose the commit message before committing when requested.
- Keep unrelated changes in separate commits.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Git

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.

# Project-specific skill

## SEEDU Java Coding Standard:

All Java code in this project must follow the SE-EDU Java coding conventions.

## Formatting

- Use 4 spaces for indentation. Do not use tabs.
- Keep lines at or below 120 characters.
- Use one statement per line.
- Use braces consistently for control structures.
- Keep related code together and use blank lines to separate logical sections.
- Do not leave unnecessary trailing whitespace.

## Naming

- Classes and interfaces use PascalCase.
- Methods and variables use camelCase.
- Constants use UPPER_SNAKE_CASE.
- Names should be descriptive and avoid unnecessary abbreviations.

## Comments and Javadoc

- Write comments in English.
- Public classes and public methods should have Javadoc where required by the convention.
- Comments should explain intent or rationale rather than simply restating code.
- Avoid unnecessary comments.

## Classes

- Keep each top-level class in its own file.
- The filename must match the public class name.
- Use appropriate access modifiers.
- Keep fields private/protected only when justified by the design.
- Prefer encapsulation through methods rather than exposing mutable state directly.

## Packages

- Java source files should declare an appropriate package.
- The package declaration should match the project's directory structure.

## General

- Follow the official SE-EDU Java Intermediate Coding Standard:
  https://se-education.org/guides/conventions/java/intermediate.html
- When a project-specific requirement conflicts with a general style rule, satisfy the project requirement while keeping the rest of the code compliant.

## SEEDU Git Commit Standard:

All Git commits in this project must follow the SE-EDU Git conventions.

## Commit subject

- Use an imperative mood.
- Capitalize the first word.
- Do not end the subject with a period.
- Keep the subject line preferably within 50 characters.
- Never exceed 72 characters.
- The subject should clearly describe the change.

## Commit body

- For non-trivial changes, include a body explaining what changed and why.
- Separate the subject from the body with a blank line.
- Wrap body lines at approximately 72 characters.
- Focus on rationale and relevant context rather than repeating the diff.

## Commit scope

- Each commit should represent one logical change.
- Do not combine unrelated changes into one commit.
- Keep refactoring separate from functional changes when practical.

## Project-specific Git requirements

- Use lightweight tags unless an annotated tag is explicitly requested.
- Do not commit or push unless explicitly requested by the user.
- When completing an assignment increment, tag the commit with the exact increment ID as required by the assignment.

## Reference

Follow:
https://se-education.org/guides/conventions/git.html