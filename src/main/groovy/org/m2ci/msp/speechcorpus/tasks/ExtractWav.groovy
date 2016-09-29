package org.m2ci.msp.speechcorpus.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.Yaml

class ExtractWav extends DefaultTask {

    @InputFile
    File yamlFile

    @InputFile
    File flacFile

    @OutputDirectory
    File destDir = project.file("$project.buildDir/wav")

    ExtractWav() {
        project.afterEvaluate {
            yamlFile = yamlFile ?: project.speechCorpus.yamlFile
            flacFile = flacFile ?: project.speechCorpus.flacFile
        }
    }

    @TaskAction
    void extract() {
        def yaml = new Yaml()
        yaml.load(yamlFile.newReader()).each { utterance ->
            project.exec {
                commandLine 'sox', flacFile, "$destDir/${utterance.prompt}.wav", 'trim', utterance.start, "=$utterance.end"
            }
        }
    }
}
