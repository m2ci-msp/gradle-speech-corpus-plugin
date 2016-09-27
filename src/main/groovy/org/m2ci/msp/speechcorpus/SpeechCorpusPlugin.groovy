package org.m2ci.msp.speechcorpus

import org.gradle.api.Plugin
import org.gradle.api.Project

import org.m2ci.msp.speechcorpus.tasks.*

class SpeechCorpusPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('flac', type: Flac)

        project.task('extractText', type: ExtractText)

        project.task('extractLab', type: ExtractLab)

        project.task('extractWav', type: ExtractWav)
    }
}
