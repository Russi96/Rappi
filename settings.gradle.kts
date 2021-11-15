dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "RappiTV"
include (":app")
include (":data")
include (":domain")
include (":usescases")
include(":framework:requestmanager")
include(":framework:imagemanager")
