import org.gradle.jvm.tasks.Jar
import java.net.URI

val servletApiVersion: String by project
val commonsIoVersion: String by project
val projectVersion: String by project
val groupPackage: String by project
val junitJupiterVersion: String by project
val _kotlinVersion: String by project

plugins {
    signing
    jacoco
    kotlin("jvm") version "1.8.10"
    id("java")
    id("maven-publish")
    id("com.github.ben-manes.versions") version "0.46.0"
    id("com.github.hierynomus.license") version "0.16.1"
    id("org.jetbrains.dokka") version "1.8.10"
}

group = groupPackage
version = projectVersion
description = rootProject.name

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("javax.servlet:javax.servlet-api:$servletApiVersion")
    implementation("commons-io:commons-io:$commonsIoVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
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
            artifact(dokkaJavadocJar)

            pom {
                name.set(rootProject.name)
                url.set("https://github.com/astrapi69/" + rootProject.name)
                description.set("Extension methods for servlets, requests and response objects")
                organization {
                    name.set("Alpha Ro Group UG (haftungsbeschrängt)")
                    url.set("http://www.alpharogroup.de/")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/astrapi69/" + rootProject.name + "/issues")
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
                    connection.set("scm:git:git:@github.com:astrapi69/" + rootProject.name + ".git")
                    developerConnection.set("scm:git:git@github.com:astrapi69/" + rootProject.name + ".git")
                    url.set("git:@github.com:astrapi69/" + rootProject.name + ".git")
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
                    val urlString = if (projectVersion.endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                    url = URI.create(urlString)
                }
            }
        }
    }
}

license {
    ext.set("year", "2015")
    ext.set("owner", "Asterios Raptis")
    header = rootProject.file("src/main/resources/LICENSE.txt")
    val value = listOf(
        "**/README",
        "**/README.md",
        "**/LICENSE",
        "**/NOTICE",
        "**/*.xml",
        "**/*.xsl",
        "**/*.xsd",
        "**/*.dtd",
        "**/*.html",
        "**/*.json",
        "**/*.jsp",
        "**/*.jpa",
        "**/*.sql",
        "**/*.properties",
        "**/*.bat",
        "**/*.gradle",
        "**/*.MF",
        "**/*.txt",
        "**/*.vm",
        "**/*.log",
        "**/*.map",
        "**/*.js.map",
        "**/*.tmpl",
        "**/*.js.tmpl",
        "**/*.editorconfig",
        "src/test/resources/**",
        "src/main/resources/**",
        "out/**",
        "build/**"
    )
    excludes(value)
}

// workhack for license issue #76
gradle.startParameter.excludedTaskNames += "licenseMain"
gradle.startParameter.excludedTaskNames += "licenseTest"

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

tasks.test {
    useTestNG()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(file("$buildDir/reports/coverage"))
    }
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            // exclude main()
            exclude("")
        }
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.0".toBigDecimal()
            }
        }
    }
}

val testCoverage by tasks.registering {
    group = "verification"
    description = "Runs the unit tests with coverage."

    dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
    val jacocoTestReport = tasks.findByName("jacocoTestReport")
    jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
    tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}
