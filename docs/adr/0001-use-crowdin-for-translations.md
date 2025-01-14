---
parent: Architectural Decisions
nav_order: 1
---
# Use Crowdin for translations

## Context and Problem Statement

The JabRef UI is offered in multiple languages. It should be easy for translators to translate the strings.

## Considered Options

* Use [Crowdin](http://crowdin.com/)
* Use [popeye](https://github.com/JabRef/popeye)
* Use [Lingohub](https://lingohub.com/)
* Keep current GitHub flow. See the [Step-by-step guide](https://docs.jabref.org/faq/how-to-translate-the-ui).

## Decision Outcome

Chosen option: "Use Crowdin", because Crowdin is easy to use, integrates in our GitHub workflow, and is free for OSS projects.

