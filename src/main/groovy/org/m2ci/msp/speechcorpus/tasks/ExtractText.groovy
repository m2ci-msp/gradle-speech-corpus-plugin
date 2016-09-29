package org.m2ci.msp.speechcorpus.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.Yaml

class ExtractText extends DefaultTask {

    @InputFile
    File yamlFile

    @OutputDirectory
    File destDir = project.file("$project.buildDir/text")

    ExtractText() {
        project.afterEvaluate {
            yamlFile = yamlFile ?: project.speechCorpus.yamlFile
        }
    }

    @TaskAction
    void extract() {
        def yaml = new Yaml()
        yaml.load(yamlFile.newReader()).each { utterance ->
            project.file("$destDir/${utterance.prompt}.txt").withWriter { writer ->
                writer.println utterance.text
            }
        }
    }
}
