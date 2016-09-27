package org.m2ci.msp.speechcorpus.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.*

class ExtractText extends DefaultTask {

    @OutputDirectory
    def destDir = project.file("$project.buildDir/text")

    @TaskAction
    def extract() {
        def options = new DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        def yaml = new Yaml(options)
        yaml.load(project.findProperty('yamlFile').newReader()).each { utterance ->
            project.file("$destDir/${utterance.prompt}.txt").withWriter { writer ->
                writer.println utterance.text
            }
        }
    }
}
