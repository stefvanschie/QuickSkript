# QuickSkript
QuickSkript aims to be a Skript parser and executor with high speed.

QuickSkript pre-parses your entire code when it loads the skript. Most interpreters out there read and parse your code while executing and this makes it so the execution time of these Skript is often high. Because QuickSkript parses the code before executing it, we can ensure that our skripts run as fast as possible when executing them.

However this fast execution comes at a cost, mainly in terms of RAM. Because QuickSkript stores all your code when loading the skript, more RAM is needed than would normally be the case with other interpreters. If your server does not have a lot of RAM, you may want to use slower, but less resource consuming interpreters to prevent your server from crashing.

What needs to be done:
* A basic PSI
* A Skript loader which turns skripts into stored elements.
* Default commands
* Basic text representation
* Basic functions
* Basic conditions
* Basic effects
* Basic expressions
