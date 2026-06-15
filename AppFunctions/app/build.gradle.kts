// =============================================================================
// App module — plugins
// =============================================================================

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

// =============================================================================
// Android configuration
// =============================================================================

android {
    namespace = "com.sohaib.appfunctions"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.sohaib.appfunctions"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    // KSP-generated AppFunctions metadata (AGP 9 does not auto-merge these assets yet)
    sourceSets {
        getByName("debug") {
            assets.directories.add("build/generated/ksp/debug/resources/assets")
        }
        getByName("release") {
            assets.directories.add("build/generated/ksp/release/resources/assets")
        }
    }
}

// =============================================================================
// KSP — annotation processors
// =============================================================================

ksp {
    // Merges all @AppFunction declarations into one schema for the OS
    arg("appfunctions:aggregateAppFunctions", "true")
}

// Ensure KSP runs before asset merge so app_functions_v2.xml is packaged
tasks.matching { it.name.startsWith("merge") && it.name.endsWith("Assets") }.configureEach {
    dependsOn(tasks.matching { it.name.startsWith("ksp") && it.name.endsWith("Kotlin") })
}

// =============================================================================
// Dependencies
// =============================================================================

dependencies {
    // --- AndroidX Core ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // --- Lifecycle ---
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // --- Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // --- Navigation ---
    implementation(libs.androidx.navigation.compose)

    // --- Room ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // --- Dependency injection ---
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // --- Coroutines ---
    implementation(libs.kotlinx.coroutines.android)

    // --- App Functions (AI / agents) ---
    implementation(libs.androidx.appfunctions)
    implementation(libs.androidx.appfunctions.service)

    // --- KSP processors ---
    ksp(libs.androidx.room.compiler)
    ksp(libs.androidx.appfunctions.compiler)

    // --- Unit tests ---
    testImplementation(libs.junit)

    // --- Instrumented tests ---
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // --- Debug-only ---
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}