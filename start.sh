gradle clean
gradle bootJar
java -jar build/libs/reactive-flashcards-dio-1.0.0.jar
#java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar build/lib/reactive-flashcards-dio-1.0.0.jar