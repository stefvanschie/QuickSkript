# QuickSkript
QuickSkript aims to be a Skript parser and executor with high speed.

QuickSkript pre-parses your entire code when it loads the skript. Most interpreters out there read and parse your code while executing and this makes it so the execution time of these Skript is often high. Because QuickSkript parses the code before executing it, we can ensure that our skripts run as fast as possible when executing them.

However this fast execution comes at a cost, mainly in terms of RAM. Because QuickSkript stores all your code when loading the skript, more RAM is needed than would normally be the case with other interpreters. If your server does not have a lot of RAM, you may want to use slower, but less resource consuming interpreters to prevent your server from crashing.

## State of the project
QuickSkript has been able to be run on a modern (1.13, Paper) server and has run its first skript file.

    command /message:
        trigger:
            message "Hi!" to the console
            
I've also measured the execution time, by slightly modifying the project to time the command execution. In total I've executed this skript 16 times (1 result pruned due to startup time). The 15 values below are the execution times in nanoseconds:
* 72 543
* 59 540
* 62 278
* 62 620
* 82 124
* 65 015
* 66 384
* 67 411
* 63 304
* 51 327
* 63 989
* 62 620
* 65 357
* 58 513
* 56 803

Mean time: 63 989 (rounded up from 63 988.533333...)  
Median time: 63 304

Measured on a recent 1.13 Paper build with an Intel Core 2 Duo E8400 @ 3.00 GHz, with IntelliJ IDEa Ultimate, Google Chrome, Notepad and Discord opened.

These tests are of course *very* sloppy and probably inaccurate (I mainly did them for my own enjoyment), but it should give you a rough idea of the execution speed. I'm not gonna draw any conclusions from these tests due to how sloppy they are, but you can decide for yourself what to do with these values.
