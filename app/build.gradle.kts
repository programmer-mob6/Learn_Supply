plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "com.example.learnsupply"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.learnsupply"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlin {
        jvmToolchain(17)
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)
    implementation(libs.ui)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)

    // viewmodel
    implementation(libs.androidx.navigation.compose)

    // API Services
    implementation(libs.api.services)

    // TS Component
    implementation(libs.ts.components)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.truth.java8.extension)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

}

koverReport {
    val excludePackages = listOf(
        "dagger.hilt.internal.aggregatedroot.codegen.*",
        "hilt_aggregated_deps.*",
        "com.example.learnsupply.*.di.*",
        "com.example.learnsupply.*.Hilt_*",
        "com.example.learnsupply.*.*_Factory*",
        "com.example.learnsupply.*.*_HiltModules*",
        "com.example.learnsupply.*.*Module_*",
        "com.example.learnsupply.*.*MembersInjector*",
        "com.example.learnsupply.*.*_Impl*",
        "com.example.learnsupply.ComposableSingletons*",
        "com.example.learnsupply.BuildConfig*",
        "com.example.learnsupply.*.Fake*",
        "com.example.learnsupply.app.ComposableSingletons*",
        "*_*Factory.*",
        "*_*Factory*",
        "*_Factory.*",
        "Hilt_*",
        "*_Hilt*",
        "*.navigation.*"
    )

    val includePackages = listOf(
        "com.example.learnsupply.data.*",
        "com.example.learnsupply.domain*",
        "com.example.learnsupply.ui.*.viewmodel",
        "com.example.learnsupply.ui.*.uistate",
        "com.example.learnsupply.ui.*.model",
    )

    filters {
        excludes {
            classes(
                "dagger.hilt.internal.aggregatedroot.codegen.*",
                "hilt_aggregated_deps.*",
                "com.example.learnsupply.*.di.*",
                "com.example.learnsupply.*.Hilt_*",
                "com.example.learnsupply.*.*_Factory*",
                "com.example.learnsupply.*.*_HiltModules*",
                "com.example.learnsupply.*.*Module_*",
                "com.example.learnsupply.*.*MembersInjector*",
                "com.example.learnsupply.*.*_Impl*",
                "com.example.learnsupply.ComposableSingletons*",
                "com.example.learnsupply.BuildConfig*",
                "com.example.learnsupply.*.Fake*",
                "com.example.learnsupply.app.ComposableSingletons*"
            )

            packages(
                "kotlinx.coroutines.*"
            )
        }
    }

    androidReports("debug") {
        xml {
            onCheck = true

            setReportFile(file("result.xml"))

            filters {
                excludes {
                    classes(
                        excludePackages
                    )

                    packages(
                        "kotlinx.coroutines.*"
                    )
                }

                includes {
                    packages(
                        includePackages
                    )
                }
            }
        }
        html {
            title = "Kover Report"

            charset = "UTF-8"

            onCheck = true

            filters {
                excludes {
                    classes(
                        excludePackages
                    )

                    packages(
                        "kotlinx.coroutines.*"
                    )
                }

                includes {
                    packages(
                        includePackages
                    )
                }
            }
        }
    }
}