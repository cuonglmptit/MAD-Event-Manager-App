plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mad"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mad"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    // https://mvnrepository.com/artifact/com.android.volley/volley
    implementation("com.android.volley:volley:1.2.1")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.firebase.messaging)
    // https://mvnrepository.com/artifact/androidx.swiperefreshlayout/swiperefreshlayout
    runtimeOnly("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
    implementation("com.github.bumptech.glide:glide:4.11.0")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.google.firebase:firebase-messaging")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")
}