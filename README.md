##AnyTask

AnyTask is a simple scheduler, built with efficiency in mind. AnyTask is CLI based, and is a no-frills way to managing your time well. Our aim is to excel in the basic functions. AnyTask will not be bloated with functions, an thus will run in the shortest possible time. We have designed AnyTask to be simple and easy to use – users will never feel lost with the assistance of our in-built ‘help’ feature and easily understandable commands.


##Developers

1. Joan Tan - Team Lead, Integration, Code Quality
2. Lin Zirui - Testing, Deliverables and Deadlines
3. Manish - Documentation, Scheduling and Tracking

##Implemented Functionalities

###1. add
####(a) add floating tasks
```
add <Task Name>
```
####(b) add task with deadline
```
add <Task Name> by <deadline, format 31/12/2015>
```
####(c) add task with start and end time
```
add <Task Name> from <Start Time> to <End Time, format 31/12/2015 23:59>
```
###2. tag
tags are begin with "#".
#### add tag(s)
```
tag <Task Name> <Tags>
```
###3. delete
####(a) delete task with unique task id
```
delete <Unique Task ID>
```
####(b) delete tag with name
```
delete <Task Name> <Tag>
```
###(c) delete task with name 
```
delete <Task Name>
```
###4. edit
####(a) edit name 
```
edit <Old Task Name> name to <New Task Name>
```
####(b) edit deadline
```
edit <Task Name> deadline to <New Deadline>
```
####(c) edit tag
```
edit <Task Name> <Old Tag> to <New Tag>
```
###5. display
####(a) display everything
```
display
```
####(b) display floating
```
display floating
```
####(c) display undone
```
display undone
```
####(d) display by title keyword
```
display <Keyword>
```
####(e) display by tag
```
display <Tag>
```
###6. done
####mark task as done by adding tag #done
```
done <Task Name>
```
###7. setpath
####change the path of local storage
```
setpath <Path>
```
###8. help
####(a) list of commands
```
help
```
####(b) docs for certain type of command
```
help <Command Type>
```
##Unimplemented Command
###1. undo
