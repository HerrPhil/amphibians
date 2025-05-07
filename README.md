# Amphibians

## Purpuse of Project

The main goals were to connect to the internet, utilize retrofit for an API service, add a repository layer, and implement dependency injection to aid in testing.

The recipe of the general steps to do this follow.

## The Recipe

1. As usual, create the new app in android studio.
2. Create a new theme; use the theme builder [website](https://material-foundation.github.io/material-theme-builder/).
3. Start with the basics: get data from the internet.
   * unit 5/pathway 1/codelab: Get data from the internet
4. First goal: show a Text composable with "Success: 5 amphibians retrieved".
5. Base classes to create to start journey:

   com.example.amphibians
       ui
           screens
               HomeScreen.kt
               AmphibianViewModel.kt
           themes ...
           AmphibianApp.kt
       MainActivity.kt

6. Add retrofit dependencies.
7. Start connecting to the internet. Create a file, AmphibianApiService. Define BASE_URL. Create retrofit constant (val) with Builder(). Add the Scalars Converter Factory. Define API interface (@GET). Use object declaration to create AmphibianApi singleton. The singleton lazily initializes AmphibianApiService; use retrofit.create().
8. Call web service from AmphibianViewModel. Amphibian API Service getAmphibians() method must be "suspend". Wrap it in the getAmphibians() method in the view model using launch(). Put the result in amphibianUiState, a mutable state instance.
9. Add internet permission and exception handling to the launch() call.
10. Add State UI, In the AmphibianViewModel.kt file, before the view model class, add a sealed interface, AmphibianUiState, that declares 3 states: Success, Error, Loading.
Success is a data class, and the other two are object classes.
11. Default UI State to Loading in the view model; override this property to be "private set".
12. Re-factor amphibiansUiState value in getAmphibians() from String value to Success(result) value.
13. Re-factor HomeScreen(), add when(amphibianUiState), and handle 3 states to pick 3 screens; generate matching previews.
14. Borrow loading image from Mars project.
15. Borrow error image from Mars project.
16. Prepare for JSON serialization; add Kotlin serialization dependency, Kotlin serialization converter dependency.
17. Add network package; add AmphibianPhoto data class; annotate @Serializable; use @SerialName for attribute names with dashes.
18. Re-factor AmphibianApiService and switch from scalar converter factory to Kotlinx serialization converter.
19. Re-factor getAmphibians() in AmphibiansApiService interface to return List<AmphibianPhoto>. Make similar changes in the view model.
20. Introduce the separation of the UI layer and the Data layer.
21. Data layer exposes data to UI layer using Unidirectional Data Flow (UDF).
22. Now to add the Repository.
23. Create 'data' package.
24. Create interface AmphibiansRepository with one 'suspend' function getAmphibians() -> List<AmphibianPhoto>.
25. Implement interface as NetworkAmphibianRepository.
26. Override getAmphibians() to call and return AmphibianApi.retrofitService.getAmphibians().
27. Add repository to view model.
28. Re-factor view model getAmphibians(); construct repository. Get value and assign to list result.
29. Next, work on manual dependency injection.
30. Introduce the app container class; a container is an object that contains the dependencies that the app requires.
31. Create AppContainer interface in 'data' package; add one property value amphibianRepository.
32. Implement below interface the DeafultAppContainer class.
33. Re-factor to move from AmphibiansApiService to DefaultAppContainer the BASE_URL, retrofit, and retrofitService; remove 'const' from BASE_URL; make retrofitService variable private - only the container need know about it.
34. Override amphibiansRepository property to lazily create NetworkAmphibiansRepository.
35. Re-factor NetworkAmphibiansRepository constructor to accept the amphibiansApiService, created by the DefaultAppContainer.
36. Re-factor getAmphibians() of NetworkAmphibiansRepository to retrieve from amphibiansApiService.getAmphibians().
37. Re-factor AmphibiansApiService and remove AmphibiansApi object (singleton).
38. Introduce container by attaching to the the app.
39. Create AmphibiansApplication in the root package; add a 'container' variable; override onCreate() and assign DefaultAppContainer() to 'container'; add to AndroidManifest as 'android:name' of <application>.
40. Introduce repository to view model; add private parameter to view model constructor; remove repository assignment in getAmphibians().
41. NOTE: the Android framework does not allow a 'ViewModel' to be passed values in the constructor when created. Then implement ViewModelProvider.Factory object; 'Factory pattern', a creational pattern use to create objects; do this in a companion object; use APPLICATION_KEY to look up the application to get the container to get the repository to pass to the view model.
42. Re-factor AmphibiansApp composable function to construct the view model using the factory when passing the view model to the HomeScreen.
43. Set up unit tests -> dependencies.
44. Create fake data source.
45. Write repository test(s).
46. Write view model test(s).
47. Introduce the UI. Start to load and display:

    * name
    * type
    * description
    * image

    As a lazy vertical grid of Card compostables where each card is:

    <name> (<type>)
    <image>
    <description>

    One column, many rows.

48. Start process of loading and showing photos.
49. Create a Composable function to render a photo card -> one-photo edge case.
50. Replace ResultScreen() with AmphibianPhotoCard() in HomeScreen.
51. Modify the view model to assign Success with one photo to UiState.
52. Utilize AsyncImage parameters error and placeholder for images to show when there is an error loading and a default placeholder to show during loading.
53. Create a LazyVerticalGrid in a new Composable function; PhotoGridScreen().
54. Add Card Composable to fix padding of photo card.
55. Resolve top padding issue by passing calculateTopPadding() value to PhotoGridScreen, apply this modifier only to LazyVerticalGrid(), and pass Modifier + card settings to AmphibianPhotoCard().
56. Re-factor ErrorScreen to provide "Retry" feature.
57. Add remaining text() data to each Card()
    *learned that Modifier.aspectRatio(1.5f) passed to the card messes up Card() height calculations when there are other Composable components in the Card.
