package org.m2ci.msp.speechcorpus

import org.gradle.api.Project

class SpeechCorpusExtension {

    def project

    def yamlFile
    def flacFile

    SpeechCorpusExtension(Project project) {
        this.project = project
    }
}
