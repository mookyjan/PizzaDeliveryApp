package mudassir.dependencies


object App {
    const val applicationId = "com.mudassir.giphyapi"
    const val compileSdk = 33
    const val minSdk = 21
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
}
object Dependencies {
    // Core
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val activity_ktx = "androidx.activity:activity-ktx:${Versions.activity_ktx}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val android_annotation = "androidx.annotation:annotation:${Versions.android_annotation}"
    const val livedata_core = "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.liveData}"

    //Navigation
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Networking
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    // Coroutines
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    //Moshi
    const val moshi ="com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshi_adapter= "com.serjltt.moshi:moshi-lazy-adapters:${Versions.moshi_adapter}"

    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_android_processor =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val javax_annotation = "org.glassfish:javax.annotation:${Versions.javax}"

    // Lifecycle
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifecycle}"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycle_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glide_okhttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

    //Room
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler ="androidx.room:room-compiler:${Versions.room}"

    //Pagination
    const val pagination ="androidx.paging:paging-runtime:${Versions.paging}"
    const val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}"
}

object TestDependencies {
    // Core library
    const val test_core = "androidx.test:core:${Versions.test_core}"
    const val arch_core = "androidx.arch.core:core-testing:${Versions.arch_core}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines_core}"

    // AndroidJUnitRunner and JUnit Rules
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val rules = "androidx.test:rules:${Versions.rules}"

    // Assertions
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val truth_ext = "androidx.test.ext:truth:${Versions.truth_ext}"

    // Espresso dependencies
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    // Third party
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
}