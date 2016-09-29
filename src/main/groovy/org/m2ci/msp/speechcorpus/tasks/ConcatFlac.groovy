package org.m2ci.msp.speechcorpus.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import org.yaml.snakeyaml.*

class ConcatFlac extends DefaultTask {

    @Optional
    @InputDirectory
    File srcDir = project.projectDir

    @OutputFile
    File destFile = project.file("$project.buildDir/${project.name}.flac")

    void setSrcDir(String dir) {
        def srcDir = project.file(dir)
        assert srcDir.isDirectory()
        this.srcDir = srcDir
    }

    void setDestFile(String file) {
        def destFile = project.file(file)
        this.destFile = destFile
    }

    @TaskAction
    def concat() {
        def listFile = new File(temporaryDir, 'wav.txt')
        listFile.withWriter { writer ->
            def yaml = new Yaml()
            yaml.load(project.speechCorpus.yamlFile.newReader()).each {
                writer.println "file '$srcDir/${it.prompt}.wav'"
            }
        }
        project.exec {
            commandLine 'ffmpeg', '-f', 'concat', '-safe', 0, '-i', listFile, '-acodec', 'flac', '-compression_level', 12, destFile
        }
    }
}
