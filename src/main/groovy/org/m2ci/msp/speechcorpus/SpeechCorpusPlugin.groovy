package org.m2ci.msp.speechcorpus

import org.gradle.api.*
import org.gradle.api.plugins.BasePlugin

import org.m2ci.msp.speechcorpus.tasks.*

class SpeechCorpusPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.pluginManager.apply BasePlugin

        project.extensions.create 'speechCorpus', SpeechCorpusExtension, project
        project.speechCorpus {
            yamlFile = project.file("${project.name}.yaml")
            flacFile = project.file("${project.name}.flac")
        }

        project.task('extractText', type: ExtractText)

        project.task('extractTextGrid', type: ExtractTextGrid)

        project.task('extractLab', type: ExtractLab)

        project.task('extractWav', type: ExtractWav)
    }
}
