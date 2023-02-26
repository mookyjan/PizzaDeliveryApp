# Pizza Delivery App

### Structure of the code ###
Simple Android Application written in Kotlin.
This project follows Clean Architecture with MVVM with Clean Architecture Design

Project consist of One Activity and 3 Fragments 
1) Home Fragment -> show list of the Menus Item
2) Order Fragment -> show list of the item selected for order
3) Dashboard Fragment -> Will do setup for user profile (Coming Soon)

# Main libraries used

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Kotlin Coroutine](https://kotlinlang.org/docs/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
    - [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Used it for the navigation from one fragment to another fragments
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
    - [Dagger2](https://dagger.dev/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Glide](https://bumptech.github.io/glide/) - Used for loading images
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Timber](https://github.com/JakeWharton/timber) -Used for loggging
- [Junit](https://junit.org/) - For Unit Testing
- [Mockk](https://mockk.io/) - For mocking in Unit Testing


# Architecture Design
* `Diagram` folder contain App Architecture and design pattern structure diagrams

# App Screenshots
* `Screenshot` folder contain the app screenshots


# Modules

* `core/` : contains the common functions
* `data/` : contains the code to access to the data (repository pattern)
* `domain/` : contains the business logic and the usecases
* `app` : Presentation layer, contains the UI

for the simplicity of this project many things have been kept simple
like
* ErrorHandling,
* Design of the app is also kept sample and can be improved much more
* write more test case and improve the UI design

comments are written with the function that what it will do.

also TODO are given in the area which we can improve more.