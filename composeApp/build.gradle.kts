import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleServices)
    id("app.cash.sqldelight").version("2.0.1")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0")
            export("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            export("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            export(libs.essenty.backHandler)
        }
    }


    sourceSets {
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:30.0.1"))
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
            api("dev.icerock.moko:resources:0.23.0")
            api("dev.icerock.moko:resources-compose:0.23.0")
            implementation("io.coil-kt.coil3:coil-core:3.0.0-alpha06")
            implementation("io.coil-kt.coil3:coil-compose-core:3.0.0-alpha06")
            implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0-alpha06")
            implementation("io.github.alexzhirkevich:cupertino:0.1.0-alpha03")
            implementation("io.github.alexzhirkevich:cupertino-decompose:0.1.0-alpha03")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            implementation("dev.gitlive:firebase-firestore:1.12.0")
            implementation("dev.gitlive:firebase-common:1.12.0")
            implementation("dev.icerock.moko:mvvm-core:0.16.1")
            implementation("dev.icerock.moko:mvvm-compose:0.16.1")

            ///// Coroutines /////
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")

            ///// KOIN /////
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.3"))
            implementation("io.insert-koin:koin-core")
            implementation("io.insert-koin:koin-compose")

            //// DECOMPOSE NAVIGATION LIBRARY ////
            implementation(libs.decompose.decompose)
            implementation(libs.decompose.extensionsComposeJetbrains)
            implementation("com.arkivanov.essenty:lifecycle-coroutines:2.1.0")
            implementation(libs.kotlinx.serialization.json)

            //// CASHAPP MULTIPLATFORM PAGING ////
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.5.1")

            // SQL DELIGHT
            implementation("app.cash.sqldelight:runtime:2.0.1")

            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

            // KOTLIN MULTIPLATFORM NETWORK CONNECTION
            implementation("dev.tmapps:konnection:1.3.0")

            // Constraint Layout
            implementation("tech.annexflow.compose:constraintlayout-compose-multiplatform:0.4.0")

            // Zoom Image Library
            implementation("io.github.panpf.zoomimage:zoomimage-compose-sketch:1.1.0-beta01")

            // Key-Value Storage Library
            api(libs.datastore.preferences)
            api(libs.datastore)


        }

        val iosX64Main by getting {
            resources.srcDirs("build/generated/moko/iosX64Main/src")
        }
        val iosArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosArm64Main/src")
        }
        val iosSimulatorArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
        }

        val iosMain by creating {
            dependsOn(commonMain.get())
            this.dependencies {
                implementation(libs.ktor.client.darwin)
                api("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
                api("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }



    }
}

android {
    namespace = "com.ramaas.iguhallee"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.ramaas.iguhallee"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation("com.google.firebase:firebase-common-ktx:20.3.3")
        /////// KOIN ///////
        implementation(platform("io.insert-koin:koin-bom:3.5.3"))
        implementation("io.insert-koin:koin-core")
        implementation("io.insert-koin:koin-android")
    }
}
dependencies {
    implementation(libs.androidx.constraintlayout)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.ramaas.iguhallee")
            generateAsync.set(true)
        }
        linkSqlite.set(true)
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.ramaas.iguhallee" // required
    multiplatformResourcesClassName = "MR"
}


