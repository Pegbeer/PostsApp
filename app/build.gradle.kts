plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 32
    namespace = "com.pegbeer.PostApp"

    defaultConfig {
        applicationId = "com.pegbeer.postsapp"
        minSdk = 22
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    val core_ktx_version = "1.8.0"
    val app_compat_version = "1.5.1"
    val material_version = "1.7.0"
    val koin_version = "3.3.2"

    implementation("androidx.core:core-ktx:$core_ktx_version")
    implementation("androidx.appcompat:appcompat:$app_compat_version")
    implementation("com.google.android.material:material:$material_version")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Dependency Injection
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}

kapt{
    correctErrorTypes = true
}