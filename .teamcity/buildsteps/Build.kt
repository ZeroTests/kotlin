import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

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
    changeBuildType(RelativeId("Build")) {
        expectSteps {
            maven {
                goals = "clean package"
                mavenVersion = auto()
            }
        }
        steps {
            insert(1) {
                dockerCommand {
                    name = "Build Image"
                    commandType = build {
                        source = file {
                            path = "Dockerfile"
                        }
                        commandArgs = "--pull"
                    }
                    param("dockerImage.platform", "linux")
                }
            }
        }
    }
})