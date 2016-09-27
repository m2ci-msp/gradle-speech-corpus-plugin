import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.*

class ExtractWav extends DefaultTask {

    @OutputDirectory
    def destDir = project.file("$project.buildDir/wav")

    @TaskAction
    def extract() {
        def options = new DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        def yaml = new Yaml(options)
        yaml.load(project.findProperty('yamlFile').newReader()).each { utterance ->
            project.exec {
                commandLine 'sox', project.findProperty('flacFile'), "$destDir/${utterance.prompt}.wav", 'trim', utterance.start, "=$utterance.end"
            }
        }
    }
}
