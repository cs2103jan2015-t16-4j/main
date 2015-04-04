##AnyTask

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
```
###2. tag
tags are begin with `#`.
```
tag <Task Name/ID> <Tag(s)>
```
###3. delete
```
delete <Task Name/ID> <Tag>
delete <Task Name/ID> <Start Time>
delete <Task Name/ID> <End Time>
delete <Task Name/ID> <Deadline>
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
display due
display <Keyword>
display <Tag>
display from <Start Time> to <End Time>
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
###9. recurring
```
add <Task Name> by <Deadline> before <EndRecurringTime> [daily, weekly, monthly, annually]
add <Task Name> from <Start Time> to <End Time> before <EndRecurringTime> [daily, weekly, monthly, annually]
delete <ID> recurring
tag <ID> <Tag(s)> recurring
delete <ID> <Tag> recurring
display recurring
```
###10. help
```
help
help <Command Type>
```
