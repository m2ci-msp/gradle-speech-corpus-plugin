package org.m2ci.msp.speechcorpus.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.*

class ExtractLab extends DefaultTask {

    @OutputDirectory
    def destDir = project.file("$project.buildDir/lab")

    @TaskAction
    def extract() {
        def options = new DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        def yaml = new Yaml(options)
        yaml.load(project.findProperty('yamlFile').newReader()).each { utterance ->
            if (utterance.segments) {
                project.file("$destDir/${utterance.prompt}.lab").withWriter { writer ->
                    writer.println '#'
                    def end = 0.0
                    utterance.segments.each { segment ->
                        end += segment.dur
                        writer.println sprintf('%.6f 125 %s', end, segment.lab)
                    }
                }
            }
        }
    }
}
