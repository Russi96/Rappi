plugins {
    id ("com.android.library")
    id ("kotlin-android")
}

android {
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles ("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation( "org.mockito:mockito-core:4.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("io.mockk:mockk:1.12.0")

    implementation (project(":data"))
    implementation (project(":domain"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
}