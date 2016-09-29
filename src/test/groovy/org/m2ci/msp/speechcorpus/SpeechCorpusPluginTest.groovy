package org.m2ci.msp.speechcorpus

import org.testng.annotations.*

import org.gradle.testfixtures.ProjectBuilder

class SpeechCorpusPluginTest {

    def project

    @BeforeMethod
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'org.m2ci.msp.speech-corpus'
    }

    @Test
    void canApplyPlugin() {
        assert project.pluginManager.hasPlugin('org.m2ci.msp.speech-corpus')
    }

    @Test
    void testExtension() {
        assert project.extensions.findByName('speechCorpus')
        assert project.speechCorpus.yamlFile.name == "${project.name}.yaml"
        assert project.speechCorpus.yamlFile.name == "${project.name}.yaml"
        def yamlFileName = 'foobarbaz.yaml'
        def flacFileName = 'foobarbaz.flac'
        project.speechCorpus {
            yamlFile = yamlFileName
            flacFile = flacFileName
        }
        assert project.speechCorpus.yamlFile.name == yamlFileName
        assert project.speechCorpus.flacFile.name == flacFileName
    }

    @Test
    void testTaskProps() {
        def task = project.tasks.findByName('extractWav')
        assert task.yamlFile == project.speechCorpus.yamlFile
        assert task.flacFile == project.speechCorpus.flacFile
    }
}
