package org.m2ci.msp.speechcorpus

import org.gradle.api.Project

class SpeechCorpusExtension {

    Project project

    File yamlFile
    File flacFile

    SpeechCorpusExtension(Project project) {
        this.project = project
    }

    void setYamlFile(String file) {
        this.yamlFile = project.file(file)
    }

    void setFlacFile(String file) {
        this.flacFile = project.file(file)
    }
}
