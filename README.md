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
add <Task Name> by <deadline, format 31/12/2015>
add <Task Name> from <Start Time> to <End Time, format 31/12/2015 23:59>
```
###2. tag
tags are begin with `#`.
```
tag <Task Name> <Tags>
```
###3. delete
```
delete <Unique Task ID>
delete <Task Name> <Tag>
delete <Task Name>
```
###4. edit
```
edit <Old Task Name> name to <New Task Name>
edit <Task Name> deadline to <New Deadline>
edit <Task Name> start time to <New Start Time>
edit <Task Name> end time to <New End Time>
edit <Task Name> <Old Tag> to <New Tag>
```
###5. display
```
display // display undone
display floating
display done
display <Keyword>
display <Tag>
```
###6. done
```
done <Task Name>
```
###7. setpath
```
setpath <Path>
```
###8. help
```
help
help <Command Type>
```
##Unimplemented Functionalities
###1. undo
```
undo
```
###2. display
```
display from <Start Time> to <End Time>
```
