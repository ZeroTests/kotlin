object Build : BuildType({
    name = "Build"
    artifactRules = "target/*jar"

    vcs {
        root(DslContext.settingsRoot)
    }
    steps {
        maven {
            goals = "clean package"
            mavenVersion = auto()
        }
    }
})