This project uses an Android MVVM architecture with Android Architecture Components, 
Fragments, Dagger Hilt, Room, and Flow. It also supports dark mode.

The list of exercises is assumed to be known in advance and is included in string resources.
A Worker is used to ingest data in the background from a file into Room when the application 
starts. Entities are defined for an exercise and a list of records associated with an exercise. 
Daos are defined to interact with Room and emit a flow of data.

Daos are injected into ViewModels, and the ViewModels map the exercise data to UI data objects.
ViewModels emit a flow of data that the fragments can observe.

The first fragment displays ExerciseListItems in a RecyclerView. It observes a flow of exercise
data and the list can be seen updating as data is ingested. Ideally a progress spinner would be
shown while the data is being ingested, but that is not included here.

Clicking on a row in the list view takes you to the exercise detail fragment and a custom GraphView.
A navigation graph is used to do the navigation. This is my first time using the nav graph and
while it seems great for small projects it could get unwieldy in a large project.

I don't have much experience with drawing on a canvas and didn't finish drawing the text labels
for the axes. The GraphView is currently too tightly coupled with the exercise data because the
labels still require knowledge of the underlying data. Ideally there would be another layer of
abstraction for GraphLabels where the x/y values are passed in along with the string label.

This is actually the first time I've used Room and Hilt. I have experience with Realm and Dagger2, 
but since those require some amount of setup I wanted to learn the Google libraries available. 
I also haven't used Fragments in a long time but decided to give them a try because it's the more 
standard way to do things, and because I wanted to try the navigation graph.
