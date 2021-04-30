#!/bin/bash
javac *.java
rmic DistributedRemote
gnome-terminal --window --command="bash -c 'echo "RMI-Registry-Port:5000"; rmiregistry 5000; $SHELL'"
sleep 2
gnome-terminal --window --command="bash -c 'echo "RMI-Server"; java MyServer; $SHELL'" --window --command="bash -c 'java Reader; $SHELL'" --window --command="bash -c 'java Reader; $SHELL'"  --window --command="bash -c 'java Reader; $SHELL'" --window --command="bash -c 'java Writer; $SHELL'"


# https://www.javatpoint.com/RMI
