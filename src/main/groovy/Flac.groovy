import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.*

class Flac extends DefaultTask {

    @TaskAction
    def concat() {
        def listFile = project.file("$project.buildDir/wav.txt")
        listFile.withWriter { writer ->
            def options = new DumperOptions()
            options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
            def yaml = new Yaml(options)
            yaml.load(project.findProperty('yamlFile').newReader()).each {
                writer.println "file 'wav/${it.prompt}.wav'"
            }
        }
        project.exec {
            commandLine 'ffmpeg', '-f', 'concat', '-i', listFile, '-acodec', 'flac', '-compression_level', 12, project.findProperty('flacFile')
        }
    }
}
