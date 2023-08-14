plugins {
    id("java")
    `maven-publish`
}

group = "com.github.peco2282"
version = ext.get("project_version").toString()

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}
val lombokVersion = ext.get("lombok_version")
val jetbrainsAnnotationsVersion = ext.get("jetbrains_annotations_version")
val lang3Version = ext.get("lang3_version")

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")

    // https://mvnrepository.com/artifact/org.jetbrains/annotations
    implementation("org.jetbrains:annotations:${jetbrainsAnnotationsVersion}")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:${lang3Version}")

}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mathhelper") {
            group = project.group
            artifactId = "mathhelper"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
