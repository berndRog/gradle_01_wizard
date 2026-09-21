plugins {
   // Builds this module as an installable Android application.
   alias(libs.plugins.android.application)

   // Enables the Compose compiler for Kotlin source files.
   alias(libs.plugins.kotlin.compose)

   // Used by later examples with kotlinx.serialization and Retrofit.
   alias(libs.plugins.kotlin.serialization)

   // Generates Room implementations during compilation.
   alias(libs.plugins.google.devtools.ksp)
}

android {
   namespace = "de.rogallab.mobile"

   // Compile against the Android 37 SDK APIs.
   compileSdk {
      version = release(37)
   }

   defaultConfig {
      applicationId = "de.rogallab.mobile"
      minSdk = 26
      targetSdk = 37

      versionCode = 1
      versionName = "1.0"

      // Runner used for tests executed on an Android device or emulator.
      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
   }

   testOptions {
      // Disabling animations makes instrumented UI tests more deterministic.
      animationsDisabled = true

      // Robolectric tests need access to Android resources on the local JVM.
      unitTests.isIncludeAndroidResources = true
   }

   buildTypes {
      release {
         // Keep the course examples easy to inspect and debug.
         optimization {
            enable = false
         }
      }
   }

   compileOptions {
      sourceCompatibility = JavaVersion.VERSION_21
      targetCompatibility = JavaVersion.VERSION_21
   }

   buildFeatures {
      // Enables Jetpack Compose for this application module.
      compose = true
   }
}

dependencies {
   // -------------------------------------------------------------------------
   // Kotlin and Android core
   // -------------------------------------------------------------------------
   implementation(libs.androidx.core.ktx)
   implementation(libs.kotlinx.coroutines.core)
   implementation(libs.kotlinx.coroutines.android)
   implementation(libs.kotlinx.datetime)
   implementation(libs.kotlinx.serialization.json)

   // -------------------------------------------------------------------------
   // Activity and Jetpack Compose
   // -------------------------------------------------------------------------

   // The BOM selects mutually compatible versions for all Compose libraries.
   implementation(platform(libs.androidx.compose.bom))
   testImplementation(platform(libs.androidx.compose.bom))
   androidTestImplementation(platform(libs.androidx.compose.bom))

   implementation(libs.androidx.activity.compose)
   implementation(libs.androidx.compose.foundation.layout)
   implementation(libs.androidx.ui)
   implementation(libs.androidx.ui.graphics)
   implementation(libs.androidx.ui.tooling.preview)
   implementation(libs.androidx.animation)
   implementation(libs.androidx.material3)
   implementation(libs.androidx.material.icons.extended)

   // -------------------------------------------------------------------------
   // Lifecycle, ViewModel, and Navigation 3
   // -------------------------------------------------------------------------
   implementation(libs.androidx.lifecycle.runtime.ktx)
   implementation(libs.androidx.lifecycle.runtime.compose)
   implementation(libs.androidx.lifecycle.viewmodel.compose)
   implementation(libs.androidx.lifecycle.viewmodel.navigation3)
   implementation(libs.androidx.navigation3.runtime)
   implementation(libs.androidx.navigation3.ui)

   // -------------------------------------------------------------------------
   // Room 3 and SQLite
   // -------------------------------------------------------------------------
   implementation(libs.androidx.room3.runtime)
   implementation(libs.androidx.sqlite.bundled)
   ksp(libs.androidx.room3.compiler)

   // -------------------------------------------------------------------------
   // Images and media
   // -------------------------------------------------------------------------
   implementation(libs.coil.compose)
   implementation(libs.coil.network.okhttp)
   implementation(libs.androidx.media3.exoplayer)

   // -------------------------------------------------------------------------
   // Dependency injection with Koin
   // -------------------------------------------------------------------------
   implementation(libs.koin.core)
   implementation(libs.koin.android)
   implementation(libs.koin.androidx.compose)

   // -------------------------------------------------------------------------
   // Networking with Retrofit
   // -------------------------------------------------------------------------
   implementation(libs.gson.json)
   implementation(libs.retrofit2.core)
   implementation(libs.retrofit2.gson)
   implementation(libs.retrofit2.kotlinx.serialization)
   implementation(libs.retrofit2.logging)

   // -------------------------------------------------------------------------
   // Google Play Services
   // -------------------------------------------------------------------------
   implementation(libs.gplay.location)

   // -------------------------------------------------------------------------
   // Local JVM tests
   // -------------------------------------------------------------------------
   testImplementation(libs.junit)
   testImplementation(libs.androidx.test.core)
   testImplementation(libs.androidx.test.core.ktx)
   testImplementation(libs.koin.test)
   testImplementation(libs.koin.test.junit4)
   testImplementation(libs.kotlinx.coroutines.test)
   testImplementation(libs.turbine.test)
   testImplementation(libs.robolectric.test)

   // -------------------------------------------------------------------------
   // Instrumented Android and Compose tests
   // -------------------------------------------------------------------------
   androidTestImplementation(libs.kotlinx.coroutines.test)
   androidTestImplementation(libs.androidx.test.core)
   androidTestImplementation(libs.androidx.test.core.ktx)
   androidTestImplementation(libs.androidx.test.ext.junit)
   androidTestImplementation(libs.androidx.test.ext.junit.ktx)
   androidTestImplementation(libs.androidx.test.ext.truth)
   androidTestImplementation(libs.androidx.test.runner)
   androidTestImplementation(libs.androidx.ui.test.junit4)
   androidTestImplementation(libs.androidx.test.espresso.core)
   androidTestImplementation(libs.koin.test)
   androidTestImplementation(libs.koin.test.junit4)
   androidTestImplementation(libs.koin.androidx.compose)
   androidTestImplementation(libs.mockito.core)
   androidTestImplementation(libs.mockito.android)
   androidTestImplementation(libs.mockito.kotlin)

   // -------------------------------------------------------------------------
   // Debug-only tooling
   // -------------------------------------------------------------------------
   debugImplementation(libs.androidx.ui.tooling)
   debugImplementation(libs.androidx.ui.test.manifest)
}
