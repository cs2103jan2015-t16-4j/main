﻿##AnyTask

AnyTask is a simple scheduler, built with efficiency in mind. AnyTask is CLI based, and is a no-frills way to managing your time well. Our aim is to excel in the basic functions. AnyTask will not be bloated with functions, an thus will run in the shortest possible time. We have designed AnyTask to be simple and easy to use – users will never feel lost with the assistance of our in-built ‘help’ feature and easily understandable commands.


##Developers

1. Joan Tan - Team Lead, Integration, Code Quality
2. Lin Zirui - Testing, Deliverables and Deadlines
3. Manish - Documentation, Scheduling and Tracking

##Implemented Functionalities

###1. add
```
add <Task Name>
add <Task Name> by <Deadline>
add <Task Name> from <Start Time> to <End Time>
add [daily, weekly, monthly, annually] <Task Name> by <Deadline> before <EndRecurringTime>
add [daily, weekly, monthly, annually] <Task Name> from <Start Time> to <End Time> before <EndRecurringTime>
```
###2. tag
tags are begin with `#`.
```
tag <Task Name/ID> <Tags>
```
###3. delete
```
delete <Task Name/ID> <Tag>
delete <Task Name/ID>
```
###4. edit
```
edit <Old Task Name/ID> name to <New Task Name>
edit <Task Name/ID> deadline to <New Deadline>
edit <Task Name/ID> start time to <New Start Time>
edit <Task Name/ID> end time to <New End Time>
edit <Task Name/ID> <Old Tag> to <New Tag>
```
###5. display
```
display // display undone
display all
display floating
display done
display recurring
display <Keyword>
display <Tag>
display after <Start Time> before <End Time>
display before <End Time>
```
###6. done
```
done <Task Name/ID>
```
###7. setpath
```
setpath <Path>
```
###8. undo
```
undo
```
##Unimplemented Functionalities
###1. help
```
help
help <Command Type>
```
