import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand

object Build : BuildType({
    name = "Build"
    description = "test"
    artifactRules = "target/*jar"

    vcs {
        root(DslContext.settingsRoot)
    }
    steps {
        maven {
            goals = "clean package"
            mavenVersion = auto()
        }
        dockerCommand {
            name = "Build Image"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "app_test"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
    }
})