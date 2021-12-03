import Variables.API_KEY

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    /*
    signingConfigs {
        getByName("debug") {
            storeFile = file("D:\\Rappi\\RappiTv.jks")
            storePassword = "V58DjNF&Cqdy"
            keyPassword = "V58DjNF&Cqdy"
            keyAlias = "Rappi_TV"
        }
    }
    
     */


    compileSdk = Configs.compileSdk



    defaultConfig {
        applicationId = Configs.applicationId
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"${API_KEY}\"")
        }
        getByName("debug") {
            buildConfigField("String", "API_KEY", "\"${API_KEY}\"")
            isDebuggable = true
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}



dependencies {

    implementation(fileTree("libs") { include(listOf("*.jar")) })

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    //implementation modules
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usescases"))
    implementation(project(":framework:requestmanager"))
    implementation(project(":framework:imagemanager"))

    // Dagger -Hilt
    implementation("com.google.dagger:dagger:2.39.1")
    kapt("com.google.dagger:dagger-compiler:2.39.1")
    implementation("com.google.dagger:hilt-android:2.39.1")
    kapt("com.google.dagger:hilt-compiler:2.39.1")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.39.1")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.39.1")
    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.39.1")
    kaptTest("com.google.dagger:hilt-compiler:2.39.1")

    // Lifecycle Components
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    // Lifecycles only (without ViewModel or LiveData)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    // Kotlin
    implementation("androidx.activity:activity-ktx:1.4.0")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.5")

    //LottiFiles
    implementation("com.airbnb.android:lottie:4.2.0")

    // Shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")
    implementation ("com.todkars:shimmer-recyclerview:0.4.1")

}