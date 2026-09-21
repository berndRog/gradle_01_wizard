// Root build script.
//
// This project still contains only the wizard-generated app module on the
// master branch. Plugin versions are declared in the version catalog and made
// available here without applying them to the root project itself.
plugins {
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.kotlin.compose) apply false

   // These plugins are required by later course examples. Declaring them here
   // keeps their versions central while individual modules decide whether they
   // actually apply them.
   alias(libs.plugins.kotlin.serialization) apply false
   alias(libs.plugins.google.devtools.ksp) apply false
}
