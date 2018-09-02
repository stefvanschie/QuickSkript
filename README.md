# QuickSkript
QuickSkript aims to be a Skript parser and executor with high speed.

QuickSkript pre-parses your entire code when it loads the skript. Some interpreters out there read and parse your code while executing and this makes it so the execution time of these Skript is often high. Because QuickSkript parses the code before executing it, we can ensure that our skripts run as fast as possible when executing them.

Another advantage QuickSkript offers is the ability to pre calculate constants within your code. Say you have the following piece of code: 'sqrt(sum(1, 2, 3))'. Most interpreters would calculate that when you run the command, or when the event is fired. QuickSkript on the other hand can determine that the result of that calculation will always be the same and will calculate the result (2.44948...) when loading. That way we only have to calculate it once and after that we can use the already calculated result.

However this fast execution comes at a cost, mainly in terms of RAM. Because QuickSkript stores all your code when loading the skript, more RAM is needed than would normally be the case with some other interpreters. If your server does not have a lot of RAM, you may want to use slower, but less resource consuming interpreters to prevent your server from crashing.

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

Measured on a recent 1.13 Paper build with an Intel Core 2 Duo E8400 @ 3.00 GHz, with IntelliJ IDEA Ultimate, Google Chrome, Notepad and Discord opened.

These tests are of course *very* sloppy and probably inaccurate (I mainly did them for my own enjoyment), but it should give you a rough idea of the execution speed. I'm not gonna draw any conclusions from these tests due to how sloppy they are, but you can decide for yourself what to do with these values.

I was asked to also provide timings for other skript interpreters, so I decided to test [SkriptLang](https://github.com/SkriptLang/Skript). I performed the test 17 times with 2 results pruned (they were much higher than the other values, so I didn't include them). Same environment, same applications opened (a few less Chrome tabs though), except this time on 1.12.2 (I got errors running it on 1.13, I don't think it has been updated for 1.13 yet). Here are the results, again in nanoseconds:
* 122 503
* 112 580
* 088 968
* 105 051
* 096 496
* 131 058
* 087 600
* 092 390
* 104 367
* 110 869
* 124 898
* 100 602
* 112 921
* 090 377
* 097 181

Mean time: 105 857 (rounded down from 105 857.4)  
Median time: 104 367

Note that these tests were performed by executing the command from Bukkit.dispatchCommand, whereas the previous test was measured from the moment the command execution started, so the results include any additional execution time the Bukkit.dispatchCommand may have.

Just to repeat it again, these tests are *very* sloppy and in no way serve as an accurate benchmark for the speed of both QuickSkript nor SkriptLang.

*Tests executed in August 2018*
