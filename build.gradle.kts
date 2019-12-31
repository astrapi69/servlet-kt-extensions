import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.gradle.jvm.tasks.Jar
import java.net.URI

val servletApiVersion = "3.1.0"
val commonsIoVersion = "2.6"

plugins {
    signing
    kotlin("jvm") version "1.3.61"
    id("java")
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.9.17"
}

group = "de.alpharogroup"
version = "1.2-SNAPSHOT"
description = "servlet-kt-extensions"


repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

// Configure existing Dokka task to output HTML to typical Javadoc directory
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

// Create dokka Jar task from dokka task output
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    // dependsOn(tasks.dokka) not needed; dependency automatically inferred by from(tasks.dokka)
    from(tasks.dokka)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.servlet:javax.servlet-api:$servletApiVersion")
    implementation("commons-io:commons-io:$commonsIoVersion")

}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(dokkaJar)

            pom {
                name.set(rootProject.name)
                url.set("https://github.com/astrapi69/"+ rootProject.name)
                description.set("Extension methods for servlets, requests and response objects")
                organization {
                    name.set("Alpha Ro Group UG (haftungsbeschrängt)")
                    url.set("http://www.alpharogroup.de/")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/astrapi69/"+ rootProject.name +"/issues")
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("astrapi69")
                        name.set("Asterios Raptis")
                    }
                }
                scm {
                    connection.set("scm:git:git:@github.com:astrapi69/"+ rootProject.name +".git")
                    developerConnection.set("scm:git:git@github.com:astrapi69/"+ rootProject.name +".git")
                    url.set("git:@github.com:astrapi69/"+ rootProject.name +".git")
                }
            }

            repositories {
                maven {
                    credentials {
                        val usernameString = System.getenv("ossrhUsername")
                            ?: project.property("ossrhUsername")
                        val passwordString = System.getenv("ossrhPassword")
                            ?: project.property("ossrhPassword")
                        username = usernameString.toString()
                        password = passwordString.toString()
                    }
                    val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots"
                    val projectVersion = version.toString()
                    val urlString = if(projectVersion.endsWith("SNAPSHOT"))  snapshotsRepoUrl else releasesRepoUrl
                    url = URI.create(urlString)
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}